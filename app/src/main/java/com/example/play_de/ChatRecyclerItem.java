package com.example.play_de;

public class ChatRecyclerItem {
    private int image;
    private String text;
    private String name;

    public void setData(int image, String name, String text) {
        this.image = image;
        this.text = text;
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public String getText() {
        return text;
    }

    public String getName() {
        return name;
    }
}
