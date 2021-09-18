package com.example.play_de.chat;

import java.util.HashMap;
import java.util.Map;

public class ChatModel {
    public Map<String, Boolean> users = new HashMap<>();
    public Map<String, CommentModel> comments = new HashMap<>();

    public static class CommentModel {
        public String myUid;
        public String message;
        public String time;
        public Boolean read;
    }
}
