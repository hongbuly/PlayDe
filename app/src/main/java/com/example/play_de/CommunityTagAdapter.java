package com.example.play_de;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CommunityTagAdapter extends RecyclerView.Adapter<CommunityTagAdapter.ViewHolder> {
    private ArrayList<String> mData = new ArrayList<>();

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    private OnItemClickListener mListener = (position) -> {

    };

    class ViewHolder extends RecyclerView.ViewHolder {
        Button tag;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            tag = itemView.findViewById(R.id.tag);

            itemView.setOnClickListener(v -> {
                int pos = getAdapterPosition();
                if (pos != RecyclerView.NO_POSITION) {
                    mListener.onItemClick(pos);
                }
            });
        }

        void onBind(boolean isClicked, String item) {
            if (isClicked) {
                tag.setText(item);
                tag.setBackgroundResource(R.drawable.round_red_tag);
                tag.setTextColor(Color.WHITE);
            }
        }
    }

    void addItem(String item) {
        mData.add(item);
    }

    @NonNull
    @Override
    public CommunityTagAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.community_tag, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommunityTagAdapter.ViewHolder holder, int position) {
        holder.onBind(false, mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}
