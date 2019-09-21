package com.person.juc;

/**
 * created by 2019-09-21
 * volatile 内存可见性（多个线程共享内存）
 * 相较于synchronized 较为轻量级的同步策略
 *
 * 注意：
 * 1.不具备互斥性
 * 2.不保证变量的原子性
 */
public class TestVolative {

    public static void main(String []args) throws InterruptedException {
        ThreadDemo threadDemo = new ThreadDemo();
        new Thread(threadDemo).start();

        /**
         * 因为此时拿到的是副本，执行效率太高，以至于无法同步内存的信息
         * 线程之间的内存不可见
         * 但sleep 2000ms，执行效率下降，可同步内存的信息
         */
//        while (true) {
//            Thread.sleep(2000);
//            if(threadDemo.isFlag()) {
//                System.out.println("true了");
//                break;
//            }
//        }

        /**
         * 加锁效率低
         */
//        while (true) {
//            synchronized (threadDemo) {
//                if(threadDemo.isFlag()) {
//                    System.out.println("ture 了");
//                    break;
//                }
//            }
//        }

        while (true) {
            if(threadDemo.isFlag()) {
                System.out.println("true 了");
                break;
            }
        }
    }
}


class ThreadDemo implements Runnable {

    /**
     * volatile 关键字
     */
    private volatile boolean flag = false;

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    @Override
    public void run() {

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        flag = true;
        System.out.println("flag = " + flag);
    }
}