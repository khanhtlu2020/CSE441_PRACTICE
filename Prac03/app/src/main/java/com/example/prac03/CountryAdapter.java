package com.example.prac03;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.FoodViewHolder> {

    private List<CountryItem> countryList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(CountryItem countryItem);
    }

    public CountryAdapter(List<CountryItem> countryList, OnItemClickListener listener) {
        this.countryList = countryList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.country_item, parent, false);
        return new FoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        CountryItem countryItem = countryList.get(position);
        holder.CountryName.setText(countryItem.getName());
        holder.CountryImage.setImageResource(countryItem.getImageResourceId());
        holder.CountryDescription.setText(countryItem.getDescription());
        holder.itemView.setOnClickListener(v -> listener.onItemClick(countryItem));
    }

    @Override
    public int getItemCount() {
        return countryList.size();
    }

    public static class FoodViewHolder extends RecyclerView.ViewHolder {
        TextView CountryName;
        ImageView CountryImage;
        TextView CountryDescription;

        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);
            CountryName = itemView.findViewById(R.id.food_name);
            CountryImage = itemView.findViewById(R.id.food_image);
            CountryDescription = itemView.findViewById(R.id.food_des);
        }
    }
}
