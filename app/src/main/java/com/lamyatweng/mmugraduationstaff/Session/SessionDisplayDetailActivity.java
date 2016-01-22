package com.lamyatweng.mmugraduationstaff.Session;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.lamyatweng.mmugraduationstaff.Constants;
import com.lamyatweng.mmugraduationstaff.R;
import com.lamyatweng.mmugraduationstaff.Seat.SeatDisplayActivity;

public class SessionDisplayDetailActivity extends AppCompatActivity {
    Bundle mBundle = new Bundle();
    int mSessionId;
    int mRowSize;
    int mColumnSize;
    int mConvocationYear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session_display);

        // Receive convocation key from the Intent
        Intent intent = getIntent();
        final String sessionKey = intent.getStringExtra(Constants.EXTRA_SESSION_KEY);

        // Get references of views
        final TextInputLayout convocationYear = (TextInputLayout) findViewById(R.id.wrapper_convocation_year);
        final TextInputLayout sessionNumber = (TextInputLayout) findViewById(R.id.wrapper_session_number);
        final TextInputLayout sessionId = (TextInputLayout) findViewById(R.id.wrapper_session_id);
        final TextInputLayout date = (TextInputLayout) findViewById(R.id.wrapper_session_date);
        final TextInputLayout startTime = (TextInputLayout) findViewById(R.id.wrapper_session_start_time);
        final TextInputLayout endTime = (TextInputLayout) findViewById(R.id.wrapper_session_end_time);
        final TextInputLayout rowSize = (TextInputLayout) findViewById(R.id.wrapper_session_row_size);
        final TextInputLayout columnSize = (TextInputLayout) findViewById(R.id.wrapper_session_column_size);

        // Retrieve session details from Firebase and display
        Firebase sessionRef = new Firebase(Constants.FIREBASE_SESSIONS_REF);
        sessionRef.child(sessionKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ConvocationSession session = dataSnapshot.getValue(ConvocationSession.class);
                // Null checking is required for handling removed item from Firebase
                if (session != null) {
                    convocationYear.getEditText().setText(Integer.toString(session.getConvocationYear()));
                    sessionNumber.getEditText().setText(Integer.toString(session.getSessionNumber()));
                    sessionId.getEditText().setText(Integer.toString(session.getId()));
                    date.getEditText().setText(session.getDate());
                    startTime.getEditText().setText(session.getStartTime());
                    endTime.getEditText().setText(session.getEndTime());
                    rowSize.getEditText().setText(Integer.toString(session.getRowSize()));
                    columnSize.getEditText().setText(Integer.toString(session.getColumnSize()));

                    mSessionId = session.getId();
                    mRowSize = session.getRowSize();
                    mColumnSize = session.getColumnSize();
                    mConvocationYear = session.getConvocationYear();
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });

        // Open a new activity to display list of programmes in the session
        Button viewProgrammes = (Button) findViewById(R.id.button_view_programmes);
        viewProgrammes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SessionProgrammeListActivity.class);
                intent.putExtra(Constants.EXTRA_SESSION_KEY, sessionKey);
                startActivity(intent);
            }
        });

        // Open a new activity to display seating arrangement in the session
        Button viewSeating = (Button) findViewById(R.id.button_view_seating);
        viewSeating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SeatDisplayActivity.class);
                intent.putExtra(Constants.EXTRA_SESSION_KEY, sessionKey);
                intent.putExtra(Constants.EXTRA_SESSION_ID, mSessionId);
                intent.putExtra(Constants.EXTRA_SESSION_COLUMN_SIZE, mColumnSize);
                intent.putExtra(Constants.EXTRA_SESSION_ROW_SIZE, mRowSize);
                intent.putExtra(Constants.EXTRA_SESSION_CONVOCATION_YEAR, mConvocationYear);
                startActivity(intent);
            }
        });

        // Set up Toolbar with back, edit and delete button
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(Constants.TITLE_SESSION + " Detail");
        // Close activity
        toolbar.setNavigationIcon(R.mipmap.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        // Set up menu
        toolbar.inflateMenu(R.menu.session_details);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getTitle().toString()) {
                    case Constants.MENU_DELETE:
                        SessionDeleteDialogFragment sessionDeleteDialogFragment = new SessionDeleteDialogFragment();
                        sessionDeleteDialogFragment.setArguments(mBundle);
                        getFragmentManager().beginTransaction().add(sessionDeleteDialogFragment, null).addToBackStack(null).commit();
                        return true;

                    case Constants.MENU_EDIT:
                        Intent intent = new Intent(getApplicationContext(), SessionEditActivity.class);
                        intent.putExtra(Constants.EXTRA_SESSION_KEY, sessionKey);
                        startActivity(intent);
                        return true;

                    default:
                        return false;
                }
            }
        });
    }
}
