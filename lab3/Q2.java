package lab3;

import java.util.Random;

public class Q2 {
    public static void main(String[] args) {
        Random random = new Random();
        int amount = random.nextInt(1000);
        BankAccount bankAccount = new BankAccount(amount);
        for (int i = 0; i < 1_000_00; i++) {
            int rand = random.nextInt(1000);
            amount += rand;
            new Thread(() -> {
                bankAccount.deposit(rand);
            }).start();
        }
        System.out.format("Expected value: %d\n", amount);
        System.out.format("Actual value: %d\n", bankAccount.getBalance());
    }
}

class BankAccount {
    private int amount;

    public BankAccount(int amount) {
        this.amount = amount;
    }

    public synchronized void deposit(int amount) {
        this.amount += amount;
    }

    public synchronized void withdrawal(int amount) {
        if (amount > this.amount) {
            System.out.println("Insufficient money, can't withdraw.");
            return;
        }
        this.amount -= amount;
    }

    public int getBalance() {
        return amount;
    }
}
