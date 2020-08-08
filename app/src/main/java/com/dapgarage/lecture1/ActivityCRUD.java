package com.dapgarage.lecture1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.dapgarage.lecture1.database.MyDatabaseManagement;

public class ActivityCRUD extends AppCompatActivity {

    private EditText id, name, email;
    private MyDatabaseManagement myDatabaseManagement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c_r_u_d);

        id = findViewById(R.id.id);
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);

        myDatabaseManagement = new MyDatabaseManagement(ActivityCRUD.this);
    }

    public void createUser(View view) {
        if (TextUtils.isEmpty(name.getText())) {
            name.setError("Field is required!");
        } else if (TextUtils.isEmpty(email.getText())) {
            email.setError("Field is required!");
        } else {
            boolean isInserted = myDatabaseManagement.create(name.getText().toString(), email.getText().toString());
            if (isInserted)
                Toast.makeText(ActivityCRUD.this, "Inserted Successfully!", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(ActivityCRUD.this, "Insertion Failure!", Toast.LENGTH_SHORT).show();
        }
    }

    public void readUser(View view) {
        myDatabaseManagement.read();
    }

    public void updateUser(View view) {
        if (TextUtils.isEmpty(id.getText()))
            id.setError("Field is required!");
        else {
            String nameValue = (name.getText().toString().equals("") ? null : name.getText().toString());
            String emailValue =  (email.getText().toString().equals("") ? null : email.getText().toString());
            boolean isUpdated = myDatabaseManagement.update(id.getText().toString(), nameValue, emailValue);
            if (isUpdated)
                Toast.makeText(ActivityCRUD.this, "Record updated successfully!", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(ActivityCRUD.this, "Record update failure!", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteUser(View view) {
        if (TextUtils.isEmpty(id.getText()))
            id.setError("Field is required!");
        else {
            boolean isDeleted = myDatabaseManagement.delete(id.getText().toString());
            if (isDeleted)
                Toast.makeText(ActivityCRUD.this, "Record deleted successfully!", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(ActivityCRUD.this, "Record delete failure!", Toast.LENGTH_SHORT).show();
        }
    }
}