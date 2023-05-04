package tdtu.edu.nnshop.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import tdtu.edu.nnshop.R;
import tdtu.edu.nnshop.model.Product;

public class EditProductActivity extends AppCompatActivity {
    private ImageView imgProduct;
    private EditText etName, etPrice, etColor, etQuantity, etDescription;

    private TextView tvCategory;
    private Button btnSave;

    private Product product;

    FirebaseFirestore db;
    FirebaseStorage storage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);
        // Find views in layout
        imgProduct = findViewById(R.id.img_product);
        etName = findViewById(R.id.et_name);
        etPrice = findViewById(R.id.et_price);
        etColor = findViewById(R.id.et_color);
        etQuantity = findViewById(R.id.et_quantity);
        etDescription = findViewById(R.id.et_description);
        tvCategory = findViewById(R.id.textview_category);
        btnSave = findViewById(R.id.btn_save);

        db = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();


        // Get product from intent
        final Object obj = getIntent().getSerializableExtra("product");
        if(obj instanceof Product){
            product = (Product) obj;
        }
        if (product != null) {
            // Set data to views
            Glide.with(getApplicationContext()).load(product.getImg_url()).into(imgProduct);
            etName.setText(product.getName());
            etPrice.setText(String.valueOf(product.getPrice()));
            etColor.setText(product.getColor());
            etQuantity.setText(String.valueOf(product.getQuantity()));
            etDescription.setText(product.getDescription());
            tvCategory.setText(product.getCategory());
        }
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = etName.getText().toString();
                double price = Double.parseDouble(etPrice.getText().toString());
                String color = etColor.getText().toString();
                int quantity = Integer.parseInt(etQuantity.getText().toString());
                String description = etDescription.getText().toString();
                db.collection("Product").document(product.getId()).update(
                        "name", name,
                        "price", price,
                        "color", color,
                        "quantity", quantity,
                        "description", description
                ).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // update product in product list
                        product.setName(name);
                        product.setPrice(price);
                        product.setColor(color);
                        product.setQuantity(quantity);
                        product.setDescription(description);
                        startActivity(new Intent(EditProductActivity.this, AdminActivity.class));
                        finish();
                    }
                });
            }
        });
        
    }
}