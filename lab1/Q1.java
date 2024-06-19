package lab1;

public class Q1 {
    public static void main(String[] args) {
        new Thread(new PrintCharQ1('A', 10)).start();
        new Thread(new PrintCharQ1('B', 10)).start();
        new Thread(new PrintNumQ1(41)).start();
    }
}

class PrintCharQ1 implements Runnable {
    private final char c;
    private final int time;

    public PrintCharQ1(char c, int time) {
        this.c = c;
        this.time = time;
    }

    @Override
    public void run() {
        for (int i = 0; i < time; i++) {
            System.out.println(c);
        }
    }
}

class PrintNumQ1 implements Runnable {
    private final int num;

    public PrintNumQ1(int num) {
        this.num = num;
    }

    @Override
    public void run() {
        for (int i = 1; i <= num; i++) {
            System.out.println(i);
        }
    }
}
