package edu.uw.team5.huskymathchallenge.AlgebraCategory;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import edu.uw.team5.huskymathchallenge.Menu;
import edu.uw.team5.huskymathchallenge.R;
import edu.uw.team5.huskymathchallenge.model.Score;

public class AlgebraActivity extends AppCompatActivity implements View.OnClickListener {

    //shared preferences
    private SharedPreferences gamePrefs;
    private SharedPreferences scorePrefs;
    public static final String GAME_PREFS = "AlgebraFile";
    public static final String SCORE_PREFS = "AlgebraScore";

    private EditText mUserName;


    private TextView mAlgQuestion, mLiveView, mTimerView;
    private RadioGroup mRadioGroup;
    RadioButton mCorrectAnswer;
    private RadioButton mAnswer1;
    private RadioButton mAnswer2;
    private RadioButton mAnswer3;
    private RadioButton mAnswer4;
    private Button mNext;
    private int mQuestionId = 0;
    private int mScore = 0;
    private int mLive = 3;
    private String TAG = "Determine X for ";

    private ArrayList<AlgebraQuestion> mList;
    private String mAlgebraQuestion;
    private AlgebraQuestion mQuestion;
    private static final String
            url = "http://cssgate.insttech.washington.edu/~leebui99/Question.php";
    private ProgressDialog mProgressDialog;

    private final long startTime = 60 * 1000;
    private final long interval = 1 * 1000;
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_algebra);

        //initiate shared prefs
        gamePrefs = getSharedPreferences(GAME_PREFS, 0);
        scorePrefs = getSharedPreferences(SCORE_PREFS, 0);

        //Lookup view for data into the template view using the data object
        mAlgQuestion = (TextView) findViewById(R.id.algebraQuestionView);
        mLiveView = (TextView) findViewById(R.id.scoreView);
        mTimerView = (TextView) findViewById(R.id.timeView);
        mAnswer1 = (RadioButton)findViewById(R.id.radioAnswer1);
        mAnswer2 = (RadioButton) findViewById(R.id.radioAnswer2);
        mAnswer3 = (RadioButton) findViewById(R.id.radioAnswer3);
        mAnswer4 = (RadioButton) findViewById(R.id.radioAnswer4);
        mNext = (Button) findViewById(R.id.buttonSubmit);

        mNext.setOnClickListener(this);
        mLiveView.setOnClickListener(this);

        countDownTimer = new MyCountDownTimer(startTime, interval);
        mTimerView.setText(mTimerView.getText() + String.valueOf(startTime / 1000));
        countDownTimer.start();

        // mScoreView.setText(mScoreView.getText() + String.valueOf(mScore));
        //mLiveView.setText(mLiveView.getText() + String.valueOf(mLive));
        mRadioGroup = (RadioGroup)findViewById(R.id.radioAnswer);
        mCorrectAnswer = (RadioButton)findViewById(mRadioGroup.getCheckedRadioButtonId());
    }


    @Override
    protected void onStart() {
        super.onStart();
        mList = new ArrayList<AlgebraQuestion>();
        DownloadWebPageTask task = new DownloadWebPageTask();
        task.execute(new String[]{url});
    }

    @Override
    public void onClick(View v) {

//        int num;
//        num = mQuestion.getCorrect();
        int duration = Toast.LENGTH_SHORT;

        int radioId = mRadioGroup.getCheckedRadioButtonId();
        View radioButton = mRadioGroup.findViewById(radioId);
        int index = mRadioGroup.indexOfChild(radioButton);

//       Toast.makeText( AlgebraQuizActivity.this ,"SCORE" + index, duration).show();
        if ( mQuestion.getCorrect() == index){
            mScore++;
            Toast.makeText( AlgebraActivity.this ,"Right And Your Current Score: " + mScore, duration).show();

            setHighScore();
            saveScore();

        } else {
            mLive--;
            if (mLive <= 0){
                gameOver();
            }
            Toast.makeText( AlgebraActivity.this ,"Wrong and You have " + mLive + " lives left", duration).show();
        }


        if (mQuestionId < mList.size()){
            mQuestion = mList.get(mQuestionId);
            setNextQuestion();
        }else {
            Intent intent = new Intent(v.getContext(), ResultAlgebraQuizActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt("Score", mScore);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }

    private class DownloadWebPageTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = ProgressDialog.show(AlgebraActivity.this, "Wait", "Downloading...");;
        }

        @Override
        protected String doInBackground(String... urls) {
            String response = "";
            HttpURLConnection urlConnection = null;
            for (String url : urls) {
                try {
                    URL urlObject = new URL(url);
                    urlConnection = (HttpURLConnection) urlObject.openConnection();

                    InputStream content = urlConnection.getInputStream();

                    BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
                    String s = "";
                    while ((s = buffer.readLine()) != null) {
                        response += s;
                    }

                } catch (Exception e) {
                    response = "Unable to download the list of question, Reason: "
                            + e.getMessage();
                }
                finally {
                    if (urlConnection != null)
                        urlConnection.disconnect();
                }
            }
            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            // Something wrong with the network or the URL.
            mProgressDialog.dismiss();
            //Success to retrieve data from the service
            mAlgebraQuestion = result;
            result = AlgebraQuestionList.parseAlgQuestionJSON(mAlgebraQuestion, mList);
            mQuestion = mList.get(mQuestionId);
            setNextQuestion();
        }
    }

    public void setNextQuestion(){
        mAlgQuestion.setText(TAG + mQuestion.toString());
        mAnswer1.setText(mQuestion.getAnswer1());
        mAnswer2.setText(mQuestion.getAnswer2());
        mAnswer3.setText(mQuestion.getAnswer3());
        mAnswer4.setText(mQuestion.getAnswer4());
        mQuestionId++;

    }

    public class MyCountDownTimer extends CountDownTimer {

        public MyCountDownTimer(long startTime, long interval) {
            super(startTime, interval);
        }

        @Override
        public void onFinish() {
            timerUp();
        }

        @Override
        public void onTick(long millisUntilFinished) {
            mTimerView.setText("" + millisUntilFinished / 1000);
        }

    }

//    public void timerUp(){
//        AlertDialog.Builder builder = new AlertDialog.Builder(
//                AlgebraActivity.this);
//
//        builder.setTitle("Times up or No more lives")
//                .setMessage("Go back and try again if you want!")
//                .setCancelable(false)
//                .setNeutralButton(android.R.string.ok,
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//                                Intent intent = new Intent(getBaseContext(), ResultAlgebraQuizActivity.class);
//                                Bundle bundle = new Bundle();
//                                bundle.putInt("Score", mScore);
//                                intent.putExtras(bundle);
//                                startActivity(intent);
//                            }
//                        });
//        AlertDialog alert = builder.create();
//        alert.show();
//    }

    public void timerUp(){
        // Create Object of Dialog class
        final Dialog saveName = new Dialog(AlgebraActivity.this);
        // Set GUI of login screen
        saveName.setContentView(R.layout.dialog_save_name);
        saveName.setTitle("Time Up!!! Save name...");

        // Init button of login GUI
        Button btnLogin = (Button) saveName.findViewById(R.id.saveBtn);
        Button btnCancel = (Button) saveName.findViewById(R.id.btnCancel);
        mUserName = (EditText) saveName.findViewById(R.id.username);

        // Attached listener for login GUI button
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences.Editor scoreEdit = scorePrefs.edit();
                scoreEdit.putString("Name", mUserName.getText().toString());

                scoreEdit.commit();

                if (mUserName.getText().toString().trim().length() > 0) {
                    Intent intent = new Intent(AlgebraActivity.this, ResultAlgebraQuizActivity.class);
                    startActivity(intent);

                    // Redirect to dashboard / home screen.
                    saveName.dismiss();
                } else {
                    Toast.makeText(AlgebraActivity.this,
                            "Please enter Username", Toast.LENGTH_LONG).show();
                }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //saveName.dismiss();
                Intent intent = new Intent(AlgebraActivity.this, Menu.class);
                startActivity(intent);
            }
        });

        // Make dialog box visible.
        saveName.show();


    }

    public void gameOver(){
        // Create Object of Dialog class
        final Dialog saveName = new Dialog(AlgebraActivity.this);
        // Set GUI of login screen
        saveName.setContentView(R.layout.dialog_save_name);
        saveName.setTitle("Gave Over!!! Save name...");

        // Init button of login GUI
        Button btnLogin = (Button) saveName.findViewById(R.id.saveBtn);
        Button btnCancel = (Button) saveName.findViewById(R.id.btnCancel);
        mUserName = (EditText) saveName.findViewById(R.id.username);

        // Attached listener for login GUI button
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences.Editor scoreEdit = scorePrefs.edit();
                scoreEdit.putString("Name", mUserName.getText().toString());

                scoreEdit.commit();

                if (mUserName.getText().toString().trim().length() > 0) {
                    Intent intent = new Intent(AlgebraActivity.this, ResultAlgebraQuizActivity.class);
                    startActivity(intent);

                    // Redirect to dashboard / home screen.
                    saveName.dismiss();
                } else {
                    Toast.makeText(AlgebraActivity.this,
                            "Please enter Username", Toast.LENGTH_LONG).show();
                }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AlgebraActivity.this, Menu.class);
                startActivity(intent);
                //saveName.dismiss();
            }
        });

        // Make dialog box visible.
        saveName.show();

    }

    private void saveScore() {

        int exScore = mScore;
        if(exScore>0){
            //we have a valid score
            SharedPreferences.Editor scoreEdit = scorePrefs.edit();
            scoreEdit.putInt("Score", exScore);
            scoreEdit.commit();
        }
    }

    //set high score
    private void setHighScore(){
        int exScore = mScore;
        //String name = mUserName.getText().toString();
        if(exScore > 0) {
            //we have a valid score
            SharedPreferences.Editor scoreEdit = gamePrefs.edit();
            DateFormat dateForm = new SimpleDateFormat("dd MMMM yyyy");
            String dateOutput = dateForm.format(new Date());
            //get existing scores
            String scores = gamePrefs.getString("highScores", "");
            //check for scores
            if (scores.length() > 0) {
                //we have existing scores
                List<Score> scoreStrings = new ArrayList<Score>();
                //split scores
                String[] exScores = scores.split("\\|");
                //add score object for each
                for (String eSc : exScores) {
                    String[] parts = eSc.split(" - ");
                    scoreStrings.add(new Score(parts[0], Integer.parseInt(parts[1])));
                }
                //new score
                Score newScore = new Score(dateOutput, exScore);
                scoreStrings.add(newScore);
                //sort
                Collections.sort(scoreStrings);
                //get top ten
                StringBuilder scoreBuild = new StringBuilder("");
                for (int s=0; s<scoreStrings.size(); s++) {
                    if(s>=5) break;
                    if(s>0) scoreBuild.append("|");
                    scoreBuild.append(scoreStrings.get(s).getScoreText());
                }

                //write to prefs
                scoreEdit.putString("highScores", scoreBuild.toString());

                //     scoreEdit.putString("highScores", name + ": " + scoreBuild.toString());
                scoreEdit.commit();

            } else {
                //no existing scores
                scoreEdit.putString("highScores", dateOutput + " - " + exScore);
                //   scoreEdit.putString("highScores", name + ": " + dateOutput + " - " + exScore);

                scoreEdit.commit();
            }
        }
    }

    //set high score if activity destroyed
    protected void onDestroy(){
        setHighScore();
        super.onDestroy();
    }
}
