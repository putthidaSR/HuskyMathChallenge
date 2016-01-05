package edu.uw.team5.huskymathchallenge.GeometryCategory;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import edu.uw.team5.huskymathchallenge.R;

public class ViewScoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_score);

        //get text view
        TextView scoreView = (TextView)findViewById(R.id.high_scores_list);
        //get shared prefs
        SharedPreferences scorePrefs = getSharedPreferences(GeometryQuizActivity.GAME_PREFS, 0);
        //get scores
        String[] savedScores = scorePrefs.getString("highScores", "").split("\\|");
        //build string
        StringBuilder scoreBuild = new StringBuilder("");
        for(String score : savedScores){
            scoreBuild.append(score+"\n");
        }
        //display scores
        scoreView.setText(scoreBuild.toString());
    }
}
