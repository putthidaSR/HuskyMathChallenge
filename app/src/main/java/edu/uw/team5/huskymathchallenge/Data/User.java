package edu.uw.team5.huskymathchallenge.Data;

/**
 * Created by lebui on 12/5/2015.
 */
public class User {
    private int mScore;
    private String mUsername;

    public User(){

    }

    public User(String theUsername){
        this.mUsername = theUsername;
    }

    public void setScore(int score) {
        mScore = score;
    }

    public void setUsername(String username) {
        mUsername = username;
    }

    public int getScore() {
        return mScore;
    }

    public String getUsername() {
        return mUsername;
    }
}
