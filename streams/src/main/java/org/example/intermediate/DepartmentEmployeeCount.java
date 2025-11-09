package org.example.intermediate;

import org.example.intermediate.dto.Employee;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class DepartmentEmployeeCount {
    public static void main(String[] args) {
        List<Employee> employeeList = Arrays.asList(
                new Employee(1, "Jhon","HR",50000),
                new Employee(2, "Jane","IT",70000),
                new Employee(3, "Mike","IT",80000),
                new Employee(4, "Sara","Finance",60000),
                new Employee(5, "Paul","HR",55000),
                new Employee(6, "Eve","IT",80000),
                new Employee(7, "Frank","Finance",64000),
                new Employee(8, "Ala","Operations",44000)

        );

        List<String> dept = employeeList.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment, Collectors.counting()))
                .entrySet()
                .stream().
                filter(e -> e.getValue() > 2)
                .map(Map.Entry::getKey).toList();
        System.out.println(dept);
    }
}
