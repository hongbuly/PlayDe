package com.example.play_de;

public class CafeRecyclerItem {
    private int image;
    private String name;
    private String address;
    private String table;
    private String time;
    private String heart;

    public void setData(int image, String name, String address, String table, String time, String heart) {
        this.image = image;
        this.name = name;
        this.address = address;
        this.table = table;
        this.time = time;
        this.heart = heart;
    }

    public int getImage() { return image; }
    public String getName() {
        return name;
    }
    public String getAddress() {
        return address;
    }
    public String getTable() {
        return table;
    }
    public String getTime() { return time; }
    public String getHeart() {
        return heart;
    }
}
