package com.dapgarage.lecture1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.dapgarage.lecture1.adapters.RecyclerAdapter;
import com.dapgarage.lecture1.models.FirebaseDatabaseUser;
import com.dapgarage.lecture1.models.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecyclerActivity extends AppCompatActivity {


    private static final String TAG = RecyclerActivity.class.getName();
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    FirebaseDatabase mDatabase;
    DatabaseReference mReference;

    List<FirebaseDatabaseUser> usersList;
    RecyclerAdapter recyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);
        ButterKnife.bind(this);

        mDatabase = FirebaseDatabase.getInstance();
        mReference = mDatabase.getReference("User");

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(RecyclerActivity.this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(RecyclerActivity.this, 1);

        usersList = new ArrayList<>();


        recyclerAdapter = new RecyclerAdapter(usersList);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(recyclerAdapter);


        loadUsersFormFirebase();
    }

    private void loadUsersFormFirebase() {
        usersList.clear();
        mReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.i(TAG, "loadUsersFormFirebase--onDataChange: " + dataSnapshot.getValue().toString());

                Iterable<DataSnapshot> usersSnapshot = dataSnapshot.getChildren();

                for (DataSnapshot userSnapshot : usersSnapshot) {
                    FirebaseDatabaseUser databaseUser = userSnapshot.getValue(FirebaseDatabaseUser.class);
                    usersList.add(databaseUser);
                }

                recyclerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}