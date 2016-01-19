package com.lamyatweng.mmugraduationstaff.Convocation;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
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

public class ConvocationDetailsDialogFragment extends DialogFragment {
    Bundle mBundle = new Bundle();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_convocation_details, container, false);

        // Retrieve programmeKey from previous fragment
        mBundle = getArguments();
        final String convocationKey = mBundle.getString(getString(R.string.key_convocation_key));

        // Get references of views
        final TextInputLayout yearWrapper = (TextInputLayout) view.findViewById(R.id.wrapper_convocation_year);
        final TextInputLayout openDateWrapper = (TextInputLayout) view.findViewById(R.id.wrapper_open_registration_date);
        final TextInputLayout closeDateWrapper = (TextInputLayout) view.findViewById(R.id.wrapper_close_registration_date);

        // Retrieve convocation details from Firebase and display
        Firebase convocationRef = new Firebase(Constants.FIREBASE_CONVOCATION_REF);
        convocationRef.child(convocationKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Convocation convocation = dataSnapshot.getValue(Convocation.class);
                // Null checking is required for handling removed item from Firebase
                if (convocation != null) {
                    yearWrapper.getEditText().setText(convocation.getYear());
                    openDateWrapper.getEditText().setText(convocation.getOpenRegistrationDate());
                    closeDateWrapper.getEditText().setText(convocation.getCloseRegistrationDate());
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        // Set up Toolbar with back, edit and delete button
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.mipmap.ic_arrow_back_white_24dp);
        toolbar.inflateMenu(R.menu.convocation_details);
        // Close dialog
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConvocationDetailsDialogFragment.this.getDialog().cancel();
            }
        });
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getTitle().toString()) {
                    case Constants.MENU_DELETE:
                        ConvocationDeleteDialogFragment convocationDeleteDialogFragment = new ConvocationDeleteDialogFragment();
                        convocationDeleteDialogFragment.setArguments(mBundle);
                        getFragmentManager().beginTransaction().add(convocationDeleteDialogFragment, null).addToBackStack(null).commit();
                        return true;

                    case Constants.MENU_EDIT:
                        Intent intent = new Intent(getActivity(), ConvocationEditActivity.class);
                        intent.putExtra(Constants.EXTRA_CONVOCATION_KEY, convocationKey);
                        startActivity(intent);
                        return true;

                    default:
                        return false;
                }
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
