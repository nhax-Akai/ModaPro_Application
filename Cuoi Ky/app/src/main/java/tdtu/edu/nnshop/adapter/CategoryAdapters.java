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
import tdtu.edu.nnshop.activity.ProductListActivity;
import tdtu.edu.nnshop.model.Category;

public class CategoryAdapters extends RecyclerView.Adapter<CategoryAdapters.ViewHolder> {


    Context context;
    List<Category> categoryList;

    public CategoryAdapters(Context context, List<Category> categoryList) {
        this.context = context;
        this.categoryList = categoryList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate( R.layout.item_category, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load(categoryList.get(position).getImg_url()).into(holder.categoryImage);
        holder.categoryName.setText(categoryList.get(position).getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProductListActivity.class);
                intent.putExtra("category", categoryList.get(holder.getAdapterPosition()).getType());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView categoryImage;
        TextView categoryName;

        public ViewHolder(@NonNull View view){
            super(view);
            categoryImage = view.findViewById(R.id.img_category);
            categoryName = view.findViewById(R.id.name_category);
        }
    }
}
