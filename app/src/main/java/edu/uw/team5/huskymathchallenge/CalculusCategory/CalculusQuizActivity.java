package edu.uw.team5.huskymathchallenge.CalculusCategory;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class CalculusQuizActivity extends AppCompatActivity implements View.OnClickListener {

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

    private ArrayList<CalculusQuestion> mList;
    private String mAlgebraQuestion;
    private CalculusQuestion mQuestion;
    private static final String
            url = "http://cssgate.insttech.washington.edu/~leebui99/CalQuestion.php";
    private ProgressDialog mProgressDialog;

    private final long startTime = 60 * 1000;
    private final long interval = 1 * 1000;
    private CountDownTimer countDownTimer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculus_quiz);

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
        mList = new ArrayList<CalculusQuestion>();
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
            Toast.makeText( CalculusQuizActivity.this ,"Right And Your Current Score: " + mScore, duration).show();
        }else {
            mLive--;
            if (mLive < 0){
                timerUp();
            }
            Toast.makeText( CalculusQuizActivity.this ,"Wrong and You have " + mLive + " live left", duration).show();
        }


        if (mQuestionId < mList.size()){
            mQuestion = mList.get(mQuestionId);
            setNextQuestion();
        }else {
            Intent intent = new Intent(v.getContext(), ResultCalculusQuizActivity.class);
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
            mProgressDialog = ProgressDialog.show(CalculusQuizActivity.this, "Wait", "Downloading...");;
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
            result = CalculusQuestionList.parseAlgQuestionJSON(mAlgebraQuestion, mList);
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

    public void timerUp(){
        AlertDialog.Builder builder = new AlertDialog.Builder(
                CalculusQuizActivity.this);

        builder.setTitle("Times up or No more lives")
                .setMessage("Go back and try again if you want!")
                .setCancelable(false)
                .setNeutralButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent intent = new Intent(getBaseContext(), ResultCalculusQuizActivity.class);
                                startActivity(intent);
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
    }

}
