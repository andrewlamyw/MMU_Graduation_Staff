package com.lamyatweng.mmugraduationstaff.Seat;

public class Seat {

    String id;
    String row;
    String column;
    String status;
    String sessionID;
    String studentID;

    public Seat() {
    }

    public Seat(String id, String row, String column, String status, String sessionID, String studentID) {
        this.id = id;
        this.row = row;
        this.column = column;
        this.status = status;
        this.sessionID = sessionID;
        this.studentID = studentID;
    }

    public String getId() {
        return id;
    }

    public String getRow() {
        return row;
    }

    public String getColumn() {
        return column;
    }

    public String getStatus() {
        return status;
    }

    public String getSessionID() {
        return sessionID;
    }

    public String getStudentID() {
        return studentID;
    }
}
