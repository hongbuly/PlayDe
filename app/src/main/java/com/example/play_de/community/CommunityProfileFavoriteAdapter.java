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

public class CommunityProfileFavoriteAdapter extends RecyclerView.Adapter<CommunityProfileFavoriteAdapter.ViewHolder> {
    private ArrayList<CommunityProfileFavorite> mData = new ArrayList<>();

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.image);
            name = itemView.findViewById(R.id.name);
        }
    }

    void addItem(CommunityProfileFavorite item) {
        mData.add(item);
    }

    @NonNull
    @Override
    public CommunityProfileFavoriteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favorite_recycler, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommunityProfileFavoriteAdapter.ViewHolder holder, int position) {
//        Glide.with(holder.itemView.getContext())
//                .load(mData.get(position).image)
//                .apply(new RequestOptions().circleCrop())
//                .into(holder.image);
        holder.image.setImageResource(mData.get(position).image);
        holder.name.setText(mData.get(position).name);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}
