package com.example.play_de.cafe;

public class CafeRecyclerItem {
    private int id;
    private String image;
    private String name;
    private String address;
    private String table;
    private int open, close;
    private String heart;

    public void setData(int id, String image, String name, String address, String table, int open, int close, String heart) {
        this.id = id;
        this.image = image;
        this.name = name;
        this.address = address;
        this.table = table;
        this.open = open;
        this.close = close;
        this.heart = heart;
    }

    public int getId() {
        return id;
    }

    public String getImage() {
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
        String time;
        if (open > 12) {
            time = (open - 12) + ":00 PM~" + (close - 12) + ":00 PM";
        } else {
            time = open + ":00 AM~" + (close - 12) + ":00 PM";
        }
        return time;
    }

    public String getHeart() {
        return heart;
    }
}
