package com.dapgarage.lecture1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.dapgarage.lecture1.models.FirebaseDatabaseUser;
import com.dapgarage.lecture1.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CreateAccountActivity extends AppCompatActivity {

    @BindView(R.id.registerEmailInput)
    EditText registerEmailInput;

    @BindView(R.id.registerPasswordInput)
    EditText registerPasswordInput;

    @BindView(R.id.registerFirstNameInput)
    EditText registerFirstNameInput;

    @BindView(R.id.registerLastNameInput)
    EditText registerLastNameInput;

    @BindView(R.id.registerPhoneNumberInput)
    EditText registerPhoneNumberInput;

    @BindView(R.id.registerCNICInput)
    EditText registerCNICInput;

    @BindView(R.id.registerConfirmPasswordInput)
    EditText registerConfirmPasswordInput;

    @BindView(R.id.registerAddressInput)
    EditText registerAddressInput;

    FirebaseAuth mAuth;
    FirebaseDatabase mDatabase;
    DatabaseReference mReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        ButterKnife.bind(this);

        mAuth = FirebaseAuth.getInstance();

        mDatabase = FirebaseDatabase.getInstance();
        mReference = mDatabase.getReference("User");

    }

    public void registerAccount(View view) {

        String email = registerEmailInput.getText().toString();
        String password = registerPasswordInput.getText().toString();

        String firstName = registerFirstNameInput.getText().toString();
        String lastName = registerLastNameInput.getText().toString();
        String phoneNumber = registerPhoneNumberInput.getText().toString();
        String cnic = registerCNICInput.getText().toString();
        String address = registerAddressInput.getText().toString();

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    FirebaseUser user = mAuth.getCurrentUser();
                    if (user != null){

                        FirebaseDatabaseUser databaseUser = new FirebaseDatabaseUser(
                                firstName,
                                lastName,
                                email,
                                cnic,
                                phoneNumber,
                                address,
                                ""
                        );
                        mReference.child(user.getUid()).setValue(databaseUser).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if (task.isSuccessful()){
                                   Toast.makeText(CreateAccountActivity.this, "Information added successfully!", Toast.LENGTH_LONG).show();
                                }else {
                                    Toast.makeText(CreateAccountActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_LONG).show();
                                }

                                startActivity(new Intent(CreateAccountActivity.this, HomeActivity.class));
                                finish();
                            }
                        });


                    }else {
                        startActivity(new Intent(CreateAccountActivity.this, MainActivity.class));
                        finish();
                    }
                }else {
                    Toast.makeText(CreateAccountActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}