package com.lamyatweng.mmugraduationstaff;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.lamyatweng.mmugraduationstaff.Programme.ProgrammeFragment;
import com.lamyatweng.mmugraduationstaff.Student.StudentFragment;

public class MainActivity extends AppCompatActivity {

    SessionManager mSession;
    DrawerLayout mDrawerLayout;
    ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_view);

        // Redirects to LoginActivity if user is not logged in
        mSession = new SessionManager(getApplicationContext());
        mSession.checkLogin();

        // Set ActionBar
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Set toggling drawer with ActionBar Icon
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                         /* host Activity */
                mDrawerLayout,                /* DrawerLayout object */
                toolbar,                      /* Toolbar to inject into */
                R.string.drawer_open,         /* "open drawer" description */
                R.string.drawer_close         /* "close drawer" description */
        ) {
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        // Set navigation item selected listener
        final NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                if (findViewById(R.id.fragment_container) != null) {
                    if (savedInstanceState != null) {
                        // If we're being restored from a previous state,
                        // then we don't need to do anything and should return or else
                        // we could end up with overlapping fragments.
                        return false;
                    }
                    displayFragment(menuItem);
                }
                menuItem.setChecked(true);
                mDrawerLayout.closeDrawers();
                return false;
            }
        });

        // Set navigation header email TextView
        View view = navigationView.getHeaderView(0);
        TextView emailHeader = (TextView) view.findViewById(R.id.header_email);
        emailHeader.setText(mSession.getUserEmail());
    }

    /**
     * Display fragment based on selected drawerMenuItem
     * Add new item at layout/menu/navigation_items.xml
     */
    public void displayFragment(MenuItem menuItem) {

        switch (menuItem.getTitle().toString()) {

            case Constants.TITLE_PROGRAMME:
                ProgrammeFragment programmeFragment = new ProgrammeFragment();
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, programmeFragment)
                        .addToBackStack(Constants.TITLE_PROGRAMME).commit();
                Toast.makeText(getApplicationContext(), "Loading", Toast.LENGTH_LONG).show();
                break;
            case Constants.TITLE_PROFILE:
                ProfileFragment profileFragment = new ProfileFragment();
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, profileFragment)
                        .addToBackStack(Constants.TITLE_PROFILE).commit();
                break;
            case Constants.TITLE_STUDENT:
                StudentFragment studentFragment = new StudentFragment();
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, studentFragment)
                        .addToBackStack(Constants.TITLE_STUDENT).commit();
                break;
            case Constants.TITLE_GRADUATION:
                GraduationFragment graduationFragment = new GraduationFragment();
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, graduationFragment)
                        .addToBackStack(Constants.TITLE_GRADUATION).commit();
                break;
            case Constants.TITLE_CONVOCATION:
                ConvocationFragment convocationFragment = new ConvocationFragment();
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, convocationFragment)
                        .addToBackStack(Constants.TITLE_CONVOCATION).commit();
                break;
            case Constants.TITLE_LOGOUT:
                mSession.logoutUser();
                break;
        }
    }

    /**
     * Display previous fragment when device's back button is pressed
     */
    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    /**
     * Set toggling drawer with ActionBar Icon
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // if current activity is main/home activity, then clicking the menu/appbar icon will open drawer
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /** Set toggling drawer with ActionBar Icon */
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    /** Set toggling drawer with ActionBar Icon */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }
}
