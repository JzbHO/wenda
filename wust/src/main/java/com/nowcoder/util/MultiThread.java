package com.nowcoder.util;


import org.slf4j.LoggerFactory;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2017/9/29 0029.
 */
public class MultiThread {

    //private static final org.slf4j.Logger logger = LoggerFactory.getLogger(EventConsumer.class);

    public static void main(String []args){
        ExecutorService executorService= Executors.newCachedThreadPool();
        for(int i=0;i<5;i++){
            System.out.println("-------"+i);
            executorService.execute(new TestRunnable());
        }


    }
}
class TestRunnable implements Runnable{
    public void run(){
        System.out.println(Thread.currentThread().getName()+"线程调用了");
    }

}
