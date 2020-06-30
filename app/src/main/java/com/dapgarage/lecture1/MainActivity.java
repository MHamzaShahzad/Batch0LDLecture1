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

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("AppData", MODE_PRIVATE);

        // Create/Write, Delete/Remove, Update
        editor = sharedPreferences.edit();


        inputEmail = (EditText) findViewById(R.id.input_email);
        inputPassword = findViewById(R.id.input_password);
    }

    public void moveToRegister(View view) {
        Intent intent = new Intent(MainActivity.this, CreateAccountActivity.class);
        startActivity(intent);
    }

    public void moveToHome(View view) {

        Intent abc = new Intent(MainActivity.this, HomeActivity.class);
        editor.putString("email" , inputEmail.getText().toString());
        editor.putString("password" , inputPassword.getText().toString());
        editor.commit();
        startActivity(abc);
    }

}
