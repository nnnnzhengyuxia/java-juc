package com.person.juc;

import java.util.concurrent.CountDownLatch;

/**
 * created by 2019-09-21
 * 闭锁：只有当其他线程执行完，当前运算才继续执行
 */
public class TestCountDownLatch {
    public static void main(String []args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(5);
        LatchDemo latchDemo = new LatchDemo(countDownLatch);

        long startTime = System.currentTimeMillis();
        for(int i = 0; i < 5; i++) {
            new Thread(latchDemo).start();
        }

        countDownLatch.await();
        long endTime = System.currentTimeMillis();
        System.out.println(startTime);
        System.out.println(endTime);
        System.out.println(endTime - startTime);
    }
}


class LatchDemo implements Runnable {

    private CountDownLatch countDownLatch;

    public LatchDemo(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        int count = 10000;
        while (0 != count--) {
            System.out.println(count);
        }
        countDownLatch.countDown();
    }
}