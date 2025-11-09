package org.example.hard;

import org.example.hard.dto.WorkRecord;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class EmployeeWorked {
    public static void main(String[] args) {
        List<WorkRecord> workRecords = Arrays.asList(
                new WorkRecord("John", "IT"),
                new WorkRecord("John", "HR"),
                new WorkRecord("John", "FINANCE"),
                new WorkRecord("JANE", "IT"),
                new WorkRecord("PAUL", "OPERATIONS"),
                new WorkRecord("MARS", "IT"),
                new WorkRecord("CRISTIANO", "HR"),
                new WorkRecord("MERSSI", "IT"),
                new WorkRecord("VIRSU", "IT"),
                new WorkRecord("VIRSU", "FINANCE"),
                new WorkRecord("VIRSU", "HR"),
                new WorkRecord("VIRSU", "OPERATIONS")

        );
        List<String> emps = workRecords.stream()
                .collect(Collectors.groupingBy(WorkRecord::getName, Collectors.counting()))
                .entrySet().stream()
                .filter(e -> e.getValue() >= 3)
                .map(e-> String.valueOf(e.getKey()))
                .toList();
        System.out.println(emps);

    }
}
