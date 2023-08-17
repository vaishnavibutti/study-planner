package com.example.myapplication;

import static android.content.ContentValues.TAG;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.viewHolder> {
    List<String> mItems;
    static ArrayList<ItemModel> arrayList;
    //ImageButton button;
    public RVAdapter(ArrayList<ItemModel> mItems){
        this.arrayList=mItems;
    }
    @Override
    public  viewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.first_list, viewGroup, false);
        return new viewHolder(view);
    }
    @Override
    public  void onBindViewHolder(viewHolder viewHolder, int position) {
        viewHolder.name.setText(arrayList.get(position).getName());
        viewHolder.time.setText(arrayList.get(position).getTime());
        viewHolder.date.setText(arrayList.get(position).getDate());
        viewHolder.topics.setText(arrayList.get(position).getTopics());
        viewHolder.subject.setText(arrayList.get(position).getCourse());
        if(arrayList.get(position).getTopics().isEmpty()){
            viewHolder.topics.setVisibility(View.INVISIBLE);
            viewHolder.topic_heading.setVisibility(View.INVISIBLE);
            viewHolder.linear.removeView(viewHolder.topic_heading);
            viewHolder.linear.removeView(viewHolder.topics);
         System.out.println("ok");
        }


        viewHolder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String d=arrayList.get(position).getName();
                String da = arrayList.get(position).getDate().replaceAll("/","-");
                arrayList.remove(position);



                notifyDataSetChanged();
                FirebaseFirestore.getInstance().collection("Exam").document(d).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(TAG, "DocumentSnapshot successfully deleted!");

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error deleting document", e);
                    }
                });
                FirebaseFirestore.getInstance().collection(da).document("Exam").update("number", FieldValue.increment(-1));



                Toast.makeText(v.getContext(), "delete", Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView date;
        TextView topics;
        ImageButton button;
        TextView time;
        TextView topic_heading;
        TextView subject;
        LinearLayout linear;

        public viewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            topic_heading=itemView.findViewById(R.id.textView12);
            button=(ImageButton) itemView.findViewById(R.id.button);
            time=itemView.findViewById(R.id.Times);
            date=itemView.findViewById(R.id.Dates);
            topics=itemView.findViewById(R.id.Topics);
            subject=itemView.findViewById(R.id.subject1);
            linear=itemView.findViewById(R.id.layout1);

        }



}
    public static class ItemModel {


        String name;
        String time;
        String date;
        String topics;
        String course;



        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getTopics() {
            return topics;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public void setTopics(String topics) {
            this.topics = topics;
        }

        public String getCourse() {
            return course;
        }

        public void setCourse(String course) {
            this.course = course;
        }
}}
