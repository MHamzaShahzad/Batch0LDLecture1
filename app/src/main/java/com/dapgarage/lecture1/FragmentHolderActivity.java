package com.dapgarage.lecture1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.dapgarage.lecture1.fragments.Fragment1;
import com.dapgarage.lecture1.fragments.Fragment2;
import com.dapgarage.lecture1.fragments.Fragment3;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentHolderActivity extends AppCompatActivity {

    @BindView(R.id.btnFragment1)
    Button btnFragment1;

    @BindView(R.id.btnFragment2)
    Button btnFragment2;

    @BindView(R.id.btnFragment3)
    Button btnFragment3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_holder);

        ButterKnife.bind(this);

    }

    public void replaceFragment(View view) {

        int id = view.getId();

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if (id == btnFragment1.getId()) {

            Fragment1 fragment1 = new Fragment1();
            fragmentTransaction.replace(R.id.fragment_holder_layout, fragment1).commit();
        }

        if (id == btnFragment2.getId()) {

            Fragment2 fragment2 = new Fragment2();
            fragmentTransaction.replace(R.id.fragment_holder_layout, fragment2).commit();
        }

        if (id == btnFragment3.getId()) {

            Fragment3 fragment3 = new Fragment3();
            fragmentTransaction.replace(R.id.fragment_holder_layout, fragment3).commit();
        }

    }
}