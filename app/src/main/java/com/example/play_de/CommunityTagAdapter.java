package com.example.play_de;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CommunityTagAdapter extends RecyclerView.Adapter<CommunityTagAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<String> mData = new ArrayList<>();
    private ArrayList<Boolean> selectedPosition = new ArrayList<>();

    class ViewHolder extends RecyclerView.ViewHolder {
        Button tag;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tag = itemView.findViewById(R.id.tag);
        }
    }

    void addItem(String item) {
        mData.add(item);
        selectedPosition.add(false);
    }

    @NonNull
    @Override
    public CommunityTagAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.community_tag, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommunityTagAdapter.ViewHolder holder, int position) {
        holder.tag.setText(mData.get(position));
        if (selectedPosition.get(position)) {
            holder.tag.setTextColor(ContextCompat.getColor(mContext, R.color.White));
            holder.tag.setBackgroundResource(R.drawable.round_red_tag);
        } else {
            holder.tag.setTextColor(ContextCompat.getColor(mContext, R.color.Black));
            holder.tag.setBackgroundResource(R.drawable.round_grey_tag);
        }

        holder.tag.setOnClickListener(v -> {
            if (selectedPosition.get(position))
                selectedPosition.set(position, false);
            else
                selectedPosition.set(position, true);
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}