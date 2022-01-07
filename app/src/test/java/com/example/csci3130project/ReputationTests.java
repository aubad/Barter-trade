package com.example.csci3130project;

import org.junit.Test;
import static org.junit.Assert.*;

public class ReputationTests {

    Reputation reputation = new Reputation("123");
    Review rev1 = new Review("1");
    Review rev2 = new Review("2");
    Review rev3 = new Review("3");

    @Test
    public void calculateRating() {
        rev1.addRating(4.5);
        rev2.addRating(3.6);
        rev3.addRating(4.05);
        reputation.addReview(rev1);
        reputation.addReview(rev2);
        reputation.addReview(rev3);
        reputation.calculateRating();
        assertEquals(4.05, reputation.getTotalScore(), 0.01);
    }

    @Test
    public void getReviewCount() {
        reputation.addReview(rev1);
        reputation.addReview(rev2);
        reputation.addReview(rev3);
        assertEquals(3, reputation.getReviewCount());
    }
}
