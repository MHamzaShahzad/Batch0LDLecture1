package com.dapgarage.lecture1;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        sharedPreferences = getSharedPreferences("Authentication", MODE_PRIVATE);

        // Create/Write, Delete/Remove, Update
        editor = sharedPreferences.edit();


        String email = sharedPreferences.getString("email", "");
        String password = sharedPreferences.getString("password", "");

        Toast.makeText(this, email + " : " + password, Toast.LENGTH_LONG).show();
        showDialogMessage("User Details", "You logged in as " + email);
    }

    public void logout(View view) {

        editor.clear().commit();
        startActivity(new Intent(HomeActivity.this, MainActivity.class));
        finish();

    }

    private void showDialogMessage(String title, String message) {

        AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);

        builder.setCancelable(false);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setIcon(R.drawable.ic_launcher_background);
        // method 1
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // body
            }
        });
        builder.setNeutralButton("Continue", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // body
            }
        });
        // method 2
        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // body
            }
        };
        builder.setPositiveButton("Ok",  listener);

        AlertDialog dialog = builder.create();

        dialog.show();
    }


}