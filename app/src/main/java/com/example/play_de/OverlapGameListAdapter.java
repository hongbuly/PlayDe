package com.example.play_de;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class OverlapGameListAdapter extends RecyclerView.Adapter<OverlapGameListAdapter.ViewHolder> {
    private ArrayList<Drawable> itemList;
    private Context context;
    private View.OnClickListener onClickItem;

    public OverlapGameListAdapter(Context context, ArrayList<Drawable> itemList, View.OnClickListener onClickItem) {
        this.context = context;
        this.itemList = itemList;
        this.onClickItem = onClickItem;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // context 와 parent.getContext() 는 같다.
        View view = LayoutInflater.from(context).inflate(R.layout.overlap_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OverlapGameListAdapter.ViewHolder holder, int position) {
        Drawable item = itemList.get(position);

        //여기서 아이템에 대한 설정을 하면됨.
        holder.imageView.setBackground(item);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.overlap_item);
        }
    }
}
