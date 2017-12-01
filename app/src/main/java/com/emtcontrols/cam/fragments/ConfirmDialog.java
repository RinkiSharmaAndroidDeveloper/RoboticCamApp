package com.emtcontrols.cam.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.emtcontrols.cam.R;

public class ConfirmDialog extends DialogFragment implements OnClickListener {

    public final static String  USER_TO_DELETE = "userToDelete";

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder adb = new AlertDialog.Builder(getActivity())
               // .setTitle("Title").
                .setPositiveButton(R.string.yes, this)
                .setNegativeButton(R.string.cancel, this)
                .setMessage(R.string.message_delete_user);
        return adb.create();
    }

    public void onClick(DialogInterface dialog, int which) {
        boolean userPresDelete = false;
        switch (which) {
            case Dialog.BUTTON_POSITIVE:

                Intent intent = new Intent();
                intent.putExtra(USER_TO_DELETE, 0);
                getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
                break;
            case Dialog.BUTTON_NEGATIVE:
                break;
        }
    }
}
