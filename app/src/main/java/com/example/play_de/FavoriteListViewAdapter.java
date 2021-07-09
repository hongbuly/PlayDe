package com.example.play_de;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class FavoriteListViewAdapter extends BaseAdapter {

    private ImageView[] favorite = new ImageView[4];
    private TextView[] name = new TextView[4];
    private ArrayList<FavoriteListViewItem> listViewItemsList = new ArrayList<FavoriteListViewItem>();

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.favorite_listview, parent, false);
        }

        favorite[0] = convertView.findViewById(R.id.favorite01);
        favorite[1] = convertView.findViewById(R.id.favorite02);
        favorite[2] = convertView.findViewById(R.id.favorite03);
        favorite[3] = convertView.findViewById(R.id.favorite04);

        name[0] = convertView.findViewById(R.id.name01);
        name[1] = convertView.findViewById(R.id.name02);
        name[2] = convertView.findViewById(R.id.name03);
        name[3] = convertView.findViewById(R.id.name04);

        FavoriteListViewItem listViewItem = listViewItemsList.get(position);

        for (int i = 0; i < 4; i++) {
            favorite[i].setImageResource(listViewItem.getFavorite(i));
            name[i].setText(listViewItem.getName(i));
        }

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

    public void addItem(int favorite[], String name[]) {
        FavoriteListViewItem item = new FavoriteListViewItem();
        item.setData(favorite, name);
        listViewItemsList.add(item);
    }
}
