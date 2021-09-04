package com.example.play_de.cafe;

public class CafeRecyclerItem {
    public int id;
    public String image;
    public String name;
    public String address;
    public String table;
    public int open, close;
    public int heart;
    public boolean my_like = false;
    public String location;

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
        return Integer.toString(heart);
    }
}
