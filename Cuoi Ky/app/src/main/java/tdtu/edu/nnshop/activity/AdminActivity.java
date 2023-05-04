package tdtu.edu.nnshop.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import tdtu.edu.nnshop.R;
import tdtu.edu.nnshop.adapter.ProductAdminAdapters;
import tdtu.edu.nnshop.model.Product;

public class AdminActivity extends AppCompatActivity {


    FirebaseFirestore db;
    RecyclerView recyclerView;
    ProductAdminAdapters productAdminAdapter;
    List<Product> productList;

    Button btn_add,btn_logout;

    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        db=FirebaseFirestore.getInstance();
        recyclerView=findViewById(R.id.all_product);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        productList=new ArrayList<>();
        productAdminAdapter=new ProductAdminAdapters(this,productList);
        recyclerView.setAdapter(productAdminAdapter);

        progressBar=findViewById(R.id.progressBar);
        progressBar.setVisibility(ProgressBar.VISIBLE);


        db.collection("Product").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for(DocumentSnapshot document:task.getResult()){
                    String id= document.getId();
                    Product product=document.toObject(Product.class);
                    product.setId(id);
                    productList.add(product);
                    productAdminAdapter.notifyDataSetChanged();
                    progressBar.setVisibility(ProgressBar.GONE);
                }
            }
        });
        btn_add = findViewById(R.id.button_add);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminActivity.this, AddProductActivity.class));
            }
        });

        btn_logout = findViewById(R.id.button_logout);
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AdminActivity.this);

                builder.setTitle("Logout");
                builder.setMessage("Do you want to logout?");
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(AdminActivity.this, LoginActivity.class));
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

    }
}