package com.lamyatweng.mmugraduationstaff.Student;

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
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;
import com.lamyatweng.mmugraduationstaff.Constants;
import com.lamyatweng.mmugraduationstaff.R;

public class StudentEditDialogFragment extends DialogFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_student_edit, container, false);

        // Retrieve studentID passed from previous fragment
        Bundle bundle = getArguments();
        final String studentID = bundle.getString(getString(R.string.key_student_id));

        final TextInputLayout nameWrapper = (TextInputLayout) view.findViewById(R.id.wrapper_student_name);
        final TextInputLayout idWrapper = (TextInputLayout) view.findViewById(R.id.wrapper_student_id);
        final TextInputLayout emailWrapper = (TextInputLayout) view.findViewById(R.id.wrapper_student_email);
        final TextInputLayout creditHourWrapper = (TextInputLayout) view.findViewById(R.id.wrapper_student_balanceCreditHour);
        final TextInputLayout cgpaWrapper = (TextInputLayout) view.findViewById(R.id.wrapper_student_cgpa);
        final TextInputLayout financialWrapper = (TextInputLayout) view.findViewById(R.id.wrapper_student_financialDue);

        // Populate list of programmes from array into spinner
        final Spinner programmeSpinner = (Spinner) view.findViewById(R.id.programme_spinner);
        Firebase.setAndroidContext(getActivity());
        Firebase programmeRef = new Firebase(Constants.FIREBASE_PROGRAMMES_REF);
        final ArrayAdapter<CharSequence> programmeAdapter = new ArrayAdapter<>(getActivity(), R.layout.multiline_spinner_item);
        programmeRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String programmeName;
                programmeAdapter.clear();
                for (DataSnapshot courseSnapshot : dataSnapshot.getChildren()) {
                    programmeName = courseSnapshot.child("name").getValue().toString();
                    programmeAdapter.add(programmeName);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });
        programmeAdapter.setDropDownViewResource(R.layout.multiline_spinner_dropdown_item);
        programmeSpinner.setAdapter(programmeAdapter);

        // Populate list of status from array into spinner
        final Spinner statusSpinner = (Spinner) view.findViewById(R.id.status_spinner);
        final ArrayAdapter<CharSequence> statusAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.status_array, android.R.layout.simple_spinner_item);
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        statusSpinner.setAdapter(statusAdapter);

        // Populate list of muet grades from array into spinner
        final Spinner muetSpinner = (Spinner) view.findViewById(R.id.muet_spinner);
        final ArrayAdapter<CharSequence> muetAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.muet_grading_array, android.R.layout.simple_spinner_item);
        muetAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        muetSpinner.setAdapter(muetAdapter);

        // Retrieve student information from Firebase
        final String[] studentKey = new String[1];
        Firebase.setAndroidContext(getActivity());
        final Firebase studentRef = new Firebase(Constants.FIREBASE_STUDENTS_REF);
        Query queryRef = studentRef.orderByChild("id").equalTo(studentID);
        queryRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                studentKey[0] = dataSnapshot.getKey();
                Student student = dataSnapshot.getValue(Student.class);

                nameWrapper.getEditText().setText(student.getName());
                idWrapper.getEditText().setText(student.getId());
                programmeSpinner.setSelection(programmeAdapter.getPosition(student.getProgramme()));
                statusSpinner.setSelection(statusAdapter.getPosition(student.getStatus()));
                emailWrapper.getEditText().setText(student.getEmail());
                creditHourWrapper.getEditText().setText(String.valueOf(student.getBalanceCreditHour()));
                cgpaWrapper.getEditText().setText(String.valueOf(student.getCgpa()));
                muetSpinner.setSelection(muetAdapter.getPosition(String.valueOf(student.getMuet())));
                financialWrapper.getEditText().setText(String.valueOf(student.getFinancialDue()));
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Student student = dataSnapshot.getValue(Student.class);

                nameWrapper.getEditText().setText(student.getName());
                idWrapper.getEditText().setText(student.getId());
                programmeSpinner.setSelection(programmeAdapter.getPosition(student.getProgramme()));
                statusSpinner.setSelection(statusAdapter.getPosition(student.getStatus()));
                emailWrapper.getEditText().setText(student.getEmail());
                creditHourWrapper.getEditText().setText(String.valueOf(student.getBalanceCreditHour()));
                cgpaWrapper.getEditText().setText(String.valueOf(student.getCgpa()));
                muetSpinner.setSelection(muetAdapter.getPosition(String.valueOf(student.getMuet())));
                financialWrapper.getEditText().setText(String.valueOf(student.getFinancialDue()));
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

        // Set up Toolbar with close and save button
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.mipmap.ic_close_white_24dp);
        toolbar.inflateMenu(R.menu.student_add);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StudentEditDialogFragment.this.getDialog().cancel();
            }
        });
        // Set save button click listener
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getTitle().toString()) {
                    case Constants.MENU_SAVE:
                        // Get user inputs
                        String programme = programmeSpinner.getSelectedItem().toString();
                        String status = statusSpinner.getSelectedItem().toString();
                        int muet = Integer.parseInt(muetSpinner.getSelectedItem().toString());

                        String name = "";
                        if (nameWrapper.getEditText() != null)
                            name = nameWrapper.getEditText().getText().toString();

                        String id = "";
                        if (idWrapper.getEditText() != null)
                            id = idWrapper.getEditText().getText().toString();

                        String email = "";
                        if (emailWrapper.getEditText() != null)
                            email = emailWrapper.getEditText().getText().toString();

                        int balanceCreditHour = 0;
                        if (creditHourWrapper.getEditText() != null)
                            balanceCreditHour = Integer.parseInt(creditHourWrapper.getEditText().getText().toString());

                        double cgpa = 0;
                        if (cgpaWrapper.getEditText() != null)
                            cgpa = Double.parseDouble(cgpaWrapper.getEditText().getText().toString());

                        double financialDue = 0;
                        if (financialWrapper.getEditText() != null)
                            financialDue = Double.parseDouble(financialWrapper.getEditText().getText().toString());

                        // Save into Firebase
                        Student updatedStudent = new Student(name, id, programme, status, email, balanceCreditHour, cgpa, muet, financialDue);
                        studentRef.child(studentKey[0]).setValue(updatedStudent);
                        Toast.makeText(getActivity(), Constants.TITLE_STUDENT + " updated.", Toast.LENGTH_SHORT).show();

                        // Hide keyboard and Close dialog
                        StudentEditDialogFragment.this.getDialog().cancel();
                        return true;
                }
                return false;
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
