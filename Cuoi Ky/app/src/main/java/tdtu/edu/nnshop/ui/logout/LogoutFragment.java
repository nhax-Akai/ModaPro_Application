package tdtu.edu.nnshop.ui.logout;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;

import tdtu.edu.nnshop.R;
import tdtu.edu.nnshop.activity.HomeActivity;
import tdtu.edu.nnshop.activity.LoginActivity;

public class LogoutFragment extends Fragment {
    Button btn_logout, btn_home;
    FirebaseAuth auth;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_logout, container,false);

        btn_logout = root.findViewById(R.id.btn_logout);
        btn_home = root.findViewById(R.id.btn_home);
        auth = FirebaseAuth.getInstance();

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        });

        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), HomeActivity.class));
            }
        });
        return root;
    }
}
