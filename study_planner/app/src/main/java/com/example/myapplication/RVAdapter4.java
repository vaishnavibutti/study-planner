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

public class RVAdapter4 extends RecyclerView.Adapter<RVAdapter4.viewHolder4>{
    List<String> mItems;
    static ArrayList<RVAdapter.ItemModel> arrayList;
    public RVAdapter4(ArrayList<RVAdapter.ItemModel> mItems){arrayList=mItems;}
    @NonNull
    @Override
    public RVAdapter4.viewHolder4 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.first_list,parent,false);
        return new RVAdapter4.viewHolder4(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RVAdapter4.viewHolder4 holder, int position) {
        holder.name.setText(arrayList.get(position).getName()+" Minutes");
        holder.time.setText(arrayList.get(position).getTime());
        holder.date.setText(arrayList.get(position).getDate());
        holder.topics.setText(arrayList.get(position).getTopics());
        holder.subject.setText(arrayList.get(position).getCourse());
        if(arrayList.get(position).getTopics().isEmpty()) {
            holder.topics.setVisibility(View.INVISIBLE);
            holder.topic_heading.setVisibility(View.INVISIBLE);
            holder.linear.removeView(holder.topic_heading);
            holder.linear.removeView(holder.topics);
            System.out.println("ok");
        }
            holder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String d=arrayList.get(position).getName();
                    String da = arrayList.get(position).getDate().replaceAll("/","-");
                    arrayList.remove(position);



                    notifyDataSetChanged();
                    FirebaseFirestore.getInstance().collection("Study Plan").document(d).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
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
                    FirebaseFirestore.getInstance().collection(da).document("Study Plan").update("number", FieldValue.increment(-1));



                    Toast.makeText(v.getContext(), "delete", Toast.LENGTH_SHORT).show();
                }
            });

    }

    @Override
    public int getItemCount() {
            return arrayList.size();
    }
        public class viewHolder4 extends RecyclerView.ViewHolder{
            TextView name;
            TextView date;
            TextView topics;
            ImageButton button;
            TextView time;
            TextView topic_heading;
            TextView subject;
            LinearLayout linear;
            public viewHolder4(View itemView) {
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
        String subject;



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

        public String getSubject() {
            return subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }
    }

}
