package com.lamyatweng.mmugraduationstaff;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    Bundle mBundle = new Bundle();

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {


        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        // Do something with the date chosen by the user
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        Date date = calendar.getTime();
        Fragment fragment = getActivity().getFragmentManager().findFragmentByTag(ConvocationAddDialogFragment.class.getName());
        // Retrieve studentKey from previous fragment
        mBundle = getArguments();
        int textViewId = mBundle.getInt(getString(R.string.key_datePicker_textView_id));
        TextView textView = (TextView) fragment.getView().findViewById(textViewId);
        textView.setText(DateFormat.getDateInstance().format(date));

    }
}