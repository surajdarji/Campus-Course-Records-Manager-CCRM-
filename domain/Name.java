package edu.ccrm.domain;

public final class Name {
    private final String first;
    private final String last;

    public Name(String first, String last) {
        if (first == null || last == null) throw new IllegalArgumentException("Names cannot be null");
        this.first = first;
        this.last = last;
    }

    public String getFirst() { return first; }

    public String getLast() { return last; }

    @Override
    public String toString() {
        return first + " " + last;
    }
}
