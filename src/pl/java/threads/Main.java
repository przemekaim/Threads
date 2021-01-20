package pl.java.threads;

public class Main {
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

                }
            } catch (InterruptedException e) {
                e.printStackTrace();
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
                e.printStackTrace();
            }
        };

       // new Thread(r1).start();
       // new Thread(r2).start();
       Thread t1 = new Thread(r1);
        Thread t2 = new Thread(r2);
        t1.start();
        t2.start();

        while(t1.isAlive() && t2.isAlive()) {

        }
        bank.getBalance();
    }
}
