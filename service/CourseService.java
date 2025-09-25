package edu.ccrm.service;

import java.util.*;
import edu.ccrm.domain.Course;

public class CourseService {
    private final Map<String, Course> courseMap = new HashMap<>();

    public void addCourse(Course c) throws Exception {
        if (courseMap.containsKey(c.getCode())) throw new Exception("Course code exists");
        courseMap.put(c.getCode(), c);
    }

    public Course getCourse(String code) { 
        return courseMap.get(code); 
    }

    public void deactivateCourse(String code) { 
        courseMap.remove(code); 
    }

    // Returns all courses
    public List<Course> getAllCourses() { 
        return new ArrayList<>(courseMap.values()); 
    }

    // Streams-based filter
    public List<Course> filterByInstructor(String name) {
        return courseMap.values().stream()
            .filter(c -> c.toString().contains(name))
            .toList();
    }

    // **Added to fix the Menu.java error**
    public List<Course> listCourses() {
        return getAllCourses();
    }
}
