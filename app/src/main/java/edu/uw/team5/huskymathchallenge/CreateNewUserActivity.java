package edu.uw.team5.huskymathchallenge;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * This class is using web service to create a new user.
 *
 * @author Putthida Samrith and Le Phu Bui
 * @version November 4, 2015
 */
public class CreateNewUserActivity extends AppCompatActivity {

    /**
     * Attribute
     */
    private String
            url = "http://cssgate.insttech.washington.edu/~psamrith/addUser.php";
    private TextView mEmailText;
    private TextView mPwdText;

    /**
     * This method is where to initialize the activity, and field.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_user);

        mEmailText = (EditText) findViewById(R.id.email_text);
        mPwdText = (EditText) findViewById(R.id.pwd_text);
        Button addButton = (Button) findViewById(R.id.add_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mEmailText.getText().length() != 0 && mPwdText.getText().length() != 0) {
                    url += "?email=" + mEmailText.getText().toString()
                            + "&password=" + mPwdText.getText().toString();
                    new AddUserWebTask().execute(url);

                    //go to Menu screen for new user
//                    Intent intent = new Intent(CreateNewUserActivity.this, Menu.class);
//                    startActivity(intent);
                }
            }
        });

        Button backToLoginButton = (Button) findViewById(R.id.back_to_login_btn);
        backToLoginButton.setVisibility(View.VISIBLE);
        backToLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CreateNewUserActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }

    /**
     *
     */
    private class AddUserWebTask extends AsyncTask<String, Void, String> {

        private static final String TAG = "AddUserWebTask";

        @Override
        protected String doInBackground(String... urls) {
            // params comes from the execute() call: params[0] is the url.
            try {
                return downloadUrl(urls[0]);
            } catch (IOException e) {
                return "Unable to retrieve web page. URL may be invalid.";
            }
        }

        // Given a URL, establishes an HttpUrlConnection and retrieves
        // the web page content as a InputStream, which it returns as
        // a string.
        private String downloadUrl(String myurl) throws IOException {
            InputStream is = null;
            // Only display the first 500 characters of the retrieved
            // web page content.
            int len = 500;

            try {
                URL url = new URL(myurl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                // Starts the query
                conn.connect();
                int response = conn.getResponseCode();
                Log.d(TAG, "The response is: " + response);
                is = conn.getInputStream();

                // Convert the InputStream into a string
                String contentAsString = readIt(is, len);
                Log.d(TAG, "The string is: " + contentAsString);
                return contentAsString;

                // Makes sure that the InputStream is closed after the app is
                // finished using it.
            } catch (Exception e) {
                Log.d(TAG, "Something happened" + e.getMessage());
            } finally {
                if (is != null) {
                    is.close();
                }
            }
            return null;
        }

        /**
         * Reads an InputStream and converts it to a String.
         * @param stream
         * @param len
         * @return
         * @throws IOException
         * @throws UnsupportedEncodingException
         */
        public String readIt(InputStream stream, int len) throws IOException, UnsupportedEncodingException {
            Reader reader = null;
            reader = new InputStreamReader(stream, "UTF-8");
            char[] buffer = new char[len];
            reader.read(buffer);
            return new String(buffer);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            // Parse JSON
            try {
                JSONObject jsonObject = new JSONObject(s);
                String status = jsonObject.getString("result");
                if (status.equalsIgnoreCase("success")) {
                    Toast.makeText(getApplicationContext(), "Successfully created a new account!",
                            Toast.LENGTH_SHORT)
                            .show();
                    Intent intent = new Intent(CreateNewUserActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    String reason = jsonObject.getString("error");
                    Toast.makeText(getApplicationContext(), "Failed :" + reason,
                            Toast.LENGTH_SHORT)
                            .show();
                }

                getFragmentManager().popBackStackImmediate();
            } catch (Exception e) {
                Log.d(TAG, "Parsing JSON Exception " + e.getMessage());
            }
        }
    }

}
