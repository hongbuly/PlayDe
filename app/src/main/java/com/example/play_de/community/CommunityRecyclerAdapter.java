package com.example.play_de.community;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.play_de.R;

import java.util.ArrayList;

public class CommunityRecyclerAdapter extends RecyclerView.Adapter<CommunityRecyclerAdapter.ViewHolder> {
    private ArrayList<CommunityItem> mData;
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

    CommunityItem getData(int position) {
        return mData.get(position);
    }

    void setHeart(int position) {
        if (mData.get(position).my_like) {
            mData.get(position).like--;
            mData.get(position).my_like = false;
        } else {
            mData.get(position).like++;
            mData.get(position).my_like = true;
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name;
        TextView level;
        TextView content;

        TextView heart;
        TextView comment;

        ImageView three_dot;
        TextView time;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.image);
            name = itemView.findViewById(R.id.name);
            level = itemView.findViewById(R.id.level);
            content = itemView.findViewById(R.id.content);

            heart = itemView.findViewById(R.id.heart);
            comment = itemView.findViewById(R.id.comment);

            three_dot = itemView.findViewById(R.id.three_dot);
            time = itemView.findViewById(R.id.time);

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

            three_dot.setOnClickListener(v -> {
                int pos = getAdapterPosition();
                if (pos != RecyclerView.NO_POSITION) {
                    if (mListener != null) {
                        mListener.onItemClick(4, pos);
                    }
                }
            });
        }
    }

    void addItem(CommunityItem item) {
        mData.add(item);
    }

    @NonNull
    @Override
    public CommunityRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.community_recycler, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull CommunityRecyclerAdapter.ViewHolder holder, int position) {
//        Glide.with(holder.itemView.getContext())
//                .load(mData.get(position).image)
//                .apply(new RequestOptions().circleCrop())
//                .into(holder.image);

        holder.image.setImageResource(Integer.parseInt(mData.get(position).image));
        holder.name.setText(mData.get(position).name);
        holder.level.setText(mData.get(position).level);
        holder.content.setText(mData.get(position).comment);
        holder.heart.setText("공감 " + mData.get(position).like);
        holder.comment.setText("댓글 " + mData.get(position).comment_cnt);
        holder.time.setText(mData.get(position).time);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}
