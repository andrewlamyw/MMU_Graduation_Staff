package com.lamyatweng.mmugraduationstaff.Convocation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.lamyatweng.mmugraduationstaff.R;

public class ConvocationAdapter extends ArrayAdapter<Convocation> {
    public ConvocationAdapter(Context context) {
        super(context, 0);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Convocation convocation = getItem(position);

        // Inflate view if view has not been created
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_convocation, parent, false);
        }

        // Get reference of views
        TextView year = (TextView) convertView.findViewById(R.id.textView_convocation_year);

        // Set text value of views
        year.setText(Integer.toString(convocation.getYear()));

        return convertView;
    }
}
