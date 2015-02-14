package ca.uottawa.ljin027.myrestaurant;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

/**
 * Created by Ling on 11/02/2015.
 */
public class OrderFragment
        extends android.support.v4.app.Fragment
        implements View.OnClickListener {

    private static final String TAG = "OrderFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_order, container, false);
        Button orderButton = (Button) view.findViewById(R.id.button_order);
        orderButton.setOnClickListener(this);
        Log.i(TAG, "!!!!!! ViewCreated");
        return view;
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_order:
                Log.i(TAG, "!!!!!! Order Button Clicked");
                MainActivity.data.newOrder();
                break;
        }
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
        final ImageView bmpView = (ImageView) getActivity().findViewById(R.id.imageView_food_sandwiches);
        final BitmapDrawable background = (BitmapDrawable) bmpView.getDrawable();
        if(background == null) {
            Bitmap bmp = BitmapFactory.decodeStream(getResources().openRawResource(+R.drawable.food_sandwiches));
            bmpView.setImageDrawable(new BitmapDrawable(getResources(), bmp));
        }
    }

    // http://stackoverflow.com/questions/6447535/jpg-as-background-in-activity-produces-memory-leak
    // http://android-developers.blogspot.co.uk/2009/01/avoiding-memory-leaks.html
    private void recycleImages() {
        final ImageView bmpView = (ImageView) getActivity().findViewById(R.id.imageView_food_sandwiches);
        if (bmpView != null) {
            final BitmapDrawable background = (BitmapDrawable) bmpView.getDrawable();
            if (background != null) {
                background.getBitmap().recycle();
                bmpView.setImageDrawable(null);
                System.gc();
            }
        }
    }
}
