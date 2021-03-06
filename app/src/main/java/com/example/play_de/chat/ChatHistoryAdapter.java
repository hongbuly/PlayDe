package com.example.play_de.chat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.play_de.R;
import com.example.play_de.community.CommunityItem;
import com.example.play_de.main.AppHelper;
import com.example.play_de.main.MainActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class ChatHistoryAdapter extends RecyclerView.Adapter<ChatHistoryAdapter.ViewHolder> {
    private ArrayList<ChatModel> chatModels = new ArrayList<>();
    private OnItemClickListener mListener;
    private String name, image, destUid;
    private Context context;

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    ChatHistoryAdapter() {
        setChatModels();
    }

    void setChatModels() {
        FirebaseDatabase
                .getInstance()
                .getReference()
                .child("chatRooms")
                .orderByChild("users/" + MainActivity.userId)
                .equalTo(true)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        chatModels.clear();

                        for (DataSnapshot item : snapshot.getChildren()) {
                            chatModels.add(item.getValue(ChatModel.class));
                            Log.e("chatModel", "Active");
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
        TextView name;
        TextView text;
        TextView time;
        TextView read_text;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.image);
            name = itemView.findViewById(R.id.name);
            text = itemView.findViewById(R.id.text);
            time = itemView.findViewById(R.id.time);
            read_text = itemView.findViewById(R.id.read_text);

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

    @NonNull
    @Override
    public ChatHistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_history, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatHistoryAdapter.ViewHolder holder, int position) {
        String destinationUid = null;

        for (String user : chatModels.get(position).users.keySet()) {
            if (!user.equals(MainActivity.userId)) {
                destinationUid = user;
            }
        }

        String finalDestinationUid = destinationUid;

        StringBuilder urlStr = new StringBuilder();
        urlStr.append(MainActivity.mainUrl);
        urlStr.append("user/profile");
        StringRequest request = new StringRequest(
                Request.Method.POST,
                urlStr.toString(),
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        name = jsonObject.getString("nickname");
                        holder.name.setText(name);

                        destUid = Integer.toString(jsonObject.getInt("id"));

                        image = jsonObject.getString("profile");
                        if (image.equals("")) {
                            holder.image.setImageResource(R.drawable.default_user);
                        } else {
                            Uri uri = Uri.parse(image);
                            Glide.with(holder.itemView.getContext())
                                    .load(uri)
                                    .apply(new RequestOptions().circleCrop())
                                    .into(holder.image);
                        }
                    } catch (Exception e) {
                        Log.e("ChatHistory", "?????? ??????");
                    }
                },
                error -> {
                    Log.e("ChatHistory", "?????? ??????");
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                HashMap<String, String> body = new HashMap<>();
                body.put("user_id", finalDestinationUid);
                return body;
            }
        };

        request.setShouldCache(false);
        context = holder.itemView.getContext();
        AppHelper.requestQueue = Volley.newRequestQueue(context);
        AppHelper.requestQueue.add(request);

        //???????????? ?????? ???????????? ?????? ??? ????????? ???????????? ????????? ?????????.
        Map<String, ChatModel.CommentModel> commentMap = new TreeMap<>(Collections.reverseOrder());
        commentMap.putAll(chatModels.get(position).comments);
        String lastMessageKey = (String) commentMap.keySet().toArray()[0];
        String message = chatModels.get(position).comments.get(lastMessageKey).message;
        try {
            if (message.substring(0, 6).equals("image:")) {
                holder.text.setText("?????????");
            } else
                holder.text.setText(message);
        } catch (StringIndexOutOfBoundsException e) {
            holder.text.setText(message);
        }

        String[] date = getDate().split(":");
        String[] time = chatModels.get(position).comments.get(lastMessageKey).time.split(":");
        if (Integer.parseInt(date[0], 10) < Integer.parseInt(time[0], 10)) {
            holder.time.setText("?????????");
        } else if (Integer.parseInt(date[2], 10) == Integer.parseInt(time[2], 10) - 1) {
            holder.time.setText("??????");
        } else if (Integer.parseInt(date[2], 10) < Integer.parseInt(time[2], 10) - 1) {
            String date_text = time[1] + "??? " + time[2] + "???";
            holder.time.setText(date_text);
        } else {
            String time_text = getTime(time[3]) + ":" + time[4];
            holder.time.setText(time_text);
        }

        int count = 0;
        for (int i = 0; i < 10; i++) {
            String nextKey;
            try {
                nextKey = (String) commentMap.keySet().toArray()[i];
            } catch (Exception e) {
                break;
            }
            if (chatModels.get(position).comments.get(nextKey).myUid.equals(MainActivity.userId))
                break;

            //?????? ????????? ?????? ??????
            boolean isRead = chatModels.get(position).comments.get(nextKey).read;
            if (isRead) {
                break;
            } else {
                ++count;
            }
        }

        if (count != 0) {
            holder.read_text.setText(Integer.toString(count));
            holder.read_text.setVisibility(View.VISIBLE);
        }
    }

    @SuppressLint("SimpleDateFormat")
    String getDate() {
        SimpleDateFormat mFormat = new SimpleDateFormat("yyyy:MM:dd");
        long mNow = System.currentTimeMillis();
        Date mDate = new Date(mNow);
        return mFormat.format(mDate);
    }

    String setData(int position) {
        String destinationUid = null;

        for (String user : chatModels.get(position).users.keySet()) {
            if (!user.equals(MainActivity.userId)) {
                destinationUid = user;
            }
        }

        return destinationUid;
    }

    private String getTime(String hour) {
        if (Integer.parseInt(hour) < 12)
            return "?????? " + hour;
        else
            return "?????? " + (Integer.parseInt(hour) - 12);
    }

    @Override
    public int getItemCount() {
        return chatModels.size();
    }
}
