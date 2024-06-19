package lab8;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.function.Predicate;

public class Q3 {
    public static void main(String[] args) {
        FastReader fastReader = new FastReader();
        Predicate<Integer> isPerfectSquare = (num) -> Math.sqrt(num) * Math.sqrt(num) == num;

        System.out.print("Enter your number to check: ");
        int num = fastReader.nextInt();

        if (isPerfectSquare.test(num)) {
            System.out.format("The number %d is a perfect square\n", num);
        } else {
            System.out.format("The number %d is not a perfect square\n", num);
        }
    }

    static class FastReader {
        BufferedReader br;
        StringTokenizer st;

        public FastReader() {
            br = new BufferedReader(
                    new InputStreamReader(System.in));
        }

        String next() {
            while (st == null || !st.hasMoreElements()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }

        int nextInt() {
            return Integer.parseInt(next());
        }
    }
}

