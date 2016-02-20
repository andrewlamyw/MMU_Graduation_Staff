package com.lamyatweng.mmugraduationstaff.News;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.lamyatweng.mmugraduationstaff.Constants;
import com.lamyatweng.mmugraduationstaff.R;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

public class NewsAddActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_add);

        // Get references of views
        final TextInputLayout messageWrapper = (TextInputLayout) findViewById(R.id.wrapper_news_message);

        // Set Toolbar with close button
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("New " + Constants.TITLE_NEWS);
        toolbar.setNavigationIcon(R.mipmap.ic_close_white_24dp);
        toolbar.inflateMenu(R.menu.news_add);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Commit: add new news into Firebase
        final Firebase convocationRef = new Firebase(Constants.FIREBASE_STRING_CONVOCATIONS_REF);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getTitle().toString()) {
                    case Constants.MENU_SAVE:
                        // Retrieve user inputs
                        String message = messageWrapper.getEditText().getText().toString();
                        Calendar calendar = Calendar.getInstance();
                        Date date = calendar.getTime();
                        String stringDate = DateFormat.getDateInstance().format(date);

                        // Push into Firebase news list
                        News news = new News(stringDate, message);
                        Constants.FIREBASE_REF_NEWS.push().setValue(news);

                        // Display message and close dialog
                        Toast.makeText(getApplicationContext(), Constants.TITLE_NEWS + " added.", Toast.LENGTH_LONG).show();
                        finish();
                        return true;

                    default:
                        return false;
                }
            }
        });
    }
}
