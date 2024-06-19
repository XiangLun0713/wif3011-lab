package lab4;

public class Operate implements Runnable {
    Node<Integer> node;
    final int target;
    Dummy dummy = new Dummy();

    public Operate(Node<Integer> node, int target) {
        this.node = node;
        this.target = target;
    }

    @Override
    public void run() {
        int count = 0;
        while(true) {
            node.executeOnValue(target, dummy);
            count++;
            if (count == 2) System.exit(0);
        }
    }
}
