package com.lamyatweng.mmugraduationstaff.Student;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.lamyatweng.mmugraduationstaff.R;

public class StudentCustomAdapter extends ArrayAdapter<Student> {

    public StudentCustomAdapter(Context context) {
        super(context, 0);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Student student = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_student, parent, false);
        }

        TextView studentName = (TextView) convertView.findViewById(R.id.studentName);
        TextView studentId = (TextView) convertView.findViewById(R.id.studentId);
        TextView studentCourse = (TextView) convertView.findViewById(R.id.studentCourse);
        TextView studentStatus = (TextView) convertView.findViewById(R.id.studentStatus);

        studentName.setText(student.getName());
        studentId.setText(student.getId());
        studentCourse.setText(student.getProgramme());
        studentStatus.setText(student.getStatus());

        return convertView;
    }
}
