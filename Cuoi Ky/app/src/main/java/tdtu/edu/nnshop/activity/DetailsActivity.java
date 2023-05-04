package tdtu.edu.nnshop.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import tdtu.edu.nnshop.R;
import tdtu.edu.nnshop.model.Product;
import tdtu.edu.nnshop.ui.cart.CartFragment;

public class DetailsActivity extends AppCompatActivity {

    ImageView product_img;
    TextView product_name, product_price, product_description, product_quantity, product_color;
    EditText input_quantity;
    Button btn_add_to_cart;

    Product product = null;

    FirebaseFirestore db;
    FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);


        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        product_img = findViewById(R.id.product_image);       
        product_name = findViewById(R.id.product_name);
        product_color = findViewById(R.id.product_color);
        product_price = findViewById(R.id.product_price);
        product_description = findViewById(R.id.product_description);
        product_quantity = findViewById(R.id.product_quantity);
        input_quantity = findViewById(R.id.quantity_input);
        btn_add_to_cart = findViewById(R.id.add_to_cart_button);
        final Object obj = getIntent().getSerializableExtra("product");
        if(obj instanceof Product){
            product = (Product) obj;
        }
        if(product != null){
            Glide.with(getApplicationContext()).load(product.getImg_url()).into(product_img);
            product_name.setText(product.getName());
            product_price.setText("Price: "+String.valueOf(product.getPrice())+"$");
            product_color.setText("Color: "+product.getColor());
            product_description.setText("Description: "+product.getDescription());
            product_quantity.setText("Quantity available: "+String.valueOf(product.getQuantity()));
            
        }


        //add product to cart
        btn_add_to_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                double total_quantity = Double.valueOf(input_quantity.getText().toString());
                double total_price = product.getPrice() * total_quantity;
                if(total_quantity > product.getQuantity()){
                    Toast.makeText(getApplicationContext(), "Quantity input must be less than quantity available", Toast.LENGTH_SHORT).show();
                } else if(total_quantity == 0){
                    Toast.makeText(getApplicationContext(), "Quantity input must be greater than 0", Toast.LENGTH_SHORT).show();
                } else {
                    String saveCurrentTime, saveCurrentDate;
                    Calendar calForDate = Calendar.getInstance();
                    SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
                    saveCurrentDate = currentDate.format(calForDate.getTime());

                    SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
                    saveCurrentTime = currentTime.format(calForDate.getTime());

                    final HashMap<String, Object> cartMap = new HashMap<>();
                    cartMap.put("product_img_url", product.getImg_url());
                    cartMap.put("product_name", product.getName());
                    cartMap.put("product_price", product.getPrice());
                    cartMap.put("product_color",product.getColor());
                    cartMap.put("current_date", saveCurrentDate);
                    cartMap.put("current_time", saveCurrentTime);
                    cartMap.put("total_price", total_price);
                    cartMap.put("product_quantity", total_quantity);


                    db.collection("Cart").document(auth.getCurrentUser().getUid())
                            .collection("User Cart").add(cartMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentReference> task) {
                                    Toast.makeText(getApplicationContext(), "Add to cart successfully", Toast.LENGTH_SHORT).show();
                                    finish();
                                    startActivity(new Intent(DetailsActivity.this, HomeActivity.class));
                                }
                            });
                }


            }
        });

    }
}