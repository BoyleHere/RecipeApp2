package com.example.lab7_lms;

public class Student {
    private String rollNumber, name, marks;

    public Student(String rollNumber, String name, String marks) {
        this.rollNumber = rollNumber;
        this.name = name;
        this.marks = marks;
    }

    public String getRollNumber() {
        return rollNumber;
    }

    public String getName() {
        return name;
    }

    public String getMarks() {
        return marks;
    }
}
