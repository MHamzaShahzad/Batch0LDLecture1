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
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.dapgarage.lecture1.receivers.BluetoothBroadcastReceiver;
import com.dapgarage.lecture1.receivers.LocalBroadCastReceiver;
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

    LocalBroadCastReceiver receiver;
    BluetoothBroadcastReceiver bluetoothBroadcastReceiver;
    private static final String ACTION_LOCAL_BROADCAST = "android.intent.action.LOCAL_BROADCAST";

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
                //.load("https://image.shutterstock.com/image-photo/bright-spring-view-cameo-island-260nw-1048185397.jpg")
                .load("https://firebasestorage.googleapis.com/v0/b/lecture-1-5c025.appspot.com/o/profile_images%2F303854336?alt=media&token=a4aeb685-1859-47cb-a404-3e3306b54775")
                .fit()
                .centerInside()
                .into(home_page_image);
        receiver = new LocalBroadCastReceiver();
        IntentFilter intentFilter = new IntentFilter(ACTION_LOCAL_BROADCAST);
        registerReceiver(receiver, intentFilter);

        bluetoothBroadcastReceiver = new BluetoothBroadcastReceiver();
        registerReceiver(bluetoothBroadcastReceiver,  new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
        unregisterReceiver(bluetoothBroadcastReceiver);
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
        /*requestMultiplePermissionsOptimized(new String[] {
                Manifest.permission.CAMERA,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.READ_SMS,
                Manifest.permission.READ_EXTERNAL_STORAGE
        });*/

    }

    private void requestMultiplePermissions() {

        List<String> permissionsList = new ArrayList<>();

        int camera_permission = ContextCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.CAMERA);
        int location_permission = ContextCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.ACCESS_FINE_LOCATION);
        int read_sms_permission = ContextCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.READ_SMS);
        int read_external_storage_permission = ContextCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE);
        int bluetooth_permission = ContextCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.BLUETOOTH);

        if (camera_permission == PackageManager.PERMISSION_DENIED) {
            permissionsList.add(Manifest.permission.CAMERA);
        }

        if (location_permission == PackageManager.PERMISSION_DENIED) {
            permissionsList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }

        if (read_sms_permission == PackageManager.PERMISSION_DENIED) {
            permissionsList.add(Manifest.permission.READ_SMS);
        }

        if (read_external_storage_permission == PackageManager.PERMISSION_DENIED) {
            permissionsList.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }

        if (bluetooth_permission == PackageManager.PERMISSION_DENIED) {
            permissionsList.add(Manifest.permission.BLUETOOTH);
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

    public void moveToRecyclerActivity(View view) {
        startActivity(new Intent(HomeActivity.this, RecyclerActivity.class));
    }

    public void moveToTabActivity(View view) {
        startActivity(new Intent(HomeActivity.this, TabActivity.class));
    }

    public void moveToProfileActivity(View view) {
        startActivity(new Intent(HomeActivity.this, UserProfileActivity.class));
    }

    public void moveToStorageActivity(View view) {
        startActivity(new Intent(HomeActivity.this, StorageActivity.class));
    }

    public void moveToApiActivity(View view) {
        startActivity(new Intent(HomeActivity.this, ApiActivity.class));

    }

    public void moveToWebViewActivity(View view) {
        startActivity(new Intent(HomeActivity.this, WebViewActivity.class));
    }

    public void sendLocalBroadcast(View view) {
        Intent intent = new Intent(ACTION_LOCAL_BROADCAST);
        intent.putExtra("title", notification_title.getText().toString());
        intent.putExtra("description", notification_description.getText().toString());
        sendBroadcast(intent);
    }

    public void moveToCRUDActivity(View view) {
        startActivity(new Intent(HomeActivity.this, ActivityCRUD.class));
    }

    public void moveToMapsActivity(View view) {
        startActivity(new Intent(HomeActivity.this, MapsActivity.class));
    }
}