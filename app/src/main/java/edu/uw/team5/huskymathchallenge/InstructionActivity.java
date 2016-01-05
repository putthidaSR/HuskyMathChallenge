package edu.uw.team5.huskymathchallenge;

import android.app.Activity;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;



public class InstructionActivity extends Activity
        implements TextToSpeech.OnInitListener {


    private TextToSpeech tts;
    private Button btnSpeak;
    private Button stopbtn;
    private TextView txtText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instruction);

        tts = new TextToSpeech(this, this);


        btnSpeak = (Button) findViewById(R.id.readbtn);

        txtText = (TextView) findViewById(R.id.instructionText);

        // button on click event
        btnSpeak.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                speakOut();
            }

        });

        stopbtn = (Button) findViewById(R.id.stopreadingbtn);
        // button on click event
        stopbtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                tts.stop();
            }

        });
    }

    @Override
    public void onDestroy() {
        // Don't forget to shutdown!
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }

    @Override
    public void onInit(int status) {
        // TODO Auto-generated method stub

        if (status == TextToSpeech.SUCCESS) {

            int result = tts.setLanguage(Locale.US);

            // tts.setPitch(5); // set pitch level

            // tts.setSpeechRate(2); // set speech speed rate

            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "Language is not supported");
            } else {
                btnSpeak.setEnabled(true);
                speakOut();
            }

        } else {
            Log.e("TTS", "Initilization Failed");
        }

    }

    private void speakOut() {

        String text = txtText.getText().toString();

        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }
}
