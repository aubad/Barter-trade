package com.example.csci3130project;

import java.util.ArrayList;

public class ReputationCalculator {

    private ArrayList<Reputation> orders = new ArrayList<>(1);

    public ReputationCalculator() {
    }

    /*
     * Add reputation to queue
     */
    public void takeReputation(Reputation rep) {
        orders.add(rep);
    }

    /*
     * Calculate total ratings for all reputations in queue
     */
    public void calculateReputation() {

        for (Reputation r : orders) {
            r.calculateRating();
        }

    }
}
