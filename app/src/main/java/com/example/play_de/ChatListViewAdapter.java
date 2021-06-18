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

    private ImageView left_image;
    private TextView left_text;
    private View left_view;
    private ImageView right_image;
    private TextView right_text;
    private View right_view;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.chat_listview, parent, false);
        }

        left_image = convertView.findViewById(R.id.left_chat_image);
        left_text = convertView.findViewById(R.id.left_chat_text);
        left_view = convertView.findViewById(R.id.left_chat_view);
        right_image = convertView.findViewById(R.id.right_chat_image);
        right_text = convertView.findViewById(R.id.right_chat_text);
        right_view = convertView.findViewById(R.id.right_chat_view);

        ChatListViewItem listViewItem = listViewItemsList.get(position);
        if (listViewItem.getWhoSend()) {
            ComponentSetVisibility(listViewItem.getWhoSend());
            textView = convertView.findViewById(R.id.right_chat_text);
        } else {
            ComponentSetVisibility(listViewItem.getWhoSend());
            textView = convertView.findViewById(R.id.left_chat_text);
        }
        textView.setText(listViewItem.getText());
        return convertView;
    }

    void ComponentSetVisibility(boolean visible) {
        if (visible) {
            left_image.setVisibility(View.GONE);
            left_text.setVisibility(View.GONE);
            left_view.setVisibility(View.GONE);
            right_image.setVisibility(View.VISIBLE);
            right_text.setVisibility(View.VISIBLE);
            right_view.setVisibility(View.VISIBLE);
        } else {
            left_image.setVisibility(View.VISIBLE);
            left_text.setVisibility(View.VISIBLE);
            left_view.setVisibility(View.VISIBLE);
            right_image.setVisibility(View.GONE);
            right_text.setVisibility(View.GONE);
            right_view.setVisibility(View.GONE);
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
