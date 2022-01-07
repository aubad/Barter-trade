package com.example.csci3130project;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * The Reputation class is used to create Reputation objects for the application.
 * These objects reviews for a given user.
 *
 * @author Group 6, CSCI3130 F21
 */

public class Reputation {

    private double rating;
    private int reviewCount;
    private ArrayList<Review> reviews = new ArrayList<>(0);
    private String userID;

    public Reputation() {

    }

    public Reputation(String ID) { ;
        userID = ID;
        rating = 0;
        reviewCount = 0;
    }

    /**
     * A method for getting the user ID of this reputation
     * @return a String containing the userID
     */
    public String getUserID() {
        return userID;
    }

    /**
     * A method for getting the total numerical score
     * @return a double containing the score
     */
    public double getTotalScore() {
        //calculateRating();
        return rating;
    }

    /**
     * A method for updating the user's overall numerical rating
     */
    public void calculateRating() {
        double sum = 0;
        if (reviews.size() > 0 && reviewCount > 0) {
            for (int i = 0; i < reviews.size(); i++) {
                sum = sum + reviews.get(i).getRating();
            }
            this.rating = sum / reviewCount;
        }
    }

    /**
     * A method for retrieving the total number of reviews
     * @return an integer containing the total review number
     */
    public int getReviewCount() {
        return reviewCount;
    }

    /**
     * A method for updating the user's overall numerical rating
     * @param rev - the review to add to this reputation
     */
    public void addReview(Review rev) {
        reviewCount++;
        reviews.add(rev);
    }

    public ArrayList<Review> getReviews() {
        return reviews;
    }
}
