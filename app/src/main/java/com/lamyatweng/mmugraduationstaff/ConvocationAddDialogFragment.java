package com.lamyatweng.mmugraduationstaff;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;

public class ConvocationAddDialogFragment extends DialogFragment {
    Bundle bundle = new Bundle();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_convocation_add, container, false);

        // Get references of views
        final TextInputLayout yearWrapper = (TextInputLayout) view.findViewById(R.id.wrapper_convocation_year);
        final TextView openDateTextView = (TextView) view.findViewById(R.id.open_registration_date);
        final TextView closeDateTextView = (TextView) view.findViewById(R.id.close_registration_date);

        // Open date picker and set text for open registration date
        openDateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment();
                bundle.putInt(getString(R.string.key_datePicker_textView_id), R.id.open_registration_date);
                newFragment.setArguments(bundle);
                newFragment.show(getFragmentManager(), "datePicker");
            }
        });

        // Open date picker and set text for close registration date
        closeDateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment();
                bundle.putInt(getString(R.string.key_datePicker_textView_id), R.id.close_registration_date);
                newFragment.setArguments(bundle);
                newFragment.show(getFragmentManager(), "datePicker");
            }
        });

        // Set Toolbar with close and save button
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        toolbar.setTitle("New " + Constants.TITLE_STUDENT);
        toolbar.setNavigationIcon(R.mipmap.ic_close_white_24dp);
        toolbar.inflateMenu(R.menu.student_add);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConvocationAddDialogFragment.this.getDialog().cancel();
            }
        });

        // Commit: add new convocation into Firebase
        Firebase.setAndroidContext(getActivity());
        final Firebase convocationRef = new Firebase(Constants.FIREBASE_CONVOCATION_REF);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getTitle().toString()) {
                    case Constants.MENU_SAVE:
                        // Retrieve user inputs
                        String year = yearWrapper.getEditText().getText().toString();
                        String openDate = openDateTextView.getText().toString();
                        String closeDate = closeDateTextView.getText().toString();

                        // Push into Firebase programme list
                        Convocation convocation = new Convocation(year, openDate, closeDate);
                        convocationRef.push().setValue(convocation);

                        // Display message and close dialog
                        Toast.makeText(getActivity(), Constants.TITLE_CONVOCATION + " added.", Toast.LENGTH_LONG).show();
                        ConvocationAddDialogFragment.this.getDialog().cancel();
                        return true;

                    default:
                        return false;
                }
            }
        });

        return view;
    }

    /**
     * Set dialog theme
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new Dialog(getActivity(), android.R.style.Theme_DeviceDefault_Light_DialogWhenLarge_NoActionBar);
    }
}
