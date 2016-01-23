package com.lamyatweng.mmugraduationstaff.Seat;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.widget.Toast;

import com.lamyatweng.mmugraduationstaff.Constants;
import com.lamyatweng.mmugraduationstaff.R;

public class SeatSetSizeDialogFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Get session key from previous Activity
        Bundle bundle = getArguments();
        final String sessionKey = bundle.getString(getString(R.string.key_session_key));
        final int sessionId = bundle.getInt(getString(R.string.key_session_id));

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.dialog_set_seat_size, null));
        builder.setTitle("Enter size");
        builder.setPositiveButton("CREATE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                TextInputLayout rowWrapper = (TextInputLayout) ((AlertDialog) dialog).findViewById(R.id.wrapper_row_size);
                TextInputLayout columnWrapper = (TextInputLayout) ((AlertDialog) dialog).findViewById(R.id.wrapper_column_size);

                int row = Integer.parseInt(rowWrapper.getEditText().getText().toString());
                int column = Integer.parseInt(columnWrapper.getEditText().getText().toString());

                // Update session row and column size
                Constants.FIREBASE_REF_SESSIONS.child(sessionKey).child(Constants.FIREBASE_ATTR_SESSIONS_ROWSIZE).setValue(row);
                Constants.FIREBASE_REF_SESSIONS.child(sessionKey).child(Constants.FIREBASE_ATTR_SESSIONS_COLUMNSIZE).setValue(column);


                createNewSeats(row, column, sessionId);
                Toast.makeText(getActivity(), "Create seats complete", Toast.LENGTH_LONG).show();
            }
        });
        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        return builder.create();
    }

    private void createNewSeats(int numberOfRows, int numberOfColumns, int sessionId) {
        Seat newSeat;
        int id;
        String twoDigitRow;
        String twoDigitColumn;
        String status = "Available";
        String studentId = " ";
        for (int row = 1; row <= numberOfRows; row++) {
            for (int column = 1; column <= numberOfColumns; column++) {
                if (row < 10)
                    twoDigitRow = "0" + Integer.toString(row);
                else
                    twoDigitRow = Integer.toString(row);

                if (column < 10)
                    twoDigitColumn = "0" + Integer.toString(column);
                else
                    twoDigitColumn = Integer.toString(column);

                id = Integer.parseInt(Integer.toString(sessionId) + twoDigitRow + twoDigitColumn);

                newSeat = new Seat(id, twoDigitRow, twoDigitColumn, status, sessionId, studentId);
                Constants.FIREBASE_REF_SEATS.push().setValue(newSeat);
            }
        }
    }
}