package edu.uw.team5.huskymathchallenge.AllAboutMath;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

import edu.uw.team5.huskymathchallenge.Menu;
import edu.uw.team5.huskymathchallenge.R;

public class AllAboutMath extends AppCompatActivity {

    //shared preferences
    private SharedPreferences gamePrefs;
    private SharedPreferences scorePrefs;
    public static final String GAME_PREFS = "AllAboutMathFile";
    public static final String SCORE_PREFS = "AllAboutMathScore";

    private EditText mUserName;
    private final long startTime = 200 * 1000;
    private final long interval = 1 * 1000;
    private CountDownTimer countDownTimer;
    private TextView mTimerView;
    private Handler mHandler = new Handler();
    private int points = 0;
    final static String youwon1 = ":)";
    final static String youwon2 = "you";
    final static String youwon3 = "won";
    final static String youwon4 = "!!";
    final static String PLUS = "x+y";
    final static String MINUS = "x-y";
    final static String MULTI = "x*y";
    final static String DIVIDE = "x/y";
    final static String SQUARE = "x*x";
    final static String CUBE = "x*x*x";
    final static String SQUAREROOT = "squareroot";
    private String CURRENTMODE = PLUS;
    final static int CEILINGRESET = 10000;
    final static int CEILING = 1000;
    final static int CEILINGHALF = CEILING/2;
    private Random myRandom = new Random();
    private int myCurrentSum;
    private int targetInt;
    private String localTxt = "A";
    private int bttnValueInt;
    private boolean acceptInput = true;



    //private int start, timeLeft, timeTaken;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advanced_math);
        splash(null);
       // share();

        //initiate shared prefs
        gamePrefs = getSharedPreferences(GAME_PREFS, 0);
        scorePrefs = getSharedPreferences(SCORE_PREFS, 0);

        mTimerView = (TextView) findViewById(R.id.timer);

        countDownTimer = new MyCountDownTimer(startTime, interval);
        mTimerView.setText(mTimerView.getText() + String.valueOf(startTime / 1000));
        countDownTimer.start();

    }

    @Override
    protected void onResume()
    {
        super.onResume();
        splash(null);
    }

    @Override
    protected void onPause() {
        super.onPause();
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

    public void splash(View view) {

        final TextView additionArrow = (TextView)findViewById(R.id.additionarrow);
        ViewGroup.LayoutParams additionArrowParams = additionArrow.getLayoutParams();
        additionArrowParams.height = 0;
        additionArrow.setLayoutParams(additionArrowParams);
        final TextView subtractionArrow = (TextView)findViewById(R.id.subtractionarrow);
        ViewGroup.LayoutParams subtractionArrowParams = subtractionArrow.getLayoutParams();
        subtractionArrowParams.height = 0;
        subtractionArrow.setLayoutParams(subtractionArrowParams);
        final TextView multiplicationArrow = (TextView)findViewById(R.id.multiplicationarrow);
        ViewGroup.LayoutParams multiplicationArrowParams = multiplicationArrow.getLayoutParams();
        multiplicationArrowParams.height = 0;
        multiplicationArrow.setLayoutParams(multiplicationArrowParams);
        final TextView divisionArrow = (TextView)findViewById(R.id.divisionarrow);
        ViewGroup.LayoutParams divisionArrowParams = divisionArrow.getLayoutParams();
        divisionArrowParams.height = 0;
        divisionArrow.setLayoutParams(divisionArrowParams);

        setall(null);
    }
    public void setall(View view) {
        myCurrentSum = 0;
        targetInt = myRandom.nextInt(CEILING);
        final TextView targetNumber = (TextView)findViewById(R.id.targetnumber);
        final TextView currentNumber = (TextView)findViewById(R.id.currentnumber);
        currentNumber.setText(String.valueOf(0));
        targetNumber.setText(String.valueOf(targetInt));
        CURRENTMODE = PLUS;
        //youWonToggle(false);
       setFlock();

//        Button button = (Button) findViewById(R.id.share_btn);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                int start = Integer.valueOf((int) (startTime / 1000));
//                int timeL = Integer.valueOf((String) mTimerView.getText());
//                int timeTaken = start - timeL;
//                Intent sendIntent = new Intent();
//                sendIntent.setAction(Intent.ACTION_SEND);
//                sendIntent.putExtra(Intent.EXTRA_TEXT, "It took me " + (timeTaken) + "second to complete the game.");
//                sendIntent.setType("text/plain");
//                startActivity(Intent.createChooser(sendIntent, getResources().getText(R.string.send_to)));
//
//            }
//
//        });

    }

    public void setFlock(){
        bump1(2000, false);
        bump2(1500, false);
        bump3(1000, false);
        bump4(targetInt, false);
        if (targetInt % 128 == 0) {bump5(128, true);}
        else { bump5(512, false);}
        bump6(targetInt, false);
        bump7(targetInt, false);
        bump8(targetInt, false);
        bump9(1000, false);
        bump10(targetInt, false);
        bump11(250, false);
        bump12(2, true);
        bump13(targetInt, false);
        bump14(targetInt, false);
        bump15(targetInt, false);
        bump16(1, true);
        bump17(targetInt, false);
        bump18(100, false);
        bump19(20, false);
        bump20(0, true);
    }


    public void chooseModeOp (View view) {
        int h = 40;
        final TextView currentNumber = (TextView)findViewById(R.id.currentnumber);
        if (Integer.parseInt((String) currentNumber.getText()) > CEILINGRESET) {
            CURRENTMODE = MINUS;
        }

        final TextView additionArrow = (TextView)findViewById(R.id.additionarrow);
        ViewGroup.LayoutParams additionArrowParams = additionArrow.getLayoutParams();
        additionArrowParams.height = 0;
        additionArrow.setLayoutParams(additionArrowParams);
        final TextView subtractionArrow = (TextView)findViewById(R.id.subtractionarrow);
        ViewGroup.LayoutParams subtractionArrowParams = subtractionArrow.getLayoutParams();
        subtractionArrowParams.height = 0;
        subtractionArrow.setLayoutParams(subtractionArrowParams);
        final TextView multiplicationArrow = (TextView)findViewById(R.id.multiplicationarrow);
        ViewGroup.LayoutParams multiplicationArrowParams = multiplicationArrow.getLayoutParams();
        multiplicationArrowParams.height = 0;
        multiplicationArrow.setLayoutParams(multiplicationArrowParams);
        final TextView divisionArrow = (TextView)findViewById(R.id.divisionarrow);
        ViewGroup.LayoutParams divisionArrowParams = divisionArrow.getLayoutParams();
        divisionArrowParams.height = 0;
        divisionArrow.setLayoutParams(divisionArrowParams);
        switch (view.getId()) {
            case R.id.addition:
                CURRENTMODE=PLUS;
                additionArrowParams.height = h;
                additionArrow.setLayoutParams(additionArrowParams);
                break;
            case R.id.subtraction:
                CURRENTMODE=MINUS;
                subtractionArrowParams.height = h;
                subtractionArrow.setLayoutParams(subtractionArrowParams);
                break;
            case R.id.multiplication:
                CURRENTMODE=MULTI;
                multiplicationArrowParams.height = h;
                multiplicationArrow.setLayoutParams(multiplicationArrowParams);
                break;
            case R.id.division:
                CURRENTMODE=DIVIDE;
                divisionArrowParams.height = h;
                divisionArrow.setLayoutParams(divisionArrowParams);
                break;
            case R.id.square:
                CURRENTMODE=SQUARE;
                exponentMe();
                CURRENTMODE=PLUS;
                additionArrowParams.height = h;
                additionArrow.setLayoutParams(additionArrowParams);
                break;
            case R.id.cube:
                CURRENTMODE=CUBE;
                exponentMe();
                CURRENTMODE=PLUS;
                additionArrowParams.height = h;
                additionArrow.setLayoutParams(additionArrowParams);
                break;
            case R.id.squareroot:
                CURRENTMODE=SQUAREROOT;
                exponentMe();
                CURRENTMODE=PLUS;
                additionArrowParams.height = h;
                additionArrow.setLayoutParams(additionArrowParams);
                break;
            default: CURRENTMODE=PLUS;
        }
    }

    public void exponentMe() {

        if (CURRENTMODE.toLowerCase() == SQUARE) {
            myCurrentSum = myCurrentSum * myCurrentSum;
        }
        if (CURRENTMODE.toLowerCase() == CUBE) {
            myCurrentSum = myCurrentSum * myCurrentSum * myCurrentSum;
        }
        if (CURRENTMODE.toLowerCase() == SQUAREROOT) {
           myCurrentSum = Math.round((float)Math.sqrt(myCurrentSum));
        }

        final TextView currentNumber = (TextView)findViewById(R.id.currentnumber);
        currentNumber.setText(String.valueOf( myCurrentSum));
        eyewon(null);
    }


    public void processIntegerInput(View view) {
        if (acceptInput == true) {
            hitceiling();
            Animation fasterscrollRight = AnimationUtils.loadAnimation(this, R.anim.fastersplashscrollright);
           Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(this, R.anim.animhyperspacejump);
            final TextView currentNumber = (TextView)findViewById(R.id.currentnumber);
            switch (view.getId()) {
                case R.id.movingBttn1:
                    final TextView tv1 = (TextView)findViewById(R.id.movingBttn1);
                    localTxt = String.valueOf(tv1.getText());
                    tv1.startAnimation(hyperspaceJumpAnimation);
                    break;
                case R.id.movingBttn2:
                    final TextView tv2 = (TextView)findViewById(R.id.movingBttn2);
                    localTxt = String.valueOf(tv2.getText());
                    tv2.startAnimation(hyperspaceJumpAnimation);
                    break;
                case R.id.movingBttn3:
                    final TextView tv3 = (TextView)findViewById(R.id.movingBttn3);
                    localTxt = String.valueOf(tv3.getText());
                    tv3.startAnimation(hyperspaceJumpAnimation);
                    break;
                case R.id.movingBttn4:
                    final TextView tv4 = (TextView)findViewById(R.id.movingBttn4);
                    localTxt = String.valueOf(tv4.getText());
                   tv4.startAnimation(hyperspaceJumpAnimation);
                    break;
                case R.id.movingBttn5:
                    final TextView tv5 = (TextView)findViewById(R.id.movingBttn5);
                    localTxt = String.valueOf(tv5.getText());
                    tv5.startAnimation(hyperspaceJumpAnimation);
                    break;
                case R.id.movingBttn6:
                    final TextView tv6 = (TextView)findViewById(R.id.movingBttn6);
                    localTxt = String.valueOf(tv6.getText());
                    tv6.startAnimation(hyperspaceJumpAnimation);
                    break;
                case R.id.movingBttn7:
                    final TextView tv7 = (TextView)findViewById(R.id.movingBttn7);
                    localTxt = String.valueOf(tv7.getText());
                    tv7.startAnimation(hyperspaceJumpAnimation);
                    break;
                case R.id.movingBttn8:
                    final TextView tv8 = (TextView)findViewById(R.id.movingBttn8);
                    localTxt = String.valueOf(tv8.getText());
                    tv8.startAnimation(hyperspaceJumpAnimation);
                    break;
                case R.id.movingBttn9:
                    final TextView tv9 = (TextView)findViewById(R.id.movingBttn9);
                    localTxt = String.valueOf(tv9.getText());
                    tv9.startAnimation(hyperspaceJumpAnimation);
                    break;
                case R.id.movingBttn10:
                    final TextView tv10 = (TextView)findViewById(R.id.movingBttn10);
                    localTxt = String.valueOf(tv10.getText());
                    tv10.startAnimation(hyperspaceJumpAnimation);
                    break;
                case R.id.movingBttn11:
                    final TextView tv11 = (TextView)findViewById(R.id.movingBttn11);
                    localTxt = String.valueOf(tv11.getText());
                   tv11.startAnimation(hyperspaceJumpAnimation);
                    break;
                case R.id.movingBttn12:
                    final TextView tv12 = (TextView)findViewById(R.id.movingBttn12);
                    localTxt = String.valueOf(tv12.getText());
                    tv12.startAnimation(hyperspaceJumpAnimation);
                    break;
                case R.id.movingBttn13:
                    final TextView tv13 = (TextView)findViewById(R.id.movingBttn13);
                    localTxt = String.valueOf(tv13.getText());
                   tv13.startAnimation(hyperspaceJumpAnimation);
                    break;
                case R.id.movingBttn14:
                    final TextView tv14 = (TextView)findViewById(R.id.movingBttn14);
                    localTxt = String.valueOf(tv14.getText());
                   tv14.startAnimation(hyperspaceJumpAnimation);
                    break;
                case R.id.movingBttn15:
                    final TextView tv15 = (TextView)findViewById(R.id.movingBttn15);
                    localTxt = String.valueOf(tv15.getText());
                    tv15.startAnimation(hyperspaceJumpAnimation);
                    break;
                case R.id.movingBttn16:
                    final TextView tv16 = (TextView)findViewById(R.id.movingBttn16);
                    localTxt = String.valueOf(tv16.getText());
                    tv16.startAnimation(hyperspaceJumpAnimation);
                    break;
                case R.id.movingBttn17:
                    final TextView tv17 = (TextView)findViewById(R.id.movingBttn17);
                    localTxt = String.valueOf(tv17.getText());
                    tv17.startAnimation(hyperspaceJumpAnimation);
                    break;
                case R.id.movingBttn18:
                    final TextView tv18 = (TextView)findViewById(R.id.movingBttn18);
                    localTxt = String.valueOf(tv18.getText());
                    tv18.startAnimation(hyperspaceJumpAnimation);
                    break;
                case R.id.movingBttn19:
                    final TextView tv19 = (TextView)findViewById(R.id.movingBttn19);
                    localTxt = String.valueOf(tv19.getText());
                    tv19.startAnimation(hyperspaceJumpAnimation);
                    break;
                case R.id.movingBttn20:
                    final TextView tv20 = (TextView)findViewById(R.id.movingBttn20);
                    localTxt = String.valueOf(tv20.getText());
                    tv20.startAnimation(hyperspaceJumpAnimation);
                    break;

            }

            bttnValueInt=Integer.parseInt(localTxt);
            if (CURRENTMODE.toLowerCase() == PLUS) { myCurrentSum = myCurrentSum + bttnValueInt; }
            else if (CURRENTMODE.toLowerCase() == MINUS) { myCurrentSum = myCurrentSum - bttnValueInt; }
            else if (CURRENTMODE.toLowerCase() == MULTI) { myCurrentSum = myCurrentSum * bttnValueInt; }
            else if (CURRENTMODE.toLowerCase() == DIVIDE) { myCurrentSum = myCurrentSum / bttnValueInt; }
            currentNumber.setText(String.valueOf( myCurrentSum));
            currentNumber.startAnimation(fasterscrollRight);
            eyewon(null);

        }
    }

    public void bump1(int input, boolean force) {
        final TextView tv = (TextView)findViewById(R.id.movingBttn1);
        if (acceptInput == false) {
            tv.setText(String.valueOf(youwon1));
        } else {
            if (force == true) {tv.setText(String.valueOf(input));}
            else {tv.setText(String.valueOf(myRandom.nextInt(input)));}
        }
    }
    public void bump2(int input, boolean force) {
        final TextView tv = (TextView)findViewById(R.id.movingBttn2);
        if (acceptInput == false) {
            tv.setText(String.valueOf(youwon2));
        } else {
            if (force == true) {tv.setText(String.valueOf(input));}
            else {tv.setText(String.valueOf(myRandom.nextInt(input)));}
        }
    }
    public void bump3(int input, boolean force) {
        final TextView tv = (TextView)findViewById(R.id.movingBttn3);
        if (acceptInput == false) {
            tv.setText(String.valueOf(youwon3));
        } else {
            if (force == true) {tv.setText(String.valueOf(input));}
            else {tv.setText(String.valueOf(myRandom.nextInt(input)));}
           }
    }
    public void bump4(int input, boolean force) {
        final TextView tv = (TextView)findViewById(R.id.movingBttn4);
        if (acceptInput == false) {
            tv.setText(String.valueOf(youwon4));
        } else {
            if (force == true) {tv.setText(String.valueOf(input));}
            else {tv.setText(String.valueOf(myRandom.nextInt(input)));}
        }
    }
    public void bump5(int input, boolean force) {
        final TextView tv = (TextView)findViewById(R.id.movingBttn5);
        if (acceptInput == false) {
            tv.setText(String.valueOf(youwon1));
        } else {
            if (force == true) {tv.setText(String.valueOf(input));}
            else {tv.setText(String.valueOf(myRandom.nextInt(input)));}
        }
    }
    public void bump6(int input, boolean force) {
        final TextView tv = (TextView)findViewById(R.id.movingBttn6);
        if (acceptInput == false) {
            tv.setText(String.valueOf(youwon1));

        } else {
            if (force == true) {tv.setText(String.valueOf(input));}
            else {tv.setText(String.valueOf(myRandom.nextInt(input)));}

        }
    }
    public void bump7(int input, boolean force) {
        final TextView tv = (TextView)findViewById(R.id.movingBttn7);
        if (acceptInput == false) {
            tv.setText(String.valueOf(youwon1));
        } else {
            if (force == true) {tv.setText(String.valueOf(input));}
            else {tv.setText(String.valueOf(myRandom.nextInt(input)));}
        }
    }
    public void bump8(int input, boolean force) {
        final TextView tv = (TextView)findViewById(R.id.movingBttn8);
        if (acceptInput == false) {
            tv.setText(String.valueOf(youwon1));

        } else {
            if (force == true) {tv.setText(String.valueOf(input));}
            else {tv.setText(String.valueOf(myRandom.nextInt(input)));}

        }
    }
    public void bump9(int input, boolean force) {
        final TextView tv = (TextView)findViewById(R.id.movingBttn9);
        if (acceptInput == false) {
            tv.setText(String.valueOf(youwon1));
        } else {
            if (force == true) {tv.setText(String.valueOf(input));}
            else {tv.setText(String.valueOf(myRandom.nextInt(input)));}
        }
    }
    public void bump10(int input, boolean force) {
        final TextView tv = (TextView)findViewById(R.id.movingBttn10);
        if (acceptInput == false) {
            tv.setText(String.valueOf(youwon1));
        } else {
            if (force == true) {tv.setText(String.valueOf(input));}
            else {tv.setText(String.valueOf(myRandom.nextInt(input)));}
        }
    }
    public void bump11(int input, boolean force) {
        final TextView tv = (TextView)findViewById(R.id.movingBttn11);
        if (acceptInput == false) {
            tv.setText(String.valueOf(youwon1));
        } else {
            if (force == true) {tv.setText(String.valueOf(input));}
            else {tv.setText(String.valueOf(myRandom.nextInt(input)));}
        }
    }
    public void bump12(int input, boolean force) {
        final TextView tv = (TextView)findViewById(R.id.movingBttn12);
        if (acceptInput == false) {
            tv.setText(String.valueOf(youwon1));
        } else {
            if (force == true) {tv.setText(String.valueOf(input));}
            else {tv.setText(String.valueOf(myRandom.nextInt(input)));}
        }
    }
    public void bump13(int input, boolean force) {
        final TextView tv = (TextView)findViewById(R.id.movingBttn13);
        if (acceptInput == false) {
            tv.setText(String.valueOf(youwon1));
        } else {
            if (force == true) {tv.setText(String.valueOf(input));}
            else {tv.setText(String.valueOf(myRandom.nextInt(input)));}
        }
    }
    public void bump14(int input, boolean force) {
        final TextView tv = (TextView)findViewById(R.id.movingBttn14);
        if (acceptInput == false) {
            tv.setText(String.valueOf(youwon1));
        } else {
            if (force == true) {tv.setText(String.valueOf(input));}
            else {tv.setText(String.valueOf(myRandom.nextInt(input)));}
        }
    }
    public void bump15(int input, boolean force) {
        final TextView tv = (TextView)findViewById(R.id.movingBttn15);
        if (acceptInput == false) {
            tv.setText(String.valueOf(youwon1));
        } else {
            if (force == true) {tv.setText(String.valueOf(input));}
            else {tv.setText(String.valueOf(myRandom.nextInt(input)));}

        }
    }
    public void bump16(int input, boolean force) {
        final TextView tv = (TextView)findViewById(R.id.movingBttn16);
        if (acceptInput == false) {
            tv.setText(String.valueOf(youwon1));
        } else {
            if (force == true) {tv.setText(String.valueOf(input));}
            else {tv.setText(String.valueOf(myRandom.nextInt(input)));}

        }
    }
    public void bump17(int input, boolean force) {
        final TextView tv = (TextView)findViewById(R.id.movingBttn17);
        if (acceptInput == false) {
            tv.setText(String.valueOf(youwon1));
        } else {
            if (force == true) {tv.setText(String.valueOf(input));}
            else {
                tv.setText(String.valueOf(myRandom.nextInt(input)));
            }
        }
    }
    public void bump18(int input, boolean force) {
        final TextView tv = (TextView)findViewById(R.id.movingBttn18);
        if (acceptInput == false) {
            tv.setText(String.valueOf(youwon1));
        } else {
            if (force == true) {tv.setText(String.valueOf(input));}
            else {tv.setText(String.valueOf(myRandom.nextInt(input)));}
        }
    }
    public void bump19(int input, boolean force) {
        final TextView tv = (TextView)findViewById(R.id.movingBttn19);
        if (acceptInput == false) {
            tv.setText(String.valueOf(youwon1));
            Animation rotateNinety = AnimationUtils.loadAnimation(this, R.anim.rotatezerotoninety);
            tv.startAnimation(rotateNinety);
        } else {
            if (force == true) {
                tv.setText(String.valueOf(input));
            } else {
                tv.setText(String.valueOf(myRandom.nextInt(input)));
            }
        }
    }
    public void bump20(int input, boolean force) {
        final TextView tv = (TextView)findViewById(R.id.movingBttn20);
        if (acceptInput == false) {
            tv.setText(String.valueOf(youwon1));
        } else {
            if (force == true) {
                tv.setText(String.valueOf(input));
            } else {
                tv.setText(String.valueOf(myRandom.nextInt(input)));
            }
        }
    }

    //Method when winning
    public void eyewon(View view) {
        final TextView targetNumber = (TextView)findViewById(R.id.targetnumber);
        final TextView currentNumber = (TextView)findViewById(R.id.currentnumber);
        if (Integer.parseInt((String) currentNumber.getText()) == Integer.parseInt((String) targetNumber.getText())) {
            saveTime();
            winGame();
            youWonToggle(true);
        }
    }

    public void hitceiling() {
        final TextView currentNumber = (TextView)findViewById(R.id.currentnumber);
        if (Integer.parseInt((String) currentNumber.getText()) > CEILINGRESET)
        {  CURRENTMODE = MINUS; }
    }

    public void youWonToggle(boolean input) {
        if (input == true) {
            acceptInput = false;
        } else { acceptInput = true;}
    }


    public void timerUp(){
        AlertDialog.Builder builder = new AlertDialog.Builder(
                AllAboutMath.this);

        builder.setTitle("Times Up!!!")
                .setMessage("Go back and try again...")
                .setCancelable(false)
                .setNeutralButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent intent = new Intent(getBaseContext(), Menu.class);
                                startActivity(intent);
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void winGame(){
        // Create Object of Dialog class
        final Dialog saveName = new Dialog(AllAboutMath.this);
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
                    Intent intent = new Intent(AllAboutMath.this, ResultAllMathActivity.class);
                    startActivity(intent);

                    // Redirect to dashboard / home screen.
                    saveName.dismiss();
                } else {
                    Toast.makeText(AllAboutMath.this,
                            "Please enter Username", Toast.LENGTH_LONG).show();
                }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AllAboutMath.this, Menu.class);
                startActivity(intent);
                //saveName.dismiss();
            }
        });

        // Make dialog box visible.
        saveName.show();

    }

    private void saveTime() {

        int start = Integer.valueOf((int) (startTime / 1000));
        int timeLeft = Integer.valueOf((String) mTimerView.getText());
        int timeTaken = start - timeLeft;

        int exScore = timeTaken;
        if(exScore>0){
            //we have a valid score
            SharedPreferences.Editor scoreEdit = scorePrefs.edit();
            scoreEdit.putInt("Score", exScore);
            scoreEdit.commit();
        }
    }

}
