package com.person.juc;


import java.util.concurrent.atomic.AtomicInteger;

/**
 * created by 2019-09-21
 * java.util.concurrent.atomic 提供了大量的原子变量
 *
 * 1.volatile 内存可见性
 * 2.CAS（compare and swap）保证数据的原子性
 *      硬件对于并发操作共享数据的支持
 *      三个操作数：
 *      内存值V
 *      预估值A
 *      更新值B
 *      当且仅当  V == A, V=B ,否则不做任何操作。
 */
public class TestAtomicDemo {

    public static void main(String []args) {
        AtomicDemo atomicDemo = new AtomicDemo();
        int count = 10;
        while (0 != count--) {
            new Thread(atomicDemo).start();
        }
    }

}


class AtomicDemo implements Runnable {

//    private int serialNumber = 0;
    private AtomicInteger serialNumber = new AtomicInteger(0);

    public int getSerialNumber() {
        return serialNumber.getAndIncrement();
    }

    @Override
    public void run() {
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + ":" + getSerialNumber());
    }
}