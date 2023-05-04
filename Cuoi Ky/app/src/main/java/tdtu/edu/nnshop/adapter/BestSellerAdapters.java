package tdtu.edu.nnshop.adapter;


import android.content.Context;
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
import tdtu.edu.nnshop.model.BestSeller;

public class BestSellerAdapters extends RecyclerView.Adapter<BestSellerAdapters.ViewHolder> {

    private Context context;
    private List<BestSeller> bestSellerList;

    public BestSellerAdapters(Context context, List<BestSeller> bestSellerList) {
        this.context = context;
        this.bestSellerList = bestSellerList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_best, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load(bestSellerList.get(position).getImg_url()).into(holder.bsImage);
        holder.name.setText(bestSellerList.get(position).getName());
        double price = bestSellerList.get(position).getPrice();
        holder.price.setText("Price: "+String.valueOf(price) +"$");

    
    }

    @Override
    public int getItemCount() {
        return bestSellerList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView bsImage;
        TextView name,price;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            bsImage= itemView.findViewById(R.id.bs_img);
            name= itemView.findViewById(R.id.bs_name);
            price = itemView.findViewById(R.id.ds_price);

        }
    }
}
