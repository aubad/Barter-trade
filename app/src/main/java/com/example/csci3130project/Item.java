package com.example.csci3130project;


/**
 * The Item class is used to create Item objects for the application.
 * These objects store information about goods that are posted for trading.
 *
 * @author Group 6, CSCI3130 F21
 */

public class Item {

    private String userID;
    private String name;
    private String description;
    private String category;

    private int itemValue;
    private double longitude;
    private double latitude;
    private boolean status = false;


    public Item(String userID, String name, String description, String category, Integer itemValue, double longitude, double latitude, boolean status) {

        this.userID = userID;
        this.name = name;
        this.description = description;
        this.category = category;
        this.itemValue = itemValue;
        this.longitude = longitude;
        this.latitude = latitude;
        this.status = status;

    }


    public String getUserID() {
        return this.userID;
    }

    /**
     * A method for retrieving the name of an item
     *
     * @return a String containing the name of this item
     */
    public String getName() {
        return this.name;
    }

    /**
     * A method for retrieving the description of an item
     *
     * @return a String containing the description of this item
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * A method for retrieving the category of an item (ex. clothing, furniture)
     *
     * @return a String containing the category of this item
     */
    public String getCategory() {
        return this.category;
    }

    public int getItemValue() {
        return this.itemValue;
    }

    public double getLongitude() {
        return this.longitude;
    }

    public double getLatitude() {
        return this.latitude;
    }

    public boolean getStatus() {
        return this.status;
    }


    /**
     * A method for updating the item's name
     *
     * @param name - String containing new item name
     */
    public void setName(String name) {
        this.name = name;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    /**
     * A method for updating the item's description
     *
     * @param description - String containing new item description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * A method for updating the item's category
     *
     * @param category - String containing new item category
     */
    public void setCategory(String category) {
        this.category = category;
    }

    public void setItemValue(int itemValue) {
        this.itemValue = itemValue;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    /**
     * A method for updating the status of the item to sold
     */
    public void isSold(){
        this.status = true;
    }

    /**
     * A method for printing an items category and name
     */
    public String toString(){
        return category + ": " + name;
    }

    @Override
    public boolean equals(Object i) {
        if (this == i) return true;
        if (i == null || getClass() != i.getClass()) return false;
        Item that = (Item) i;
        return name.equals(that.name) &&
                userID.equals(that.userID);
    }

}
