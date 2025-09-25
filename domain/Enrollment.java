package edu.ccrm.domain;

import java.time.LocalDate;

public class Enrollment {
    private final String studentId;
    private final String courseCode;
    private final Semester semester;
    private LocalDate enrolledDate;

    public Enrollment(String studentId, String courseCode, Semester semester) {
        this.studentId = studentId;
        this.courseCode = courseCode;
        this.semester = semester;
        this.enrolledDate = LocalDate.now();
    }

    public String getStudentId() { return studentId; }
    public String getCourseCode() { return courseCode; }
    public Semester getSemester() { return semester; }
    public LocalDate getEnrolledDate() { return enrolledDate; }

    @Override
    public String toString() {
        return "Student: " + studentId + ", Course: " + courseCode + ", Semester: " + semester;
    }
}
