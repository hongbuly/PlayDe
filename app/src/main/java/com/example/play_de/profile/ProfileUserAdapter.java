package com.example.play_de.profile;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.play_de.R;

import java.util.ArrayList;

public class ProfileUserAdapter extends RecyclerView.Adapter<ProfileUserAdapter.ViewHolder> {
    private ArrayList<ProfileUser> mData = new ArrayList<>();

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name;
        Switch mSwitch;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            name = itemView.findViewById(R.id.name);
            mSwitch = itemView.findViewById(R.id.mSwitch);
        }
    }

    void addItem(ProfileUser item) {
        mData.add(item);
    }

    @NonNull
    @Override
    public ProfileUserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_recycler, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileUserAdapter.ViewHolder holder, int position) {
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
        holder.mSwitch.setTextOn(mData.get(position).switchOn);
        holder.mSwitch.setTextOff(mData.get(position).switchOff);

        holder.mSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            mData.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, mData.size());
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}