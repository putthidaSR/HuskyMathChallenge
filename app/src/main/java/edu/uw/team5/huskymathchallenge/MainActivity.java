/*
 * This is the MainActivity class.
 *
 * Author: Putthida Samrith and Le Phu Bui
 * Date: November 4, 2015
 * MainActivity.java
 */

package edu.uw.team5.huskymathchallenge;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

/**
 * This is the main class and Launcher.
 *
 * @author Putthida Samrith and Le Phu Bui
 * @version December 6, 2015
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        LoginFragment loginFragment = new LoginFragment();
        fragmentTransaction.add(R.id.fragment_container, loginFragment)
                .commit();


    }

}
