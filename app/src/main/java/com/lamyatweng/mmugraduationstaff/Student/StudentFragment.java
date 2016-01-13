package com.lamyatweng.mmugraduationstaff.Student;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.lamyatweng.mmugraduationstaff.Constants;
import com.lamyatweng.mmugraduationstaff.R;

public class StudentFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_student, container, false);

        // Populate list of students from Firebase into ListView
        final StudentCustomAdapter adapter = new StudentCustomAdapter(getActivity());
        Firebase.setAndroidContext(getActivity());
        Firebase studentRef = new Firebase(Constants.FIREBASE_STUDENTS_REF);
        studentRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Student student;
                adapter.clear();
                for (DataSnapshot studentSnapshot : dataSnapshot.getChildren()) {
                    student = studentSnapshot.getValue(Student.class);
                    adapter.add(student);
                }
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Snackbar.make(rootView, firebaseError.getMessage(), Snackbar.LENGTH_LONG).show();
            }
        });
        ListView studentListView = (ListView) rootView.findViewById(R.id.student_list_view);
        studentListView.setAdapter(adapter);

        // Launch a dialog to add new student
        FloatingActionButton addStudentFab = (FloatingActionButton) rootView.findViewById(R.id.add_student_fab);
        addStudentFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                StudentAddDialogFragment newFragment = new StudentAddDialogFragment();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transaction.add(newFragment, null).addToBackStack(null).commit();
            }
        });

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        android.support.v7.app.ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null)
            actionBar.setTitle(Constants.TITLE_STUDENT);
    }
}
