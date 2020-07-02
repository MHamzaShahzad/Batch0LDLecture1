package com.dapgarage.lecture1;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import butterknife.BindView;

public class HomeActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    String email, password;

    @BindView(R.id.notification_title)
    EditText notification_title;

    @BindView(R.id.notification_description)
    EditText notification_description;

    @BindView(R.id.home_page_image)
    ImageView home_page_image;

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

    @Override
    protected void onStart() {
        super.onStart();
        Picasso.get()
                .load("https://image.shutterstock.com/image-photo/bright-spring-view-cameo-island-260nw-1048185397.jpg")
                .fit()
                .centerInside()
                .into(home_page_image);
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
        builder.setPositiveButton("Ok", listener);

        AlertDialog dialog = builder.create();

        dialog.show();
    }

    public void showNotification(View view) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(HomeActivity.this, "channel_1");
        builder.setSmallIcon(R.drawable.ic_launcher_background);
        builder.setContentTitle(notification_title.getText().toString());
        builder.setContentText(notification_description.getText().toString());
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);

        createNotificationChannel();

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(HomeActivity.this);
        managerCompat.notify(1, builder.build());

    }

    private void createNotificationChannel() {
        int currentDeviceOSVersion = Build.VERSION.SDK_INT;
        if (currentDeviceOSVersion >= Build.VERSION_CODES.O) {
            // Create Channel
            NotificationChannel channel = new NotificationChannel("channel_1", "channel_1_name", NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("channel_1_description");

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }



    public void getAppPermissions(View view) {

        if (!checkPermission()){
            requestPermission();
        }

    }

    private boolean checkPermission() {

        return ContextCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission(){

        ActivityCompat.requestPermissions(HomeActivity.this, new String[]{Manifest.permission.CAMERA}, 101);
    }

}