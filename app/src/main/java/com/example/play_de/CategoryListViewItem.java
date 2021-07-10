package com.example.play_de;

public class CategoryListViewItem {

    private int image;
    private String name;
    private String theme;
    private String people;
    private String level;

    public void setData(int image, String name, String theme, String people, String level) {
        this.image = image;
        this.name = name;
        this.theme = theme;
        this.people = people;
        this.level = level;
    }

    public int getImage() { return this.image; }
    public String getName() {
        return this.name;
    }
    public String getTheme() {
        return this.theme;
    }
    public String getPeople() {
        return this.people;
    }
    public String getLevel() {
        return this.level;
    }
}
