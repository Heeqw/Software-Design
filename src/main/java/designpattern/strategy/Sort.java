package designpattern.strategy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

class Employee {

    private String name;
    private int age;

    public Employee(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public static Employee of(String name, int age) {
        return new Employee(name, age);
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

}

public class Sort {

    public static void main(String[] args) {

        List<Employee> employees = new ArrayList<Employee>(List.of(
                Employee.of("Jack", 35),
                Employee.of("Danny", 25),
                Employee.of("Carter", 45)));
        // sort by age
        Collections.sort(employees, (o1, o2) -> o1.getAge() - o2.getAge());


        System.out.println(employees);

        // sort by name
        System.out.println(employees);

    }

}
