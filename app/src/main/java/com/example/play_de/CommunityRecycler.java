package com.example.play_de;

public class CommunityRecycler {
    private int image;
    private String name;
    private String level;
    private String content;

    public void setData(int image, String name, String level, String content) {
        this.image = image;
        this.name = name;
        this.level = level;
        this.content = content;
    }

    public int getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public String getLevel() {
        return level;
    }

    public String getContent() {
        return content;
    }
}
