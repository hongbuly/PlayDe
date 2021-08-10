package com.example.play_de;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CafeRecyclerAdapter extends RecyclerView.Adapter<CafeRecyclerAdapter.ViewHolder> {
    private ArrayList<CafeRecyclerItem> mData;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
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
                        mListener.onItemClick(v, pos);
                    }
                }
            });
        }
    }

    CafeRecyclerAdapter(ArrayList<CafeRecyclerItem> list) {
        mData = list;
    }

    @NonNull
    @Override
    public CafeRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.cafe_listview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CafeRecyclerAdapter.ViewHolder holder, int position) {
        int imageNum = mData.get(position).getImage();
        holder.image.setImageResource(imageNum);
        String nameText = mData.get(position).getName();
        holder.name.setText(nameText);
        String addressText = mData.get(position).getAddress();
        holder.address.setText(addressText);
        String tableText = mData.get(position).getTable();
        holder.table.setText(tableText);
        String timeText = mData.get(position).getTime();
        holder.time.setText(timeText);
        String heartText = mData.get(position).getHeart();
        holder.heart.setText(heartText);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}
