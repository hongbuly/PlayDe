package com.example.play_de;

public class CategoryListViewItem {

    private String nameStr;
    private String themeStr;
    private String peopleStr;
    private String levelStr;

    public void setName(String name) {
        nameStr = name;
    }
    public void setTheme(String theme){
        themeStr = theme;
    }
    public void setPeople(String people) {
        peopleStr = people;
    }
    public void setLevel(String level){
        levelStr = level;
    }

    public String getName() {
        return this.nameStr;
    }
    public String getTheme() {
        return this.themeStr;
    }
    public String getPeople() {
        return this.peopleStr;
    }
    public String getLevel() {
        return this.levelStr;
    }
}
