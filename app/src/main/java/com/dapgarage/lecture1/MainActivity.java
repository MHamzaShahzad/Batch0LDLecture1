package com.dapgarage.lecture1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity {

    EditText inputEmail;
    EditText inputPassword;

    SharedPreferences authSharedPreferences;
    SharedPreferences.Editor authEditor;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        authSharedPreferences = getSharedPreferences("Authentication", MODE_PRIVATE);

        // Create/Write, Delete/Remove, Update

        authEditor = authSharedPreferences.edit();
        mAuth = FirebaseAuth.getInstance();

        if (isUserAlreadyLoggedIn()) {
            startActivity(new Intent(MainActivity.this, HomeActivity.class));
            finish();
        }

        inputEmail = (EditText) findViewById(R.id.input_email);
        inputPassword = findViewById(R.id.input_password);

    }

    public void moveToRegister(View view) {
        Intent intent = new Intent(MainActivity.this, CreateAccountActivity.class);
        startActivity(intent);
    }

    public void moveToHome(View view) {

        String email = inputEmail.getText().toString();
        String password = inputPassword.getText().toString();

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Intent abc = new Intent(MainActivity.this, HomeActivity.class);
                            authEditor.putString("email", email);
                            authEditor.putString("password", password);
                            //authEditor.putBoolean("isLogin", true);
                            authEditor.commit();
                            startActivity(abc);
                            finish();
                        }else{
                            Toast.makeText(MainActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );



    }

    private boolean isUserAlreadyLoggedIn() {
        //return authSharedPreferences.getBoolean("isLogin", false);
        FirebaseUser user = mAuth.getCurrentUser();
        return user != null;
    }

}
