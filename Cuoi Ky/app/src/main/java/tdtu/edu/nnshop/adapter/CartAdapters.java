package tdtu.edu.nnshop.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import tdtu.edu.nnshop.R;
import tdtu.edu.nnshop.model.Cart;

public class CartAdapters extends RecyclerView.Adapter<CartAdapters.ViewHolder>{

    private Context context;
    private List<Cart> cartList;

    FirebaseFirestore db;
    FirebaseAuth auth;

    public CartAdapters(Context context, List<Cart> cartList) {
        this.context = context;
        this.cartList = cartList;
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CartAdapters.ViewHolder(LayoutInflater.from(parent.getContext()).inflate( R.layout.item_cart, parent, false));
    }

    @Override

    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Glide.with(context).load(cartList.get(position).getProduct_img_url()).into(holder.productImage);
        holder.productName.setText("Name: " + cartList.get(position).getProduct_name());
        String price = String.valueOf(cartList.get(position).getProduct_price());
        holder.productPrice.setText("Price: " + price + "$");
        holder.productColor.setText("Color: " + cartList.get(position).getProduct_color());
        holder.current_date.setText("Added Date: " + cartList.get(position).getCurrent_date());
        holder.current_time.setText("Added Time: " + cartList.get(position).getCurrent_time());
        String quantity = String.valueOf(cartList.get(position).getProduct_quantity());
        holder.productQuantity.setText(quantity);
        String total_price = String.valueOf(cartList.get(position).getTotal_price());
        holder.total_price.setText("Total Price: " + total_price + "$");
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Do you want to delete the selected product?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                db.collection("Cart").document(auth.getCurrentUser().getUid())
                                        .collection("User Cart").document(cartList.get(position).getId())
                                        .delete()
                                        .addOnCompleteListener(task -> {
                                            if (task.isSuccessful()){
                                                cartList.remove(cartList.get(position));
                                                notifyDataSetChanged();
                                                Toast.makeText(context, "Successfully deleted", Toast.LENGTH_SHORT).show();
                                            }
                                            else {
                                                Toast.makeText(context, "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }

    @Override

    public int getItemCount() {
        return cartList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView productImage;
        TextView productName, productPrice, productColor, productQuantity, current_date, current_time, total_price;
        Button deleteButton;

        public ViewHolder(View itemView){
            super(itemView);
            productImage = itemView.findViewById(R.id.product_image);
            productName = itemView.findViewById(R.id.product_name);
            productPrice = itemView.findViewById(R.id.product_price);
            productColor = itemView.findViewById(R.id.product_color);
            productQuantity = itemView.findViewById(R.id.product_quantity);
            current_date = itemView.findViewById(R.id.product_added_date);
            current_time = itemView.findViewById(R.id.product_added_time);
            total_price = itemView.findViewById(R.id.product_total_price);
            deleteButton = itemView.findViewById(R.id.delete_button);


        }
    }

    

    
}
