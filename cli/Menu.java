package edu.ccrm.cli;

import edu.ccrm.service.StudentService;
import edu.ccrm.service.CourseService;
import edu.ccrm.domain.Student;
import edu.ccrm.domain.Name;
import edu.ccrm.domain.Course;
import edu.ccrm.domain.Semester;
import edu.ccrm.domain.Grade;
import edu.ccrm.exception.DuplicateEnrollmentException;

import java.util.Scanner;
import java.util.List;
import java.nio.file.*;
import java.io.IOException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Menu {
    private final Scanner sc = new Scanner(System.in);
    private final StudentService studentService = new StudentService();
    private final CourseService courseService = new CourseService();

    public void start() {
        outer: while (true) {
            System.out.println("\nCampus Course Records Manager");
            System.out.println("1) Manage Students");
            System.out.println("2) Manage Courses");
            System.out.println("3) Enroll Student");
            System.out.println("4) Record Grade");
            System.out.println("5) List Students");
            System.out.println("6) List Courses");
            System.out.println("7) Import Students from CSV");
            System.out.println("8) Import Courses from CSV");
            System.out.println("9) Export Students & Courses");
            System.out.println("10) Backup Exported Files");
            System.out.println("0) Exit");
            System.out.print("Choose option: ");

            String choice = sc.nextLine();
            switch (choice) {
                case "1": manageStudents(); break;
                case "2": manageCourses(); break;
                case "3": enrollStudent(); break;
                case "4": recordGrade(); break;
                case "5": listStudents(); break;
                case "6": listCourses(); break;
                case "7": importStudentsCSV(); break;
                case "8": importCoursesCSV(); break;
                case "9": exportData(); break;
                case "10": backupData(); break;
                case "0": 
                    System.out.println("Goodbye."); 
                    break outer;
                default: 
                    System.out.println("Invalid choice.");
            }
        }
    }

    // =================== Student Management ===================
    private void manageStudents() {
        try {
            System.out.print("Enter Student ID: ");
            String id = sc.nextLine();

            System.out.print("Enter Registration No: ");
            String regNo = sc.nextLine();

            System.out.print("Enter full name (First Last): ");
            String[] nameParts = sc.nextLine().split(" ", 2);
            if (nameParts.length < 2) {
                System.out.println("Please enter first and last name.");
                return;
            }
            Name name = new Name(nameParts[0], nameParts[1]);

            System.out.print("Enter Email: ");
            String email = sc.nextLine();

            Student student = new Student(id, name, email, regNo);
            studentService.addStudent(student);
            System.out.println("Student added: " + student);
        } catch (DuplicateEnrollmentException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    // =================== Course Management ===================
    private void manageCourses() {
        try {
            System.out.print("Enter Course Code: ");
            String code = sc.nextLine();

            System.out.print("Enter Course Title: ");
            String title = sc.nextLine();

            System.out.print("Enter Credits (integer): ");
            int credits = Integer.parseInt(sc.nextLine());

            System.out.print("Enter Instructor Name (First Last): ");
            String[] instrNameParts = sc.nextLine().split(" ", 2);
            Name instructorName = new Name(instrNameParts[0], instrNameParts[1]);
            var instructor = new edu.ccrm.domain.Instructor("I" + code, instructorName, "instructor@example.com", "EID" + code);

            System.out.print("Enter Semester (SPRING, SUMMER, FALL): ");
            Semester semester = Semester.valueOf(sc.nextLine().toUpperCase());

            System.out.print("Enter Department: ");
            String dept = sc.nextLine();

            Course course = new Course.Builder()
                    .code(code)
                    .title(title)
                    .credits(credits)
                    .instructor(instructor)
                    .semester(semester)
                    .department(dept)
                    .build();

            courseService.addCourse(course);
            System.out.println("Course added: " + course);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // =================== Enrollment ===================
    private void enrollStudent() {
        System.out.print("Enter Student RegNo: ");
        String regNo = sc.nextLine();
        Student student = studentService.getStudent(regNo);
        if (student == null) {
            System.out.println("Student not found.");
            return;
        }

        System.out.print("Enter Course Code to enroll: ");
        String courseCode = sc.nextLine();
        Course course = courseService.getCourse(courseCode);
        if (course == null) {
            System.out.println("Course not found.");
            return;
        }

        student.enrollCourse(courseCode);
        System.out.println("Student enrolled: " + regNo + " in course " + courseCode);
    }

    // =================== Grading ===================
    private void recordGrade() {
        System.out.print("Enter Student RegNo: ");
        String regNo = sc.nextLine();
        Student student = studentService.getStudent(regNo);
        if (student == null) {
            System.out.println("Student not found.");
            return;
        }

        System.out.print("Enter Course Code: ");
        String courseCode = sc.nextLine();
        if (!student.getEnrolledCourses().contains(courseCode)) {
            System.out.println("Student is not enrolled in that course.");
            return;
        }

        System.out.print("Enter Grade (S10, A9, B8, C7, D6, E5, F0): ");
        String gradeVal = sc.nextLine().toUpperCase();
        try {
            Grade grade = Grade.valueOf(gradeVal);
            student.setGrade(courseCode, grade);
            System.out.println("Grade recorded: " + grade + " for course " + courseCode);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid grade entered.");
        }
    }

    // =================== List ===================
    private void listStudents() {
        List<Student> students = studentService.listStudents();
        if (students.isEmpty()) {
            System.out.println("No students found.");
            return;
        }
        System.out.println("Students:");
        students.forEach(System.out::println);
    }

    private void listCourses() {
        List<Course> courses = courseService.listCourses();
        if (courses.isEmpty()) {
            System.out.println("No courses found.");
            return;
        }
        System.out.println("Courses:");
        courses.forEach(System.out::println);
    }

    // =================== Import/Export ===================
    private void importStudentsCSV() {
        try (Stream<String> lines = Files.lines(Paths.get("test-data/students.csv"))) {
            List<Student> students = lines.skip(1)
                .map(line -> line.split(","))
                .map(parts -> {
                    String[] fullName = parts[2].split(" ", 2);
                    Name name = new Name(fullName[0], fullName.length > 1 ? fullName[1] : "");
                    return new Student(parts[0], name, parts[3], parts[1]);
                })
                .collect(Collectors.toList());

            students.forEach(s -> {
                try {
                    studentService.addStudent(s);
                } catch (DuplicateEnrollmentException e) {
                    System.out.println("Failed to add student " + s.getFullName() + ": " + e.getMessage());
                }
            });

            System.out.println("Imported " + students.size() + " students.");
        } catch (IOException e) {
            System.out.println("Error importing students: " + e.getMessage());
        }
    }

    private void importCoursesCSV() {
        try (Stream<String> lines = Files.lines(Paths.get("test-data/courses.csv"))) {
            List<Course> courses = lines.skip(1)
                .map(line -> line.split(","))
                .map(parts -> new Course.Builder()
                        .code(parts[0])
                        .title(parts[1])
                        .credits(Integer.parseInt(parts[2]))
                        .instructor(new edu.ccrm.domain.Instructor(
                                "I" + parts[0],
                                new Name(parts[3].split(" ")[0], parts[3].split(" ").length > 1 ? parts[3].split(" ")[1] : ""),
                                "instructor@example.com",
                                "EID" + parts[0]))
                        .semester(Semester.valueOf(parts[4].toUpperCase()))
                        .department(parts[5])
                        .build())
                .collect(Collectors.toList());

            courses.forEach(c -> {
                try {
                    courseService.addCourse(c);
                } catch (Exception e) { // replace with the specific exception type if known
                    System.out.println("Failed to add course " + c.getCode() + ": " + e.getMessage());
                }
            });

            System.out.println("Imported " + courses.size() + " courses.");
        } catch (IOException e) {
            System.out.println("Error importing courses: " + e.getMessage());
        }
    }

    private void exportData() {
        try {
            Files.createDirectories(Paths.get("test-data/export"));

            // Export students
            List<String> studentLines = studentService.listStudents().stream()
                .map(s -> s.getId() + "," + s.getRegNo() + "," + s.getFullName() + "," + s.getEmail() + "," + s.getStatus())
                .collect(Collectors.toList());
            studentLines.add(0, "id,regNo,fullName,email,status");
            Files.write(Paths.get("export/students_export.csv"), studentLines);

            // Export courses
            List<String> courseLines = courseService.listCourses().stream()
                .map(c -> c.getCode() + "," + c.getTitle() + "," + c.getCredits() + "," + c.getInstructor().getFullName() + "," + c.getSemester() + "," + c.getDepartment())
                .collect(Collectors.toList());
            courseLines.add(0, "code,title,credits,instructor,semester,department");
            Files.write(Paths.get("export/courses_export.csv"), courseLines);

            System.out.println("Data exported successfully.");
        } catch (IOException e) {
            System.out.println("Error exporting data: " + e.getMessage());
        }
    }

    private void backupData() {
        try {
            Path exportDir = Paths.get("test-data/export");
            if (!Files.exists(exportDir)) {
                System.out.println("Nothing to backup. Export first.");
                return;
            }

            Path backupDir = Paths.get("backup_" + System.currentTimeMillis());
            Files.createDirectories(backupDir);

            try (Stream<Path> paths = Files.walk(exportDir)) {
                paths.filter(Files::isRegularFile).forEach(file -> {
                    try {
                        Files.copy(file, backupDir.resolve(file.getFileName()), StandardCopyOption.REPLACE_EXISTING);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }

            System.out.println("Backup completed at " + backupDir.toAbsolutePath());
        } catch (IOException e) {
            System.out.println("Error during backup: " + e.getMessage());
        }
    }
}
