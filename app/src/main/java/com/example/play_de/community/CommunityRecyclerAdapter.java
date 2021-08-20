package com.example.play_de.community;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.play_de.R;
import com.example.play_de.chat.UserModel;

import java.util.ArrayList;

public class CommunityRecyclerAdapter extends RecyclerView.Adapter<CommunityRecyclerAdapter.ViewHolder> {
    private ArrayList<UserModel> mData = new ArrayList<>();
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int component, int position);
    }

    void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    UserModel getData(int position) {
        return mData.get(position);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name;
        TextView level;
        TextView content;

        TextView heart;
        TextView comment;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.image);
            name = itemView.findViewById(R.id.name);
            level = itemView.findViewById(R.id.level);
            content = itemView.findViewById(R.id.content);

            heart = itemView.findViewById(R.id.heart);
            comment = itemView.findViewById(R.id.comment);

            image.setOnClickListener(v -> {
                int pos = getAdapterPosition();
                if (pos != RecyclerView.NO_POSITION) {
                    if (mListener != null) {
                        mListener.onItemClick(1, pos);
                    }
                }
            });

            heart.setOnClickListener(v -> {
                int pos = getAdapterPosition();
                if (pos != RecyclerView.NO_POSITION) {
                    if (mListener != null) {
                        mListener.onItemClick(2, pos);
                    }
                }
            });

            comment.setOnClickListener(v -> {
                int pos = getAdapterPosition();
                if (pos != RecyclerView.NO_POSITION) {
                    if (mListener != null) {
                        mListener.onItemClick(3, pos);
                    }
                }
            });
        }
    }

    @NonNull
    @Override
    public CommunityRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.community_recycler, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommunityRecyclerAdapter.ViewHolder holder, int position) {
        Glide.with(holder.itemView.getContext())
                .load(mData.get(position).image)
                .apply(new RequestOptions().circleCrop())
                .into(holder.image);
        holder.name.setText(mData.get(position).name);
        holder.level.setText(mData.get(position).level);
        holder.content.setText(mData.get(position).pushToken);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}
