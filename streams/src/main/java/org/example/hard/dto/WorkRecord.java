package org.example.hard.dto;

public class WorkRecord {
    private String name;
    private String dept;

    public WorkRecord(String name, String dept) {
        this.name = name;
        this.dept = dept;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    @Override
    public String toString() {
        return "WorkRecord{" +
                "name='" + name + '\'' +
                ", dept='" + dept + '\'' +
                '}';
    }
}
