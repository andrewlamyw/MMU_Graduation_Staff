package com.lamyatweng.mmugraduationstaff.Programme;

public class Programme {
    String name;
    String faculty;
    String level;

    public Programme() {
        // empty default constructor, necessary for Firebase to be able to deserialize
    }

    public Programme(String name, String level, String faculty) {
        this.name = name;
        this.level = level;
        this.faculty = faculty;
    }

    public String getLevel() {
        return level;
    }

    public String getName() {
        return name;
    }

    public String getFaculty() {
        return faculty;
    }
}
