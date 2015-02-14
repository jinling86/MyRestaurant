package ca.uottawa.ljin027.myrestaurant;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Ling on 10/02/2015.
 */
public class WelcomeFragment extends android.support.v4.app.Fragment {

    private static final String TAG = "--> WelcomeFragment";
    TextView orderStatistics;

    @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_welcome, container, false);
            orderStatistics = (TextView) view.findViewById(R.id.textView_count);
            Log.i(TAG, "!!!!!! ViewCreated");
            return view;
    }

    @Override
    public void onPause() {
        recycleImages();
        super.onPause();
        Log.i(TAG, "!!!!!! Paused");
    }

    @Override
    public void onResume() {
        orderStatistics.setText("We sold " + MainActivity.data.getOrderStatistics() + " orders today!");
        resetImages();
        super.onResume();
        Log.i(TAG, "!!!!!! Resumed and Updated");
    }

    private void resetImages() {
    }

    // http://stackoverflow.com/questions/6447535/jpg-as-background-in-activity-produces-memory-leak
    // http://android-developers.blogspot.co.uk/2009/01/avoiding-memory-leaks.html
    private void recycleImages() {
    }
}
