package ca.uottawa.ljin027.myrestaurant;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

/**
 * Created by Ling on 10/02/2015.
 */
public class ContactFragment extends BitmapFragment {

    public ContactFragment() {
        super();
        TAG = "--> ContactFragment";
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
        // Create view
        View view = inflater.inflate(R.layout.fragment_contact, container, false);

        // Implement button click listener
        ImageButton contactLingButton = (ImageButton) view.findViewById(R.id.imageButton_contact_ling);
        contactLingButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View view) {
                        Log.i(TAG, "!!!!!! Contact Ling Clicked");
                        Uri uri = Uri.parse(getString(R.string.ling_url));
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);
                }
            });
        ImageButton contactXiButton = (ImageButton) view.findViewById(R.id.imageButton_contact_xi);
        contactXiButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View view) {
                Log.i(TAG, "!!!!!! Contact Xi Clicked");
                Uri uri = Uri.parse(getString(R.string.xi_url));
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
        Log.i(TAG, "!!!!!! ViewCreated");

        // When the facebook icon is clicked, browse the facebook.com
        ImageButton facebookButton = (ImageButton) view.findViewById(R.id.imageButton_contact_facebook);
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
        ImageButton twitterButton = (ImageButton) view.findViewById(R.id.imageButton_contact_twitter);
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
        addBitmap(R.id.imageView_contact_ling, R.drawable.head_ling);
        addBitmap(R.id.imageView_contact_xi, R.drawable.head_xi);
        addBitmap(R.id.imageButton_contact_facebook, R.drawable.ic_facebook);
        addBitmap(R.id.imageButton_contact_twitter, R.drawable.ic_twitter);

        return view;
    }
}
