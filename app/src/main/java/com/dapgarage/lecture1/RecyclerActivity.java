package com.dapgarage.lecture1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.dapgarage.lecture1.adapters.RecyclerAdapter;
import com.dapgarage.lecture1.models.User;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecyclerActivity extends AppCompatActivity {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);
        ButterKnife.bind(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(RecyclerActivity.this);

        List<User> list = new ArrayList<>();
        list.add(new User("Hamza", "hamza@gmail.com", "23"));
        list.add(new User("Muzakir", "muzakir@gmail.com", "21"));
        list.add(new User("Muzammal", "muzammal@gmail.com", "22"));
        list.add(new User("Samaama", "samaama@gmail.com", "20"));

        RecyclerAdapter recyclerAdapter = new RecyclerAdapter(list);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(recyclerAdapter);

    }
}