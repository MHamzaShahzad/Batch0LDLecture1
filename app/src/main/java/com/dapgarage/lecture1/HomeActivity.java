package com.dapgarage.lecture1;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    String email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        sharedPreferences = getSharedPreferences("Authentication", MODE_PRIVATE);

        // Create/Write, Delete/Remove, Update
        editor = sharedPreferences.edit();


        email = sharedPreferences.getString("email", "");
        password = sharedPreferences.getString("password", "");

        Toast.makeText(this, email + " : " + password, Toast.LENGTH_LONG).show();

    }

    public void logout(View view) {

        editor.clear().commit();
        startActivity(new Intent(HomeActivity.this, MainActivity.class));
        finish();

    }

    public void showDialogMessage(View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);

        builder.setCancelable(false);
        builder.setTitle("Your Info");
        builder.setMessage("You logged in as " + email);
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

    public void showNotification(View view){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(HomeActivity.this, "channel_1");
        builder.setSmallIcon(R.drawable.ic_launcher_background);
        builder.setContentTitle("Title");
        builder.setContentText("Notification Description");
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);

        createNotificationChannel();


        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(HomeActivity.this);
        managerCompat.notify(1, builder.build());

    }

    private void createNotificationChannel(){
        int currentDeviceOSVersion = Build.VERSION.SDK_INT;
        if (currentDeviceOSVersion >= Build.VERSION_CODES.O){
            // Create Channel
            NotificationChannel channel = new NotificationChannel("channel_1", "channel_1_name", NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("channel_1_description");

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }

}