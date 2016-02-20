package com.lamyatweng.mmugraduationstaff.Convocation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.lamyatweng.mmugraduationstaff.R;

public class ConvocationAdapter extends ArrayAdapter<Convocation> {
    LayoutInflater mInflater;

    public ConvocationAdapter(Context context) {
        super(context, 0);
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Create a new view if no available view to reuse
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_convocation, parent, false);
        }

        TextView year = (TextView) convertView.findViewById(R.id.textView_convocation_year);

        // Set text value of views
        Convocation convocation = getItem(position);
        year.setText(Integer.toString(convocation.getYear()));

        return convertView;
    }
}
