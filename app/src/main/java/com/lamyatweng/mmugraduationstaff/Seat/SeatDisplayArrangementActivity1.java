package com.lamyatweng.mmugraduationstaff.Seat;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;
import com.lamyatweng.mmugraduationstaff.Constants;
import com.lamyatweng.mmugraduationstaff.R;

public class SeatDisplayArrangementActivity1 extends AppCompatActivity
        implements SeatAddDialogFragment.OnCreateSeatDialogButtonClicked {
    final int GRID_COLUMN_WIDTH_IN_DP = 24;
    Query mSeatQuery;
    ValueEventListener mSeatListener;
    SeatAdapter mSeatAdapter;
    GridView mGridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat_display_arrangement1);

        // Get sessionId & numOfCol from previous activity
        Intent intent = getIntent();
        final int sessionId = intent.getIntExtra(Constants.EXTRA_SESSION_ID, -1);
        int numberOfColumns = intent.getIntExtra(Constants.EXTRA_SESSION_COLUMN_SIZE, -1);
        final String sessionKey = intent.getStringExtra(Constants.EXTRA_SESSION_KEY);

        // Initialise variables for displaying seats in GridView
        mSeatQuery = Constants.FIREBASE_REF_SEATS.orderByChild("sessionID").equalTo(sessionId);
        mSeatAdapter = new SeatAdapter(this);
        mGridView = (GridView) findViewById(R.id.grid_view);
        mGridView.setAdapter(mSeatAdapter);
        updateGridViewWidth(mGridView, numberOfColumns);

        // Set toolbar title
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(Constants.TITLE_SEAT + " Arrangement");
        // Set back button
        toolbar.setNavigationIcon(R.mipmap.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        // Set menu
        toolbar.inflateMenu(R.menu.seat_arrangement);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Query seatQuery = Constants.FIREBASE_REF_SEATS.orderByChild(Constants.FIREBASE_ATTR_SEATS_SESSIONID).equalTo(sessionId);

                switch (item.getTitle().toString()) {
                    case Constants.MENU_DELETE:
                        seatQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    Log.i(getClass().getName(), "delete button clicked, dataSnapshot is not null");
                                    SeatDeleteDialogFragment seatDeleteDialogFragment = new SeatDeleteDialogFragment();
                                    Bundle bundle = new Bundle();
                                    bundle.putInt(getString(R.string.key_session_id), sessionId);
                                    seatDeleteDialogFragment.setArguments(bundle);
                                    getFragmentManager().beginTransaction().add(seatDeleteDialogFragment, null).commit();
                                } else {
                                    Log.i(getClass().getName(), "delete button clicked, dataSnapshot is null");
                                    Toast.makeText(getApplicationContext(), "There are no seats to delete", Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onCancelled(FirebaseError firebaseError) {

                            }
                        });
                        return true;

                    case Constants.MENU_ADD:
                        // first, check whether there are any seats link to the session id
                        seatQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    Log.i(getClass().getName(), "add button clicked, dataSnapshot is not null");
                                    SeatExistDialogFragment dialogFragment = new SeatExistDialogFragment();
                                    getFragmentManager().beginTransaction().add(dialogFragment, null).commit();
                                } else {
                                    Log.i(getClass().getName(), "add button clicked, dataSnapshot is null");
//                                    Toast.makeText(getApplicationContext(), "Creating", Toast.LENGTH_LONG).show();
                                    SeatAddDialogFragment dialogFragment = new SeatAddDialogFragment();
                                    Bundle bundle = new Bundle();
                                    bundle.putString(getString(R.string.key_session_key), sessionKey);
                                    bundle.putInt(getString(R.string.key_session_id), sessionId);
                                    dialogFragment.setArguments(bundle);
                                    getFragmentManager().beginTransaction().add(dialogFragment, null).commit();

                                }
                            }

                            @Override
                            public void onCancelled(FirebaseError firebaseError) {

                            }
                        });
                        return true;

                    default:
                        return false;
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Get seats and add into adapter
        mSeatListener = mSeatQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot seatsSnapshot) {
                Log.i(getClass().getName(), "onResume onDataChange is called");
                mSeatAdapter.clear();
                for (DataSnapshot seatSnapshot : seatsSnapshot.getChildren()) {
                    mSeatAdapter.add(seatSnapshot.getValue(Seat.class));
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        Constants.FIREBASE_REF_SEATS.removeEventListener(mSeatListener);
        Log.i(getClass().getName(), "onPause removeEventListener is called");
    }

    /**
     * Set gridView adapter, number of columns, and grid total width
     */
    private void updateGridViewWidth(GridView gridView, int numberOfColumns) {
        gridView.setNumColumns(numberOfColumns);
        ViewGroup.LayoutParams layoutParams = gridView.getLayoutParams();
        layoutParams.width = convertDpToPixels(numberOfColumns * GRID_COLUMN_WIDTH_IN_DP, getApplicationContext());
        gridView.setLayoutParams(layoutParams);
    }

    /**
     * Used for calculating grid total width
     */
    int convertDpToPixels(float dp, Context context) {
        Resources resources = context.getResources();
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp,
                resources.getDisplayMetrics()
        );
    }

    @Override
    public void onCreateSeatDialogButtonClicked(int numberOfColumns) {
        updateGridViewWidth(mGridView, numberOfColumns);
    }
}
