package ca.uottawa.ljin027.myrestaurant;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Ling on 10/02/2015.
 */
public class WelcomeFragment extends android.support.v4.app.Fragment {

    private static final String TAG = "WelcomeFragment";
    TextView orderStatistics;

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
