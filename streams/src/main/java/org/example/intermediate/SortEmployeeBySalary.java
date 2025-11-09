package org.example.intermediate;

import org.example.intermediate.dto.Employee;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class SortEmployeeBySalary {
    public static void main(String[] args) {
        List<Employee> employees = Arrays.asList(
                new Employee(101, "John", 50000),
                new Employee(102, "Alice", 70000),
                new Employee(103, "Bob", 45000),
                new Employee(104, "David", 90000)
        );
        List<Employee> sEmployees = employees
                .stream()
                .sorted(Comparator.comparing(Employee::getSalary).reversed())
                .toList();
        System.out.println(sEmployees);
    }
}
