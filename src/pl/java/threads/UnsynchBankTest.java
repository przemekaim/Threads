package pl.java.threads;

public class UnsynchBankTest {
    public static final int NACCOUNTS = 100;
    public static final double INITIAL_BALANCE = 1000;
    public static final double MAX_AMOUNT = 100;
    public static final int DELAY = 10;

    public static void main(String[] args) {
        var bank = new Bank2Synch(NACCOUNTS, INITIAL_BALANCE);
        for (int i = 0; i < NACCOUNTS; i ++) {
            int fromAccount = i;
            Runnable r = () -> {
                try {
                    while (true) {
                        int toAccount = (int) (Math.random() * bank.size());
                        double amount = MAX_AMOUNT * Math.random();
                        bank.transfer(fromAccount, toAccount, amount);
                        Thread.sleep((int) (DELAY * Math.random()));
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            };
            Thread t = new Thread(r);
            t.start();
        }

    }
}
