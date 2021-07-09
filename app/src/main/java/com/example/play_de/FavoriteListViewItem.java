package com.example.play_de;

public class FavoriteListViewItem {

    private String[] name = new String[4];
    private int[] favorite = new int[4];

    public void setData(int favorite[], String name[]) {
        this.favorite = favorite;
        this.name = name;
    }

    public int getFavorite(int num){
        return this.favorite[num];
    }

    public String getName(int num) {
        return this.name[num];
    }
}
