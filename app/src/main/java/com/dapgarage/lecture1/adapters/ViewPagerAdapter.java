package com.dapgarage.lecture1.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.dapgarage.lecture1.fragments.Fragment1;
import com.dapgarage.lecture1.fragments.Fragment2;
import com.dapgarage.lecture1.fragments.Fragment3;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    int NumberOfTabs;

    public ViewPagerAdapter(@NonNull FragmentManager fm, int NumberOfTabs) {
        super(fm);
        this.NumberOfTabs = NumberOfTabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return new Fragment1();
            case 1:
                return new Fragment2();
            case 2:
                return new Fragment3();
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return NumberOfTabs;
    }

}
