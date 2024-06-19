package lab4;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Node<Integer> node = new Node<>();
        new Thread(new Operate(node, 2)).start();
        new Thread(new Write(node)).start();
    }
}
