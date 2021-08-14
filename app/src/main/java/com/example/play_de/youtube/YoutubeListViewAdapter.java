package com.example.play_de.youtube;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.play_de.R;

import java.util.ArrayList;

public class YoutubeListViewAdapter extends BaseAdapter {
    private TextView titleTextView;
    private TextView contentTextView;

    private ArrayList<YoutubeListViewItem> listViewItemsList = new ArrayList<YoutubeListViewItem>();

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.youtube_listview, parent, false);
        }

        titleTextView = (TextView) convertView.findViewById(R.id.p11_list_title);
        contentTextView = (TextView) convertView.findViewById(R.id.p11_list_content);

        final YoutubeListViewItem listViewItem = listViewItemsList.get(position);

        titleTextView.setText(listViewItem.getTitle());
        contentTextView.setText(listViewItem.getContent());

        TextView cmdArea = convertView.findViewById(R.id.p11_list_content);
        cmdArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //리스트 클릭하면 이벤트 발생
            }
        });

        return convertView;
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

    public void addItem(String title, String content) {
        YoutubeListViewItem item = new YoutubeListViewItem();

        item.setTitle(title);
        item.setContent(content);

        listViewItemsList.add(item);
    }
}
