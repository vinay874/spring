package org.example.hard;



import org.example.hard.dto.SalaryRange;
import org.example.intermediate.dto.Employee;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SalaryRangeGrouping {
    public static void main(String[] args) {
        List<Employee> employees = Arrays.asList(
                new Employee("Alice", 45000),
                new Employee("Bob", 7000),
                new Employee("Charlie",120000),
                new Employee("David", 99000),
                new Employee("Eva", 30000),
                new Employee("Frank", 105000)
        );

        Map<SalaryRange, List<Employee>> salaryRanges = employees.stream()
                .collect(Collectors.groupingBy(
                        e -> {
                            if (e.getSalary() < 50000) return SalaryRange.LOW;
                            else if (e.getSalary() >= 50000 && e.getSalary() < 100000) return SalaryRange.MEDIUM;
                            else return SalaryRange.HIGH;
                        }
                ));

        System.out.println(salaryRanges);
    }
}
