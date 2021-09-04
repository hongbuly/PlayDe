package com.example.play_de.community;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.play_de.R;

import java.util.ArrayList;

public class CommunityCommentAdapter extends RecyclerView.Adapter<CommunityCommentAdapter.ViewHolder> {
    private Context context;
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
        RelativeLayout comment01;
        ImageView image;
        TextView name;
        TextView content;

        TextView answer;
        ImageView three_dot;

        RelativeLayout comment02;
        ImageView image02;
        TextView name02;
        TextView content02;

        TextView answer02;
        ImageView three_dot02;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            comment01 = itemView.findViewById(R.id.comment01);
            image = itemView.findViewById(R.id.image);
            name = itemView.findViewById(R.id.name);
            content = itemView.findViewById(R.id.content);

            answer = itemView.findViewById(R.id.answer);
            three_dot = itemView.findViewById(R.id.three_dot);

            comment02 = itemView.findViewById(R.id.comment02);
            image02 = itemView.findViewById(R.id.image02);
            name02 = itemView.findViewById(R.id.name02);
            content02 = itemView.findViewById(R.id.content02);

            answer02 = itemView.findViewById(R.id.answer02);
            three_dot02 = itemView.findViewById(R.id.three_dot02);

            image.setOnClickListener(v -> {
                int pos = getAdapterPosition();
                if (pos != RecyclerView.NO_POSITION) {
                    if (mListener != null) {
                        mListener.onItemClick(0, pos);
                    }
                }
            });

            image02.setOnClickListener(v -> {
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

            answer02.setOnClickListener(v -> {
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

            three_dot02.setOnClickListener(v -> {
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
        context = view.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommunityCommentAdapter.ViewHolder holder, int position) {
        Uri uri = null;

        if (!mData.get(position).image.equals("")) {
            uri = Uri.parse(mData.get(position).image);
            Glide.with(context)
                    .load(uri)
                    .apply(new RequestOptions().circleCrop())
                    .into(holder.image02);
            Glide.with(context)
                    .load(uri)
                    .apply(new RequestOptions().circleCrop())
                    .into(holder.image);
        } else {
            holder.image.setImageResource(R.drawable.circle_grey);
        }

        if (mData.get(position).second_comment) {
            holder.comment01.setVisibility(View.GONE);
            holder.comment02.setVisibility(View.VISIBLE);
            holder.name02.setText(mData.get(position).name);
            holder.content02.setText(mData.get(position).comment);
        } else {
            holder.comment01.setVisibility(View.VISIBLE);
            holder.comment02.setVisibility(View.GONE);
            holder.name.setText(mData.get(position).name);
            holder.content.setText(mData.get(position).comment);
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}
