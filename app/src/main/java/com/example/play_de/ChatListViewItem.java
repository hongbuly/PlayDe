package com.example.play_de;

public class ChatListViewItem {
    private String textStr;
    private boolean isISendBool;
    public void setText(String text) {
        textStr = text;
    }
    public void setWhoSend(boolean isISend) {
        isISendBool = isISend;
    }
    public String getText() {
        return this.textStr;
    }
    public Boolean getWhoSend() {
        return this.isISendBool;
    }
}
