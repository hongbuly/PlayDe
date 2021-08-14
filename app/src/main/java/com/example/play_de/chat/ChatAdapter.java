package com.example.play_de.chat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.play_de.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {
    private FirebaseDatabase firebaseDatabase;
    private String destUid, chatRoomUid, myUid;
    private UserModel destUser;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyy.MM.dd HH:mm");
    private RecyclerView chat_view;

    private List<ChatModel.Comment> comments;
    private ArrayList<ChatRecyclerItem> mData = new ArrayList<>();
    private String nameStr;
    private TextView left_text;
    private TextView right_text;

    ChatAdapter(FirebaseDatabase firebaseDatabase, String destUid, UserModel destUser, String chatRoomUid, RecyclerView chat_view, String myUid) {
        comments = new ArrayList<>();
        this.firebaseDatabase = firebaseDatabase;
        this.destUid = destUid;
        this.destUser = destUser;
        this.chatRoomUid = chatRoomUid;
        this.chat_view = chat_view;
        this.myUid = myUid;
        getDestUid();
    }

    private void getDestUid() {
        firebaseDatabase.getReference().child("users").child(destUid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                destUser = snapshot.getValue(UserModel.class);

                //채팅 내용 읽어들임
                getMessageList();
            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });
    }

    //채팅 내용 읽어들임
    private void getMessageList() {
        FirebaseDatabase.getInstance().getReference().child("chatRooms").child(chatRoomUid).child("comments").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                comments.clear();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    comments.add(dataSnapshot.getValue(ChatModel.Comment.class));
                }
                notifyDataSetChanged();

                chat_view.scrollToPosition(comments.size() - 1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView text;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.image);
            text = itemView.findViewById(R.id.text);

            left_text = itemView.findViewById(R.id.left_chat);
            right_text = itemView.findViewById(R.id.right_chat);
        }

        void onBind(ChatRecyclerItem item, int position) {
            image.setImageResource(item.getImage());

            if (comments.get(position).uid.equals(myUid)) {
                ComponentSetVisibility(item.getName().equals(nameStr));
                text = right_text;
            } else {
                ComponentSetVisibility(item.getName().equals(nameStr));
                text = left_text;
            }
            text.setText(item.getText());
        }
    }

    private void ComponentSetVisibility(boolean visible) {
        if (visible) {
            left_text.setVisibility(View.GONE);
            right_text.setVisibility(View.VISIBLE);
        } else {
            left_text.setVisibility(View.VISIBLE);
            right_text.setVisibility(View.GONE);
        }
    }

    void addItem(ChatRecyclerItem item) {
        mData.add(item);
    }

    @NonNull
    @Override
    public ChatAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_listview, parent, false);
        return new ChatAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.onBind(mData.get(position), position);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}
