package ca.uottawa.ljin027.myrestaurant;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.TreeMap;

/**
 * Created by Ling on 11/02/2015.
 */
public class LoginFragment extends android.support.v4.app.Fragment {

    private static final String TAG = "--> LoginFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        final TreeMap<String, String> currentUsers = new TreeMap<String, String>();

        final TextView loginGreetings = (TextView) view.findViewById(R.id.textView_login_greeting);
        final TextView loginUsername = (TextView) view.findViewById(R.id.editText_login_username);
        final TextView loginPassword = (TextView) view.findViewById(R.id.editText_login_password);

        final Button signInButton = (Button) view.findViewById(R.id.button_sign_in);
        final Button signOutButton = (Button) view.findViewById(R.id.button_sign_out);

        // When the sign in button is clicked, sign in/up the user if the password is right/legal
        signInButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = loginUsername.getText().toString();
                String password = loginPassword.getText().toString();

                if (username.length() == 0) {
                    Toast.makeText(getActivity(), getString(R.string.username_error), Toast.LENGTH_SHORT).show();
                    return;
                }

                if (currentUsers.containsKey(username)) {
                    if (!currentUsers.get(username).equals(password)) {
                        // User enters wrong password
                        Toast.makeText(getActivity(), "Password Error!", Toast.LENGTH_SHORT).show();
                    } else {
                        // Username and password is alright
                        loginGreetings.setText(getString(R.string.greet_to) + username + " !");
                        loginUsername.setText("");
                        loginPassword.setText("");
                        MainActivity.data.newUser(username);
                        Toast.makeText(getActivity(), "Sign in as " + username, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    if (password.length() != 0) {
                        // New user signs up
                        currentUsers.put(username, password);
                        loginGreetings.setText(getString(R.string.greet_to) + username + " !");
                        loginUsername.setText("");
                        loginPassword.setText("");
                        MainActivity.data.newUser(username);
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
                MainActivity.data.newUser(null);
                loginGreetings.setText(getString(R.string.greet_to));
                Toast.makeText(getActivity(), "Sign out successfully !", Toast.LENGTH_SHORT).show();
            }
        });

        ImageButton facebookButton = (ImageButton) view.findViewById(R.id.imageButton_login_facebook);
        facebookButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
            Log.i(TAG, "!!!!!! Fackbook Clicked");
            Uri uri = Uri.parse("http://www.facebook.com");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
            }
        });

        ImageButton twitterButton = (ImageButton) view.findViewById(R.id.imageButton_login_twitter);
        twitterButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
            Log.i(TAG, "!!!!!! Twitter Clicked");
            Uri uri = Uri.parse("http://www.twitter.com");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        resetImages();
        super.onResume();
        Log.i(TAG, "!!!!!! Resumed");
    }

    @Override
    public void onPause() {
        recycleImages();
        super.onPause();
        Log.i(TAG, "!!!!!! Paused");
    }

    private void resetImages() {
        ImageButton image = (ImageButton) getActivity().findViewById(R.id.imageButton_login_facebook);
        BitmapDrawable background = (BitmapDrawable) image.getDrawable();
        if(background == null) {
            Bitmap bmp = BitmapFactory.decodeStream(getResources().openRawResource(+R.drawable.ic_facebook));
            image.setBackground(new BitmapDrawable(getResources(), bmp));
        }

        image = (ImageButton) getActivity().findViewById(R.id.imageButton_login_twitter);
        background = (BitmapDrawable) image.getDrawable();
        if(background == null) {
            Bitmap bmp = BitmapFactory.decodeStream(getResources().openRawResource(+R.drawable.ic_twitter));
            image.setBackground(new BitmapDrawable(getResources(), bmp));
        }
    }

    // http://stackoverflow.com/questions/6447535/jpg-as-background-in-activity-produces-memory-leak
    // http://android-developers.blogspot.co.uk/2009/01/avoiding-memory-leaks.html
    private void recycleImages() {
        ImageButton image = (ImageButton) getActivity().findViewById(R.id.imageButton_login_facebook);
        if (image != null) {
            final BitmapDrawable background = (BitmapDrawable) image.getDrawable();
            if (background != null) {
                background.getBitmap().recycle();
                image.setImageDrawable(null);
                System.gc();
            }
        }
        image = (ImageButton) getActivity().findViewById(R.id.imageButton_login_twitter);
        if (image != null) {
            final BitmapDrawable background = (BitmapDrawable) image.getDrawable();
            if (background != null) {
                background.getBitmap().recycle();
                image.setImageDrawable(null);
                System.gc();
            }
        }
    }
}
