package com.example.play_de.cafe;

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

import java.util.ArrayList;

public class CafeRecyclerAdapter extends RecyclerView.Adapter<CafeRecyclerAdapter.ViewHolder> {
    private ArrayList<CafeRecyclerItem> mData = new ArrayList<>();
    private OnItemClickListener mListener;

    void initialSetUp() {
        mData = new ArrayList<>();
    }

    public interface OnItemClickListener {
        void onItemClick(int component, int position);
    }

    CafeRecyclerItem getData(int position) {
        return mData.get(position);
    }

    void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    void setHeart(int position) {
        if (mData.get(position).my_like) {
            mData.get(position).heart--;
            mData.get(position).my_like = false;
        } else {
            mData.get(position).heart++;
            mData.get(position).my_like = true;
        }
    }

    float getLatitude(int position) {
        return Float.parseFloat(mData.get(position).location.substring(0, mData.get(position).location.lastIndexOf(",")));
    }

    float getLongitude(int position) {
        return Float.parseFloat(mData.get(position).location.substring(mData.get(position).location.lastIndexOf(",") + 1));
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name;
        TextView address;
        TextView table;
        TextView time;
        TextView heart;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.image);
            name = itemView.findViewById(R.id.cafe_name);
            address = itemView.findViewById(R.id.cafe_address);
            table = itemView.findViewById(R.id.cafe_table);
            time = itemView.findViewById(R.id.cafe_time);
            heart = itemView.findViewById(R.id.cafe_heart);

            itemView.setOnClickListener(v -> {
                int pos = getAdapterPosition();
                if (pos != RecyclerView.NO_POSITION) {
                    if (mListener != null) {
                        mListener.onItemClick(0, pos);
                    }
                }
            });

            heart.setOnClickListener(v -> {
                int pos = getAdapterPosition();
                if (pos != RecyclerView.NO_POSITION) {
                    if (mListener != null) {
                        mListener.onItemClick(1, pos);
                    }
                }
            });
        }
    }

    void addItem(CafeRecyclerItem item) {
        mData.add(item);
    }

    @NonNull
    @Override
    public CafeRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cafe_listview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CafeRecyclerAdapter.ViewHolder holder, int position) {
        if (mData.get(position).image.equals("")) {
            holder.image.setImageResource(R.drawable.cafe01);
        } else
            holder.image.setImageURI(Uri.parse(mData.get(position).image));
        holder.name.setText(mData.get(position).name);
        holder.address.setText(mData.get(position).address);
        holder.table.setText(mData.get(position).table);
        holder.time.setText(mData.get(position).getTime());
        holder.heart.setText(mData.get(position).getHeart());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}
