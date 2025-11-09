package org.example.intermediate;

import org.example.intermediate.dto.Employee;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GroupByDepartment {
    public static void main(String[] args) {
        List<Employee> employeeList = Arrays.asList(
                new Employee(1,"John","HR",50000),
                new Employee(2,"Jane","IT",70000),
                new Employee(3,"Mike","IT",80000),
                new Employee(4,"Sara","Finance",60000),
                new Employee(5,"John","HR",55000)

        );

        Map<String, Double> result = employeeList
                .stream()
                .collect(Collectors.groupingBy(Employee::getDepartment, Collectors.averagingInt(Employee::getSalary)));

        System.out.println(result);
    }
}
