package edu.uw.team5.huskymathchallenge;

import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.Solo;

/**
 * Created by lebui on 11/25/2015.
 */
public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {
    private Solo solo;

    public MainActivityTest() {
        super(MainActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        solo = new Solo(getInstrumentation(), getActivity());
    }

    @Override
    public void tearDown() throws Exception {
        //tearDown() is run after a test case has finished.
        //finishOpenedActivities() will finish all the activities that have been opened during the test execution.
        solo.finishOpenedActivities();
    }

    public void testDataShowsUp() {
        solo.unlockScreen();
        boolean textFound = solo.searchText("Enter Email");
        assertTrue("User List retrieved", textFound);

    }

    public void testDataShowsU1p() {
        solo.unlockScreen();
        boolean textFound = solo.searchText("Don't have an account yet?");
        assertTrue("User List retrieved", textFound);

    }

}
