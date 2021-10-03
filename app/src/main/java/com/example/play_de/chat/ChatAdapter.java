package com.example.play_de.chat;

import android.content.Context;
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
    private List<ChatModel.CommentModel> comments;
    private List<String> keys;
    private List<Integer> dateSelect;
    private String destImage;
    private String chatRoomUid;
    private Context context;

    public ChatAdapter(String chatRoomUid, String destImage) {
        comments = new ArrayList<>();
        keys = new ArrayList<>();
        dateSelect = new ArrayList<>();
        this.destImage = destImage;
        this.chatRoomUid = chatRoomUid;

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
                        String date = "-1";

                        for (DataSnapshot item : snapshot.getChildren()) {
                            if (date.equals("-1") || !item.getValue(ChatModel.CommentModel.class).time.substring(0, 9).equals(date)) {
                                comments.add(item.getValue(ChatModel.CommentModel.class));
                                keys.add(item.getKey());
                                date = item.getValue(ChatModel.CommentModel.class).time.substring(0, 9);
                                dateSelect.add(comments.size() - 1);
                            }
                            comments.add(item.getValue(ChatModel.CommentModel.class));
                            keys.add(item.getKey());
                        }
                        notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView date;

        ImageView image;
        TextView left_text;
        TextView right_text;

        LinearLayout left_info, right_info;
        TextView left_time, left_read;
        TextView right_time, right_read;

        ImageView left_chat_img, right_chat_img;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.date);
            image = itemView.findViewById(R.id.image);
            left_text = itemView.findViewById(R.id.left_chat);
            right_text = itemView.findViewById(R.id.right_chat);
            left_info = itemView.findViewById(R.id.left_info);
            right_info = itemView.findViewById(R.id.right_info);
            left_time = itemView.findViewById(R.id.left_time);
            left_read = itemView.findViewById(R.id.left_read);
            right_time = itemView.findViewById(R.id.right_time);
            right_read = itemView.findViewById(R.id.right_read);
            left_chat_img = itemView.findViewById(R.id.left_chat_img);
            right_chat_img = itemView.findViewById(R.id.right_chat_img);
        }

        void onBind(ChatModel.CommentModel comment, int position) {
            if (dateSelect.contains(position)) {
                String[] time = comment.time.split(":");
                String date_txt = time[0] + "년 " + time[1] + "월 " + time[2] + "일";
                date.setText(date_txt);
                date.setVisibility(View.VISIBLE);
                right_info.setVisibility(View.GONE);
                right_text.setVisibility(View.GONE);
            } else {
                date.setVisibility(View.GONE);
                String uid = comment.myUid;
                String message = comment.message;
                String[] time = comment.time.split(":");
                String hour = time[3];
                String minute = time[4];
                String time_text = getTime(hour) + ":" + minute;

                if (uid.equals(MainActivity.userId)) {
                    right_info.setVisibility(View.VISIBLE);
                    left_info.setVisibility(View.GONE);
                    image.setVisibility(View.GONE);
                    left_text.setVisibility(View.GONE);
                    left_chat_img.setVisibility(View.GONE);
                    right_time.setText(time_text);
                    if (comment.read)
                        right_read.setText("읽음");

                    try {
                        if (message.substring(0, 6).equals("image:")) {
                            right_text.setVisibility(View.GONE);
                            right_chat_img.setVisibility(View.VISIBLE);
                            Uri uri = Uri.parse(message.substring(6));
                            Glide.with(context)
                                    .load(uri)
                                    .apply(new RequestOptions().fitCenter())
                                    .into(right_chat_img);
                        } else {
                            right_text.setVisibility(View.VISIBLE);
                            right_text.setText(message);
                        }
                    } catch (StringIndexOutOfBoundsException e) {
                        right_chat_img.setVisibility(View.GONE);
                        right_text.setVisibility(View.VISIBLE);
                        right_text.setText(message);
                    }
                } else {
                    //상대 메시지를 내가 본 것
                    read_chat(position);
                    right_info.setVisibility(View.GONE);
                    left_info.setVisibility(View.VISIBLE);
                    image.setVisibility(View.VISIBLE);
                    left_read.setText("읽음");
                    left_time.setText(time_text);
                    right_text.setVisibility(View.GONE);
                    right_chat_img.setVisibility(View.GONE);

                    try {
                        if (message.substring(0, 6).equals("image:")) {
                            right_text.setVisibility(View.GONE);
                            left_text.setVisibility(View.GONE);
                            left_chat_img.setVisibility(View.VISIBLE);
                            Uri uri = Uri.parse(message.substring(6));
                            Glide.with(context)
                                    .load(uri)
                                    .apply(new RequestOptions().fitCenter())
                                    .into(left_chat_img);
                        } else {
                            left_text.setVisibility(View.VISIBLE);
                            left_text.setText(message);
                        }
                    } catch (StringIndexOutOfBoundsException e) {
                        left_chat_img.setVisibility(View.GONE);
                        left_text.setVisibility(View.VISIBLE);
                        left_text.setText(message);
                    }
                }
            }
        }

        String getTime(String hour) {
            if (Integer.parseInt(hour) < 12)
                return "오후 " + hour;
            else
                return "오전 " + (Integer.parseInt(hour) - 12);
        }

        void read_chat(int position) {
            if (!dateSelect.contains(position)) {
                FirebaseDatabase
                        .getInstance()
                        .getReference()
                        .child("chatRooms")
                        .child(chatRoomUid)
                        .child("comments")
                        .child(keys.get(position))
                        .child("read")
                        .setValue(true);
            }
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
        context = holder.itemView.getContext();
        //이미지만 여기서 함.
        if (destImage.equals("")) {
            holder.image.setImageResource(R.drawable.default_user);
        } else {
            Uri uri = Uri.parse(destImage);
            Glide.with(context)
                    .load(uri)
                    .apply(new RequestOptions().circleCrop())
                    .into(holder.image);
        }

        holder.onBind(comments.get(position), position);
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }
}
