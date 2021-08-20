package com.example.play_de.community;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.play_de.R;

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
            holder.tag.setBackgroundResource(R.drawable.round_red20);
        } else {
            holder.tag.setTextColor(ContextCompat.getColor(mContext, R.color.LineGrey));
            holder.tag.setBackgroundResource(R.drawable.round_corner_line20);
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