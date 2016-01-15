package com.lamyatweng.mmugraduationstaff.Programme;

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

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.lamyatweng.mmugraduationstaff.Constants;
import com.lamyatweng.mmugraduationstaff.R;

public class ProgrammeDetailsDialogFragment extends DialogFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_programme_details, container, false);

        // Retrieve programmeKey from previous fragment
        Bundle bundle = getArguments();
        final String programmeKey = bundle.getString(getString(R.string.key_programme_key));

        // Get references of views
        final TextInputLayout programmeNameWrapper = (TextInputLayout) view.findViewById(R.id.wrapper_programme_name);
        final TextInputLayout educationalLevelWrapper = (TextInputLayout) view.findViewById(R.id.wrapper_educational_level);
        final TextInputLayout facultyWrapper = (TextInputLayout) view.findViewById(R.id.wrapper_faculty);

        // Retrieve programme details from Firebase and display
        Firebase programmeRef = new Firebase(Constants.FIREBASE_PROGRAMMES_REF);
        programmeRef.child(programmeKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Programme programme = dataSnapshot.getValue(Programme.class);
                if (programme != null) {
                    programmeNameWrapper.getEditText().setText(programme.getName());
                    educationalLevelWrapper.getEditText().setText(programme.getLevel());
                    facultyWrapper.getEditText().setText(programme.getFaculty());
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        // Set up Toolbar with back, edit and delete button
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.mipmap.ic_arrow_back_white_24dp);
        toolbar.inflateMenu(R.menu.programme_details);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgrammeDetailsDialogFragment.this.getDialog().cancel();
            }
        });
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Bundle bundle = new Bundle();
                bundle.putString(getString(R.string.key_programme_key), programmeKey);
                switch (item.getTitle().toString()) {
                    case Constants.MENU_DELETE:
                        ProgrammeDeleteDialogFragment programmeDeleteDialogFragment = new ProgrammeDeleteDialogFragment();
                        programmeDeleteDialogFragment.setArguments(bundle);
                        getFragmentManager().beginTransaction().add(programmeDeleteDialogFragment, null).addToBackStack(null).commit();
                        return true;
                    case Constants.MENU_EDIT:
                        ProgrammeEditDialogFragment programmeEditDialogFragment = new ProgrammeEditDialogFragment();
                        programmeEditDialogFragment.setArguments(bundle);
                        getFragmentManager().beginTransaction().add(programmeEditDialogFragment, null).addToBackStack(null).commit();
                        return true;
                }
                return false;
            }
        });

        return view;
    }

    /**
     * Set dialog theme as full screen
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new Dialog(getActivity(), android.R.style.Theme_DeviceDefault_Light_DialogWhenLarge_NoActionBar);
    }
}