package pl.java.threads;

import java.util.Arrays;
import java.util.concurrent.locks.ReentrantLock;

public class Bank2Synch {
    private final double[] accounts;

    public Bank2Synch(int amount, double initial) {
        accounts = new double[amount];
        Arrays.fill(accounts, initial);
    }


    public void transfer(int from, int to, double amount) {
        if (accounts[from] < amount) return;
        System.out.print(Thread.currentThread());
        accounts[from] -= amount;
        System.out.printf(" %10.2f z %d na %d", amount, from, to);
        accounts[to] += amount;
        System.out.printf(" Sadlo calkowite %10.2f%n", getTotalBalance());

    }

    public double getTotalBalance() {
        double amount = 0;
        for (double money : accounts) {
            amount += money;
        }
        return amount;
    }

    public int size() {
        return accounts.length;
    }

    public void getBalance() {
        for (int i = 0; i < accounts.length; i++)
            System.out.println("Ilosc pieniedzy na " + (i + 1) + " koncie: " + accounts[i]);
    }
}
