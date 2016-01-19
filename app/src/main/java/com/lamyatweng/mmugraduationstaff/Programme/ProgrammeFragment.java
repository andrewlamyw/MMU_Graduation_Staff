package com.lamyatweng.mmugraduationstaff.Programme;

import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;
import com.lamyatweng.mmugraduationstaff.Constants;
import com.lamyatweng.mmugraduationstaff.R;

public class ProgrammeFragment extends Fragment {
    Bundle bundle = new Bundle();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_programme, container, false);
        this.setHasOptionsMenu(true);

        // Populate programmes sort by name from Firebase into ListView
        Firebase.setAndroidContext(getActivity());
        final ProgrammeCustomAdapter adapter = new ProgrammeCustomAdapter(getActivity());
        final Firebase programmeRef = new Firebase(Constants.FIREBASE_PROGRAMMES_REF);
        Query programmeQuery = programmeRef.orderByChild("name");
        programmeQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Programme programme;
                adapter.clear();
                for (DataSnapshot programmeSnapshot : dataSnapshot.getChildren()) {
                    programme = programmeSnapshot.getValue(Programme.class);
                    adapter.add(programme);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Snackbar.make(rootView, firebaseError.getMessage(), Snackbar.LENGTH_LONG).show();
            }
        });
        ListView programmeListView = (ListView) rootView.findViewById(R.id.programme_list_view);
        programmeListView.setAdapter(adapter);

        // Launch a dialog to display programme details
        programmeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Programme selectedProgramme = (Programme) parent.getItemAtPosition(position);

                // Retrieve selected programme key from Firebase and save in bundle
                Query queryRef = programmeRef.orderByChild("name").equalTo(selectedProgramme.getName());
                queryRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot programmeSnapshot : dataSnapshot.getChildren()) {
                            Programme firebaseProgramme = programmeSnapshot.getValue(Programme.class);
                            if (firebaseProgramme.getFaculty().equals(selectedProgramme.getFaculty())) {
                                bundle.putString(getString(R.string.key_programme_key), programmeSnapshot.getKey());
                                ProgrammeDetailsDialogFragment programmeDetailsDialogFragment = new ProgrammeDetailsDialogFragment();
                                programmeDetailsDialogFragment.setArguments(bundle);
                                getFragmentManager().beginTransaction().add(programmeDetailsDialogFragment, null).
                                        addToBackStack(programmeDetailsDialogFragment.getClass().getName()).commit();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });
            }
        });

        // Launch a dialog to add new programme
        FloatingActionButton addProgrammeFab = (FloatingActionButton) rootView.findViewById(R.id.add_programme_fab);
        addProgrammeFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgrammeAddDialogFragment newFragment = new ProgrammeAddDialogFragment();
                getFragmentManager().beginTransaction().add(newFragment, null).addToBackStack(null).commit();
            }
        });

        return rootView;
    }

    /**
     * Set ActionBar title
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        android.support.v7.app.ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null)
            actionBar.setTitle(Constants.TITLE_PROGRAMME);
    }

    /**
     * Set menu
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.programme_menu, menu);
    }

    /**
     * Set menu
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                return true;

            case R.id.action_filter:
                return true;

            case R.id.action_share:
                return true;

            case R.id.action_settings:
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}