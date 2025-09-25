package edu.ccrm.domain;

import java.util.*;

public class Student extends Person {
    private final String regNo;
    private Status status;
    private final List<String> enrolledCourses = new ArrayList<>();
    private final Map<String, Grade> grades = new HashMap<>();

    public static final int MAX_CREDITS = 18;

    public Student(String id, Name fullName, String email, String regNo) {
        super(id, fullName, email);
        this.regNo = regNo;
        this.status = Status.ACTIVE;
    }

    public String getRegNo() { return regNo; }

    public List<String> getEnrolledCourses() { return Collections.unmodifiableList(enrolledCourses); }

    public Map<String, Grade> getGrades() { return Collections.unmodifiableMap(grades); }

    public Status getStatus() { return status; }

    public void setStatus(Status status) { this.status = status; }

    public void enrollCourse(String courseCode) {
        if (!enrolledCourses.contains(courseCode)) {
            enrolledCourses.add(courseCode);
        }
    }

    public void unenrollCourse(String courseCode) {
        enrolledCourses.remove(courseCode);
        grades.remove(courseCode);
    }

    public void setGrade(String courseCode, Grade grade) {
        if (enrolledCourses.contains(courseCode)) {
            grades.put(courseCode, grade);
        }
    }

    public Grade getGrade(String courseCode) {
        return grades.get(courseCode);
    }

    @Override
    public void printProfile() {
        System.out.println("Student ID: " + id);
        System.out.println("Name: " + fullName);
        System.out.println("Email: " + email);
        System.out.println("Registration No: " + regNo);
        System.out.println("Status: " + status);
        System.out.println("Enrolled Courses: " + enrolledCourses);
        System.out.println("Grades: " + grades);
        System.out.println("Account Created At: " + createdAt);
    }

    @Override
    public String toString() {
        return fullName + " (" + regNo + ")";
    }
}
