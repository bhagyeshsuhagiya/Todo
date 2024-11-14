package com.example.todo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private Activity activity;
    private Context context;
    private ArrayList<String> task_id, task_name, task_description;

    public CustomAdapter(Activity activity, Context context, ArrayList<String> task_id, ArrayList<String> task_name, ArrayList<String> task_description) {
        this.activity = activity;
        this.context = context;
        this.task_id = task_id;
        this.task_name = task_name;
        this.task_description = task_description;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_raw, parent, false);
        return new MyViewHolder(view,context);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.idTxt.setText(task_id.get(position));
        holder.taskTxt.setText(task_name.get(position));
        holder.descTxt.setText(task_description.get(position));
        holder.myRaw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UpdateActivity.class);
                intent.putExtra("id", task_id.get(position));
                intent.putExtra("task", task_name.get(position));
                intent.putExtra("desc", task_description.get(position));
                activity.startActivityForResult(intent, 1);  // Assuming request code is 1
            }
        });
    }

    @Override
    public int getItemCount() {
        return task_id.size();
    }

     class MyViewHolder extends RecyclerView.ViewHolder {
        TextView idTxt, taskTxt, descTxt;
        LinearLayout myRaw;


        public MyViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            idTxt = itemView.findViewById(R.id.id_txt);
            taskTxt = itemView.findViewById(R.id.task_txt);
            descTxt = itemView.findViewById(R.id.desc_txt);
            myRaw = itemView.findViewById(R.id.myraw);
            Animation anim_anim = AnimationUtils.loadAnimation(context,R.anim.anim_anim);
            myRaw.setAnimation(anim_anim);

        }
    }
}
