package com.dapgarage.lecture1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CreateAccountActivity extends AppCompatActivity {

    @BindView(R.id.registerEmailInput)
    EditText registerEmailInput;

    @BindView(R.id.registerPasswordInput)
    EditText registerPasswordInput;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        ButterKnife.bind(this);

        mAuth = FirebaseAuth.getInstance();


    }

    public void registerAccount(View view) {

        String email = registerEmailInput.getText().toString();
        String password = registerPasswordInput.getText().toString();

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    FirebaseUser user = mAuth.getCurrentUser();
                    if (user != null){
                        startActivity(new Intent(CreateAccountActivity.this, HomeActivity.class));
                        finish();
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