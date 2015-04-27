package ca.uottawa.ljin027.myrestaurant;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.TreeMap;

/**
 * @author Ling Jin
 * Created by Ling on 11/02/2015.
 * This class implements the login activity based on fragment. (Requirement 4)
 * A user can sign up if he/she has not signed up yet.
 * After signed up, he/she can sign out or sign in with the right password.
 * A user must sign in first to order.
 * The username and the password field cannot be empty.
 * After sign up/in, a welcome text will show on the top of the fragment.
 */
public class LoginFragment extends BitmapFragment {
    // Widgets variable, for button listener interface
    private TextView loginGreetings = null;
    private TextView loginHint = null;
    private TextView loginUsername = null;
    private TextView loginPassword = null;
    private Button signInButton = null;
    private Button signOutButton = null;

    // Constructor, initialize super class and TAG
    public LoginFragment() {
        super();
        TAG = "--> LoginFragment";
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
        // Create view
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        // Add button click listener
        loginGreetings = (TextView) view.findViewById(R.id.textView_login_greeting);
        loginHint = (TextView) view.findViewById(R.id.textView_sign_in_please);
        loginUsername = (TextView) view.findViewById(R.id.editText_login_username);
        loginPassword = (TextView) view.findViewById(R.id.editText_login_password);

        signInButton = (Button) view.findViewById(R.id.button_sign_in);
        signOutButton = (Button) view.findViewById(R.id.button_sign_out);

        // When the sign in button is clicked, sign in/up the user if the password is right/legal
        signInButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = loginUsername.getText().toString();
                String password = loginPassword.getText().toString();

                // We force the soft keyboard to be close here
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
                if(imm != null) {
                    if(loginUsername != null) imm.hideSoftInputFromWindow(loginUsername.getWindowToken(), 0);
                    if(loginPassword != null) imm.hideSoftInputFromWindow(loginPassword.getWindowToken(), 0);
                }

                if (username.length() == 0) {
                    Toast.makeText(getActivity(), getString(R.string.username_error), Toast.LENGTH_SHORT).show();
                    return;
                }

                if (MainActivity.data.hasRegistered(username)) {
                    if (!MainActivity.data.hasRightPassword(username, password)) {
                        // User enters wrong password
                        Toast.makeText(getActivity(), "Password Error!", Toast.LENGTH_SHORT).show();
                    } else {
                        // Username and password is alright
                        MainActivity.data.newUser(username);

                        loginGreetings.setText(getGreeting());
                        loginUsername.setText("");
                        loginPassword.setText("");
                        disableLogin();

                        Toast.makeText(getActivity(), "Sign in as " + username, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    if (password.length() != 0) {
                        // New user signs up
                        MainActivity.data.addUser(username, password);
                        MainActivity.data.newUser(username);

                        loginGreetings.setText(getGreeting());
                        loginUsername.setText("");
                        loginPassword.setText("");
                        disableLogin();

                        Toast.makeText(getActivity(), "Sign up as " + username, Toast.LENGTH_SHORT).show();
                    } else {
                        // Password should contain 1 letter
                        MessageDialog dialog = new MessageDialog();
                        dialog.setMessage(getString(R.string.password_error));
                        dialog.show(getFragmentManager(), null);
                    }
                }

                return;
            }
        });

        // When the sign out button is clicked, sign out current user and modify the greeting
        signOutButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(MainActivity.data.hasUser()) {
                    MainActivity.data.newUser(null);
                    loginGreetings.setText(getGreeting());
                    enableLogin();
                    Toast.makeText(getActivity(), "Sign out successfully !", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // When the facebook icon is clicked, browse the facebook.com
        ImageButton facebookButton = (ImageButton) view.findViewById(R.id.imageButton_login_facebook);
        facebookButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
            Log.i(TAG, "!!!!!! Facebook Clicked");
            Uri uri = Uri.parse(getString(R.string.facebook_url));
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
            }
        });

        // When the twitter icon is clicked, browse the twitter.com
        ImageButton twitterButton = (ImageButton) view.findViewById(R.id.imageButton_login_twitter);
        twitterButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
            Log.i(TAG, "!!!!!! Twitter Clicked");
            Uri uri = Uri.parse(getString(R.string.twitter_url));
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
            }
        });

        // Add resource to bitmap manager
        addBitmap(R.id.imageButton_login_facebook, R.drawable.ic_facebook);
        addBitmap(R.id.imageButton_login_twitter, R.drawable.ic_twitter);

        return view;
    }

    // Enable/disable widgets according to saved state
    @Override
    public void onResume() {
        loginGreetings.setText(getGreeting());
        if(MainActivity.data.hasUser()) {
            disableLogin();
        } else {
            enableLogin();
        }
        super.onResume();
        Log.i(TAG, "!!!!!! Resumed and Updated");
    }

    // Make a greeting string
    private String getGreeting() {
        if(MainActivity.data.hasUser())
            return getString(R.string.greet_to) + " " + MainActivity.data.getCurrentUser()  + " !";
        else
            return getString(R.string.greet_to);
    }

    // Set widgets to disable user to sign in
    private void disableLogin() {
        loginGreetings.setVisibility(View.VISIBLE);
        loginHint.setVisibility(View.INVISIBLE);
        loginUsername.setVisibility(View.INVISIBLE);
        loginPassword.setVisibility(View.INVISIBLE);
        signInButton.setVisibility(View.INVISIBLE);
        signOutButton.setVisibility(View.VISIBLE);
    }

    // Set widgets to enable user to sign in
    private void enableLogin() {
        loginGreetings.setVisibility(View.VISIBLE);
        loginHint.setVisibility(View.VISIBLE);
        loginUsername.setVisibility(View.VISIBLE);
        loginPassword.setVisibility(View.VISIBLE);
        signInButton.setVisibility(View.VISIBLE);
        signOutButton.setVisibility(View.INVISIBLE);
    }

}
