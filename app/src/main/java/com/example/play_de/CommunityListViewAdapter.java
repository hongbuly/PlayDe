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
    private CommunityFragment communityFragment;

    private ArrayList<CommunityListViewItem> listViewItemsList = new ArrayList<CommunityListViewItem>();

    CommunityListViewAdapter(CommunityFragment communityFragment) {
        this.communityFragment = communityFragment;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.community_listview, parent, false);
        }

        profileImageView = convertView.findViewById(R.id.profile);
        nameTextView = convertView.findViewById(R.id.name);
        dongTextView = convertView.findViewById(R.id.dong);
        heartTextView = convertView.findViewById(R.id.heart);
        placeTextView = convertView.findViewById(R.id.place);

        final CommunityListViewItem listViewItem = listViewItemsList.get(position);

        profileImageView.setImageResource(listViewItem.getProfile()); //이미지 가져오는 형식에 따라 바꿔야됨
        nameTextView.setText(listViewItem.getName());
        dongTextView.setText(listViewItem.getDong());
        heartTextView.setText(listViewItem.getHeart());
        placeTextView.setText(listViewItem.getPlace());

        profileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listViewItem.setIsHeart(false);
                communityFragment.onListClick(position);
            }
        });

        heartTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listViewItem.setIsHeart(true);
                communityFragment.onListClick(position);
            }
        });

        return convertView;
    }

    public ArrayList<CommunityListViewItem> getListViewItemsList() {
        return listViewItemsList;
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

    public void addItem(int profile, String name, String dong, String heart, String place) {
        CommunityListViewItem item = new CommunityListViewItem();

        item.setIsHeart(false);
        item.setProfile(profile);
        item.setName(name);
        item.setDong(dong);
        item.setHeart(heart);
        item.setPlace(place);

        listViewItemsList.add(item);
    }
}
