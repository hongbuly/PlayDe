package com.example.play_de.chat;

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

import java.util.ArrayList;
import java.util.Collections;
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
                            holder.image.setImageResource(R.drawable.circle_grey);
                        } else {
                            Uri uri = Uri.parse(image);
                            Glide.with(holder.itemView.getContext())
                                    .load(uri)
                                    .apply(new RequestOptions().circleCrop())
                                    .into(holder.image);
                        }
                    } catch (Exception e) {
                        Log.e("ChatHistory", "예외 발생");
                    }
                },
                error -> {
                    Log.e("ChatHistory", "에러 발생");
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

        //메시지를 내림 차순으로 정렬 후 마지막 메시지의 키값을 가져옴.
        Map<String, ChatModel.CommentModel> commentMap = new TreeMap<>(Collections.reverseOrder());
        commentMap.putAll(chatModels.get(position).comments);
        String lastMessageKey = (String) commentMap.keySet().toArray()[0];
        String message = chatModels.get(position).comments.get(lastMessageKey).message;
        holder.text.setText(message);

        String[] time = chatModels.get(position).comments.get(lastMessageKey).time.split(":");
        String time_text = getTime(time[0]) + ":" + time[1];
        holder.time.setText(time_text);

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

            //내가 안읽은 채팅 개수
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
            return "오후 " + hour;
        else
            return "오전 " + (Integer.parseInt(hour) - 12);
    }

    @Override
    public int getItemCount() {
        return chatModels.size();
    }
}
