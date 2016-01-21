package com.lamyatweng.mmugraduationstaff.Session;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.lamyatweng.mmugraduationstaff.Constants;
import com.lamyatweng.mmugraduationstaff.R;

public class SessionProgrammeAddActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session_programme_add);

        // Receive session key from the Intent
        Intent intent = getIntent();
        final String sessionKey = intent.getStringExtra(Constants.EXTRA_SESSION_KEY);


    }
}
