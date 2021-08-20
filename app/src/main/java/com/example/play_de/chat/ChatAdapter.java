package com.example.play_de.chat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.play_de.R;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {
    private TextView left_text;
    private TextView right_text;

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView text;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.image);
            left_text = itemView.findViewById(R.id.left_chat);
            right_text = itemView.findViewById(R.id.right_chat);
        }

        void onBind() {
            image.setImageResource(R.drawable.cafe01);

//            if (item.uid.equals(myUid)) {
//                image.setVisibility(View.GONE);
//                left_text.setVisibility(View.GONE);
//                right_text.setVisibility(View.VISIBLE);
//                text = right_text;
//            } else {
//                left_text.setVisibility(View.VISIBLE);
//                right_text.setVisibility(View.GONE);
//                text = left_text;
//            }
//            text.setText(item.message);
        }
    }

    @NonNull
    @Override
    public ChatAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_listview, parent, false);
        return new ChatAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        holder.onBind(comments.get(position));
    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
