package com.example.csci3130project;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ReviewTests {

    Review review = new Review("123");

    @Test
    public void getRating() {
        review.addRating(4.5);
        assertEquals(4.5, review.getRating(), 0.01);
    }

    @Test
    public void getComment() {
        review.addComment("Not too bad");
        assertEquals("Not too bad", review.getComment());
    }
}
