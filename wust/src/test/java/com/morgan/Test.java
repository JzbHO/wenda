package com.morgan;

import com.morgan.util.JedisAdapter;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPool;

import java.util.Date;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Administrator on 2017/7/19 0019.
 */


class Consumer implements Runnable{
    private BlockingQueue<String>q;
    public Consumer(BlockingQueue<String >q){
        this.q=q;
    }
    @Override
    public void run() {
        try{
            while(true){
                System.out.println(Thread.currentThread().getName()+":"+q.take());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

class Producer implements Runnable{
    private BlockingQueue<String> q;
    public Producer(BlockingQueue<String> q){
        this.q=q;
    }


    @Override
    public void run() {
        try {
            for (int i = 0; i < 100; i++) {
                Thread.sleep(1000);
                q.put(String.valueOf(i));
                System.out.println("Producer");
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}

public class Test {
//    public static void main(String[]args){
//        JedisPool pool;
//        pool=new JedisPool("redis://119.29.20.230:6379/10");
//        pool.getResource();
//        System.out.println(pool==null);
//
//    }



    private static AtomicInteger count=new AtomicInteger(0);


    public static void mutilThread(){
        for(int j=0;j<10;j++){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    for(int i=0;i<10;i++) {
                        System.out.println(count.incrementAndGet());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }
    }


    public static void blockQueue(){
        BlockingQueue<String> q=new ArrayBlockingQueue<String>(10);
        new Thread(new Producer(q)).start();
        new Thread(new Consumer(q),"Consumer1").start();
        new Thread(new Consumer(q),"Consumer2").start();

    }



    public static void testExecutor(){
        ExecutorService service= Executors.newFixedThreadPool(2);
        service.submit(new Runnable() {
            @Override
            public void run() {
                for(int i=0;i<10;i++) {
                    try {
                        Thread.sleep(1000);
                        System.out.println("Executor:" + i);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        service.submit(new Runnable() {
            @Override
            public void run() {
                for(int i=0;i<10;i++) {
                    try {
                        Thread.sleep(1000);
                        System.out.println("Executor2:" + i);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        }


        public static  void testFuture(){
            ExecutorService executorService=Executors.newSingleThreadExecutor();
            Future<Integer> future=executorService.submit(new Callable<Integer>() {
                @Override
                public Integer call() throws Exception {
                    Thread.sleep(3000);
                    return 1;
                }
            });
          //  executorService.shutdown();
            try{
                System.out.print(future.get());
            }catch (Exception e){
                e.printStackTrace();
            }



        }





}