package com.lamyatweng.mmugraduationstaff.Student;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.lamyatweng.mmugraduationstaff.Constants;
import com.lamyatweng.mmugraduationstaff.R;

public class StudentDetailsDialogFragment extends DialogFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_student_details, container, false);

        // Retrieve studentID passed from previous fragment
        Bundle bundle = getArguments();
        String studentID = bundle.getString(getString(R.string.key_student_id));


        final TextView id = (TextView) rootView.findViewById(R.id.details_student_id);
        final TextView name = (TextView) rootView.findViewById(R.id.details_student_name);
        final TextView email = (TextView) rootView.findViewById(R.id.details_student_email);
        final TextView status = (TextView) rootView.findViewById(R.id.details_student_status);
        final TextView cgpa = (TextView) rootView.findViewById(R.id.details_student_cgpa);
        final TextView programme = (TextView) rootView.findViewById(R.id.details_student_programme);
        final TextView creditHour = (TextView) rootView.findViewById(R.id.details_student_creditHour);
        final TextView muet = (TextView) rootView.findViewById(R.id.details_student_muet);
        final TextView financial = (TextView) rootView.findViewById(R.id.details_student_financial);

        // Retrieve student information from Firebase
        Firebase.setAndroidContext(getActivity());
        Firebase studentRef = new Firebase(Constants.FIREBASE_STUDENTS_REF);
        Query queryRef = studentRef.orderByChild("id").equalTo(studentID);
        queryRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Student student = dataSnapshot.getValue(Student.class);

                name.setText(student.getName());
                id.setText(Integer.toString(student.getId()));
                programme.setText(student.getProgramme());
                status.setText(student.getStatus());
                email.setText(student.getEmail());
                creditHour.setText(String.valueOf(student.getBalanceCreditHour()));
                cgpa.setText(String.valueOf(student.getCgpa()));
                muet.setText(String.valueOf(student.getMuet()));
                financial.setText(String.valueOf(student.getFinancialDue()));
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Student student = dataSnapshot.getValue(Student.class);

                name.setText(student.getName());
                id.setText(Integer.toString(student.getId()));
                programme.setText(student.getProgramme());
                status.setText(student.getStatus());
                email.setText(student.getEmail());
                creditHour.setText(String.valueOf(student.getBalanceCreditHour()));
                cgpa.setText(String.valueOf(student.getCgpa()));
                muet.setText(String.valueOf(student.getMuet()));
                financial.setText(String.valueOf(student.getFinancialDue()));
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });

        // Set up Toolbar with back button
        Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.mipmap.ic_arrow_back_white_24dp);
        toolbar.inflateMenu(R.menu.student_edit);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StudentDetailsDialogFragment.this.getDialog().cancel();
            }
        });

        return rootView;
    }

    /**
     * Set dialog theme
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new Dialog(getActivity(), android.R.style.Theme_DeviceDefault_Light_DialogWhenLarge_NoActionBar);
    }
}
