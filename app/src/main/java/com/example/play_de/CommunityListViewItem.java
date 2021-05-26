package com.example.play_de;

import android.graphics.drawable.Drawable;

public class CommunityListViewItem {

    private Drawable profileImage;
    private String nameStr;
    private String dongStr;
    private String heartStr;
    private String placeStr;

    public void setProfile(Drawable profile){ profileImage = profile; }
    public void setName(String name){ nameStr = name; }
    public void setDong(String dong){ dongStr = dong; }
    public void setHeart(String heart){ heartStr = heart; }
    public void setPlace(String place){ placeStr = place; }

    public Drawable getProfile(){return this.profileImage; }
    public String getName(){ return this.nameStr; }
    public String getDong(){ return this.dongStr; }
    public String getHeart(){ return this.heartStr; }
    public String getPlace(){ return this.placeStr; }
}
