package tdtu.edu.nnshop.ui.cart;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import tdtu.edu.nnshop.R;
import tdtu.edu.nnshop.adapter.CartAdapters;
import tdtu.edu.nnshop.model.Cart;

public class CartFragment extends Fragment{

    FirebaseAuth auth;
    FirebaseFirestore db;

    RecyclerView recyclerView;
    List<Cart> cartList;
    CartAdapters cartAdapters;

    Button btn_delete;

    public CartFragment() {
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_cart, container,false);

        db=FirebaseFirestore.getInstance();
        auth=FirebaseAuth.getInstance();
        recyclerView=root.findViewById(R.id.cart_rec);     
        
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        cartList=new ArrayList<>();
        cartAdapters=new CartAdapters(getActivity(),cartList);
        recyclerView.setAdapter(cartAdapters);
        db.collection("Cart").document(auth.getCurrentUser().getUid()).collection("User Cart").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for(DocumentSnapshot document:task.getResult()){
                    String id= document.getId();
                    Cart cart=document.toObject(Cart.class);
                    cart.setId(id);
                    cartList.add(cart);
                    cartAdapters.notifyDataSetChanged();
                }
                
            }
        });
        return root;
    }


}
