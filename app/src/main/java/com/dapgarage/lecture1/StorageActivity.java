package com.dapgarage.lecture1;

import androidx.annotation.NonNull;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StorageActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = StorageActivity.class.getName();
    private static final int PICK_IMAGE_RC = 12;

    @BindView(R.id.imageView)
    ImageView imageView;

    @BindView(R.id.btnPickImage)
    Button btnPickImage;

    @BindView(R.id.btnUploadImage)
    Button btnUploadImage;

    private Uri imageUri;
    private FirebaseStorage mStorage;
    private StorageReference mStorageReference;

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
        btnUploadImage.setOnClickListener(this);

        mStorage = FirebaseStorage.getInstance();
        mStorageReference = mStorage.getReference("/profile_images");
    }

    // Method # 2, remaining part
    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == btnPickImage.getId()) {
            pickImageFromGallery();
        }

        if (id == btnUploadImage.getId()) {

            if (imageUri == null)
                Toast.makeText(StorageActivity.this, "No image selected, yet!", Toast.LENGTH_LONG).show();
            else
                uploadImage(imageUri);

        }
    }

    private void pickImageFromGallery() {
        Intent imageIntent = new Intent();
        imageIntent.setType("image/*");
        imageIntent.setAction(Intent.ACTION_GET_CONTENT);
        //startActivityForResult(imageIntent, PICK_IMAGE_RC);
        startActivityForResult(Intent.createChooser(imageIntent, "Select Image"), PICK_IMAGE_RC);
    }

    private void uploadImage(Uri uri) {
        mStorageReference.child(uri.getLastPathSegment()).putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(StorageActivity.this, "Image Uploaded Successfully!", Toast.LENGTH_LONG).show();

                taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Log.i(TAG, "onSuccess: " + uri);
                        // Set this image uri as profile_image field in relevant database user.
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        FirebaseDatabase.getInstance().getReference("User").child(user.getUid()).child("firstName").removeValue();
                        FirebaseDatabase.getInstance().getReference("User").child(user.getUid()).child("profileImage").setValue(uri).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful())
                                    Toast.makeText(StorageActivity.this, "Updated Successfully!", Toast.LENGTH_LONG).show();
                                else
                                    Toast.makeText(StorageActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                });

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(StorageActivity.this, "Image Upload Failed! \n" + e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_RC) {
            if (resultCode == RESULT_OK) {
                imageUri = data.getData();
                if (imageUri != null) {
                    Log.i(TAG, "onActivityResult: " + imageUri.toString());
                    imageView.setImageURI(imageUri);
                }
            } else
                Toast.makeText(StorageActivity.this, "Error picking image!", Toast.LENGTH_LONG).show();
        }
    }
}