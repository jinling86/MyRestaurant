package ca.uottawa.ljin027.myrestaurant;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.TreeMap;

/**
 * Created by Ling on 11/02/2015.
 */
public class LoginFragment extends android.support.v4.app.Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        final TreeMap<String, String> currentUsers = new TreeMap<String, String>();

        final TextView loginGreetings = (TextView) view.findViewById(R.id.textView_login_greeting);
        final TextView loginUsername = (TextView) view.findViewById(R.id.editText_login_username);
        final TextView loginPassword = (TextView) view.findViewById(R.id.editText_login_password);

        final Button loginButton = (Button) view.findViewById(R.id.button_login);

        loginButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = loginUsername.getText().toString();
                String password = loginPassword.getText().toString();

                if (username.length() == 0)
                    return;

                if (currentUsers.containsKey(username)) {
                    if (!currentUsers.get(username).equals(password)) {
                        // Show a message
                    } else {
                        loginGreetings.setText("Welcome " + username + " !");
                        loginUsername.setText("");
                        loginPassword.setText("");
                    }
                } else {
                    if (password.length() != 0) {
                        currentUsers.put(username, password);
                        loginGreetings.setText("Welcome " + username + " !");
                        loginUsername.setText("");
                        loginPassword.setText("");
                    } else {
                        // Show a message here
                    }
                }
                return;
            }
        });

        return view;
    }
}
