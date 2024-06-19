package lab3;

public class Q1 {
    public static void main(String[] args) {
        Room room = new Room();

        // simulate 4 cleaners want to enter
        for (int i = 0; i < 4; i++) {
            new Thread(() -> {
                room.addCleaner();
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                room.removeCleaner();
            }).start();
        }

        // simulate 10 guests want to enter
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                room.addGuest();
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                room.removeGuest();
            }).start();
        }
    }
}

class Room {
    private int numOfGuests = 0;
    private int numOfCleaners = 0;

    public synchronized void addGuest() {
        while (numOfGuests >= 6 || numOfCleaners > 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        numOfGuests++;
        System.out.format("Guest entered, now got %d guest(s)\n", numOfGuests);
    }

    public synchronized void removeGuest() {
        numOfGuests--;
        System.out.format("Guest leaved, now got %d guest(s)\n", numOfGuests);
        notifyAll();
    }

    public synchronized void addCleaner() {
        while (numOfCleaners >= 1 || numOfGuests > 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        numOfCleaners++;
        System.out.format("Cleaner entered, now got %d cleaner(s)\n", numOfCleaners);
    }

    public synchronized void removeCleaner() {
        numOfCleaners--;
        System.out.format("Cleaner leaved, now got %d cleaner(s)\n", numOfCleaners);
        notifyAll();
    }
}