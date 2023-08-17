package com.example.myapplication;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager2.widget.ViewPager2;


import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;


import com.google.android.material.navigation.NavigationView;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    NavigationView navigationView;

    TabLayout tabLayout;

    PagerAdapter viewpager;
    ViewPager2 viewPager2;
    private final String[] titles={"Exam","Assignment","Lecture","Study Plan"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        viewPager2=findViewById(R.id.viewpager);
        tabLayout=findViewById(R.id.tablayout);


        drawerLayout=findViewById(R.id.drawer);
        navigationView=findViewById(R.id.nav_view);
        toggle= new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(true);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        viewPager2=findViewById(R.id.viewpager);
        tabLayout=findViewById(R.id.tablayout);
        viewpager= new PagerAdapter(this);
        viewPager2.setAdapter(viewpager);
        new TabLayoutMediator(tabLayout,viewPager2,((tab, position) -> tab.setText(titles[position]))).attach();

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.FirstItem:
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            case R.id.SecondItem:
               Intent intent=new Intent(this,Calender.class);
               startActivity(intent);
               return true;
            default:
                return true;
        }

    }
}