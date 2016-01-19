package com.lamyatweng.mmugraduationstaff.Convocation;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.lamyatweng.mmugraduationstaff.Constants;
import com.lamyatweng.mmugraduationstaff.R;

public class ConvocationDisplayActivity extends AppCompatActivity {
    Bundle mBundle = new Bundle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_convocation_display);

        // Receive convocation key from the Intent
        Intent intent = getIntent();
        final String convocationKey = intent.getStringExtra(Constants.EXTRA_CONVOCATION_KEY);
        mBundle.putString(getString(R.string.key_convocation_key), convocationKey);

        // Get references of views
        final TextInputLayout yearWrapper = (TextInputLayout) findViewById(R.id.wrapper_convocation_year);
        final TextInputLayout openDateWrapper = (TextInputLayout) findViewById(R.id.wrapper_open_registration_date);
        final TextInputLayout closeDateWrapper = (TextInputLayout) findViewById(R.id.wrapper_close_registration_date);

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
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.mipmap.ic_arrow_back_white_24dp);
        toolbar.inflateMenu(R.menu.convocation_details);
        // Close dialog
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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
                        Intent intent = new Intent(getApplicationContext(), ConvocationEditActivity.class);
                        intent.putExtra(Constants.EXTRA_CONVOCATION_KEY, convocationKey);
                        startActivity(intent);
                        return true;

                    default:
                        return false;
                }
            }
        });
    }
}
