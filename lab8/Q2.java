package lab8;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Q2 {
    public static void main(String[] args) {
        // populate the list
        List<Integer> list = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            list.add(i);
        }

        System.out.println("Print all numbers:");
        evaluate(list, (num) -> true);

        System.out.println("Print odd numbers:");
        evaluate(list, (num) -> num % 2 != 0);

        System.out.println("Print even numbers:");
        evaluate(list, (num) -> num % 2 == 0);

        System.out.println("Print numbers greater than 5:");
        evaluate(list, (num) -> num > 5);
    }

    public static void evaluate(List<Integer> list, Predicate<Integer> predicate) {
        List<Integer> filteredList = list.stream().filter(predicate).toList();
        for (int num : filteredList) {
            // print out num that passes the evaluation
            System.out.println(num);
        }
    }
}
