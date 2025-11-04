package org.example.springcore;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class Student {
    private int id;
    private String name;
    private List<String> subjects;
    private Set<String> skills;
    private Map<String, Integer> marks;

    public Student() {
        System.out.println("Default Constructor Called");
    }

    public Student(int id, String name) {
        this.id = id;
        this.name = name;
        System.out.println("Constructor Injection Called");
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setSubjects(List<String> subjects) {
        this.subjects = subjects;
    }
    public void setSkills(Set<String> skills) {
        this.skills = skills;
    }
    public void setMarks(Map<String, Integer> marks) {
        this.marks = marks;
    }

    public void displayInfo() {
        System.out.println("ID: " + id);
        System.out.println("Name: " + name);
        System.out.println("Subjects: " + subjects);
        System.out.println("Skills: " + skills);
        System.out.println("Marks: " + marks);
    }
}
