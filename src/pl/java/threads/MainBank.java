package pl.java.threads;

public class MainBank {
    public static final int DELAY = 10;
    public static final double MAX_AMOUNT = 1000;
    public static final int STEPS = 10;

    public static void main(String[] args) {
        Bank bank = new Bank(4, 100000);

        Runnable r1 = () -> {
            try {
                for (int i = 0; i < STEPS; i++) {
                    double amount = MAX_AMOUNT * Math.random();
                    bank.transfer(0, 1, amount);
                    Thread.sleep((int) (DELAY * Math.random()));
                    if (i == 5 && !Thread.currentThread().isInterrupted()) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        };

        Runnable r2 = () -> {
            try {
                for (int i = 0; i < STEPS; i++) {
                    double amount = MAX_AMOUNT * Math.random();
                    bank.transfer(2, 3, amount);
                    Thread.sleep((int) (DELAY * Math.random()));
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        };

        Thread t1 = new Thread(r1);
        t1.setName("Transfer 0 -> 1");
        t1.start();
        //new Thread(r1).start();
        new Thread(r2).start();
    }
}
