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
import android.widget.Toast;

/**
 * Created by Ling on 11/02/2015.
 */
public class OrderFragment extends BitmapFragment{

    // Constructor, initialize super class and TAG
    public OrderFragment() {
        super();
        TAG = "--> OrderFragment";
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
        // Create view
        final View view = inflater.inflate(R.layout.fragment_order, container, false);

        // When the order button is clicked, add update the order statistics
        Button orderButton = (Button) view.findViewById(R.id.button_order);
        orderButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View view) {
                Log.i(TAG, "!!!!!! Order Button Clicked");
                if(!MainActivity.data.hasUser()) {
                    MainActivity currentActivity = (MainActivity) getActivity();
                    currentActivity.gotoFragment(MainActivity.LOGIN_FRAGMENT);
                    Toast.makeText(getActivity(), "Sign in to make order!", Toast.LENGTH_SHORT).show();
                }
                else {
                    MainActivity.data.newOrder();
                    MainActivity currentActivity = (MainActivity) getActivity();
                    currentActivity.gotoFragment(MainActivity.WELCOME_FRAGMENT);
                    Toast.makeText(getActivity(), "Thank you for your purchase!", Toast.LENGTH_SHORT).show();
                }

            }
        });
        Log.i(TAG, "!!!!!! ViewCreated");

        // Add resource to bitmap manager
        addBitmap(R.id.imageView_food_sandwiches, R.drawable.food_dueling_lobster_tails);

        return view;
    }
}
