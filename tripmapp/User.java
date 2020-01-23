package com.tripmapp;

import java.util.HashMap;
import java.util.Map;

/**
 * This class helps us to create user object.
 *
 * @author Ömer Faruk Akgül
 * @version 19.12.19
 */
public class User {
    private String name;
    private String surname;
    private String email;
    private String password;

    /**
     * initializes parameters
     * @param name
     * @param surname
     * @param email
     * @param password
     */
    public User(String name, String surname, String email, String password) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
    }

    /**
     * returns the name
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * adjusts the name
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * returns the surname
     * @return surname
     */
    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * returns the mail
     * @return email
     */
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * returns the password
     * @return password
     */
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * creates the map of user
     * @return result
     */
    public Map<String, String> toMap(){
        Map<String, String> result = new HashMap<>();
        result.put("name", name);
        result.put("surname", surname);
        result.put("email", email);
        result.put("password", password);
        return result;
    }


}
