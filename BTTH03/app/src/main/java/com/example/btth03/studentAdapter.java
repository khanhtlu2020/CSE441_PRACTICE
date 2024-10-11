package com.example.btth03;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.zip.Inflater;

public class studentAdapter extends RecyclerView.Adapter<studentAdapter.studentViewHoder>{
    private List<Student> students;
    private OnItemClickListener onClickItem;
    public interface OnItemClickListener{
        boolean onCreateOptionsMenu(Menu menu);

        void OnItemClicked(int position);
    }

    public studentAdapter(List<Student> students, OnItemClickListener onClickItem) {
        this.students = students;
        this.onClickItem = onClickItem;
    }
    public void updateData(List<Student> l){
        this.students=l;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public studentViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_student,parent,false);
        return new studentViewHoder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull studentViewHoder holder, int position) {
        Student student = students.get(position);
        if(student.getGender().trim().equals("Nữ")){
            holder.img.setImageResource(R.drawable.ic_female);
        }else{
            holder.img.setImageResource(R.drawable.ic_male);
        }
        holder.tv_id.setText(student.getId());
        holder.tv_name.setText(student.getFull_name().toString());
        holder.tv_gpa.setText(student.getGpa()+"");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickItem.OnItemClicked(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    public class studentViewHoder extends RecyclerView.ViewHolder{
        private ImageView img;
        private TextView tv_id,tv_name,tv_gpa;
        public studentViewHoder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img);
            tv_id = itemView.findViewById(R.id.tv_id);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_gpa = itemView.findViewById(R.id.tv_gpa);
        }
    }
}
