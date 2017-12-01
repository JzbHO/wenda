package com.morgan.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import redis.clients.jedis.*;

import java.util.List;
import java.util.Set;

/**
 * Created by Administrator on 2017/8/14 0014.
 */
@Service
public class JedisAdapter  implements InitializingBean {
    private JedisPool pool;
    private static final Logger logger = LoggerFactory.getLogger(JedisAdapter.class);


    public  static void main(String []args) throws InterruptedException {
//        //指定9 号数据库
//        Jedis jedis=new Jedis("redis://localhost:6379/9");
//        //删除指定数据库
//        jedis.flushDB();
//        //jedis.zadd();
//      jedis.set("hello","value");
//      jedis.setex("hello1",2,"value1");
//      print(jedis.get("hello1"));
//      print(jedis.get("hello1")+"第二次");
//      print(jedis.keys("*"));
//
//      String list="list";
//      for(int i=0;i<10;i++){
//          jedis.lpush(list,String.valueOf(i));
//      }
//      print(jedis.llen(list));
//      print(jedis.lpop(list));
//      jedis.linsert(list, BinaryClient.LIST_POSITION.AFTER,"5","xx");
//      print(jedis.llen(list));
//        print(jedis.lrange(list,0,10));
//
//
//      String userKey="user";
//      jedis.hset(userKey,"name","Jack");
//        jedis.hset(userKey,"sex","boy");
//        jedis.hset(userKey,"phone","180866");
//        print(jedis.hget(userKey,"name"));
//        print(jedis.hgetAll(userKey));
//        print(jedis.hexists(userKey,"age"));
//        print(jedis.hkeys(userKey));
//        print(jedis.hvals(userKey));
//        jedis.hsetnx(userKey,"school","Wust");
//        print(jedis.hgetAll(userKey));
//
//
//        String setKey1="key1";
//        String setKey2="key2";
//
//        for(int i=0;i<10;i++){
//            jedis.sadd(setKey1,String.valueOf(i));
//            jedis.sadd(setKey2,String.valueOf(i*i));
//        }
//        print(jedis.smembers(setKey1));
//        print(jedis.sunion(setKey1,setKey2));
//        print(jedis.sdiff(setKey1,setKey2));
//        print(jedis.sinter(setKey1,setKey2));
//        print(jedis.sismember(setKey1,"1"));
//        print(jedis.sismember(setKey1,"100"));
//        print(jedis.scard(setKey1));
//        print(jedis.srem(setKey1,"5"));
//        print(jedis.smembers(setKey1));
//
//
//        //优先队列，实则就是堆，排过序的
//        String rankKey="rankKey";
//        jedis.zadd(rankKey,15,"Mi");
//        jedis.zadd(rankKey,80,"Le");
//        jedis.zadd(rankKey,38,"Kai");
//        jedis.zadd(rankKey,9,"Fi");
//        print(jedis.zcard(rankKey));
//        print(jedis.zcount(rankKey,9,38));
//        jedis.zincrby(rankKey,2,"Kai");
//        //默认由小到大排序
//        print(jedis.zrange(rankKey,0,3));
//        //逆序
//        print(jedis.zrevrange(rankKey,0,3));
//       // 取得数值和Key
//        for(Tuple tuple:jedis.zrangeByScoreWithScores(rankKey,0,100)){
//            print(tuple.getElement()+":"+String.valueOf(tuple.getScore()));
//        }
//        print(jedis.zrank(rankKey,"Mi"));
//        print(jedis.zrevrank(rankKey,"Mi"));
//
//        String setKey="zset";
//        jedis.zadd(setKey,1,"a");
//        jedis.zadd(setKey,1,"b");
//        jedis.zadd(setKey,1,"c");
//        jedis.zadd(setKey,1,"d");
//        print(jedis.zlexcount(setKey,"[a","[d"));



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

     public long add(String key,String value){
        Jedis jedis=null;
        try{
            jedis=pool.getResource();
            return jedis.sadd(key,value);
        }catch (Exception e){
            logger.error("发生异常"+e.getMessage());
        }finally {
            if(jedis!=null) {
                jedis.close();
            }
        }
        return 0;
     }
    public List<String> lrange(String key,int begin,int end){
        Jedis jedis=null;
        try{
            jedis=pool.getResource();
            return jedis.lrange(key,begin,end);
        }catch (Exception e){
            logger.error("发生异常"+e.getMessage());
        }finally {
            if(jedis!=null) {
                jedis.close();
            }
        }
        return null;
    }
    public long srem(String key,String value){
        Jedis jedis=null;
        try{
            jedis=pool.getResource();
            return jedis.srem(key,value);
        }catch (Exception e){
            logger.error("发生异常"+e.getMessage());
        }finally {
            if(jedis!=null) {
                jedis.close();
            }
        }
        return 0;
    }
    public Double zscore(String key,String memver){
        Jedis jedis=null;
        try{
            jedis=pool.getResource();
            return jedis.zscore(key,memver);
        }catch (Exception e){
            logger.error("发生异常"+e.getMessage());
        }finally {
            if(jedis!=null) {
                jedis.close();
            }
        }
        return null;
    }
    public long scard(String key){
        Jedis jedis=null;
        try{
            jedis=pool.getResource();
            return jedis.scard(key);
        }catch (Exception e){
            logger.error("发生异常"+e.getMessage());
        }finally {
            if(jedis!=null) {
                jedis.close();
            }
        }
        return 0;
    }

    public boolean sismemebr(String key,String value){
        Jedis jedis=null;
        try{
            jedis=pool.getResource();
            return jedis.sismember(key,value);
        }catch (Exception e){
            logger.error("发生异常"+e.getMessage());
        }finally {
            if(jedis!=null) {
                jedis.close();
            }
        }
        return true;
    }

    public List<String> brpop(int time ,String key){
        Jedis jedis=null;
        try{
            jedis=pool.getResource();
            return jedis.blpop(0,key);
        }catch (Exception e){
            logger.error("发生异常"+e.getMessage());
        }finally {
            if(jedis!=null) {
                jedis.close();
            }
        }
        return null;
    }
    public Set<String> zrevrange(String key,int start,int end){
        Jedis jedis=null;
        try{
            jedis=pool.getResource();
            return jedis.zrevrange(key,start,end);
        }catch (Exception e){
            logger.error("发生异常"+e.getMessage());
        }finally {
            if(jedis!=null) {
                jedis.close();
            }
        }
        return null;
    }

    public Long zcard(String key){
        Jedis jedis=null;
        try{
            jedis=pool.getResource();
            return jedis.zcard(key);
        }catch (Exception e){
            logger.error("发生异常"+e.getMessage());
        }finally {
            if(jedis!=null) {
                jedis.close();
            }
        }
        return null;
    }
    public long lpush(String key,String value){
        Jedis jedis=null;
        try{
            jedis=pool.getResource();
            return jedis.lpush(key,value);
        }catch (Exception e){
            logger.error("发生异常"+e.getMessage());
        }finally {
            if(jedis!=null) {
                jedis.close();
            }
        }
        return 1;
    }

    public Jedis getResource(){
        return pool.getResource();
    }
    public Transaction multi(Jedis jedis){
        try{
            return jedis.multi();
        }catch (Exception e){
            logger.error("事务获取发生异常"+e.getMessage());
        }finally {

        }
        return null;

    }


    public List<Object> exec(Transaction ex,Jedis jedis){
        try{
            return ex.exec();
        }catch (Exception e){
            logger.error("发生异常"+e.getMessage());
            ex.discard();
        }finally {
            if(jedis!=null){
                jedis.close();
            }
        }
        return null;
    }


}
