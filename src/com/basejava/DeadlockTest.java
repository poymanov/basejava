package com.basejava;

public class DeadlockTest {
    private static final Object Lock1 = new Object();
    private static final Object Lock2 = new Object();

    public static void main(String[] args) {
        ThreadDemo1 t1 = new ThreadDemo1();
        ThreadDemo2 t2 = new ThreadDemo2();
        t1.start();
        t2.start();
    }

    private static class ThreadDemo1 extends Thread {
        public void run() {
            runThread(Lock1, Lock2);
        }
    }
    private static class ThreadDemo2 extends Thread {
        public void run() {
            runThread(Lock2, Lock1);
        }
    }

    private static void runThread(Object lock1, Object lock2) {
        synchronized (lock1) {
            System.out.println("Thread: Holding lock...");

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("Thread: Waiting for lock...");

            synchronized (lock2) {
                System.out.println("Thread: Holding lock...");
            }
        }
    }
}