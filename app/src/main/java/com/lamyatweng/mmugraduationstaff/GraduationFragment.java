package com.lamyatweng.mmugraduationstaff;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.lamyatweng.mmugraduationstaff.Student.Student;

public class GraduationFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_graduation, container, false);

        // Check is currently logged in student fulfill graduation requirements
        // If fulfilled, change student status to "Pending approval"
        // else, tell user requirements not fulfilled
        Button applyButton = (Button) rootView.findViewById(R.id.button_apply_for_graduation);
        applyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get email of currently logged in user
                SessionManager session = new SessionManager(getActivity().getApplicationContext());
                session.checkLogin();
                String userEmail = session.getUserEmail();

                // Retrieve student information from Firebase
                Firebase.setAndroidContext(getActivity());
                final Firebase studentRef = new Firebase(Constants.FIREBASE_STUDENTS_REF);
                final Query queryRef = studentRef.orderByChild("email").equalTo(userEmail);
                queryRef.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        Student student = dataSnapshot.getValue(Student.class);
                        if (student.getBalanceCreditHour() <= 0 &&
                                student.getCgpa() >= 2 &&
                                student.getMuet() >= 3 &&
                                student.getFinancialDue() <= 0) {
                            student.setStatus("Pending approval");
                            studentRef.child(dataSnapshot.getKey()).setValue(student);
                            Toast.makeText(getActivity(), "Apply successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getActivity(), "Apply failed. Requirements unfulfilled.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
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
            }
        });
        return rootView;
    }
}
