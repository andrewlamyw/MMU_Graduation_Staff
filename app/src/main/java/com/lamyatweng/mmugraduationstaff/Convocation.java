package com.lamyatweng.mmugraduationstaff;

public class Convocation {
    String year;
    String openRegistrationDate;
    String closeRegistrationDate;

    public Convocation() {
        // Required by Firebase to deserialize
    }

    public Convocation(String year, String openRegistrationDate, String closeRegistrationDate) {
        this.year = year;
        this.openRegistrationDate = openRegistrationDate;
        this.closeRegistrationDate = closeRegistrationDate;
    }

    public String getYear() {
        return year;
    }

    public String getOpenRegistrationDate() {
        return openRegistrationDate;
    }

    public String getCloseRegistrationDate() {
        return closeRegistrationDate;
    }
}
