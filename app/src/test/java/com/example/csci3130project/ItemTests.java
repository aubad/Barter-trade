package com.example.csci3130project;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ItemTests {

    String description = "This item is clothing. It is a red shirt size XL";
    Item testItem;

    String description2 = "This item is furniture. It is a used couch";
    Item testItem2;

    @Before
    public void initialize() {
        testItem = new Item("123", "", "", "", 0, 0, 0, false);
        testItem.setName("itemName");
        testItem.setDescription(description);
        testItem.setCategory("Clothing");
        testItem.setItemValue(5);
        testItem.setLatitude(1.12345678);
        testItem.setLongitude(1.12345678);

        testItem2 = new Item("456", "", "", "", 0, 0, 0, false);
        testItem2.setName("itemName2");
        testItem2.setDescription(description2);
        testItem2.setCategory("Furniture");
        testItem2.setItemValue(20);
        testItem2.setLatitude(10.12345678);
        testItem2.setLongitude(10.12345678);
    }

    @Test
    public void getItemName_Test() {
        assertEquals("itemName", testItem.getName());
        assertEquals("itemName2", testItem2.getName());
    }

    @Test
    public void getItemDescription_Test() {
        assertEquals("This item is clothing. It is a red shirt size XL", testItem.getDescription());
        assertEquals("This item is furniture. It is a used couch", testItem2.getDescription());
    }

    @Test
    public void getItemCategory_Test() {
        assertEquals("Clothing", testItem.getCategory());
        assertEquals("Furniture", testItem2.getCategory());
    }

    @Test
    public void setItemName_Test() {
        testItem.setName("newName");
        assertEquals("newName", testItem.getName());
    }

    @Test
    public void setItemDescription_Test() {
        testItem.setDescription("This item is technology. It's a used iPhone 7.");
        assertEquals("This item is technology. It's a used iPhone 7.", testItem.getDescription());
    }

    @Test
    public void setItemCategory_Test() {
        testItem.setCategory("Technology");
        assertEquals("Technology", testItem.getCategory());
    }

}
