package com.example.mission2;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mission2.model.User;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private ArrayList<User> userArrayList;
    private OnCardListener onCardListener;

    public RecyclerViewAdapter(ArrayList<User> dataUser, Activity activity, OnCardListener onCardListener) {
        userArrayList = dataUser;
        this.onCardListener = onCardListener;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.card_user, parent, false);
        return new ViewHolder(view, onCardListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {
        holder.cardTextViewFullName.setText(userArrayList.get(position).getFullName());
        holder.cardTextViewAge.setText(userArrayList.get(position).getAge() + " years old");
        holder.cardTextViewAddress.setText(userArrayList.get(position).getAddress());
    }

    @Override
    public int getItemCount() {
        return userArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView cardTextViewFullName;
        TextView cardTextViewAge;
        TextView cardTextViewAddress;
        OnCardListener onCardListener;

        public ViewHolder(@NonNull View itemView, OnCardListener onCardListener) {
            super(itemView);
            cardTextViewFullName = itemView.findViewById(R.id.cardTextViewFullName);
            cardTextViewAge = itemView.findViewById(R.id.cardTextViewAge);
            cardTextViewAddress = itemView.findViewById(R.id.cardTextViewAddress);
            this.onCardListener = onCardListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onCardListener.onCardClick(getAdapterPosition());
        }
    }

    public interface OnCardListener {
        void onCardClick(int position);
    }
}
