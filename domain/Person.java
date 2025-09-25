package edu.ccrm.domain;

import java.time.LocalDate;

public abstract class Person {
    protected final String id;
    protected final Name fullName;
    protected String email;
    protected boolean active;
    protected final LocalDate createdAt;

    public Person(String id, Name fullName, String email) {
        if (id == null || id.isEmpty())
            throw new IllegalArgumentException("Id cannot be null or empty");
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.active = true;
        this.createdAt = LocalDate.now();
    }

    public String getId() { return id; }

    public Name getFullName() { return fullName; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public boolean isActive() { return active; }

    public void deactivate() { this.active = false; }

    public LocalDate getCreatedAt() { return createdAt; }

    // must be implemented by subclasses
    public abstract void printProfile();
}
