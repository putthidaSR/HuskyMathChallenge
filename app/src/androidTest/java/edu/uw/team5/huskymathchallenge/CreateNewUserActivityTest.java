package edu.uw.team5.huskymathchallenge;

import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.Solo;


public class CreateNewUserActivityTest extends ActivityInstrumentationTestCase2<CreateNewUserActivity> {
    private Solo solo;

    public CreateNewUserActivityTest() {
        super(CreateNewUserActivity.class);
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
        boolean textFound = solo.searchText("Enter your email");
        assertTrue("User List retrieved", textFound);

    }





}
