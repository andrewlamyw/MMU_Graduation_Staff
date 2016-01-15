package com.lamyatweng.mmugraduationstaff.Student;

public class Student {
    String name;
    String id;
    String programme;
    String status;
    String email;
    double financialDue;
    double cgpa;
    int balanceCreditHour;
    int muet;

    public Student() {
        // Required by Firebase to deserialize
    }

    public Student(String name, String id, String programme, String status, String email,
                   int balanceCreditHour, double cgpa, int muet, double financialDue) {
        this.name = name;
        this.id = id;
        this.programme = programme;
        this.status = status;
        this.email = email;
        this.balanceCreditHour = balanceCreditHour;
        this.cgpa = cgpa;
        this.muet = muet;
        this.financialDue = financialDue;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getProgramme() {
        return programme;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public int getBalanceCreditHour() {
        return balanceCreditHour;
    }

    public double getCgpa() {
        return cgpa;
    }

    public int getMuet() {
        return muet;
    }

    public double getFinancialDue() {
        return financialDue;
    }
}
