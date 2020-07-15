package com.dapgarage.lecture1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.dapgarage.lecture1.models.FirebaseDatabaseUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserProfileActivity extends AppCompatActivity {


    private static final String TAG = UserProfileActivity.class.getName();

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
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
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG, "loadUserFromFirebase--onCancelled: " + databaseError.getMessage() );
            }
        });
    }
}