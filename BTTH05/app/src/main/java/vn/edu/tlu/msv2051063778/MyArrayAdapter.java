package vn.edu.tlu.msv2051063778;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyArrayAdapter extends RecyclerView.Adapter<MyArrayAdapter.ViewHolder> {

    private Activity context;
    private ArrayList<Item> myArray;
    private int layoutId;

    public MyArrayAdapter(Activity context, int layoutId, ArrayList<Item> arr) {
        this.context = context;
        this.layoutId = layoutId;
        this.myArray = arr;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = context.getLayoutInflater();
        View itemView = inflater.inflate(layoutId, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Item myItem = myArray.get(position);

        holder.tenMon.setText(myItem.getTenMon());
        holder.donGia.setText(String.format("%.0f đ", myItem.getDonGia()));

        int resId = context.getResources().getIdentifier(myItem.getHinh(), "drawable", context.getPackageName());
        holder.imgMon.setImageResource(resId);

        // Xử lý sự kiện khi nhấn vào nút "Đặt món"
        holder.btnDatMon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Đã đặt: " + myItem.getTenMon(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return myArray.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgMon;
        TextView tenMon, donGia;
        Button btnDatMon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgMon = itemView.findViewById(R.id.imgMon);
            tenMon = itemView.findViewById(R.id.txtTenMon);
            donGia = itemView.findViewById(R.id.txtGiaMon);
            btnDatMon = itemView.findViewById(R.id.btnDatMon);
        }
    }
}
