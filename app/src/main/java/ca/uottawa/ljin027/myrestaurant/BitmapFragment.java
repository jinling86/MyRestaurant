package ca.uottawa.ljin027.myrestaurant;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabWidget;

import java.util.Set;
import java.util.TreeMap;

/**
 * Created by Ling on 14/02/2015.
 */
public class BitmapFragment  extends android.support.v4.app.Fragment {
    protected String TAG = null;
    private TreeMap<Integer, Integer> resourceMap = new TreeMap<Integer, Integer>();

    // Interface for adding bitmap resources
    public void addBitmap(int viewID, int bitmapID) {
        resourceMap.put(viewID, bitmapID);
    }

    @Override
    // When the fragment resumes, reload the bitmap into memory
    public void onResume() {
        resetImages();
        super.onResume();
        Log.i(TAG, "!!!!!! Resumed");
    }

    @Override
    // When the fragment pauses, release the bitmap resources
    public void onPause() {
        recycleImages();
        super.onPause();
        Log.i(TAG, "!!!!!! Paused");
    }

    // Release all the bitmap resources
    private void resetImages() {
        Set<Integer> viewIDs = resourceMap.keySet();
        for(int viewID: viewIDs) {
            final ImageView imageView = (ImageView) getActivity().findViewById(viewID);
            BitmapDrawable background = (BitmapDrawable) imageView.getDrawable();
            if(background == null) {
                int resourceID = resourceMap.get(viewID);
                Bitmap bmp = BitmapFactory.decodeStream(getResources().openRawResource(+ resourceID));
                imageView.setBackground(new BitmapDrawable(getResources(), bmp));
            }
        }
    }

    // Reload all the bitmap into memory
    // Please refer to:
    // http://stackoverflow.com/questions/6447535/jpg-as-background-in-activity-produces-memory-leak
    // http://android-developers.blogspot.co.uk/2009/01/avoiding-memory-leaks.html
    private void recycleImages() {
        Set<Integer> viewIDs = resourceMap.keySet();
        for(int viewID: viewIDs) {
            ImageView imageView = (ImageView) getActivity().findViewById(viewID);
            if (imageView != null) {
                final BitmapDrawable background = (BitmapDrawable) imageView.getDrawable();
                if (background != null) {
                    background.getBitmap().recycle();
                    imageView.setImageDrawable(null);
                    System.gc();
                }
            }
        }
    }
}
