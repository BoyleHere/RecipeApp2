package com.example.lab7_lms;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ViewStudentsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private StudentAdapter adapter;
    private DatabaseHelper dbHelper;
    private SearchView searchView;
    private ImageButton backButton;
    private List<Student> studentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_students);

        dbHelper = new DatabaseHelper(this);
        recyclerView = findViewById(R.id.recyclerView);
        searchView = findViewById(R.id.searchView);
        backButton = findViewById(R.id.btnBack);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        loadStudents();

        // Search functionality
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.filter(newText);
                return false;
            }
        });

        // Go back button functionality
        backButton.setOnClickListener(v -> finish());
    }

    private void loadStudents() {
        studentList = new ArrayList<>();
        Cursor cursor = dbHelper.getAllStudents();

        if (cursor.moveToFirst()) {
            do {
                studentList.add(new Student(
                        cursor.getString(0), // Roll Number
                        cursor.getString(1), // Name
                        cursor.getString(2)  // Marks
                ));
            } while (cursor.moveToNext());
        }
        cursor.close();

        adapter = new StudentAdapter(studentList, this, dbHelper);
        recyclerView.setAdapter(adapter);
    }

    public void deleteStudent(final String rollNumber) {
        new AlertDialog.Builder(this)
                .setTitle("Delete Student")
                .setMessage("Are you sure you want to delete this student?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    if (dbHelper.deleteStudent(rollNumber)) {
                        Toast.makeText(ViewStudentsActivity.this, "Deleted successfully!", Toast.LENGTH_SHORT).show();
                        loadStudents();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    public void editStudent(Student student) {
        Intent intent = new Intent(this, EditStudentActivity.class);
        intent.putExtra("roll_number", student.getRollNumber());
        intent.putExtra("name", student.getName());
        intent.putExtra("marks", student.getMarks());
        startActivity(intent);
    }
}
