package lab8;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.function.Predicate;

public class Q4 {
    public static void main(String[] args) {
        FastReader fastReader = new FastReader();
        Predicate<String> isPalindrome = (string) -> new StringBuilder(string).reverse().toString().equals(string);

        System.out.print("Enter your string to check: ");
        String s = fastReader.nextLine();

        if (isPalindrome.test(s)) {
            System.out.format("The string %s is a palindrome\n", s);
        } else {
            System.out.format("The string %s is not a palindrome\n", s);
        }

    }

    static class FastReader {
        BufferedReader br;

        public FastReader() {
            br = new BufferedReader(
                    new InputStreamReader(System.in));
        }

        String nextLine() {
            String str = "";
            try {
                str = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return str;
        }
    }
}
