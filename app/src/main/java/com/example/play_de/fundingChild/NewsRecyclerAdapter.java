package com.example.play_de.fundingChild;

import android.annotation.SuppressLint;
import android.net.Uri;
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
import com.example.play_de.community.CommunityItem;

import java.util.ArrayList;

public class NewsRecyclerAdapter extends RecyclerView.Adapter<NewsRecyclerAdapter.ViewHolder> {
    private ArrayList<newsItem> mData;
    private OnItemClickListener mListener;

    static class newsItem {
        public String title;
        public String name;
        public String time;
    }

    void initialSetUp() {
        mData = new ArrayList<>();
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    newsItem getData(int position) {
        return mData.get(position);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, name, time;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            name = itemView.findViewById(R.id.name);
            time = itemView.findViewById(R.id.time);

            itemView.setOnClickListener(v -> {
                int pos = getAdapterPosition();
                if (pos != RecyclerView.NO_POSITION) {
                    if (mListener != null) {
                        mListener.onItemClick(pos);
                    }
                }
            });
        }
    }

    void addItem(newsItem item) {
        mData.add(item);
    }

    @NonNull
    @Override
    public NewsRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.funding_news_recycler, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull NewsRecyclerAdapter.ViewHolder holder, int position) {
        holder.title.setText(mData.get(position).title);
        holder.name.setText(mData.get(position).name);
        holder.time.setText(mData.get(position).time);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}
