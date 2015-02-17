package ca.uottawa.ljin027.myrestaurant;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author Ling Jin and Xi Song
 * Created by Ling on 11/02/2015.
 * This class processes the orders. It displays four dishes to the user and count the user's choices
 * until the user click the order button. A maximum number of dishes is implied. Also it will
 * calculate the prices of the food.
 * A user mush sign in first to make order.
 * This is implemented for the requirement 3 of our assignment.
 *
 */
public class OrderFragment extends BitmapFragment{

    private TextView textView_food_1_count = null;
    private TextView textView_food_2_count = null;
    private TextView textView_food_3_count = null;
    private TextView textView_food_4_count = null;
    private TextView textView_food_1_price = null;
    private TextView textView_food_2_price = null;
    private TextView textView_food_3_price = null;
    private TextView textView_food_4_price = null;

    // Constructor, initialize super class and TAG
    public OrderFragment() {
        super();
        TAG = "--> OrderFragment";
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
        // Create view
        final View view = inflater.inflate(R.layout.fragment_order, container, false);

        textView_food_1_count = (TextView) view.findViewById(R.id.textView_food_1_count);
        textView_food_2_count = (TextView) view.findViewById(R.id.textView_food_2_count);
        textView_food_3_count = (TextView) view.findViewById(R.id.textView_food_3_count);
        textView_food_4_count = (TextView) view.findViewById(R.id.textView_food_4_count);
        textView_food_1_price = (TextView) view.findViewById(R.id.textView_food_1_price);
        textView_food_2_price = (TextView) view.findViewById(R.id.textView_food_2_price);
        textView_food_3_price = (TextView) view.findViewById(R.id.textView_food_3_price);
        textView_food_4_price = (TextView) view.findViewById(R.id.textView_food_4_price);

        // When the order button is clicked, add update the order statistics
        Button orderButton = (Button) view.findViewById(R.id.button_order);
        orderButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "!!!!!! Order Button Clicked");
                if(!MainActivity.data.hasUser()) {

                    MainActivity currentActivity = (MainActivity) getActivity();
                    currentActivity.gotoFragment(MainActivity.LOGIN_FRAGMENT);
                    Toast.makeText(getActivity(), "Sign in to make order!", Toast.LENGTH_SHORT).show();
                }
                else {
                    if(MainActivity.data.orderEnabled()) {
                        AskDialog askDialog = new AskDialog();
                        askDialog.show(getFragmentManager(), null);
                    }
                }
            }
        });

        // Set click listeners
        CountChangeListener plusMinusListener = new CountChangeListener();
        Button food1PlusButton = (Button) view.findViewById(R.id.button_food_1_plus);
        food1PlusButton.setOnClickListener(plusMinusListener);
        Button food2PlusButton = (Button) view.findViewById(R.id.button_food_2_plus);
        food2PlusButton.setOnClickListener(plusMinusListener);
        Button food3PlusButton = (Button) view.findViewById(R.id.button_food_3_plus);
        food3PlusButton.setOnClickListener(plusMinusListener);
        Button food4PlusButton = (Button) view.findViewById(R.id.button_food_4_plus);
        food4PlusButton.setOnClickListener(plusMinusListener);
        Button food1MinusButton = (Button) view.findViewById(R.id.button_food_1_minus);
        food1MinusButton.setOnClickListener(plusMinusListener);
        Button food2MinusButton = (Button) view.findViewById(R.id.button_food_2_minus);
        food2MinusButton.setOnClickListener(plusMinusListener);
        Button food3MinusButton = (Button) view.findViewById(R.id.button_food_3_minus);
        food3MinusButton.setOnClickListener(plusMinusListener);
        Button food4MinusButton = (Button) view.findViewById(R.id.button_food_4_minus);
        food4MinusButton.setOnClickListener(plusMinusListener);

        TextView textView_food_1 = (TextView) view.findViewById(R.id.textView_food_1_description);
        TextView textView_food_2 = (TextView) view.findViewById(R.id.textView_food_2_description);
        TextView textView_food_3 = (TextView) view.findViewById(R.id.textView_food_3_description);
        TextView textView_food_4 = (TextView) view.findViewById(R.id.textView_food_4_description);
        textView_food_1.setText(MainActivity.data.get_food_1_description());
        textView_food_2.setText(MainActivity.data.get_food_2_description());
        textView_food_3.setText(MainActivity.data.get_food_3_description());
        textView_food_4.setText(MainActivity.data.get_food_4_description());

        // When the facebook icon is clicked, browse the facebook.com
        ImageButton facebookButton = (ImageButton) view.findViewById(R.id.imageButton_order_facebook);
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
        ImageButton twitterButton = (ImageButton) view.findViewById(R.id.imageButton_order_twitter);
        twitterButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "!!!!!! Twitter Clicked");
                Uri uri = Uri.parse(getString(R.string.twitter_url));
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        Log.i(TAG, "!!!!!! ViewCreated");

        // Add resource to bitmap manager
        addBitmap(R.id.imageView_food_1, R.drawable.food_dueling_lobster_tails);
        addBitmap(R.id.imageView_food_2, R.drawable.food_lobster_bake);
        addBitmap(R.id.imageView_food_3, R.drawable.food_lobster_lovers_dream);
        addBitmap(R.id.imageView_food_4, R.drawable.food_lobster_shrimp_salmon);
        addBitmap(R.id.imageButton_order_facebook, R.drawable.ic_facebook);
        addBitmap(R.id.imageButton_order_twitter, R.drawable.ic_twitter);

        return view;
    }

    // Click listeners implementations
    // Although we require the user to sign in before make an order,
    // we still process the button clicks. However, a hint will be displayed to warn the user
    private class CountChangeListener implements Button.OnClickListener {
        @Override
        public void onClick(View view) {
            switch(view.getId()) {
                case R.id.button_food_1_plus:
                    MainActivity.data.more_food_1();
                    textView_food_1_count.setText(MainActivity.data.getCount(1));
                    textView_food_1_price.setText(MainActivity.data.getPrice(1));
                    break;
                case R.id.button_food_2_plus:
                    MainActivity.data.more_food_2();
                    textView_food_2_count.setText(MainActivity.data.getCount(2));
                    textView_food_2_price.setText(MainActivity.data.getPrice(2));
                    break;
                case R.id.button_food_3_plus:
                    MainActivity.data.more_food_3();
                    textView_food_3_count.setText(MainActivity.data.getCount(3));
                    textView_food_3_price.setText(MainActivity.data.getPrice(3));
                    break;
                case R.id.button_food_4_plus:
                    MainActivity.data.more_food_4();
                    textView_food_4_count.setText(MainActivity.data.getCount(4));
                    textView_food_4_price.setText(MainActivity.data.getPrice(4));
                    break;
                case R.id.button_food_1_minus:
                    MainActivity.data.less_food_1();
                    textView_food_1_count.setText(MainActivity.data.getCount(1));
                    textView_food_1_price.setText(MainActivity.data.getPrice(1));
                    break;
                case R.id.button_food_2_minus:
                    MainActivity.data.less_food_2();
                    textView_food_2_count.setText(MainActivity.data.getCount(2));
                    textView_food_2_price.setText(MainActivity.data.getPrice(2));
                    break;
                case R.id.button_food_3_minus:
                    MainActivity.data.less_food_3();
                    textView_food_3_count.setText(MainActivity.data.getCount(3));
                    textView_food_3_price.setText(MainActivity.data.getPrice(3));
                    break;
                case R.id.button_food_4_minus:
                    MainActivity.data.less_food_4();
                    textView_food_4_count.setText(MainActivity.data.getCount(4));
                    textView_food_4_price.setText(MainActivity.data.getPrice(4));
                    break;
            }
            if(!MainActivity.data.hasUser())
                Toast.makeText(getActivity(), "Please sign in first!", Toast.LENGTH_SHORT).show();
        }
    }

    // A dialog ask for confirmation
    // If user confirms the order, we will jump to the welcome activity
    // If user cancels the order, do nothing
    public static class AskDialog extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            String message = MainActivity.data.getCurrentOrder();
            builder.setMessage(message);

            builder.setPositiveButton( "Check Out!", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int whichButton) {
                    MainActivity.data.newOrder();
                    MainActivity currentActivity = (MainActivity) getActivity();
                    currentActivity.gotoFragment(MainActivity.WELCOME_FRAGMENT);
                    Toast.makeText(getActivity(), "Thank you for your purchase!", Toast.LENGTH_SHORT).show();
                }
            });

            builder.setNegativeButton( "Think Again!", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int whichButton) {
                }
            });

            return builder.create();
        }
    }

    // Restore previous state
    @Override
    public void onResume() {
        textView_food_1_count.setText(MainActivity.data.getCount(1));
        textView_food_1_price.setText(MainActivity.data.getPrice(1));
        textView_food_2_count.setText(MainActivity.data.getCount(2));
        textView_food_2_price.setText(MainActivity.data.getPrice(2));
        textView_food_3_count.setText(MainActivity.data.getCount(3));
        textView_food_3_price.setText(MainActivity.data.getPrice(3));
        textView_food_4_count.setText(MainActivity.data.getCount(4));
        textView_food_4_price.setText(MainActivity.data.getPrice(4));

        super.onResume();
        Log.i(TAG, "!!!!!! Resumed");
    }
}
