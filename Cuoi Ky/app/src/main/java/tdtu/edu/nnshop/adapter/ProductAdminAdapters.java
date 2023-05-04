package tdtu.edu.nnshop.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import tdtu.edu.nnshop.R;
import tdtu.edu.nnshop.activity.EditProductActivity;
import tdtu.edu.nnshop.model.Product;


public class ProductAdminAdapters extends RecyclerView.Adapter<ProductAdminAdapters.ViewHolder>{

    Context context;
    List<Product> productList;

    FirebaseAuth auth;
    FirebaseFirestore db;

    public ProductAdminAdapters(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
        auth= FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
    }

    @NonNull
    @Override
    public ProductAdminAdapters.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product_admin, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdminAdapters.ViewHolder holder, int position) {
        Glide.with(context).load(productList.get(position).getImg_url()).into(holder.productImage);
        holder.productName.setText(productList.get(position).getName());
        String price = String.valueOf(productList.get(position).getPrice());
        holder.productPrice.setText("Price: " + price + "$");
        holder.productColor.setText("Color: " + productList.get(position).getColor());

        holder.button_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Do you want to delete this product?")
                    .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            db.collection("Product").document(productList.get(position).getId()).delete();
                            String id = productList.get(position).getId();
                            productList.remove(position);
                            notifyItemRemoved(position);
                            notifyItemRangeChanged(position, productList.size());
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        holder.button_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EditProductActivity.class);
                intent.putExtra("product", productList.get(position));
                context.startActivity(intent);
            }
        });

    }


    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView productImage;
        TextView productName;
        TextView productPrice;
        TextView productColor;
        Button button_delete;
        Button button_edit;

        public ViewHolder(@NonNull View view){
            super(view);
            productImage = view.findViewById(R.id.img_url_product);
            productName = view.findViewById(R.id.textview_name);
            productPrice = view.findViewById(R.id.textview_price);
            productColor = view.findViewById(R.id.textview_color);
            button_delete = view.findViewById(R.id.btn_delete);
            button_edit = view.findViewById(R.id.btn_edit);
        }
    }
    
}
