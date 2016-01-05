package edu.uw.team5.huskymathchallenge.AllAboutMath;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.login.LoginManager;

import edu.uw.team5.huskymathchallenge.MainActivity;
import edu.uw.team5.huskymathchallenge.Menu;
import edu.uw.team5.huskymathchallenge.R;

public class ResultAllMathActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        TextView displayScore = (TextView) findViewById(R.id.displayScorebtn);

        //get shared prefs
        SharedPreferences scorePrefs = getSharedPreferences(AllAboutMath.SCORE_PREFS, 0);
        final int score1 = scorePrefs.getInt("Score", 0);
        final String name = scorePrefs.getString("Name", "NULL");

        //display scores
        displayScore.setText("Congratulations, " + name + "! The game took you " + String.valueOf(score1) + "seconds to complete.");

        Button button = (Button) findViewById(R.id.sharebtn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "Hey, this is " + name + ". I just play Husky Math Challenge game. It took me "
                        + String.valueOf(score1) + "seconds to complete All About Math quiz :) ");
                sendIntent.setType("text/plain");
                startActivity(Intent.createChooser(sendIntent, getResources().getText(R.string.send_to)));
            }

        });

        Button mLogout = (Button) findViewById(R.id.quitbt);
        mLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginManager.getInstance().logOut();

                Intent intent = new Intent(ResultAllMathActivity.this, MainActivity.class);
                startActivity(intent);

            }

        });

        Button mPlayAgain = (Button) findViewById(R.id.playagainbtn);
        mPlayAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ResultAllMathActivity.this, Menu.class);
                startActivity(intent);

            }

        });
        Button score = (Button) findViewById(R.id.leaderboardbtn);
        score.setText(null);
        score.setVisibility(View.INVISIBLE);


    }
}