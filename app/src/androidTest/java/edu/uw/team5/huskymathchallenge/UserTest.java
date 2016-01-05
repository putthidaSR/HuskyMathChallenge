package edu.uw.team5.huskymathchallenge;

import junit.framework.TestCase;

import edu.uw.team5.huskymathchallenge.model.User;

/**
 * Created by PutthidaSR on 12/6/15.
 */
public class UserTest extends TestCase {


    public void testConstructor() {
        User.UserInfo user = new User.UserInfo("test", "testing");
        assertNotNull(user);
    }

    public void testToString() {
        User.UserInfo user = new User.UserInfo("test", "testing");
        assertEquals("test", user.toString());
    }

}

