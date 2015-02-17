package ca.uottawa.ljin027.myrestaurant;

import android.app.AlertDialog;
import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;

/**
 * @author Ling Jin and Xi Song
 * Created by Ling on 2015/2/14.
 * We use a very simple dialog fragment to display message to user
 * Please refer to:
 * http://developer.android.com/reference/android/app/DialogFragment.html
 * http://stackoverflow.com/questions/4954130/center-message-in-android-dialog-box
 * http://stackoverflow.com/questions/15909672/how-to-set-font-size-for-text-of-dialog-buttons
 *
 */

// http://developer.android.com/reference/android/app/DialogFragment.html
// http://stackoverflow.com/questions/4954130/center-message-in-android-dialog-box
// http://stackoverflow.com/questions/15909672/how-to-set-font-size-for-text-of-dialog-buttons

public class MessageDialog extends DialogFragment {

    private String message;
    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(message);

        return builder.create();
    }
}
