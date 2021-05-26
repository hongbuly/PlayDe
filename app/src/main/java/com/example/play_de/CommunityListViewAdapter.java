package com.example.play_de;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CommunityListViewAdapter extends BaseAdapter {

    private ImageView profileImageView;
    private TextView nameTextView;
    private TextView dongTextView;
    private TextView heartTextView;
    private TextView placeTextView;

    private ArrayList<CommunityListViewItem> listViewItemsList = new ArrayList<CommunityListViewItem>();

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.community_listview, parent, false);
        }

        profileImageView = convertView.findViewById(R.id.p14_profileList);
        nameTextView = convertView.findViewById(R.id.p14_nameList);
        dongTextView = convertView.findViewById(R.id.p14_dongList);
        heartTextView = convertView.findViewById(R.id.p14_heartList);
        placeTextView = convertView.findViewById(R.id.p14_placeList);

        CommunityListViewItem listViewItem = listViewItemsList.get(position);

        profileImageView.setImageDrawable(listViewItem.getProfile()); //이미지 가져오는 형식에 따라 바꿔야됨
        nameTextView.setText(listViewItem.getName());
        dongTextView.setText(listViewItem.getDong());
        heartTextView.setText(listViewItem.getHeart());
        placeTextView.setText(listViewItem.getPlace());

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

    public void addItem(Drawable profile, String name, String dong, String heart, String place) {
        CommunityListViewItem item = new CommunityListViewItem();

        item.setProfile(profile);
        item.setName(name);
        item.setDong(dong);
        item.setHeart(heart);
        item.setPlace(place);

        listViewItemsList.add(item);
    }
}
