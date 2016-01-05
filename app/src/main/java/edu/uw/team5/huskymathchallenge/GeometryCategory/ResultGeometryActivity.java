package edu.uw.team5.huskymathchallenge.GeometryCategory;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import edu.uw.team5.huskymathchallenge.MainActivity;
import edu.uw.team5.huskymathchallenge.Menu;
import edu.uw.team5.huskymathchallenge.R;

public class ResultGeometryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        TextView displayScore = (TextView) findViewById(R.id.displayScorebtn);

        //get shared prefs
        SharedPreferences scorePrefs = getSharedPreferences(GeometryQuizActivity.SCORE_PREFS, 0);
        final int score1 = scorePrefs.getInt("Score", 0);
        final String name = scorePrefs.getString("Name", "NULL");

        //display scores
        displayScore.setText("Congratulations, " + name + "! You got " + String.valueOf(score1) + "points.");

        Button button = (Button) findViewById(R.id.sharebtn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "Hey, this is " + name + ". I just play Husky Math Challenge game. I got "
                        + String.valueOf(score1) + "points for Geometry quiz :) ");
                sendIntent.setType("text/plain");
                startActivity(Intent.createChooser(sendIntent, getResources().getText(R.string.send_to)));
            }

        });

        Button score = (Button) findViewById(R.id.leaderboardbtn);
        score.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ResultGeometryActivity.this, ViewScoreActivity.class);
                startActivity(intent);

            }

        });

        Button mLogout = (Button) findViewById(R.id.quitbt);
        mLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ResultGeometryActivity.this, MainActivity.class);
                startActivity(intent);

            }

        });

        Button mPlayAgain = (Button) findViewById(R.id.playagainbtn);
        mPlayAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ResultGeometryActivity.this, Menu.class);
                startActivity(intent);

            }

        });


    }
}