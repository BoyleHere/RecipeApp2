package com.example.lab7_lms;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper dbHelper;
    EditText etRoll, etName, etMarks;
    Button btnAdd, btnUpdate, btnDelete, btnView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DatabaseHelper(this);

        etRoll = findViewById(R.id.etRoll);
        etName = findViewById(R.id.etName);
        etMarks = findViewById(R.id.etMarks);
        btnAdd = findViewById(R.id.btnAdd);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);
        btnView = findViewById(R.id.btnView);

        btnAdd.setOnClickListener(v -> addStudent());
        btnUpdate.setOnClickListener(v -> updateStudent());
        btnDelete.setOnClickListener(v -> deleteStudent());
        btnView.setOnClickListener(v -> viewStudents());
    }

    private void addStudent() {
        String roll = etRoll.getText().toString();
        String name = etName.getText().toString();
        String marks = etMarks.getText().toString();

        boolean inserted = dbHelper.addStudent(roll, name, marks);
        Toast.makeText(this, inserted ? "Student Added" : "Error", Toast.LENGTH_SHORT).show();
    }

    private void updateStudent() {
        String roll = etRoll.getText().toString();
        String name = etName.getText().toString();
        String marks = etMarks.getText().toString();

        boolean updated = dbHelper.updateStudent(roll, name, marks);
        Toast.makeText(this, updated ? "Student Updated" : "Not Found", Toast.LENGTH_SHORT).show();
    }

    private void deleteStudent() {
        String roll = etRoll.getText().toString();
        boolean deleted = dbHelper.deleteStudent(roll);
        Toast.makeText(this, deleted ? "Student Deleted" : "Not Found", Toast.LENGTH_SHORT).show();
    }

    private void viewStudents() {
        Cursor cursor = dbHelper.getAllStudents();
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No Students Found", Toast.LENGTH_SHORT).show();
            return;
        }

        btnView.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ViewStudentsActivity.class);
            startActivity(intent);
        });

    }
}
