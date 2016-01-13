package com.lamyatweng.mmugraduationstaff;

import android.app.DialogFragment;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class ConvocationFragment extends Fragment{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_convocation, container, false);

        Button buttonShowTimePickerDialog = (Button) rootView.findViewById(R.id.button_timePicker);
        buttonShowTimePickerDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                DialogFragment newFragment = new TimePickerFragment();
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getFragmentManager(), "timePicker");
            }
        });

        return rootView;
    }
}
