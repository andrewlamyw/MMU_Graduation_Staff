package com.lamyatweng.mmugraduationstaff;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;
import com.lamyatweng.mmugraduationstaff.Student.Student;
import com.lamyatweng.mmugraduationstaff.Student.StudentAdapter;

public class GraduationFragment extends Fragment {
    Bundle bundle = new Bundle();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_graduation, container, false);
        this.setHasOptionsMenu(true);

        // Populate students from Firebase into ListView
        final StudentAdapter adapter = new StudentAdapter(getActivity());
        Query query = Constants.FIREBASE_REF_STUDENTS.orderByChild("status").equalTo("Pending approval");
        query.addValueEventListener(new ValueEventListener() {
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
            }
        });
        final ListView studentListView = (ListView) view.findViewById(R.id.student_list_view);
        studentListView.setAdapter(adapter);


        // Launch a dialog to display student details
        /*studentListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Student selectedStudent = (Student) parent.getItemAtPosition(position);

                // Retrieve selected student key from Firebase and save in bundle
                Query queryRef = Constants.FIREBASE_REF_STUDENTS.orderByChild("id").equalTo(selectedStudent.getId());
                queryRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot studentSnapshot : dataSnapshot.getChildren()) {
                            Student firebaseStudent = studentSnapshot.getValue(Student.class);
                            if (firebaseStudent.getName().equals(selectedStudent.getName())) {
                                bundle.putString(getString(R.string.key_student_key), studentSnapshot.getKey());
                                StudentDisplayDetailDialogFragment studentDisplayDetailDialogFragment = new StudentDisplayDetailDialogFragment();
                                studentDisplayDetailDialogFragment.setArguments(bundle);
                                getFragmentManager().beginTransaction().add(studentDisplayDetailDialogFragment, null).
                                        addToBackStack(studentDisplayDetailDialogFragment.getClass().getName()).commit();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });
            }
        });*/

        return view;
    }

    /**
     * Set ActionBar title
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        android.support.v7.app.ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null)
            actionBar.setTitle("Approve " + Constants.TITLE_GRADUATION);
    }

    /**
     * Set menu
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.graduation_menu, menu);
    }

    /**
     * Set menu
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_approve_all:
                Query query = Constants.FIREBASE_REF_STUDENTS.orderByChild("status").equalTo("Pending approval");
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChildren()) {
                            for (DataSnapshot studentSnapshot : dataSnapshot.getChildren()) {
                                Constants.FIREBASE_REF_STUDENTS.child(studentSnapshot.getKey()).child(Constants.FIREBASE_ATTR_STUDENTS_STATUS).setValue("Completed");
                            }
                            Toast.makeText(getActivity(), "Approved successful", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getActivity(), "No student to approve", Toast.LENGTH_LONG).show();
                        }

                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {
                    }
                });
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
