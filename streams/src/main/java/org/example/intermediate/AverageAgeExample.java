package org.example.intermediate;

import org.example.intermediate.dto.Person;

import java.util.Arrays;
import java.util.List;
import java.util.OptionalDouble;

public class AverageAgeExample {
    public static void main(String[] args) {

        List<Person> persons = Arrays.asList(
                new Person("Alice", 25),
                new Person("Bob", 30),
                new Person("Charlie", 28),
                new Person("David", 35)
        );
        double average = persons.stream().mapToInt(Person::getAge).average().orElse(0.0);
        System.out.println(average);
    }
}
