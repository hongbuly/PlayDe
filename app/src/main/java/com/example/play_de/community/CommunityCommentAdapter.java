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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CommunityCommentAdapter extends RecyclerView.Adapter<CommunityCommentAdapter.ViewHolder> {
    private ArrayList<CommunityComment> mData = new ArrayList<>();
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
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

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.image);
            name = itemView.findViewById(R.id.name);
            level = itemView.findViewById(R.id.level);
            content = itemView.findViewById(R.id.content);

            answer = itemView.findViewById(R.id.answer);

            image.setOnClickListener(v -> {
                int pos = getAdapterPosition();
                if (pos != RecyclerView.NO_POSITION) {
                    if (mListener != null) {
                        mListener.onItemClick(pos);
                    }
                }
            });

            answer.setOnClickListener(v -> {
                int pos = getAdapterPosition();
                if (pos != RecyclerView.NO_POSITION) {
                    if (mListener != null) {
                        mListener.onItemClick(pos);
                    }
                }
            });
        }
    }

    CommunityComment getData(int position) {
        return mData.get(position);
    }

    void addItem(CommunityComment item) {
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
        holder.image.setImageResource(mData.get(position).image);
        holder.name.setText(mData.get(position).name);
        holder.level.setText(mData.get(position).level);
        holder.content.setText(mData.get(position).comment);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}
