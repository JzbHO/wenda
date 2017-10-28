package com.morgan.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;
import java.util.Set;

/**
 * Created by Administrator on 2017/8/14 0014.
 */
@Service
public class JedisAdapter  implements InitializingBean {
    private JedisPool pool;
    private static final Logger logger = LoggerFactory.getLogger(JedisAdapter.class);
    public static void print(int index,Object obj){
        System.out.println(String.format("%d, %s", index, obj.toString()));
    }

    public  static void main(String []args){
        //指定9 号数据库
        Jedis jedis=new Jedis("redis://localhost:6379/9");
        //删除指定数据库
        jedis.flushDB();
        //jedis.zadd();
        jedis.lpush("hello","world");
        long legnth=jedis.lpush("hello","world1");

        print(1,jedis.lpop("hello"));
//        jedis.set("hello","world2");
//        print(2,jedis.get("hello"));
//        //设置有效时间
//        jedis.setex("hello2",5,"world");
//
//        //短时间内大量变化 使用redis
//        jedis.set("pv","100");
//        //加一
//        jedis.incrBy("pv",5);
//        jedis.decrBy("pv",2);
//        jedis.incr("pv");
//
//        //打印所有字符
//        print(3,jedis.keys("*"));
//
//        //重点
//        String listname="list";
//        //删除指定key  不存在则会被忽略掉
//        jedis.del(listname);
//
//        for(int i=0;i<10;i++){
//            jedis.lpush(listname,String.valueOf(i));
//        }
//        //最后进去的元素下标为0
//        print(4,jedis.lrange(listname,0,12));
//        //队列操作
//        print(5,jedis.llen(listname));
//        //弹出最顶后的一个元素
//        print(6,jedis.lpop(listname));
//        print(7,jedis.llen(listname));
//        //指定元素 ，插入元素,查找指定元素
//        print(8,jedis.lindex(listname,3));
//        print(9,jedis.linsert(listname, BinaryClient.LIST_POSITION.AFTER,"4","xx"));
//        print(10,jedis.linsert(listname, BinaryClient.LIST_POSITION.BEFORE,"4","bb"));
//        print(4,jedis.lrange(listname,0,12));
    }




    @Override
    public void afterPropertiesSet() throws Exception {
        pool=new JedisPool("redis://localhost:6379/10");
    }

    public long sadd(String key,String value){
        Jedis jedis=null;
        try{
            jedis=pool.getResource();
            return jedis.sadd(key,value);
        }catch (Exception e){
            System.out.println("发生异常"+e.getMessage());
        }finally {
            if(jedis!=null){
                jedis.close();
            }
        }
        return 0;
    }

    public long srem(String key,String value){
        Jedis jedis=null;
        try{
            jedis=pool.getResource();
            return jedis.srem(key,value);
        }catch (Exception e){
            System.out.println("发生异常"+e.getMessage());
        }finally {
            if(jedis!=null){
                jedis.close();
            }
        }
        return 0;
    }
    public boolean sismember(String key,String value) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.sismember(key, value);
        } catch (Exception e) {
            System.out.println("发生异常" + e.getMessage());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return false;
    }

    public long lpush(String key,String value){
        Jedis jedis=null;
        try {
            jedis = pool.getResource();
            return jedis.lpush(key, value);
        }catch (Exception e){
            logger.error("发生异常"+e.getMessage());
        }finally {
            if(jedis!=null){
                jedis.close();
            }
        }
        return 0;
    }


    public List<String> brpop(int timeout, String key){
        Jedis jedis=null;
        try {
            jedis = pool.getResource();
            //0表示无限等待
            return jedis.brpop(0, key);
        }catch (Exception e){
            logger.error("发生异常"+e.getMessage());
        }finally {
            if(jedis!=null){
                jedis.close();
            }
        }
        return null;
    }

    public Set<String> zrevrange(String key, int start, int end) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.zrevrange(key, start, end);
        } catch (Exception e) {
            logger.error("发生异常" + e.getMessage());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return null;
    }

    public double zcard(String key){
        Jedis jedis=null;

        try {
            jedis = pool.getResource();
            return jedis.zcard(key);
        }catch (Exception e){

        }finally {
            if(jedis!=null)
                jedis.close();
        }
        return 0;
    }




}