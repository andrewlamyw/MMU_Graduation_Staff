package com.lamyatweng.mmugraduationstaff.Programme;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.lamyatweng.mmugraduationstaff.R;

public class ProgrammeCustomAdapter extends ArrayAdapter<Programme> {

    public ProgrammeCustomAdapter(Context context) {
        super(context, 0);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Programme programme = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_programme, parent, false);
        }

        TextView programmeName = (TextView) convertView.findViewById(R.id.programmeName);
        TextView programmeFaculty = (TextView) convertView.findViewById(R.id.programmeFaculty);

        programmeName.setText(programme.getName());
        programmeFaculty.setText(programme.getFaculty());

        return convertView;
    }
}
