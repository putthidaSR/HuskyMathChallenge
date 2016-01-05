package edu.uw.team5.huskymathchallenge;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * A simple {@link Fragment} subclass.
 *
 */
/**
 * A fragment that contains log in and button to create new account
 *
 * @author Putthida Samrith and Le Phu Bui
 * @version December 6, 2015
 */
public class LoginFragment extends Fragment {

    private ProgressDialog pDialog;

    private EditText mEmailText;
    private EditText mPwdText;

    private CallbackManager mCallbackManager;
    private AccessTokenTracker tokenTracker;

    private static String
            url = "http://cssgate.insttech.washington.edu/~psamrith/login.php";

    private Button mGuestLoginBtn;
    private Button mCreateNewBtn;

    private MediaPlayer dogBarkSound;


    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mCallbackManager = CallbackManager.Factory.create();

        tokenTracker=new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken accessToken, AccessToken accessToken1) {

            }
        };
        tokenTracker.startTracking();

        // Progress dialog
        pDialog = new ProgressDialog(getContext());
        pDialog.setCancelable(false);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        mCreateNewBtn = (Button) view.findViewById(R.id.create_new_button);
        mCreateNewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //adding popup sound when clicking a button
                dogBarkSound = MediaPlayer.create(getActivity(), R.raw.dog);
                dogBarkSound.start();

                Intent myIntent = new Intent(getActivity(), CreateNewUserActivity.class);
                getActivity().startActivity(myIntent);

            }
        });

        mEmailText = (EditText) view.findViewById(R.id.email);
        mPwdText = (EditText) view.findViewById(R.id.pwd);

        mGuestLoginBtn = (Button) view.findViewById(R.id.login_button);
        mGuestLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mEmailText.getText().length() != 0 && mPwdText.getText().length() != 0) {
                    url += "?email=" + mEmailText.getText().toString()
                            + "&password=" + mPwdText.getText().toString();
                    new UserWebTask().execute(url);
                }
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        ConnectivityManager connMgr = (ConnectivityManager)
                getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (!(networkInfo != null && networkInfo.isConnected())) {

            Toast.makeText(getActivity()
                    , "No network connection available.", Toast.LENGTH_SHORT)
                    .show();
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LoginButton loginButton = (LoginButton) view.findViewById(R.id.facebook_button);
        loginButton.setCompoundDrawables(null, null, null, null);
        loginButton.setReadPermissions("user_friends");
        loginButton.setFragment(this);
        loginButton.registerCallback(mCallbackManager, mFacebookCallback);
    }

    /**
     * This represents a method to handle Facebook login and callback.
     */
    private FacebookCallback<LoginResult> mFacebookCallback = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {
            //add welcome + profile name message (toast)
            AccessToken accessToken = loginResult.getAccessToken();
            Profile profile = Profile.getCurrentProfile();

            Toast.makeText(getActivity(), constructWelcomeMessage(profile),
                    Toast.LENGTH_LONG)
                    .show();

            System.out.println("Success");

            Intent myIntent = new Intent(getActivity(), Menu.class);
            getActivity().startActivity(myIntent);

        }

        @Override
        public void onCancel() {

        }

        @Override
        public void onError(FacebookException e) {

        }
    };

    /**
     * Method for grabbing user's facebook name
     * @param profile
     * @return
     */
    private String constructWelcomeMessage(Profile profile) {
        StringBuffer stringBuffer = new StringBuffer();
        if (profile != null) {
            stringBuffer.append("Welcome " + profile.getName());
        }
        return stringBuffer.toString();
    }

    /**
     * onActivityResult method
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }


    /**
     * This inner class is required to create log in for users.
     */
    private class UserWebTask extends AsyncTask<String, Void, String> {

        private static final String TAG = "UserWebTask";

        @Override
        protected String doInBackground(String...urls) {
            // params comes from the execute() call: params[0] is the url.
            try {
                return downloadUrl(urls[0]);
            } catch (IOException e) {
                return "Unable to retrieve web page. URL may be invalid.";
            }
        }

        /**
         * Given a URL, establishes an HttpUrlConnection and retrieves
         * the web page content as a InputStream, which it returns as
         * a string.
         *
         * @param myurl
         * @return
         * @throws IOException
         */
        private String downloadUrl(String myurl) throws IOException {
            InputStream is = null;

            // Only display the first 2000 characters of the retrieved
            // web page content.
            int len = 2000;

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
            } catch(Exception e ) {
                Log.d(TAG, "Something happened" + e.getMessage());
            }
            finally {
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

                    showDialog();
                    pDialog.setMessage("Logging in ...");

                    Toast.makeText(getContext(), "Successfully log in...",
                            Toast.LENGTH_SHORT)
                            .show();
                    Intent myIntent = new Intent(getActivity(), Menu.class);
                    getActivity().startActivity(myIntent);
                    hideDialog();
                } else {

                    String reason = jsonObject.getString("error");
                    Toast.makeText(getContext(), "Failed :" + reason,
                            Toast.LENGTH_SHORT)
                            .show();

                }

                getFragmentManager().popBackStackImmediate();

            } catch (Exception e) {
                Log.d(TAG, "Parsing JSON Exception " + e.getMessage());
            }
        }
    }

    /**
     * Method for showing progressDialog
     */
    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    /**
     * Method for hiding progressdialog
     */
    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}
