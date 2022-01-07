package com.example.csci3130project;

public class ProxyChat {

    private String msg;
    private String userId;
    private String userName;
    private String recipientId;
    private String recipientName;
    private Chat realChat;

    public ProxyChat(String msg, String userId, String userName, String recipientId,String recipientName) {
        if (realChat == null) {
            realChat = new Chat();
        }
        realChat.setMsg(msg);
        realChat.setUserId(userId);
        realChat.setUserName(userName);
        realChat.setRecipientId(recipientId);
        realChat.setRecipientName(recipientName);
    }

    public Chat getReal() {
        return realChat;
    }

}
