package com.example.csci3130project;

/**
 * The Chat class is used to create Chat objects for the application.
 * These objects store info about a message chain between two users.
 *
 * @author Group 6, CSCI3130 F21
 */

public class Chat {

    private String msg;
    private String userId;
    private String userName;
    private String recipientId;
    private String recipientName;
    public Chat(){
    }

    /**
     * A method for updating this message
     * @param msg - String containing new messaage
     */
    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     * A method for updating the user ID of the sender
     * @param userId - String containing sender's ID
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * A method for updating the username of the sender
     * @param userName - String containing sender's username
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * A method for updating the user ID of the recipient
     * @param recipient - String containing recipient's ID
     */
    public void setRecipientId(String recipient){
        this.recipientId = recipient;
    }

    /**
     * A method for updating the name of the recipient
     * @param recipientName - String containing recipient's name
     */
    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }

    /**
     * A method for retrieving the message
     * @return a String containing the message
     */
    public String getMsg() {
        return msg;
    }

    /**
     * A method for retrieving the sender's ID
     * @return a String containing the ID
     */
    public String getUserId() {
        return userId;
    }

    /**
     * A method for retrieving the sender's username
     * @return a String containing the username
     */
    public String getUserName() {
        return userName;
    }

    /**
     * A method for retrieving the recipient's ID
     * @return a String containing the recipient ID
     */
    public  String getRecipientId(){
        return this.recipientId;
    }

    /**
     * A method for retrieving the recipient's name
     * @return a String containing the name
     */
    public String getRecipientName() {
        return recipientName;
    }


}

