package com.example.play_de.cafe;

public class CafeRecyclerItem {
    private int id;
    private int image;
    private String name;
    private String address;
    private String table;
    private String time;
    private String heart;

    public void setData(int id, int image, String name, String address, String table, String time, String heart) {
        this.id = id;
        this.image = image;
        this.name = name;
        this.address = address;
        this.table = table;
        this.time = time;
        this.heart = heart;
    }

    public int getId() {
        return id;
    }

    public int getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getTable() {
        return table;
    }

    public String getTime() {
        return time;
    }

    public String getHeart() {
        return heart;
    }
}
