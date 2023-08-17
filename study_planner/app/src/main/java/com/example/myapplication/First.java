package com.example.myapplication;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class First extends Fragment {
    FloatingActionButton Add;
    FirebaseFirestore db;
    List<DocumentSnapshot> mylist;
    public static ArrayList<First_Type> mArrayList  ;
    static RVAdapter adapter;

    String name[]={"home","add","calender"};

    static RecyclerView recyclerview;
    static ArrayList<RVAdapter.ItemModel> arrayList;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mArrayList= new ArrayList<>();
        arrayList = new ArrayList<>();
        db=FirebaseFirestore.getInstance();

        db.collection("Exam").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                                             @Override
                                                             public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                                                 if(queryDocumentSnapshots.isEmpty()){
                                                                     Log.d(TAG,"onSucces:LISTEMPTY");
                                                                     return;
                                                                 }
                                                                 else{
                                                                     List<First_Type> firstTypes =queryDocumentSnapshots.toObjects(First_Type.class);

                                                                     mArrayList.addAll(firstTypes);
                                                                     Log.d(TAG,"onSuccess: "+mArrayList);

                                                                     System.out.println(mArrayList.get(0).getName());
                                                                     System.out.println(mArrayList.size());
                                                                     for(int i=0;i<mArrayList.size();i++){
                                                                         RVAdapter.ItemModel itemModel = new RVAdapter.ItemModel();
                                                                         String x = mArrayList.get(i).getTopics().replaceAll("\n",",");
                                                                         itemModel.setName(mArrayList.get(i).getName());
                                                                         itemModel.setTime(mArrayList.get(i).getTime());
                                                                         itemModel.setDate(mArrayList.get(i).getDate());
                                                                         itemModel.setTime(mArrayList.get(i).getTime());
                                                                         itemModel.setCourse(mArrayList.get(i).getCourse());
                                                                         itemModel.setTopics(x);
                                                                         arrayList.add(itemModel);
                                                                     }

                                                                    RVAdapter adapter = new RVAdapter(arrayList);

                                                                     recyclerview.setAdapter(adapter);

                                                                 }
                                                             }
                                                         }

        ).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(),"ERROR GETTING DATA",Toast.LENGTH_LONG).show();
            }
        });
        // Inflate the layout for this fragment
        View rootView=inflater.inflate(R.layout.fragment_first, container, false);
        Add= rootView.findViewById(R.id.add);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerview = (RecyclerView) rootView.findViewById(R.id.recyclerview);
        recyclerview.setLayoutManager(layoutManager);
        System.out.println(mArrayList.size());

        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getContext(), "HEYY", Toast.LENGTH_SHORT).show();
              //startActivity(new Intent(getActivity(),Second.class));

                Intent intent = new Intent(getActivity(),First_Question.class);
                ((MainActivity) getActivity()).startActivity(intent);

            }
        });



        return rootView;

    }










        }




