package org.example;

import org.example.springcore.Student;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        System.out.println("----Setter Injection-----");
        Student setterStudent = (Student) applicationContext.getBean("studentSetter");
        setterStudent.displayInfo();
        System.out.println("----constructor Injection-----");
        Student consutorStudent = (Student) applicationContext.getBean("studentConstructor");
        consutorStudent.displayInfo();
    }
}