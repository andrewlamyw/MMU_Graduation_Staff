package com.lamyatweng.mmugraduationstaff.Student;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lamyatweng.mmugraduationstaff.R;

public class StudentDetailsFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_student_details, container, false);
        String strtext = getArguments().getString("message");
        return rootView;
    }
}
