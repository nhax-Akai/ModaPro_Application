package tdtu.edu.nnshop.ui.home;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import tdtu.edu.nnshop.R;
import tdtu.edu.nnshop.adapter.CategoryAdapters;
import tdtu.edu.nnshop.adapter.ProductAdapters;
import tdtu.edu.nnshop.model.BestSeller;
import tdtu.edu.nnshop.adapter.BestSellerAdapters;
import tdtu.edu.nnshop.model.Category;
import tdtu.edu.nnshop.model.Product;

public class HomeFragment extends Fragment {

    RecyclerView bestSellerRec;
    List<BestSeller> bestSellerList;
    BestSellerAdapters bestSellerAdapters;

    FirebaseFirestore db;

    //category
    RecyclerView categoryRec;
    List<Category> categoryList;
    CategoryAdapters categoryAdapters;

    //product
    RecyclerView productRec;
    List<Product> productList;
    ProductAdapters productAdapters;

    ScrollView scrollView;
    ProgressBar progressBar;

    //sreach
    EditText search;
    RecyclerView searchRec;
    List<Product> searchList;
    ProductAdapters searchAdapters;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        db = FirebaseFirestore.getInstance();
        bestSellerRec = root.findViewById(R.id.bestseller_rec);
        scrollView = root.findViewById(R.id.scroll_view);
        progressBar = root.findViewById(R.id.progressBar);

        progressBar.setVisibility(View.VISIBLE);
        scrollView.setVisibility(View.GONE);

        bestSellerRec.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        bestSellerList = new ArrayList<>();
        bestSellerAdapters = new BestSellerAdapters(getActivity(), bestSellerList);
        bestSellerRec.setAdapter(bestSellerAdapters);
        db.collection("BestSeller")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                BestSeller bestSellerModel = document.toObject(BestSeller.class);
                                bestSellerList.add(bestSellerModel);
                                bestSellerAdapters.notifyDataSetChanged();
                            }
                        } else {
                            Toast.makeText(getActivity(), "Error" + task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        categoryRec = root.findViewById(R.id.category_rec);
        categoryRec.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        categoryList = new ArrayList<>();
        categoryAdapters = new CategoryAdapters(getActivity(), categoryList);
        categoryRec.setAdapter(categoryAdapters);
        db.collection("Category")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                Category categoryModel = document.toObject(Category.class);
                                categoryList.add(categoryModel);
                                categoryAdapters.notifyDataSetChanged();
                                progressBar.setVisibility(View.GONE);
                                scrollView.setVisibility(View.VISIBLE);
                            }
                        } else {
                            Toast.makeText(getActivity(), "Error" + task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        productRec = root.findViewById(R.id.product_rec);
        productRec.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        productList = new ArrayList<>();
        productAdapters = new ProductAdapters(getActivity(), productList);
        productRec.setAdapter(productAdapters);
        //get all product
        db.collection("Product")
                .limit(10)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                Product productModel = document.toObject(Product.class);
                                productList.add(productModel);
                                productAdapters.notifyDataSetChanged();

                            }
                        } else {
                            Toast.makeText(getActivity(), "Error" + task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        //search
        search = root.findViewById(R.id.search_box);
        searchRec = root.findViewById(R.id.search_rec);
        searchList = new ArrayList<>();
        searchAdapters = new ProductAdapters(getContext(), searchList);
        searchRec.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        searchRec.setAdapter(searchAdapters);
        searchRec.setHasFixedSize(true);

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().isEmpty()) {
                    searchList.clear();
                    searchAdapters.notifyDataSetChanged();
                } else {
                    searchProduct(editable.toString());

                }
            }
        });
        return root;
    }

    private void searchProduct(String category) {

        if (!category.isEmpty()) {
            db.collection("Product")
                    .whereEqualTo("category", category)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful() && task.getResult() != null) {
                                searchList.clear();
                                searchAdapters.notifyDataSetChanged();
                                for (DocumentSnapshot document : task.getResult().getDocuments()) {

                                    Product productModel = document.toObject(Product.class);
                                    searchList.add(productModel);
                                    searchAdapters.notifyDataSetChanged();

                                }

                            } else {
                                Toast.makeText(getActivity(), "Error" + task.getException(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

        }
    }
}
