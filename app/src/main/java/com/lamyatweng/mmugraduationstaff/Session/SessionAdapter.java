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
        ViewHolder holder;

        // Create new view if no available view to reuse
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_session, parent, false);
            holder = new ViewHolder();
            holder.number = (TextView) convertView.findViewById(R.id.session_number);
            holder.date = (TextView) convertView.findViewById(R.id.session_date);
            holder.startTime = (TextView) convertView.findViewById(R.id.session_start_time);
            holder.closeTime = (TextView) convertView.findViewById(R.id.session_end_time);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // Set text value of views
        ConvocationSession session = getItem(position);
        holder.number.setText(Integer.toString(session.getSessionNumber()));
        holder.date.setText(session.getDate());
        holder.startTime.setText(session.getStartTime());
        holder.closeTime.setText(session.getEndTime());

        return convertView;
    }

    static class ViewHolder {
        TextView number;
        TextView date;
        TextView startTime;
        TextView closeTime;
    }
}
