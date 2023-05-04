package tdtu.edu.nnshop.ui.profile;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import de.hdodenhof.circleimageview.CircleImageView;
import tdtu.edu.nnshop.R;
import tdtu.edu.nnshop.model.User;


public class ProfileFragment extends Fragment {

    CircleImageView img_Profile;
    EditText edtName, edtEmail, edtPhone, edtAddress;
    Button btnUpdate;
    
    FirebaseStorage storage;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseDatabase db;

    User user=null;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        storage = FirebaseStorage.getInstance();
        db = FirebaseDatabase.getInstance();

        img_Profile = root.findViewById(R.id.img_profile);
        edtName = root.findViewById(R.id.profile_name);
        edtEmail = root.findViewById(R.id.profile_email);
        edtPhone = root.findViewById(R.id.profile_number);
        edtAddress = root.findViewById(R.id.profile_address);
        btnUpdate = root.findViewById(R.id.update);

        db.getReference().child("Users").child(auth.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                user = snapshot.getValue(User.class);
                if (user != null) {
                    Glide.with(getContext()).load(user.getImgUrl()).into(img_Profile);
                    edtName.setText(user.getUsername());
                    edtEmail.setText(user.getEmail());
                    edtPhone.setText(user.getPhone());
                    edtAddress.setText(user.getAddress());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        img_Profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 1);

            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = edtName.getText().toString();
                String phone = edtPhone.getText().toString();
                String address = edtAddress.getText().toString();
                String email = edtEmail.getText().toString();

                if(name.isEmpty() || phone.isEmpty() || address.isEmpty() || email.isEmpty()){
                    Toast.makeText(getContext(), "Please fill all the information", Toast.LENGTH_SHORT).show();
                }else{
                    db.getReference().child("Users").child(auth.getUid()).child("username").setValue(name);
                    db.getReference().child("Users").child(auth.getUid()).child("phone").setValue(phone);
                    db.getReference().child("Users").child(auth.getUid()).child("address").setValue(address);
                    db.getReference().child("Users").child(auth.getUid()).child("email").setValue(email);
                    Toast.makeText(getContext(), "Update successfully", Toast.LENGTH_SHORT).show();
                }
            }
        });
        
        return root;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data.getData() != null){
            Uri uri_profile = data.getData();
            img_Profile.setImageURI(uri_profile);


            final StorageReference reference = storage.getReference().child("profile pictures")
                    .child(auth.getUid());
            
            reference.putFile(uri_profile).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            db.getReference().child("Users").child(auth.getUid()).child("imgUrl")
                                    .setValue(uri.toString());
                        }
                    });
                }
            });
            

        }
            
    }

}