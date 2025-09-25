package edu.ccrm.domain;

public enum Grade {
    S10(10), A9(9), B8(8), C7(7), D6(6), E5(5), F0(0);

    private final int points;

    Grade(int points) {
        this.points = points;
    }

    public int getPoints() { return points; }
}
