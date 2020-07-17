package com.dapgarage.lecture1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StorageActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = StorageActivity.class.getName();
    private static final int PICK_IMAGE_RC = 12;

    @BindView(R.id.imageView)
    ImageView imageView;

    @BindView(R.id.btnPickImage)
    Button btnPickImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage);
        ButterKnife.bind(this);

        // Method # 1
        /*btnPickImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/

        // Method # 2
        btnPickImage.setOnClickListener(this);

    }

    // Method # 2, remaining part
    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == btnPickImage.getId()) {
            pickImageFromGallery();
        }
    }

    private void pickImageFromGallery() {
        Intent imageIntent = new Intent();
        imageIntent.setType("image/*");
        imageIntent.setAction(Intent.ACTION_GET_CONTENT);
        //startActivityForResult(imageIntent, PICK_IMAGE_RC);
        startActivityForResult(Intent.createChooser(imageIntent, "Select Image"), PICK_IMAGE_RC);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_RC) {
            if (resultCode == RESULT_OK) {
                Uri imageUri = data.getData();
                if (imageUri != null) {
                    Log.i(TAG, "onActivityResult: " + imageUri.toString());
                    imageView.setImageURI(imageUri);
                }
            }else
                Toast.makeText(StorageActivity.this, "Error picking image!", Toast.LENGTH_LONG).show();
        }
    }
}