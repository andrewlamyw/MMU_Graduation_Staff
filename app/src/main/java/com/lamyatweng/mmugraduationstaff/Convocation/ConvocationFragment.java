package com.lamyatweng.mmugraduationstaff.Convocation;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.lamyatweng.mmugraduationstaff.Constants;
import com.lamyatweng.mmugraduationstaff.R;

public class ConvocationFragment extends Fragment{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_convocation, container, false);

        // Populate students from Firebase into ListView
        final ConvocationCustomAdapter adapter = new ConvocationCustomAdapter(getActivity());
        Firebase.setAndroidContext(getActivity());
        final Firebase convocationRef = new Firebase(Constants.FIREBASE_CONVOCATION_REF);
        convocationRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Convocation convocation;
                adapter.clear();
                for (DataSnapshot studentSnapshot : dataSnapshot.getChildren()) {
                    convocation = studentSnapshot.getValue(Convocation.class);
                    adapter.add(convocation);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });
        ListView convocationListView = (ListView) view.findViewById(R.id.convocation_list_view);
        convocationListView.setAdapter(adapter);


        // Launch a dialog to add new student when user click floating action button
        FloatingActionButton addConvocationFab = (FloatingActionButton) view.findViewById(R.id.add_convocation_fab);
        addConvocationFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConvocationAddDialogFragment convocationAddDialogFragment = new ConvocationAddDialogFragment();
                getFragmentManager().beginTransaction()
                        .add(convocationAddDialogFragment, convocationAddDialogFragment.getClass()
                                .getName()).addToBackStack(null).commit();
            }
        });

        return view;
    }

    /**
     * Set ActionBar title
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        android.support.v7.app.ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null)
            actionBar.setTitle(Constants.TITLE_CONVOCATION);
    }
}
