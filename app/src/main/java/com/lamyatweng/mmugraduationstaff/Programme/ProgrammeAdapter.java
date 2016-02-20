package com.lamyatweng.mmugraduationstaff.Programme;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.lamyatweng.mmugraduationstaff.R;

public class ProgrammeAdapter extends ArrayAdapter<Programme> {
    LayoutInflater mInflater;

    public ProgrammeAdapter(Context context) {
        super(context, 0);
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        // Create new view if no available view to reuse
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_programme, parent, false);
            holder = new ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.textView_programme_name);
            holder.faculty = (TextView) convertView.findViewById(R.id.textView_programme_faculty);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // Set text value of views
        Programme programme = getItem(position);
        holder.name.setText(programme.getName());
        holder.faculty.setText(programme.getFaculty());

        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        // Create new view if no available view to reuse
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_programme, parent, false);
            holder = new ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.textView_programme_name);
            holder.faculty = (TextView) convertView.findViewById(R.id.textView_programme_faculty);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // Set text value of views
        Programme programme = getItem(position);
        holder.name.setText(programme.getName());
        holder.faculty.setText(programme.getFaculty());

        return convertView;
    }

    static class ViewHolder {
        TextView name;
        TextView faculty;
    }
}