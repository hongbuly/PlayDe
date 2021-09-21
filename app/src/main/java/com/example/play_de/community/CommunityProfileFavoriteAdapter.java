package com.example.play_de.community;

import android.net.Uri;
import android.util.Log;
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

    public void addItem(CommunityProfileFavorite item) {
        mData.add(item);
    }

    @NonNull
    @Override
    public CommunityProfileFavoriteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_favorite_listview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommunityProfileFavoriteAdapter.ViewHolder holder, int position) {
        Log.e("image", mData.get(position).image);
        if (mData.get(position).image.equals("{\"order\":null}")) {
            holder.image.setImageResource(R.drawable.circle_grey);
        } else {
            Uri uri = Uri.parse(mData.get(position).image);
            Glide.with(holder.itemView.getContext())
                    .load(uri)
                    .apply(new RequestOptions().circleCrop())
                    .into(holder.image);
        }
        holder.name.setText(mData.get(position).name);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}
