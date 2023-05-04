package tdtu.edu.nnshop.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import tdtu.edu.nnshop.R;
import tdtu.edu.nnshop.activity.DetailsActivity;
import tdtu.edu.nnshop.model.Product;


public class ProductAdapters extends RecyclerView.Adapter<ProductAdapters.ViewHolder>{

    Context context;
    List<Product> productList;

    public ProductAdapters(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public ProductAdapters.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate( R.layout.item_product, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapters.ViewHolder holder, int position) {
        Glide.with(context).load(productList.get(position).getImg_url()).into(holder.productImage);
        holder.productName.setText("Name: " + productList.get(position).getName());
        String price = String.valueOf(productList.get(position).getPrice());
        holder.productPrice.setText("Price: " + price + "$");
        holder.productColor.setText("Color: " + productList.get(position).getColor());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailsActivity.class);
                intent.putExtra( "product", productList.get(position));
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

        public ViewHolder(@NonNull View view){
            super(view);
            productImage = view.findViewById(R.id.img_url_product);
            productName = view.findViewById(R.id.textview_name);
            productPrice = view.findViewById(R.id.textview_price);
            productColor = view.findViewById(R.id.textview_color);
        }
    }
}
