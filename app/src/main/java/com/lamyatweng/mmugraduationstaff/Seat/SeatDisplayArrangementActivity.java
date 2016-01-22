package com.lamyatweng.mmugraduationstaff.Seat;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;
import com.lamyatweng.mmugraduationstaff.Constants;
import com.lamyatweng.mmugraduationstaff.R;
import com.lamyatweng.mmugraduationstaff.Session.ConvocationSession;

public class SeatDisplayArrangementActivity extends AppCompatActivity {
    int mSessionId;
    int mNumberOfRows;
    int mNumberOfColumns;
    int mConvocationYear;
    SeatAdapter mAdapter;
    Firebase mSeatRef;
    Bundle mBundle = new Bundle();

    public static int convertDpToPixels(float dp, Context context) {
        Resources resources = context.getResources();
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp,
                resources.getDisplayMetrics()
        );
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seating_display);

        mAdapter = new SeatAdapter(this);

        // Receive convocation key from the Intent
        Intent intent = getIntent();
        final String sessionKey = intent.getStringExtra(Constants.EXTRA_SESSION_KEY);
        mSessionId = intent.getIntExtra(Constants.EXTRA_SESSION_ID, 0);
        mNumberOfColumns = intent.getIntExtra(Constants.EXTRA_SESSION_COLUMN_SIZE, 0);
        mNumberOfRows = intent.getIntExtra(Constants.EXTRA_SESSION_ROW_SIZE, 0);
        mConvocationYear = intent.getIntExtra(Constants.EXTRA_SESSION_CONVOCATION_YEAR, 0);
        mBundle.putInt(getString(R.string.key_session_id), intent.getIntExtra(Constants.EXTRA_SESSION_ID, 0));

        // Get references of views
        final GridView gridView = (GridView) findViewById(R.id.grid_view);

        // Retrieve session details from Firebase and display
        Firebase sessionRef = new Firebase(Constants.FIREBASE_SESSIONS_REF);
        sessionRef.child(sessionKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ConvocationSession session = dataSnapshot.getValue(ConvocationSession.class);
                // Null checking is required for handling removed item from Firebase
                if (session != null) {
                    mSessionId = session.getId();
                    mNumberOfRows = session.getRowSize();
                    mNumberOfColumns = session.getColumnSize();
                    mConvocationYear = session.getConvocationYear();

                    gridView.setNumColumns(session.getColumnSize());
                    gridView.setAdapter(mAdapter);
                    ViewGroup.LayoutParams layoutParams = gridView.getLayoutParams();
                    layoutParams.width = convertDpToPixels(session.getColumnSize() * 23, getApplicationContext());
                    gridView.setLayoutParams(layoutParams);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });

        // Retrieve seating arrangement from Firebase and display
        mSeatRef = new Firebase(Constants.FIREBASE_SEATS_REF);
        Query seatQuery = mSeatRef.orderByChild("sessionID").equalTo(mSessionId);
        seatQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Boolean found = false;
                mAdapter.clear();
                for (DataSnapshot seatSnapshot : dataSnapshot.getChildren()) {
                    mAdapter.add(seatSnapshot.getValue(Seat.class));
                    if (!found)
                        found = true;
                }
                if (!found) {
                    createSeatingArrangement();
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        // Open a new activity to display seat detail
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Seat selectedSeat = (Seat) parent.getItemAtPosition(position);
                Query seatQuery = mSeatRef.orderByChild("id").equalTo(selectedSeat.getId());
                seatQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot seatSnapshot : dataSnapshot.getChildren()) {
                            Seat firebaseSeat = seatSnapshot.getValue(Seat.class);
                            if (firebaseSeat.getSessionID() == selectedSeat.getSessionID()) {
                                Intent intent = new Intent(getApplicationContext(), SeatEditActivity.class);
                                intent.putExtra(Constants.EXTRA_SEAT_KEY, seatSnapshot.getKey());
                                startActivity(intent);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {
                    }
                });
            }
        });

        // Set up Toolbar with back button
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(Constants.TITLE_SEAT);
        // Close activity
        toolbar.setNavigationIcon(R.mipmap.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        // Set up menu
        toolbar.inflateMenu(R.menu.seat_details);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getTitle().toString()) {
                    case Constants.MENU_DELETE:
                        SeatDeleteDialogFragment seatDeleteDialogFragment = new SeatDeleteDialogFragment();
                        seatDeleteDialogFragment.setArguments(mBundle);
                        getFragmentManager().beginTransaction().add(seatDeleteDialogFragment, null).addToBackStack(null).commit();
                        return true;

                    default:
                        return false;
                }
            }
        });
    }

    // Create new seats and add to Firebase based on number of columns and rows in session
    private void createSeatingArrangement() {
        Seat seat;
        int id;
        String twoDigitRow;
        String twoDigitColumn;
        String status = "available";
        String studentId = " ";
        for (int row = 1; row <= mNumberOfRows; row++) {
            for (int column = 1; column <= mNumberOfColumns; column++) {

                if (row < 10)
                    twoDigitRow = "0" + Integer.toString(row);
                else
                    twoDigitRow = Integer.toString(row);

                if (column < 10)
                    twoDigitColumn = "0" + Integer.toString(column);
                else
                    twoDigitColumn = Integer.toString(column);

                id = Integer.parseInt(Integer.toString(mSessionId) + twoDigitRow + twoDigitColumn);

                seat = new Seat(id, twoDigitRow, twoDigitColumn, status, mSessionId, studentId);
                mSeatRef.push().setValue(seat);
            }
        }
    }
}
