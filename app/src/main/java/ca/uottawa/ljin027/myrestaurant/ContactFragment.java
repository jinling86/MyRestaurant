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

/**
 * Created by Ling on 10/02/2015.
 */
public class ContactFragment extends android.support.v4.app.Fragment {
    private static final String TAG = "ContactFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact, container, false);
        ImageButton orderButton = (ImageButton) view.findViewById(R.id.imageButton_contact_ling);
        orderButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.imageButton_contact_ling:
                        Log.i(TAG, "!!!!!! Ling's Information Clicked");
                        Uri uri = Uri.parse("http://www.google.com");
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);
                        break;
                }
            }
        });
        Log.i(TAG, "!!!!!! ViewCreated");
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
        final ImageButton bmpButton = (ImageButton) getActivity().findViewById(R.id.imageButton_contact_ling);
        final BitmapDrawable background = (BitmapDrawable) bmpButton.getDrawable();
        if(background == null) {
            Bitmap bmp = BitmapFactory.decodeStream(getResources().openRawResource(+R.drawable.head_ling_umbrella));
            BitmapDrawable bmpDrawable = new BitmapDrawable(getResources(), bmp);

            bmpButton.setImageDrawable(bmpDrawable);
            bmpButton.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        }
    }

    // http://stackoverflow.com/questions/6447535/jpg-as-background-in-activity-produces-memory-leak
    // http://android-developers.blogspot.co.uk/2009/01/avoiding-memory-leaks.html
    private void recycleImages() {
        final ImageButton bmpButton = (ImageButton) getActivity().findViewById(R.id.imageButton_contact_ling);
        if (bmpButton != null) {
            final BitmapDrawable background = (BitmapDrawable) bmpButton.getDrawable();
            if (background != null) {
                background.getBitmap().recycle();
                bmpButton.setImageDrawable(null);
                System.gc();
            }
        }
    }
}
