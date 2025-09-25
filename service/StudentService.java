package edu.ccrm.service;

import edu.ccrm.domain.Student;
import edu.ccrm.exception.DuplicateEnrollmentException;
import java.util.*;

public class StudentService {
    private final Map<String, Student> students = new HashMap<>();

    public void addStudent(Student s) throws DuplicateEnrollmentException {
        if (students.containsKey(s.getRegNo())) {
            throw new DuplicateEnrollmentException("Student already exists with RegNo: " + s.getRegNo());
        }
        students.put(s.getRegNo(), s);
    }

    public Student getStudent(String regNo) {
        return students.get(regNo);
    }

    public List<Student> listStudents() {
        return new ArrayList<>(students.values());
    }

    public void deactivateStudent(String regNo) {
        Student s = students.get(regNo);
        if (s != null) {
            s.deactivate();
        }
    }
}
