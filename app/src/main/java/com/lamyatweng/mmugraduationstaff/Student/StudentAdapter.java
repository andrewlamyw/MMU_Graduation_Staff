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
        // Create new view if no available view to reuse
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_student, parent, false);
        }

        TextView name = (TextView) convertView.findViewById(R.id.textView_student_name);
        TextView id = (TextView) convertView.findViewById(R.id.textView_student_id);
        TextView programme = (TextView) convertView.findViewById(R.id.textView_student_programme);
        TextView status = (TextView) convertView.findViewById(R.id.textView_student_status);

        // Set text value of views
        Student student = getItem(position);
        name.setText(student.getName());
        id.setText(student.getId());
        programme.setText(student.getProgramme());
        status.setText(student.getStatus());

        return convertView;
    }
}
