package edu.uw.team5.huskymathchallenge.CalculusCategory;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ResultCalculusQuizActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText mSaveUser;
    private TextView mResultView, mDisplayScoreView;
    private Button mSaveButton, mBackMenuButton;
    private int mScore;
    private String dbString;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_calculus_quiz);

        //Get text view
        mResultView =(TextView)findViewById(R.id.resultView);
        //Get score
        Bundle bundle = getIntent().getExtras();
        mScore = bundle.getInt("Score");
        mResultView.setText("Your Final Score: " + mScore);

        mSaveButton = (Button) findViewById(R.id.saveScore);
        mBackMenuButton = (Button) findViewById(R.id.backToMenu);
        mDisplayScoreView = (TextView) findViewById(R.id.displayScoreView);
        mSaveUser = (EditText) findViewById(R.id.username);
        //mSaveButton.setOnClickListener(this);
        mBackMenuButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(ResultCalculusQuizActivity.this, CategoryActivity.class);
        startActivity(intent);
    }
}
