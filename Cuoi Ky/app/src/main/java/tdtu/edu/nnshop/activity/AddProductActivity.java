package tdtu.edu.nnshop.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tdtu.edu.nnshop.R;
import tdtu.edu.nnshop.model.Product;

public class AddProductActivity extends AppCompatActivity {


    EditText edtName, edtPrice, edtDescription, edtColor, edtQuantity;
    Spinner spnCategory;
    Button btnAdd;
    ImageView imgProduct;
    FirebaseStorage storage;
    FirebaseFirestore db;
    Uri uriImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        storage = FirebaseStorage.getInstance();
        db = FirebaseFirestore.getInstance();
        
        edtName = findViewById(R.id.editTextProductName);
        edtPrice = findViewById(R.id.editTextPrice);
        edtDescription = findViewById(R.id.editTextDescription);
        edtColor = findViewById(R.id.editTextColor);
        edtQuantity = findViewById(R.id.editTextQuantity);
        spnCategory = findViewById(R.id.spinnerCategory);
        btnAdd = findViewById(R.id.buttonAddProduct);
        imgProduct = findViewById(R.id.imageView);
       

        imgProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //choose image
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 2);
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = edtName.getText().toString().trim();
                String price = edtPrice.getText().toString().trim();
                String description = edtDescription.getText().toString().trim();
                String color = edtColor.getText().toString().trim();
                String quantity = edtQuantity.getText().toString().trim();
                String category = spnCategory.getSelectedItem().toString().trim();

                if(name.isEmpty() || price.isEmpty() || description.isEmpty() || color.isEmpty() || quantity.isEmpty() || category.isEmpty()){
                    Toast.makeText(AddProductActivity.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
                }else{
                    //upload image
                    StorageReference storageReference = storage.getReference().child("ProductImage").child(name);

                    storageReference.putFile(uriImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) { //upload image success
                            storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) { //get image url
                                    //public Product(String name, String img_url, double price, String description, String category, String color, int quantity)
                                    int quantityInt = Integer.valueOf(quantity);
                                    double priceDouble = Double.valueOf(price);
                                    Product product = new Product(name, uri.toString(), priceDouble, description, category, color, quantityInt);
                                    Map<String, Object> productMap = new HashMap<>();
                                    productMap.put("name", name);
                                    productMap.put("img_url", uri.toString());
                                    productMap.put("price", priceDouble);
                                    productMap.put("description", description);
                                    productMap.put("category", category);
                                    productMap.put("color", color);
                                    productMap.put("quantity", quantityInt);

                                    db.collection("Product").add(productMap).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                        @Override
                                        public void onSuccess(DocumentReference documentReference) {
                                            Toast.makeText(AddProductActivity.this, "Add product successfully", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(AddProductActivity.this, AdminActivity.class));
                                            finish(); // Close the activity
                                        }
                                    });
                                    
                                }
                            });
                        }
                    });
                }


            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data!=null){
            uriImage=data.getData();
            imgProduct.setImageURI(uriImage);
            Toast.makeText(this, "Image selected", Toast.LENGTH_SHORT).show();
        }else
            Toast.makeText(this, "Please select an image", Toast.LENGTH_SHORT).show();

    }

}