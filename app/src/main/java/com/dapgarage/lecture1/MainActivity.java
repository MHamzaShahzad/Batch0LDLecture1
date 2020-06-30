package com.dapgarage.lecture1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;


public class MainActivity extends AppCompatActivity {

    EditText inputEmail;
    EditText inputPassword;

    SharedPreferences authSharedPreferences;
    SharedPreferences.Editor authEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        authSharedPreferences = getSharedPreferences("Authentication", MODE_PRIVATE);

        // Create/Write, Delete/Remove, Update

        authEditor = authSharedPreferences.edit();

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

        Intent abc = new Intent(MainActivity.this, HomeActivity.class);
        authEditor.putString("email", inputEmail.getText().toString());
        authEditor.putString("password", inputPassword.getText().toString());
        authEditor.putBoolean("isLogin", true);
        authEditor.commit();
        startActivity(abc);
    }

    private boolean isUserAlreadyLoggedIn() {
        return authSharedPreferences.getBoolean("isLogin", false);
    }

}
