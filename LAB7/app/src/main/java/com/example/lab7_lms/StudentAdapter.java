package com.example.lab7_lms;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.ViewHolder> {

    private List<Student> studentList;
    private List<Student> fullList;
    private Context context;
    private ViewStudentsActivity activity;
    private DatabaseHelper dbHelper;

    public StudentAdapter(List<Student> studentList, ViewStudentsActivity activity, DatabaseHelper dbHelper) {
        this.studentList = studentList;
        this.fullList = new ArrayList<>(studentList);
        this.context = activity;
        this.activity = activity;
        this.dbHelper = dbHelper;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.student_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Student student = studentList.get(position);
        holder.tvRoll.setText("Roll No: " + student.getRollNumber());
        holder.tvName.setText("Name: " + student.getName());
        holder.tvMarks.setText("Marks: " + student.getMarks());

        holder.btnEdit.setOnClickListener(v -> activity.editStudent(student));
        holder.btnDelete.setOnClickListener(v -> activity.deleteStudent(student.getRollNumber()));
    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

    public void filter(String query) {
        studentList.clear();
        if (query.isEmpty()) {
            studentList.addAll(fullList);
        } else {
            for (Student student : fullList) {
                if (student.getName().toLowerCase().contains(query.toLowerCase()) ||
                        student.getRollNumber().contains(query)) {
                    studentList.add(student);
                }
            }
        }
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvRoll, tvName, tvMarks;
        Button btnEdit, btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvRoll = itemView.findViewById(R.id.tvRoll);
            tvName = itemView.findViewById(R.id.tvName);
            tvMarks = itemView.findViewById(R.id.tvMarks);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
