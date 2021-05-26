package com.example.play_de;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CategoryListViewAdapter extends BaseAdapter {

    private TextView nameTextView;
    private TextView themeTextView;
    private TextView peopleTextView;
    private TextView levelTextView;

    private ArrayList<CategoryListViewItem> listViewItemsList = new ArrayList<CategoryListViewItem>();

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.category_listview, parent, false);
        }

        nameTextView = (TextView) convertView.findViewById(R.id.p12_nameList);
        themeTextView = (TextView) convertView.findViewById(R.id.p12_themeList);
        peopleTextView = (TextView) convertView.findViewById(R.id.p12_peopleList);
        levelTextView = (TextView) convertView.findViewById(R.id.p12_levelList);

        CategoryListViewItem listViewItem = listViewItemsList.get(position);

        nameTextView.setText(listViewItem.getName());
        themeTextView.setText(listViewItem.getTheme());
        peopleTextView.setText(listViewItem.getPeople());
        levelTextView.setText(listViewItem.getLevel());

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

    public void addItem(String name, String theme, String people, String level) {
        CategoryListViewItem item = new CategoryListViewItem();

        item.setName(name);
        item.setTheme(theme);
        item.setPeople(people);
        item.setLevel(level);

        listViewItemsList.add(item);
    }
}
