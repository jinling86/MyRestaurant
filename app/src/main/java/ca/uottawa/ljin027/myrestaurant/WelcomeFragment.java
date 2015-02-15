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
public class WelcomeFragment extends BitmapFragment {

    TextView orderStatistics;

    // Constructor, initialize super class and TAG
    public WelcomeFragment() {
        super();
        TAG = "--> WelcomeFragment";
    }

    @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_welcome, container, false);
            orderStatistics = (TextView) view.findViewById(R.id.textView_count);
            Log.i(TAG, "!!!!!! ViewCreated");
            return view;
    }

    @Override
    public void onResume() {
        orderStatistics.setText("We sold " + MainActivity.data.getOrderStatistics() + " orders today!");
        super.onResume();
        Log.i(TAG, "!!!!!! Resumed and Updated");
    }

}
