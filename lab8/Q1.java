package lab8;

public class Q1 {
    public static void main(String[] args) {
        MathOperation addition = (num1, num2) -> num1 + num2;
        MathOperation subtraction = (num1, num2) -> num1 - num2;
        MathOperation multiplication = (num1, num2) -> num1 * num2;
        MathOperation division = (num1, num2) -> num1 / num2;

        int num1 = 10, num2 = 5;
        System.out.format("%d + %d = %d\n", num1, num2, addition.operation(num1, num2));
        System.out.format("%d - %d = %d\n", num1, num2, subtraction.operation(num1, num2));
        System.out.format("%d * %d = %d\n", num1, num2, multiplication.operation(num1, num2));
        System.out.format("%d / %d = %d\n", num1, num2, division.operation(num1, num2));
    }
}

@FunctionalInterface
interface MathOperation {
    int operation(int num1, int num2);
}
