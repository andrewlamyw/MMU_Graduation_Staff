package com.lamyatweng.mmugraduationstaff.Programme;

public class Programme {
    String name;
    String faculty;
    String level;

    // Required default constructor for Firebase object mapping
    @SuppressWarnings("unused")
    public Programme() {
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

    @Override
    public boolean equals(Object o) {
        return o instanceof Programme && name.equals(((Programme) o).name)
                && faculty.equals(((Programme) o).faculty)
                && level.equals(((Programme) o).level);
    }
}
