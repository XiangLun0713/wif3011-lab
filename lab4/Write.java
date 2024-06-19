package lab4;

import java.util.Random;

public class Write implements Runnable {
    Node<Integer> node;

    public Write(Node<Integer> node) {
        this.node = node;
    }

    @Override
    public void run() {
        Random random = new Random();
        while (true) {
            int rand = random.nextInt(0, 5);
            node.setValue(rand);
        }
    }
}
