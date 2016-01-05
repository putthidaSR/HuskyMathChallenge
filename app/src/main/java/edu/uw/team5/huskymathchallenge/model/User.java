package edu.uw.team5.huskymathchallenge.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class is a model class for user.
 *
 * @author Putthida Samrith and Le Phu Bui
 * @version November 4, 2015
 */
public class User {
    /**
     * An array of user items.
     */
    public static List<UserInfo> ITEMS = new ArrayList<UserInfo>();

    /**
     * A map of user items, by email.
     */
    public static Map<String, UserInfo> ITEM_MAP = new HashMap<String, UserInfo>();

    /**
     * This method to add email to the list.
     * @param item the list of emails
     */
    private static void addItem(UserInfo item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.email, item);
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class UserInfo {
        public String email;
        public String pwd;

        /**
         * Constructor
         *
         * @param email user's email
         * @param pwd user's password
         */
        public UserInfo(String email, String pwd) {
            this.email = email;
            this.pwd = pwd;
        }

        /**
         * toString() method
         * @return the value of email
         */
        @Override
        public String toString() {
            return email;
        }
    }

}
