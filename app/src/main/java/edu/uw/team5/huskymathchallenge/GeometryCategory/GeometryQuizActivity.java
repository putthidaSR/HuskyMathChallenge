package edu.uw.team5.huskymathchallenge.GeometryCategory;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import edu.uw.team5.huskymathchallenge.Menu;
import edu.uw.team5.huskymathchallenge.R;
import edu.uw.team5.huskymathchallenge.model.Score;

public class GeometryQuizActivity extends Activity
{
    //shared preferences
    private SharedPreferences gamePrefs;
    private SharedPreferences scorePrefs;
    public static final String GAME_PREFS = "GeometryFile";
    public static final String SCORE_PREFS = "GeometryScore";

    private static final String TAG = "Geometry Quiz";
    private List<String> fileNameList; //shape file names
    private List<String> quizShapList;
    private Map<String, Boolean> shapeMap;

    private String correctAnswer;
    private int correctAnswers; // number of correct guesses
    private int guessRows;
    private Random random;
    private Handler handler;
    private Animation shakeAnimation;

    private TextView answerTextView;
    private ImageView shapeImageView;
    private TableLayout buttonTableLayout;

    private final long startTime = 30 * 1000;
    private final long interval = 1 * 1000;
    private CountDownTimer countDownTimer;
    private TextView mTimerView;

    private TextView mLives;
    private int lives = 3;

    private TextView scoreVal;
    private int score;
    private EditText mUserName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geometry_quiz);

        //initiate shared prefs
        gamePrefs = getSharedPreferences(GAME_PREFS, 0);
        scorePrefs = getSharedPreferences(SCORE_PREFS, 0);

        fileNameList = new ArrayList<String>();
        quizShapList = new ArrayList<String>();
        shapeMap = new HashMap<String, Boolean>();
        guessRows = 2;
        random = new Random();
        handler = new Handler();
        shakeAnimation =
                AnimationUtils.loadAnimation(this, R.anim.incorrect_shake);
        shakeAnimation.setRepeatCount(3); String[] regionNames =
            getResources().getStringArray(R.array.shapeList);
        for (String region : regionNames )
            shapeMap.put(region, true);

        shapeImageView = (ImageView) findViewById(R.id.shapeImageView);

        buttonTableLayout = (TableLayout) findViewById(R.id.buttonTableLayout);
        answerTextView = (TextView) findViewById(R.id.answerTextView);

        mLives = (TextView) findViewById(R.id.lives);
        mLives.setText(String.valueOf(lives));

        mTimerView = (TextView) findViewById(R.id.timeView);

        scoreVal = (TextView) findViewById(R.id.scoreValue);
        scoreVal.setText(String.valueOf(score));

        countDownTimer = new MyCountDownTimer(startTime, interval);
        mTimerView.setText(mTimerView.getText() + String.valueOf(startTime / 1000));
        countDownTimer.start();

        resetQuiz();
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
        // Create Object of Dialog class
        final Dialog saveName = new Dialog(GeometryQuizActivity.this);
        // Set GUI of login screen
        saveName.setContentView(R.layout.dialog_save_name);
        saveName.setTitle("Save name...");

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
                    Intent intent = new Intent(GeometryQuizActivity.this, ResultGeometryActivity.class);
                    startActivity(intent);

                    // Redirect to dashboard / home screen.
                    saveName.dismiss();
                } else {
                    Toast.makeText(GeometryQuizActivity.this,
                            "Please enter Username", Toast.LENGTH_LONG).show();
                }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //saveName.dismiss();
                Intent intent = new Intent(GeometryQuizActivity.this, Menu.class);
                startActivity(intent);
            }
        });

        // Make dialog box visible.
        saveName.show();


    }

    public void gameOver(){
        // Create Object of Dialog class
        final Dialog saveName = new Dialog(GeometryQuizActivity.this);
        // Set GUI of login screen
        saveName.setContentView(R.layout.dialog_save_name);
        saveName.setTitle("Save name...");

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
                    Intent intent = new Intent(GeometryQuizActivity.this, ResultGeometryActivity.class);
                    startActivity(intent);

                    // Redirect to dashboard / home screen.
                    saveName.dismiss();
                } else {
                    Toast.makeText(GeometryQuizActivity.this,
                            "Please enter Username", Toast.LENGTH_LONG).show();
                }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GeometryQuizActivity.this, Menu.class);
                startActivity(intent);
                //saveName.dismiss();
            }
        });

        // Make dialog box visible.
        saveName.show();

    }

    private void resetQuiz() {
        AssetManager assets = getAssets();
        fileNameList.clear();

        try {
            Set<String> regions = shapeMap.keySet();

            for (String region : regions) {
                if (shapeMap.get(region)) {
                    String[] paths = assets.list(region);

                    for (String path : paths)
                        fileNameList.add(path.replace(".png", ""));
                }
            }
        }
        catch (IOException e) {
            Log.e(TAG, "Error loading image file names", e);
        }

        correctAnswers = 0;
        quizShapList.clear();

        int shapeCounter = 1;
        int numberOfFlags = fileNameList.size();
        while (shapeCounter <= 7) {
            int randomIndex = random.nextInt(numberOfFlags);
            String fileName = fileNameList.get(randomIndex);
            if (!quizShapList.contains(fileName)) {
                quizShapList.add(fileName);
                ++shapeCounter;
            }}
        loadNextImage();
    }

    private void loadNextImage() {
        String nextImageName = quizShapList.remove(0);
        correctAnswer = nextImageName;

        answerTextView.setText("");

        String region =
                nextImageName.substring(0, nextImageName.indexOf('-'));
        AssetManager assets = getAssets(); // get app's AssetManager
        InputStream stream;
        try {
            stream = assets.open(region + "/" + nextImageName + ".png");

            Drawable geometryShape = Drawable.createFromStream(stream, nextImageName);
            shapeImageView.setImageDrawable(geometryShape);
        } catch (IOException e) {
            Log.e(TAG, "Error loading " + nextImageName, e);
        }
        for (int row = 0; row < buttonTableLayout.getChildCount(); ++row)
            ((TableRow) buttonTableLayout.getChildAt(row)).removeAllViews();

        Collections.shuffle(fileNameList);

        int correct = fileNameList.indexOf(correctAnswer);
        fileNameList.add(fileNameList.remove(correct));

        LayoutInflater inflater = (LayoutInflater) getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);


        for (int row = 0; row < guessRows; row++) {
            TableRow currentTableRow = getTableRow(row);

            for (int column = 0; column < 2; column++) {
                Button newGuessButton =
                        (Button) inflater.inflate(R.layout.guess_button, null);
                String fileName = fileNameList.get((row * 3) + column);
                newGuessButton.setText(getCountryName(fileName));
                newGuessButton.setOnClickListener(guessButtonListener);
                currentTableRow.addView(newGuessButton);
            }
        }
        int row = random.nextInt(guessRows);
        int column = random.nextInt(2);
        TableRow randomTableRow = getTableRow(row);
        String countryName = getCountryName(correctAnswer);
        ((Button) randomTableRow.getChildAt(column)).setText(countryName);
    }

    private TableRow getTableRow(int row) {
        return (TableRow) buttonTableLayout.getChildAt(row);
    }

    private String getCountryName(String name) {
        return name.substring(name.indexOf('-') + 1).replace('_', ' ');
    }

    private void submitGuess(Button guessButton) {
        String guess = guessButton.getText().toString();
        String answer = getCountryName(correctAnswer);

        if (guess.equals(answer)) {
            ++score;
            ++correctAnswers;
            //updateScore();


            scoreVal.setText(String.valueOf(score));

            setHighScore();
            saveScore();

            answerTextView.setText(answer + "!");
            answerTextView.setTextColor(
                    getResources().getColor(R.color.correct_answer));

            disableButtons();
                if(lives != 0) {
                    handler.postDelayed(
                    new Runnable() {
                        @Override
                        public void run() {
                            loadNextImage();
                        }
                    }, 1000);
                }
        }
        else {

            lives--;
            mLives.setText(String.valueOf(lives));

            shapeImageView.startAnimation(shakeAnimation);
            answerTextView.setText(R.string.incorrect_answer);
            answerTextView.setTextColor(
                    getResources().getColor(R.color.incorrect_answer));
            guessButton.setEnabled(false);
        }

        if(lives <= 0) {
            gameOver();
        }
    }

    private void disableButtons() {
        for (int row = 0; row < buttonTableLayout.getChildCount(); ++row) {
            TableRow tableRow = (TableRow) buttonTableLayout.getChildAt(row);
            for (int i = 0; i < tableRow.getChildCount(); ++i)
                tableRow.getChildAt(i).setEnabled(false);
        }
    }

    private OnClickListener guessButtonListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            submitGuess((Button) v);
        }
    };

    /**
     * This method is to update the score on EditText.
     */
    private void updateScore() {
        TextView editText = (TextView) findViewById(R.id.scoreValue);
        editText.setText(String.valueOf(scoreVal));
    }

    //method retrieves score
    private int getScore(){
        String scoreStr = scoreVal.getText().toString();
        return Integer.parseInt(scoreStr.substring(scoreStr.lastIndexOf(" ")+1));
    }

    private void saveScore() {

        int exScore = getScore();
        if(exScore>0){
            //we have a valid score
            SharedPreferences.Editor scoreEdit = scorePrefs.edit();
            scoreEdit.putInt("Score", exScore);
            scoreEdit.commit();
        }
    }

    //set high score
    private void setHighScore(){
        int exScore = getScore();
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