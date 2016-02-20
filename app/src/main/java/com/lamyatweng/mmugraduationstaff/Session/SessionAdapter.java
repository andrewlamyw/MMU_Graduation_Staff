package com.lamyatweng.mmugraduationstaff.Session;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.lamyatweng.mmugraduationstaff.R;


public class SessionAdapter extends ArrayAdapter<ConvocationSession> {
    LayoutInflater mInflater;

    public SessionAdapter(Context context) {
        super(context, 0);
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Create new view if no available view to reuse
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_session, parent, false);
        }

        TextView number = (TextView) convertView.findViewById(R.id.session_number);
        TextView date = (TextView) convertView.findViewById(R.id.session_date);
        TextView startTime = (TextView) convertView.findViewById(R.id.session_start_time);
        TextView closeTime = (TextView) convertView.findViewById(R.id.session_end_time);

        // Set text value of views
        ConvocationSession session = getItem(position);
        number.setText(Integer.toString(session.getSessionNumber()));
        date.setText(session.getDate());
        startTime.setText(session.getStartTime());
        closeTime.setText(session.getEndTime());

        return convertView;
    }
}
