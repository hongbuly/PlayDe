package com.example.play_de.chat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.play_de.R;
import com.example.play_de.main.MainActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {
    private List<ChatModel> comments;
    private String uid;

    public ChatAdapter(String chatRoomUid, String uid) {
        comments = new ArrayList<>();
        this.uid = uid;

        FirebaseDatabase
                .getInstance()
                .getReference()
                .child("chatRooms")
                .child(chatRoomUid)
                .child("comments")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        comments.clear();

                        for (DataSnapshot item : snapshot.getChildren()) {
                            comments.add(item.getValue(ChatModel.class));
                        }
                        notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView left_text;
        TextView right_text;
        TextView text;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.image);
            left_text = itemView.findViewById(R.id.left_chat);
            right_text = itemView.findViewById(R.id.right_chat);
        }

        void onBind(ChatModel comment) {
            image.setImageResource(R.drawable.cafe01);

            if (comment.comments.get(uid).equals(MainActivity.userId)) {
                image.setVisibility(View.GONE);
                left_text.setVisibility(View.GONE);
                right_text.setVisibility(View.VISIBLE);
                text = right_text;
            } else {
                left_text.setVisibility(View.VISIBLE);
                right_text.setVisibility(View.GONE);
                text = left_text;
            }
            text.setText(comment.comments.get("message"));
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
        holder.onBind(comments.get(position));
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }
}
