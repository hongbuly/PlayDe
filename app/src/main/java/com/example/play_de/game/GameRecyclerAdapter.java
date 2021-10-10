package com.example.play_de.game;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.play_de.R;
import com.example.play_de.community.CommunityCommentAdapter;
import com.example.play_de.community.CommunityItem;

import java.util.ArrayList;

public class GameRecyclerAdapter extends RecyclerView.Adapter<GameRecyclerAdapter.ViewHolder> {
    private Context context;
    private ArrayList<String> mData = new ArrayList<>();
    private OnItemClickListener mListener;

    void initialSetUp() {
        mData = new ArrayList<>();
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            GradientDrawable drawable = (GradientDrawable) context.getDrawable(R.drawable.image_round10);
            image.setBackground(drawable);
            image.setClipToOutline(true);

            image.setOnClickListener(v -> {
                int pos = getAdapterPosition();
                if (pos != RecyclerView.NO_POSITION) {
                    if (mListener != null) {
                        mListener.onItemClick(pos);
                    }
                }
            });
        }
    }

    @NonNull
    @Override
    public GameRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.game_recycler, parent, false);
        context = view.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GameRecyclerAdapter.ViewHolder holder, int position) {
        holder.image.setImageURI(Uri.parse(mData.get(position)));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}
