package com.example.play_de.chat;

public class NotificationModel {
    public String to;
    public Notification notification = new Notification();

    public static class Notification {
        public String title;
        public String text;
    }
}
