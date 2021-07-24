package com.example.play_de;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ChatListViewAdapter extends BaseAdapter {
    private TextView textView;
    private ArrayList<ChatListViewItem> listViewItemsList = new ArrayList<ChatListViewItem>();

    private TextView left_text;
    private TextView right_text;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.chat_listview, parent, false);
        }

        left_text = convertView.findViewById(R.id.left_chat);
        right_text = convertView.findViewById(R.id.right_chat);

        ChatListViewItem listViewItem = listViewItemsList.get(position);
        if (listViewItem.getWhoSend()) {
            ComponentSetVisibility(listViewItem.getWhoSend());
            textView = convertView.findViewById(R.id.right_chat);
        } else {
            ComponentSetVisibility(listViewItem.getWhoSend());
            textView = convertView.findViewById(R.id.left_chat);
        }
        textView.setText(listViewItem.getText());
        return convertView;
    }

    void ComponentSetVisibility(boolean visible) {
        if (visible) {
            left_text.setVisibility(View.GONE);
            right_text.setVisibility(View.VISIBLE);
        } else {
            left_text.setVisibility(View.VISIBLE);
            right_text.setVisibility(View.GONE);
        }
    }

    @Override
    public int getCount() {
        return listViewItemsList.size();
    }

    @Override
    public Object getItem(int position) {
        return listViewItemsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void addItem(String text, boolean isISend) {
        ChatListViewItem item = new ChatListViewItem();
        item.setText(text);
        item.setWhoSend(isISend);
        listViewItemsList.add(item);
    }
}
