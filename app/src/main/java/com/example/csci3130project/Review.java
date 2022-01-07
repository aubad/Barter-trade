package com.example.csci3130project;

import java.util.ArrayList;

/**
 * The Review class is used to create Review objects for the application.
 * These objects store ratings and comments for a given review.
 *
 * @author Group 6, CSCI3130 F21
 */

public class Review {

    private String submitter;
    private String comment;
    private double rating;

    public Review() {

    }

    public Review(String submitter) {
        this.submitter = submitter;
    }

    public String getSubmitter() {
        return submitter;
    }

    /**
     * A method for retrieving this review's numerical rating
     * @return an double containing the numerical rating
     */
    public double getRating(){
        return rating;
    }

    /**
     * A method for adding a numerical rating
     * @param rating - a double containing the rating
     */
    public void addRating(double rating) {
        this.rating = rating;
    }

    /**
     * A method for retrieving this review's comment
     * @return a String containing a written comment
     */
    public String getComment() {
        return comment;
    }

    /**
     * A method for adding a comment
     * @param comment - a String containing the comment
     */
    public void addComment(String comment) {
        this.comment = comment;
    }
}
