package com.example.csci3130project;

/**
 * The User class is used to create User objects for the application.
 * These objects store user information, such as name and email.
 *
 * @author Group 6, CSCI3130 F21
 */

public class User {

    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String password;

    public User() {
    }

    /**
     * A method for retrieving the user's first name
     * @return a String containing the user's first name.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * A method for retrieving the user's first name
     * @return a String containing the user's last name.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * A method for retrieving the user's email
     * @return a String containing the user's email.
     */
    public String getEmail() {
        return email;
    }

    /**
     * A method for retrieving the user's username/display name
     * @return a String containing the user's username/display name.
     */
    public String getUsername() {
        return username;
    }

    /**
     * A method for retrieving the user's password
     * @return a String containing the user's password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * A method for updating the user's first name
     * @param fName - String containing new first name
     */
    public void setFirstName(String fName) {
        firstName = fName;
    }

    /**
     * A method for updating the user's last name
     * @param lName - String containing new last name
     */
    public void setLastName(String lName) {
        lastName = lName;
    }

    /**
     * A method for updating the user's email
     * @param email - String containing new email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * A method for updating the user's username
     * @param username - String containing new username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * A method for updating the user's username
     * @param password - String containing new username
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
