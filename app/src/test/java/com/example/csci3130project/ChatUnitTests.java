package com.example.csci3130project;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class ChatUnitTests {

    Chat testChat;

    @Before
    public void initialize() {
        testChat = new Chat();
        testChat.setMsg("Hello");
        testChat.setUserId("123");
        testChat.setUserName("User1");
        testChat.setRecipientId("456");
        testChat.setRecipientName("User2");
    }

    @Test
    public void getMsg_Test() {
        assertEquals("Hello", testChat.getMsg());
    }

    @Test
    public void setMsg_Test() {
        testChat.setMsg("Hi there");
        assertEquals("Hi there", testChat.getMsg());
    }

    @Test
    public void getMyID_Test() {
        assertEquals("123", testChat.getUserId());
    }

    @Test
    public void setMyID_Test() {
        testChat.setUserId("679");
        assertEquals("679", testChat.getUserId());
    }

    @Test
    public void getTheirID_Test() {
        assertEquals("456", testChat.getRecipientId());
    }

    @Test
    public void setTheirID_Test() {
        testChat.setRecipientId("002");
        assertEquals("002", testChat.getRecipientId());
    }

    @Test
    public void getMyName_Test() {
        assertEquals("User1", testChat.getUserName());
    }

    @Test
    public void setMyName_Test() {
        testChat.setUserName("SpecialUser");
        assertEquals("SpecialUser", testChat.getUserName());
    }

    @Test
    public void getTheirName_Test() {
        assertEquals("User2", testChat.getRecipientName());
    }

    @Test
    public void setTheirName_Test() {
        testChat.setRecipientName("OtherUser");
        assertEquals("OtherUser", testChat.getRecipientName());
    }
}
