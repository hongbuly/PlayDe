package com.example.play_de.chat;

import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.play_de.R;
import com.example.play_de.main.AppHelper;
import com.example.play_de.main.MainActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {
    private List<String> comments;
    private String destImage;

    public ChatAdapter(String chatRoomUid, String destImage) {
        comments = new ArrayList<>();
        this.destImage = destImage;

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
                            comments.add((String) item.getValue());
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

        LinearLayout left_info, right_info;
        TextView left_time, left_read;
        TextView right_time, right_read;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.image);
            left_text = itemView.findViewById(R.id.left_chat);
            right_text = itemView.findViewById(R.id.right_chat);
            left_info = itemView.findViewById(R.id.left_info);
            right_info = itemView.findViewById(R.id.right_info);
            left_time = itemView.findViewById(R.id.left_time);
            left_read = itemView.findViewById(R.id.left_read);
            right_time = itemView.findViewById(R.id.right_time);
            right_read = itemView.findViewById(R.id.right_read);
        }

        void onBind(String comment) {
            String[] chat_model = comment.split(":");
            String uid = chat_model[0];
            String message = chat_model[1];
            String hour = chat_model[2];
            String minute = chat_model[3];
            String time = getTime(hour) + ":" + minute;

            if (uid.equals(MainActivity.userId)) {
                right_info.setVisibility(View.VISIBLE);
                left_info.setVisibility(View.GONE);
                image.setVisibility(View.GONE);
                left_text.setVisibility(View.GONE);
                right_text.setVisibility(View.VISIBLE);
                right_time.setText(time);
                text = right_text;
            } else {
                right_info.setVisibility(View.GONE);
                left_info.setVisibility(View.VISIBLE);
                image.setVisibility(View.VISIBLE);
                left_text.setVisibility(View.VISIBLE);
                right_text.setVisibility(View.GONE);
                left_time.setText(time);
                text = left_text;
            }

            text.setText(message);
        }

        String getTime(String hour) {
            if (Integer.parseInt(hour) < 12)
                return "오후 " + hour;
            else
                return "오전 " + (Integer.parseInt(hour) - 12);
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
        //이미지만 여기서 함.
        Uri uri = Uri.parse(destImage);
        Glide.with(holder.itemView.getContext())
                .load(uri)
                .apply(new RequestOptions().circleCrop())
                .into(holder.image);

        holder.onBind(comments.get(position));
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }
}
