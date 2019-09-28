package com.person.juc;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * created by 2019-09-28
 *
 * 线程按需交替
 * 题目：编写一个程序，开启三个线程，将这三个线程的ID分别设为ABC，每个线程将自己的ID在屏幕上打印10遍，要求
 * 输出的结果必须按顺序显示，如ABCABC.... 依次递归
 */
public class TestABCAlternate {

    public static void main(String []args) {
        AlternateDemo alternateDemo = new AlternateDemo();

        new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i = 1; i <= 20; i++) {
                    alternateDemo.loopA(i);
                }
            }
        }, "A").start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i = 1; i <= 20; i++) {
                    alternateDemo.loopB(i);
                }
            }
        }, "B").start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i = 1; i <= 20; i++) {
                    alternateDemo.loopC(i);
                }
            }
        }, "C").start();
    }
}

class AlternateDemo {
    private int number = 1; //当前正在执行线程的标记
    private Lock lock = new ReentrantLock();
    private Condition condition1 = lock.newCondition();
    private Condition condition2 = lock.newCondition();
    private Condition condition3 = lock.newCondition();

    public void loopA(int totalLoop) {
        lock.lock();
        try {
            if(number != 1) {
                condition1.await();
            }

            // 打印
            for(int i = 1; i <= 5; i++) {
                System.out.println(Thread.currentThread().getName() + "\t" + i + "\t" + totalLoop);
            }

            // 唤醒
            number = 2;
            condition2.signalAll();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
    public void loopB(int totalLoop) {
        lock.lock();
        try {
            if(number != 2) {
                condition2.await();
            }

            // 打印
            for(int i = 1; i <= 10; i++) {
                System.out.println(Thread.currentThread().getName() + "\t" + i + "\t" + totalLoop);
            }

            // 唤醒
            number = 3;
            condition3.signalAll();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void loopC(int totalLoop) {
        lock.lock();
        try {
            if(number != 3) {
                condition3.await();
            }

            // 打印
            for(int i = 1; i <= 15; i++) {
                System.out.println(Thread.currentThread().getName() + "\t" + i + "\t" + totalLoop);
            }

            // 唤醒
            number = 1;
            condition1.signalAll();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}