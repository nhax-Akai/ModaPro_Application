package tdtu.edu.nnshop.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import tdtu.edu.nnshop.R;
import tdtu.edu.nnshop.adapter.ProductAdapters;
import tdtu.edu.nnshop.model.Product;


public class ProductListActivity extends AppCompatActivity {

    FirebaseFirestore db;
    RecyclerView recyclerView;
    ProductAdapters productAdapter;
    List<Product> productList;
    ProgressBar progressBar;

    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_list);

        recyclerView = findViewById(R.id.all_product);
        recyclerView.setVisibility(View.GONE);
        db = FirebaseFirestore.getInstance();
        String category = getIntent().getStringExtra("category");
        
        productList = new ArrayList<>();
        productAdapter = new ProductAdapters(this, productList);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(productAdapter);
        title= findViewById(R.id.tv_title);
        title.setText("You selected: "+category);

        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(ProgressBar.VISIBLE);


        if(category != null && category.equalsIgnoreCase("dress")){
            db.collection("Product")
                    .whereEqualTo("category", "dress")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                Product product = document.toObject(Product.class);
                                productList.add(product);
                                productAdapter.notifyDataSetChanged();

                                progressBar.setVisibility(View.GONE);
                                recyclerView.setVisibility(View.VISIBLE);
                            }
                        }
                    });
        }
        if(category != null && category.equalsIgnoreCase("shoes")){
            db.collection("Product")
                    .whereEqualTo("category", "shoes")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                Product product = document.toObject(Product.class);
                                productList.add(product);
                                productAdapter.notifyDataSetChanged();
                                progressBar.setVisibility(View.GONE);
                                recyclerView.setVisibility(View.VISIBLE);

                            }
                        }
                    });
        }
        if(category != null && category.equalsIgnoreCase("high heels")){
            db.collection("Product")
                    .whereEqualTo("category", "high heels")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                Product product = document.toObject(Product.class);
                                productList.add(product);
                                productAdapter.notifyDataSetChanged();
                                progressBar.setVisibility(View.GONE);
                                recyclerView.setVisibility(View.VISIBLE);

                            }
                        }
                    });
        }
        if(category != null && category.equalsIgnoreCase("t-shirt")){
            db.collection("Product")
                    .whereEqualTo("category", "t-shirt")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                Product product = document.toObject(Product.class);
                                productList.add(product);
                                productAdapter.notifyDataSetChanged();
                                progressBar.setVisibility(View.GONE);
                                recyclerView.setVisibility(View.VISIBLE);

                            }
                        }
                    });
        }
        if(category != null && category.equalsIgnoreCase("hand bag")){
            db.collection("Product")
                    .whereEqualTo("category", "hand bag")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                Product product = document.toObject(Product.class);
                                productList.add(product);
                                productAdapter.notifyDataSetChanged();
                                progressBar.setVisibility(View.GONE);
                                recyclerView.setVisibility(View.VISIBLE);

                            }
                        }
                    });
        }
        if(category != null && category.equalsIgnoreCase("pants")){
            db.collection("Product")
                    .whereEqualTo("category", "pants")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                Product product = document.toObject(Product.class);
                                productList.add(product);
                                productAdapter.notifyDataSetChanged();
                                progressBar.setVisibility(View.GONE);
                                recyclerView.setVisibility(View.VISIBLE);

                            }
                        }
                    });
        }
    }


}