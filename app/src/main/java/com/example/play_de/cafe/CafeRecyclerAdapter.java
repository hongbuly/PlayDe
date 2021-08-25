package com.example.play_de.cafe;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.play_de.R;

import java.util.ArrayList;

public class CafeRecyclerAdapter extends RecyclerView.Adapter<CafeRecyclerAdapter.ViewHolder> {
    private ArrayList<CafeRecyclerItem> mData = new ArrayList<>();
    private OnItemClickListener mListener;

    void initialSetUp() {
        mData = new ArrayList<>();
    }

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

        void onBind(CafeRecyclerItem item) {
            image.setImageResource(item.getImage());
            name.setText(item.getName());
            address.setText(item.getAddress());
            table.setText(item.getTable());
            time.setText(item.getTime());
            heart.setText(item.getHeart());
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
        holder.onBind(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}
