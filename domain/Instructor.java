package edu.ccrm.domain;

public class Instructor extends Person {
    private final String employeeId;

    public Instructor(String id, Name fullName, String email, String employeeId) {
        super(id, fullName, email);
        this.employeeId = employeeId;
    }

    public String getEmployeeId() { return employeeId; }

    @Override
    public void printProfile() {
        System.out.println("Instructor ID: " + id);
        System.out.println("Name: " + fullName);
        System.out.println("Email: " + email);
        System.out.println("Employee ID: " + employeeId);
        System.out.println("Account Created At: " + createdAt);
    }

    @Override
    public String toString() {
        return fullName + " (Employee ID:" + employeeId + ")";
    }
}
