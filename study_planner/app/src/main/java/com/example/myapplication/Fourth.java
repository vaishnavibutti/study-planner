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


public class Fourth extends Fragment {
    FloatingActionButton Add;
    FirebaseFirestore db;
    List<DocumentSnapshot> mylist;
    public static ArrayList<First_Type> mArrayList  ;
    static RVAdapter4 adapter;
    static RecyclerView recyclerview;
    static ArrayList<RVAdapter.ItemModel> arrayList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mArrayList= new ArrayList<>();
        arrayList = new ArrayList<>();
        db=FirebaseFirestore.getInstance();

        View rootview= inflater.inflate(R.layout.fragment_first, container, false);
        Add=rootview.findViewById(R.id.add);
        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getContext(), "HEYY", Toast.LENGTH_SHORT).show();
                //startActivity(new Intent(getActivity(),Second.class));

                Intent intent = new Intent(getActivity(),Fourth_Question.class);
                ((MainActivity) getActivity()).startActivity(intent);

            }
        });
        db.collection("Study Plan").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
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

                                                                           RVAdapter4 adapter = new RVAdapter4(arrayList);
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
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerview = (RecyclerView) rootview.findViewById(R.id.recyclerview);
        recyclerview.setLayoutManager(layoutManager);

        return rootview;
    }

    }
