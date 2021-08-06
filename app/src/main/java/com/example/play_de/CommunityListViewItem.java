package com.example.play_de;

public class CommunityListViewItem {
    private boolean isHeart;
    private int profileImage;
    private String nameStr;
    private String dongStr;
    private String heartStr;
    private String placeStr;

    public void setIsHeart(boolean isHeart) {
        this.isHeart = isHeart;
    }

    public void setProfile(int profile) {
        profileImage = profile;
    }

    public void setName(String name) {
        nameStr = name;
    }

    public void setDong(String dong) {
        dongStr = dong;
    }

    public void setHeart(String heart) {
        heartStr = heart;
    }

    public void setPlace(String place) {
        placeStr = place;
    }

    public boolean getIsHeart() {
        return this.isHeart;
    }

    public int getProfile() {
        return this.profileImage;
    }

    public String getName() {
        return this.nameStr;
    }

    public String getDong() {
        return this.dongStr;
    }

    public String getHeart() {
        return this.heartStr;
    }

    public String getPlace() {
        return this.placeStr;
    }
}
