package com.example.play_de.community;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.play_de.R;

import java.util.ArrayList;

public class CommunityCommentAdapter extends RecyclerView.Adapter<CommunityCommentAdapter.ViewHolder> {
    private ArrayList<CommunityItem> mData = new ArrayList<>();
    private OnItemClickListener mListener;

    void initialSetUp() {
        mData = new ArrayList<>();
    }

    public interface OnItemClickListener {
        void onItemClick(int component, int position);
    }

    void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name;
        TextView level;
        TextView content;

        TextView answer;
        ImageView three_dot;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.image);
            name = itemView.findViewById(R.id.name);
            level = itemView.findViewById(R.id.level);
            content = itemView.findViewById(R.id.content);

            answer = itemView.findViewById(R.id.answer);
            three_dot = itemView.findViewById(R.id.three_dot);

            image.setOnClickListener(v -> {
                int pos = getAdapterPosition();
                if (pos != RecyclerView.NO_POSITION) {
                    if (mListener != null) {
                        mListener.onItemClick(0, pos);
                    }
                }
            });

            answer.setOnClickListener(v -> {
                int pos = getAdapterPosition();
                if (pos != RecyclerView.NO_POSITION) {
                    if (mListener != null) {
                        mListener.onItemClick(1, pos);
                    }
                }
            });

            three_dot.setOnClickListener(v -> {
                int pos = getAdapterPosition();
                if (pos != RecyclerView.NO_POSITION) {
                    if (mListener != null) {
                        mListener.onItemClick(2, pos);
                    }
                }
            });
        }
    }

    CommunityItem getData(int position) {
        return mData.get(position);
    }

    void addItem(CommunityItem item) {
        mData.add(item);
    }

    @NonNull
    @Override
    public CommunityCommentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_recycler, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommunityCommentAdapter.ViewHolder holder, int position) {
        holder.image.setImageResource(Integer.parseInt(mData.get(position).image));
        holder.name.setText(mData.get(position).name);
        holder.level.setText(mData.get(position).level);
        holder.content.setText(mData.get(position).comment);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}
