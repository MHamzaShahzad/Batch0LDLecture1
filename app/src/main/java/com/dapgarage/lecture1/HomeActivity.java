package com.dapgarage.lecture1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    String email, password;
    static final int MULTIPLE_PERMISSIONS_REQUEST_CODE = 99;

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

        ButterKnife.bind(this);

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

        requestMultiplePermissions();

        // More Optimized and flexible way
       /* requestMultiplePermissionsOptimized(new String[] {
                Manifest.permission.CAMERA,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.READ_SMS
        });*/

    }

    private void requestMultiplePermissions() {

        List<String> permissionsList = new ArrayList<>();

        int camera_permission = ContextCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.CAMERA);
        int location_permission = ContextCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.ACCESS_FINE_LOCATION);
        int read_sms_permission = ContextCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.READ_SMS);

        if (camera_permission == PackageManager.PERMISSION_DENIED) {
            permissionsList.add(Manifest.permission.CAMERA);
        }

        if (location_permission == PackageManager.PERMISSION_DENIED) {
            permissionsList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }

        if (read_sms_permission == PackageManager.PERMISSION_DENIED) {
            permissionsList.add(Manifest.permission.READ_SMS);
        }

        if (permissionsList.size() > 0) {
            String[] permissions = permissionsList.toArray(new String[0]);
            ActivityCompat.requestPermissions(HomeActivity.this, permissions, MULTIPLE_PERMISSIONS_REQUEST_CODE);
        } else
            Toast.makeText(HomeActivity.this, "All permissions already granted!", Toast.LENGTH_SHORT).show();
    }

    private void requestMultiplePermissionsOptimized(String[] permissionsList) {
        List<String> requestPermissions = new ArrayList<>();
        for (String permission : permissionsList) {
            if (ContextCompat.checkSelfPermission(HomeActivity.this, permission) == PackageManager.PERMISSION_DENIED)
                requestPermissions.add(permission);
        }
        if (requestPermissions.size() > 0) {
            String[] permissions = requestPermissions.toArray(new String[0]);
            ActivityCompat.requestPermissions(HomeActivity.this, permissions, MULTIPLE_PERMISSIONS_REQUEST_CODE);
        } else
            Toast.makeText(HomeActivity.this, "All permissions already granted!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // YOUR TASK

    }

    public void moveToFragmentHolder(View view) {
        startActivity(new Intent(HomeActivity.this, FragmentHolderActivity.class));
    }
}