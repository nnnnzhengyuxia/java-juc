package com.person.juc;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * created by 2019-09-28
 * ForkJoinPool 分支合并框架 工作窃取
 */
public class TestForkJoinPool {
    public static void main(String []args) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ForkJoinTask<Long> forkJoinTask = new ForkJoinSumCalculate(0, 100000000L);

        Long sum = forkJoinPool.invoke(forkJoinTask);
        System.out.println(sum);
    }
}

class ForkJoinSumCalculate extends RecursiveTask<Long> {

    private long start;
    private long end;

    private static final long THURSHOLD = 10000L; //临界值

    public ForkJoinSumCalculate(long start, long end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        long length = end - start;
        if(length <= THURSHOLD) {
            long sum = 0L;
            for (long i = start; i <= end; i++) {
                sum += i;
            }
            return sum;
        } else {
            long middle = (start + end) / 2;
            ForkJoinSumCalculate left = new ForkJoinSumCalculate(start, middle);
            left.fork();

            ForkJoinSumCalculate right = new ForkJoinSumCalculate(middle + 1, end);
            right.fork();

            return left.join() + right.join();
        }
    }
}