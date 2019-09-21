package com.person.juc;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * created by 2019-09-21
 * 同步锁，记得unlock
 */
public class TestLock {

    public static void main(String []args) {
        ThreadLockDemo threadLockDemo = new ThreadLockDemo();
        new Thread(threadLockDemo, "一号窗口").start();
        new Thread(threadLockDemo, "二号窗口").start();
        new Thread(threadLockDemo, "三号窗口").start();
    }

}

class ThreadLockDemo implements Runnable{

    private int ticket = 100;
    private Lock lock = new ReentrantLock();

    @Override
    public void run() {
        while(true) {
            lock.lock();
            try {
                Thread.sleep(100);
                if(ticket > 0) {
                    System.out.println(Thread.currentThread().getName() + "----" + ticket--);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }

        }
    }
}