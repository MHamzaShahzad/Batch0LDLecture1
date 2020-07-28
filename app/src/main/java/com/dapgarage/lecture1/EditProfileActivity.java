package com.dapgarage.lecture1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dapgarage.lecture1.models.FirebaseDatabaseUser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditProfileActivity extends AppCompatActivity {

    private static final String TAG = EditProfileActivity.class.getName();
    @BindView(R.id.profileFirstName)
    TextView profileFirstName;
    @BindView(R.id.profileLastName)
    TextView profileLastName;
    @BindView(R.id.profileEmail)
    TextView profileEmail;
    @BindView(R.id.profilePhoneNumber)
    TextView profilePhoneNumber;
    @BindView(R.id.profileCNIC)
    TextView profileCNIC;
    @BindView(R.id.profileAddress)
    TextView profileAddress;

    FirebaseAuth mAuth;
    FirebaseDatabase mDatabase;
    DatabaseReference mReference;

    private String profileImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        ButterKnife.bind(this);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        mReference = mDatabase.getReference("User");

        loadUserFromFirebase();
    }

    private void loadUserFromFirebase(){
        mReference.child(mAuth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.i(TAG, "loadUserFromFirebase--onDataChange: " + dataSnapshot.getValue().toString());
                FirebaseDatabaseUser databaseUser = dataSnapshot.getValue(FirebaseDatabaseUser.class);
                if (databaseUser != null){
                    profileFirstName.setText(databaseUser.getFirstName());
                    profileLastName.setText(databaseUser.getLastName());
                    profileEmail.setText(databaseUser.getEmail());
                    profileCNIC.setText(databaseUser.getCnic());
                    profileAddress.setText(databaseUser.getAddress());
                    profilePhoneNumber.setText(databaseUser.getPhoneNumber());
                    profileImage = databaseUser.getProfileImage();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG, "loadUserFromFirebase--onCancelled: " + databaseError.getMessage() );
            }
        });
    }

    public void updateAccount(View view) {

        String email = profileEmail.getText().toString();

        String firstName = profileFirstName.getText().toString();
        String lastName = profileLastName.getText().toString();
        String phoneNumber = profilePhoneNumber.getText().toString();
        String cnic = profileCNIC.getText().toString();
        String address = profileAddress.getText().toString();

        FirebaseDatabaseUser user  = new FirebaseDatabaseUser(
                firstName,
                lastName,
                email,
                cnic,
                phoneNumber,
                address,
                profileImage
        );

        mReference.child(mAuth.getCurrentUser().getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful())
                    Toast.makeText(EditProfileActivity.this, "Data updated successfully!", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(EditProfileActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }
}