package com.example.lab7_lms;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EditStudentActivity extends AppCompatActivity {

    private EditText etRoll, etName, etMarks;
    private Button btnSave;
    private DatabaseHelper dbHelper;
    private String rollNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_student);

        etRoll = findViewById(R.id.etRoll);
        etName = findViewById(R.id.etName);
        etMarks = findViewById(R.id.etMarks);
//        btnSave = findViewById(R.id.btnSave);

        dbHelper = new DatabaseHelper(this);

        // Get data from intent
        Intent intent = getIntent();
        rollNumber = intent.getStringExtra("roll_number");
        etRoll.setText(rollNumber);
        etRoll.setEnabled(false); // Roll number should not be editable
        etName.setText(intent.getStringExtra("name"));
        etMarks.setText(intent.getStringExtra("marks"));

        btnSave.setOnClickListener(v -> {
            String newName = etName.getText().toString();
            String newMarks = etMarks.getText().toString();

            if (dbHelper.updateStudent(rollNumber, newName, newMarks)) {
                Toast.makeText(EditStudentActivity.this, "Updated Successfully!", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(EditStudentActivity.this, "Update Failed!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
