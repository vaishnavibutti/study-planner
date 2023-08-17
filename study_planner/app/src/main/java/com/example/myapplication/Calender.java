package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class Calender extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    CalendarView calendar;
    TextView date_view;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    TextView totalexams;
    TextView totallectures;
    TextView totalassignments;
    TextView totalstudyplan;
    FirebaseFirestore db;
    LinearLayout table;

    @Override
    protected void onCreate(Bundle savedInstanceState){
      super.onCreate(savedInstanceState);
      setContentView(R.layout.calender);
      db=FirebaseFirestore.getInstance();
      table=findViewById(R.id.table);
      table.setVisibility(View.INVISIBLE);
      calendar=findViewById(R.id.calendar);
      date_view=findViewById(R.id.date_view);
      date_view.setVisibility(TextView.INVISIBLE);
      navigationView=findViewById(R.id.nav_viewc);
        Toolbar toolbar = findViewById(R.id.toolbarc);
        totalassignments=findViewById(R.id.totalassignments);
        totalexams=findViewById(R.id.totalexams);
        totallectures=findViewById(R.id.totallectures);
        totalstudyplan=findViewById(R.id.totalstudyplan);
        setSupportActionBar(toolbar);
        drawerLayout=findViewById(R.id.drawerc);
        navigationView=findViewById(R.id.nav_viewc);
        toggle= new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(true);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

      calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
          @Override
          public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
              String Date = dayOfMonth+"-"+(month+1)+"-"+year;
              date_view.setText(Date);
              date_view.setVisibility(TextView.VISIBLE);
              db.collection(Date).document("Exam").addSnapshotListener(new EventListener<DocumentSnapshot>() {
                  @Override
                  public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                      if(value.exists()){
                          String exam=value.get("number").toString();
                          totalexams.setText(exam);
                          if(exam.equals("0")){
                          table.setVisibility(View.INVISIBLE);}
                          else{
                              table.setVisibility(View.VISIBLE);
                          }

                      }
                      else{
                          totalexams.setText("0");
                        table.setVisibility(View.INVISIBLE);
                      }
                  }
              });
              db.collection(Date).document("Assignment").addSnapshotListener(new EventListener<DocumentSnapshot>() {
                  @Override
                  public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                      if(value.exists()){
                          String Assign=value.get("number").toString();
                          totalassignments.setText(Assign);
                          if(!(Assign.equals("0"))){
                             table.setVisibility(View.VISIBLE);
                          }
                      }
                      else {
                          totalassignments.setText("0");
                      }
                  }
              });
              db.collection(Date).document("Lecture").addSnapshotListener(new EventListener<DocumentSnapshot>() {
                  @Override
                  public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                      if(value.exists()){
                          String Lect=value.get("number").toString();
                          totallectures.setText(Lect);
                          if(!(Lect.equals("0"))){
                              table.setVisibility(View.VISIBLE);
                          }
                      }
                      else{
                          totallectures.setText("0");
                      }
                  }
              });
              db.collection(Date).document("Study Plan").addSnapshotListener(new EventListener<DocumentSnapshot>() {
                  @Override
                  public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                      if(value.exists()){
                          String Stud=value.get("number").toString();

                      }

                          totalstudyplan.setText("0");
                          table.setVisibility(View.VISIBLE);

                  }
              });

          }
      });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.FirstItem:
                Intent intent=new Intent(this,MainActivity.class);
                startActivity(intent);

                return true;
            case R.id.SecondItem:
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            default:
                return true;
        }
    }
}