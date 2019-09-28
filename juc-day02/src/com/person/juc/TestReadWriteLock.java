package com.person.juc;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * created by 2019-09-28
 * 1. ReadWriteLock: 读写锁
 * 写写/读写：互斥
 * 读读： 不需要互斥
 */
public class TestReadWriteLock {

    public static void main(String []args) {
        ReadWriteLockDemo readWriteLockDemo = new ReadWriteLockDemo();

        for(int i = 0; i < 2; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    readWriteLockDemo.setNumber((int)(Math.random() * 100));
                }
            }, "write: ").start();
        }

        for(int i = 0; i < 20; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    readWriteLockDemo.getNumber();
                }
            }, "read: ").start();
        }
    }
}

class ReadWriteLockDemo {
    private int number = 0;
    private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    public void getNumber() {
        readWriteLock.readLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + " " + number);
        } finally {
            readWriteLock.readLock().unlock();
        }
    }

    public void setNumber(int number) {
        readWriteLock.writeLock().lock();
        try {
            this.number = number;
            System.out.println(Thread.currentThread().getName() + number);
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }
}