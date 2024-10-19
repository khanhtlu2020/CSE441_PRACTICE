package com.example.btth04;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.ViewHolder> {
    Context context;
    ArrayList<Student> arrayList;
    OnItemClickListener onItemClickListener;

    public StudentAdapter(Context context, ArrayList<Student> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.student_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Student student = arrayList.get(position);
        holder.ten.setText(student.getTen());
        holder.mssv.setText("MSSV: " + student.getMssv());
        holder.lop.setText("Lớp: " + student.getLop());
        holder.diemtb.setText("Điểm trung bình: " + student.getDiemtb());
        holder.itemView.setOnClickListener(view -> onItemClickListener.onClick(student));
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView ten, mssv, lop, diemtb;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ten = itemView.findViewById(R.id.list_item_hoten);
            mssv = itemView.findViewById(R.id.list_item_mssv);
            lop = itemView.findViewById(R.id.list_item_lop);
            diemtb = itemView.findViewById(R.id.list_item_diemtb);
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onClick(Student student);
    }
}