package com.example.play_de;

public class ChatListViewItem {
    private String textStr;
    private String nickName;
    private boolean isISendBool;

    public void setText(String text) {
        textStr = text;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getText() {
        return textStr;
    }

    public String getNickName() {
        return nickName;
    }
}
