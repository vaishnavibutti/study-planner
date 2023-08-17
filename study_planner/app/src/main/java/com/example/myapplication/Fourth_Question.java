package com.example.myapplication;

import static android.content.ContentValues.TAG;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Fourth_Question extends AppCompatActivity {
    EditText name;
    Button button;
    FirebaseFirestore db;
    DatePickerDialog picker;
    TimePickerDialog timePickerDialog;
    EditText date;
    EditText time;
    EditText topics;
    EditText subjects;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fourth_ques);
        name = findViewById(R.id.Name4);
        date = findViewById(R.id.Date4);
        time = findViewById(R.id.Time4);
        topics = findViewById(R.id.Topics4);
        subjects=findViewById(R.id.subject4);
        button=findViewById(R.id.sub4);
        db=FirebaseFirestore.getInstance();
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                picker= new DatePickerDialog(Fourth_Question.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        date.setText(dayOfMonth+"/"+(monthOfYear+1)+"/"+year);
                    }
                },year,month,day);
                picker.show();
            }
        });
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePickerDialog = new TimePickerDialog(Fourth_Question.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        time.setText(hourOfDay+":"+minute);
                    }
                },0,0,false);
                timePickerDialog.show();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dat= date.getText().toString().trim();
                //button.setVisibility(View.GONE);
                String nam = name.getText().toString().trim();
                String tim = time.getText().toString().trim();
                String topic = topics.getText().toString().trim();
                String subjec= subjects.getText().toString().trim();
                if(subjec.isEmpty()){
                    Toast.makeText(v.getContext(), "Course can't be empty", Toast.LENGTH_SHORT).show();
                    name.requestFocus();
                    return;
                }
                if(nam.isEmpty()){
                    Toast.makeText(v.getContext(), "Name can't be empty", Toast.LENGTH_SHORT).show();
                    name.requestFocus();
                    return;
                }
                else if(dat.isEmpty()){
                    Toast.makeText(v.getContext(), "date can't be empty", Toast.LENGTH_SHORT).show();
                    date.requestFocus();
                    return;

                }
                else if (tim.isEmpty()){

                    Toast.makeText(v.getContext(), "date can't be empty", Toast.LENGTH_SHORT).show();
                    date.requestFocus();
                    return;
                }
                Map<String, Object> city = new HashMap<>();
                Map<String,Object> s=new HashMap<>();
                s.put("number",1);
                city.put("name",nam);
                city.put("date",dat);
                city.put("time",tim);

                city.put("topics",topic);
                city.put("course",subjec);
                String xy=dat.replaceAll("/","-");
                db.collection("Study Plan").document(nam).set(city).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG,"Error writing document",e);
                    }
                });
                db.collection(xy).document("Study Plan").collection(nam).document(subjec).set(city).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        DocumentReference docref= db.collection(xy).document("Study Plan");
                        docref.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                            @Override
                            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                                if(!(value.contains("number"))){
                                    docref.set(s);
                                }


                            }
                        });
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG,"Error writing document",e);
                    }
                });
                finish();
                db.collection(xy).document("Study Plan").update("number", FieldValue.increment(1));


                RVAdapter.ItemModel itemModel = new RVAdapter.ItemModel();
                itemModel.setName(nam);
                itemModel.setTopics(topic);
                itemModel.setTime(tim);
                itemModel.setDate(dat);
                itemModel.setCourse(subjec);
                Fourth.arrayList.add(itemModel);
                Fourth.adapter =  new RVAdapter4(Fourth.arrayList);
                Fourth.adapter.notifyDataSetChanged();
                Fourth.recyclerview.setAdapter(Fourth.adapter);

                Fragment fragment = new Fourth();

                FragmentManager fragmentManager=getSupportFragmentManager();

//                fragmentManager.beginTransaction().replace(R.id.fi,fragment).addToBackStack(null).commit();
                fragmentManager.beginTransaction().replace(R.id.fi4,fragment).commit();
            }
        });
    }
}
