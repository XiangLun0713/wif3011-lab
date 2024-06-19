package lab9;

import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Q1 {
    public static void main(String[] args) {

        Supplier<Stream<Employee>> streamSupplier = () -> Stream.of(
                new Employee("Poseidon", Gender.M, 23),
                new Employee("Hera", Gender.F, 18),
                new Employee("Apollo", Gender.M, 20),
                new Employee("Athena", Gender.F, 35),
                new Employee("Demeter", Gender.F, 50)
        );

        // solution 1
        streamSupplier.get()
                .filter(employee -> employee.gender == Gender.F && employee.age >= 21)
                .forEach(System.out::println);

        // solution 2
        streamSupplier.get()
                .collect(
                        Collectors.filtering(employee -> employee.gender == Gender.F && employee.age >= 21,
                                Collectors.toList())
                )
                .forEach(System.out::println);
    }
}

class Employee {
    String name;
    Gender gender;
    int age;

    public Employee(String name, Gender gender, int age) {
        this.name = name;
        this.gender = gender;
        this.age = age;
    }

    @Override
    public String toString() {
        return String.format("Name: %s, Gender: %s, Age: %d", this.name, this.gender.toString(), this.age);
    }
}

enum Gender {M, F}