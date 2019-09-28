package com.person.juc;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * created by 2019-09-28
 * lock   condition  使用与生产者消费者
 */
public class TestConsumerAndProducerForLock {

    public static void main(String []args) {
        Clerk clerk = new Clerk();
        Consumer consumer = new Consumer(clerk);
        Productor productor = new Productor(clerk);

        new Thread(productor, "生产者").start();
        new Thread(consumer,"消费者").start();

    }
}

class Clerk {
    private int product = 0;

    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    //进货
    public void get() throws InterruptedException {
        lock.lock();
        try {
            //虚假唤醒问题，wait应该使用在循环中
            while(product >= 1) {
                System.out.println("产品已满");
//                this.wait();
                condition.await();
            }
            System.out.println(Thread.currentThread().getName() + ": " + product++);
//            this.notifyAll();
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }

    //卖货
    public void sale() throws InterruptedException {
        lock.lock();
        try {
            //虚假唤醒问题，wait应该使用在循环中
            while (product <= 0) {
                System.out.println("缺货！");
//                this.wait();
                condition.await();
            }
            System.out.println(Thread.currentThread().getName() + ": " + product--);
//            this.notifyAll();
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }
}

class Productor implements Runnable{
    private Clerk clerk;

    public Productor(Clerk clerk) {
        this.clerk = clerk;
    }

    @Override
    public void run() {
        for(int i = 0; i<20; i++) {
            try {
                clerk.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}

class Consumer implements Runnable{
    private Clerk clerk;

    public Consumer(Clerk clerk) {
        this.clerk = clerk;
    }

    @Override
    public void run() {
        for(int i = 0; i<20; i++) {
            try {
                clerk.sale();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}