package pl.java.threads;

import java.util.Arrays;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Bank2Synch {
    private final double[] accounts;
    private final Lock bankLock;
    private final Condition sufficientFunds;

    public Bank2Synch(int amount, double initial) {
        accounts = new double[amount];
        Arrays.fill(accounts, initial);
        bankLock = new ReentrantLock();
        sufficientFunds = bankLock.newCondition();
    }


    public void transfer(int from, int to, double amount) throws InterruptedException {
        bankLock.lock();
        try {
            while (accounts[from] < amount)
                sufficientFunds.await();
            System.out.print(Thread.currentThread());
            accounts[from] -= amount;
            System.out.printf(" %10.2f z %d na %d", amount, from, to);
            accounts[to] += amount;
            System.out.printf(" Sadlo calkowite %10.2f ", getTotalBalance());
            System.out.printf(" Saldo z %10.2f %n", getBalance(from));
            sufficientFunds.signalAll(); // Wazne -> bez tego watki sie zakleszczaja (deadlock) i nie zostaja juz nigdy uruchomione
        } finally {
            bankLock.unlock();
        }
    }

    public double getTotalBalance() {
        bankLock.lock();
        try {
            double amount = 0;
            for (double money : accounts) {
                amount += money;
            }
            return amount;
        } finally {
            bankLock.unlock();
        }
    }

    public int size() {
        return accounts.length;
    }

    public double getBalance(int i) {
        return accounts[i];
    }
}


class Bank2Synchronized {
    private final double[] accounts;


    public Bank2Synchronized(int amount, double initial) {
        accounts = new double[amount];
        Arrays.fill(accounts, initial);
    }


    public synchronized void transfer(int from, int to, double amount) throws InterruptedException {
        while (accounts[from] < amount)
            wait();

        System.out.print(Thread.currentThread());
        accounts[from] -= amount;
        System.out.printf(" %10.2f z %d na %d", amount, from, to);
        accounts[to] += amount;
        System.out.printf(" Sadlo calkowite %10.2f ", getTotalBalance());
        System.out.printf(" Saldo z %10.2f %n", getBalance(from));
        notifyAll(); // wazne
    }

    public synchronized double getTotalBalance() {
        double amount = 0;
        for (double money : accounts) {
            amount += money;
        }
        return amount;
    }

    public int size() {
        return accounts.length;
    }

    public double getBalance(int i) {
        return accounts[i];
    }
}