package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
public class PagerAdapter extends FragmentStateAdapter {
        private final String[] titles={"Exam","Assignment","Lecture","Study Plan"};
public PagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
        }

@NonNull
@Override
public Fragment createFragment(int position) {
        switch (position){
        case 0:
        return new First();
        case 1:
        return new Second();
        case 2:
        return new Third();
        case 3:
                return new Fourth();


        }
        return new First();
        }

@Override
public int getItemCount() {
        return titles.length;
        }
        }

