package com.lamyatweng.mmugraduationstaff.Session;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.lamyatweng.mmugraduationstaff.Constants;
import com.lamyatweng.mmugraduationstaff.R;

public class EmptyProgrammeListDialogFragment extends DialogFragment {
    Bundle mBundle = new Bundle();
    String mSessionKey;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Retrieve SessionKey from previous activity
        mBundle = getArguments();
        mSessionKey = mBundle.getString(getString(R.string.key_session_key));

        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Programme list is empty");
        builder.setMessage("Do you want to add programme into this session?")
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(getActivity(), SessionProgrammeAddActivity.class);
                        intent.putExtra(Constants.EXTRA_SESSION_KEY, mSessionKey);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}
