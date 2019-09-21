package com.person.juc;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * future 有闭锁的效果
 */
public class TestCallable {

    public static void main(String []args) throws ExecutionException, InterruptedException {

        ThreadCallableDemo threadCallableDemo = new ThreadCallableDemo();
        Future<Integer> integerFuture = new FutureTask<>(threadCallableDemo);
        new Thread((Runnable) integerFuture).start();
        System.out.println(integerFuture.get());
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~");
    }
}

class ThreadCallableDemo implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {
        int sum = 0;
        for(int i = 0; i < 10000; i++) {
            sum += i;
        }
        return sum;
    }
}
