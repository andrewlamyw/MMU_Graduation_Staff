package com.lamyatweng.mmugraduationstaff.Student;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.lamyatweng.mmugraduationstaff.R;

public class StudentAdapter extends ArrayAdapter<Student> {
    LayoutInflater mInflater;

    public StudentAdapter(Context context) {
        super(context, 0);
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        // Create new view if no available view to reuse
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_student, parent, false);
            holder = new ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.textView_student_name);
            holder.id = (TextView) convertView.findViewById(R.id.textView_student_id);
            holder.programme = (TextView) convertView.findViewById(R.id.textView_student_programme);
            holder.status = (TextView) convertView.findViewById(R.id.textView_student_status);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // Set text value of views
        Student student = getItem(position);
        holder.name.setText(student.getName());
        holder.id.setText(student.getId());
        holder.programme.setText(student.getProgramme());
        holder.status.setText(student.getStatus());

        return convertView;
    }

    static class ViewHolder {
        TextView name;
        TextView id;
        TextView programme;
        TextView status;
    }
}
