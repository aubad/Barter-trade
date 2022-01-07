package com.example.csci3130project;

/*
* Factory Method class for User objects. This makes User more extendable
* if new user types are added in the future (ex. Admin, Regular)
 */

public class UserFactory {

    public User getUser(String userType) {
        if (userType.equals("Regular")) {
            return new User();
        }
        return null;
    }
}
