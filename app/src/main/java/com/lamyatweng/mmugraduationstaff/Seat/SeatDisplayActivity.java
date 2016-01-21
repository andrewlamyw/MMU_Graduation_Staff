package com.lamyatweng.mmugraduationstaff.Seat;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;
import com.lamyatweng.mmugraduationstaff.Constants;
import com.lamyatweng.mmugraduationstaff.R;
import com.lamyatweng.mmugraduationstaff.Session.ConvocationSession;

public class SeatDisplayActivity extends AppCompatActivity {
    int mSessionId;
    SeatAdapter mAdapter;

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

        // Get references of views
        final Button createSeatButton = (Button) findViewById(R.id.button_create_seat);
        final LinearLayout linearLayoutButtonCreate = (LinearLayout) findViewById(R.id.linear_layout_button_create);
        final LinearLayout linearLayoutEditDelete = (LinearLayout) findViewById(R.id.linear_layout_edit_delete);
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

                    gridView.setNumColumns(session.getColumnSize());
                    gridView.setAdapter(mAdapter);
                    ViewGroup.LayoutParams layoutParams = gridView.getLayoutParams();
                    layoutParams.width = convertDpToPixels(session.getColumnSize() * 20, getApplicationContext());
                    gridView.setLayoutParams(layoutParams);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });

        // Retrieve seating arrangement from Firebase and display
        Firebase seatRef = new Firebase(Constants.FIREBASE_SEATS_REF);
        Query seatQuery = seatRef.orderByChild("sessionID").equalTo(mSessionId);
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
                if (found) {
                    linearLayoutButtonCreate.setVisibility(View.GONE);
                } else {
                    linearLayoutEditDelete.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        // Set up Toolbar with back, edit and delete button
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
    }
}
