package com.nowcoder;

import com.nowcoder.model.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by Administrator on 2017/7/19 0019.
 */
@Component
public class Test {

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

        service.shutdown();//结束任务,线程执行完以后

        //主线程
        while(!service.isTerminated()){
            try {
                Thread.sleep(1000);
                System.out.println("Wail");
            } catch (Exception e) {
                e.printStackTrace();
            }


        }


        }
        public  static  void main(String []args){
        Date date=new Date();
        System.out.print(date.getTime());


        }





}