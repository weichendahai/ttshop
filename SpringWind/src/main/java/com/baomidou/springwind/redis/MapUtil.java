package com.baomidou.springwind.redis;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * Created by wangzhen on 2017/4/1.
 */
@Configuration
@ComponentScan
public class MapUtil {
    private MapUtil(){

    }
    /**
     * 通过key删除（字节）
     * @param key
     */
    public void del(byte [] key){
        Jedis jedis = redisUtil.getJedis();
        jedis.del(key);
        redisUtil.returnResource(jedis);
    }
    /**
     * 通过key删除
     * @param key
     */
    public void del(String key){
        Jedis jedis = redisUtil.getJedis();
        jedis.del(key);
        redisUtil.returnResource(jedis);
    }

    /**
     * 添加key value 并且设置存活时间(byte)
     * @param key
     * @param value
     * @param liveTime
     */
    public void set(byte [] key,byte [] value,int liveTime){
        Jedis jedis = redisUtil.getJedis();
        jedis.set(key, value);
        jedis.expire(key, liveTime);
        redisUtil.returnResource(jedis);
    }
    /**
     * 添加key value 并且设置存活时间
     * @param key
     * @param value
     * @param liveTime
     */
    public void set(String key,String value,int liveTime){
        Jedis jedis = redisUtil.getJedis();
        jedis.set(key, value);
        jedis.expire(key, liveTime);
        redisUtil.returnResource(jedis);
    }
    /**
     * 添加key value
     * @param key
     * @param value
     */
    public void set(String key,String value){
        Jedis jedis = redisUtil.getJedis();
        jedis.set(key, value);
        redisUtil.returnResource(jedis);
    }
    /**添加key value (字节)(序列化)
     * @param key
     * @param value
     */
    public void set(byte [] key,byte [] value){
        Jedis jedis = redisUtil.getJedis();
        jedis.set(key, value);
        redisUtil.returnResource(jedis);
    }
    /**
     * 获取redis value (String)
     * @param key
     * @return
     */
    public String get(String key){
        Jedis jedis = redisUtil.getJedis();
        String value = jedis.get(key);
        redisUtil.returnResource(jedis);
        return value;
    }
    /**
     * 获取redis value (byte [] )(反序列化)
     * @param key
     * @return
     */
    public byte[] get(byte [] key){
        Jedis jedis = redisUtil.getJedis();
        byte[] value = jedis.get(key);
        redisUtil.returnResource(jedis);
        return value;
    }

    /**
     * 通过正则匹配keys
     * @param pattern
     * @return
     */
    public Set<String> keys(String pattern){
        Jedis jedis = redisUtil.getJedis();
        Set<String> value = jedis.keys(pattern);
        redisUtil.returnResource(jedis);
        return value;
    }

    /**
     * 检查key是否已经存在
     * @param key
     * @return
     */
    public boolean exists(String key){
        Jedis jedis = redisUtil.getJedis();
        boolean value = jedis.exists(key);
        redisUtil.returnResource(jedis);
        return value;
    }

    /*******************redis list操作************************/
    /**
     * 往list中添加元素
     * @param key
     * @param value
     */
    public void lpush(String key,String value){
        Jedis jedis = redisUtil.getJedis();
        jedis.lpush(key, value);
        redisUtil.returnResource(jedis);
    }

    public void rpush(String key,String value){
        Jedis jedis = redisUtil.getJedis();
        jedis.rpush(key, value);
        redisUtil.returnResource(jedis);
    }

    /**
     * 数组长度
     * @param key
     * @return
     */
    public Long llen(String key){
        Jedis jedis = redisUtil.getJedis();
        Long len = jedis.llen(key);
        redisUtil.returnResource(jedis);
        return len;
    }

    /**
     * 获取下标为index的value
     * @param key
     * @param index
     * @return
     */
    public String lindex(String key,Long index){
        Jedis jedis = redisUtil.getJedis();
        String str = jedis.lindex(key, index);
        redisUtil.returnResource(jedis);
        return str;
    }

    public String lpop(String key){
        Jedis jedis = redisUtil.getJedis();
        String str = jedis.lpop(key);
        redisUtil.returnResource(jedis);
        return str;
    }

    public List<String> lrange(String key,long start,long end){
        Jedis jedis = redisUtil.getJedis();
        List<String> str = jedis.lrange(key, start, end);
        redisUtil.returnResource(jedis);
        return str;
    }
    /*********************redis list操作结束**************************/

    /**
     * 清空redis 所有数据
     * @return
     */
    public String flushDB(){
        Jedis jedis = redisUtil.getJedis();
        String str = jedis.flushDB();
        redisUtil.returnResource(jedis);
        return str;
    }
    /**
     * 查看redis里有多少数据
     */
    public long dbSize(){
        Jedis jedis = redisUtil.getJedis();
        long len = jedis.dbSize();
        redisUtil.returnResource(jedis);
        return len;
    }
    /**
     * 检查是否连接成功
     * @return
     */
    public String ping(){
        Jedis jedis = redisUtil.getJedis();
        String str = jedis.ping();
        redisUtil.returnResource(jedis);
        return str;
    }

    /**
     *计算随机数
     */
    //把满足条件的y个用户id存到一个数组中，即一个y容量的数组，
    // 然后做n次该数组的循环，循环里面做的事情是，随机出来两个0至（y-1）之间的数字a和b，把数组下标a和b对应的数组元素进行交换。
    // n做的次数越多，原数组的次序就越乱，假设需要的人数为q，则下标从0至（q-1）的数组元素就是所需要的用户id列表了。
    public static List getRandom(String[] ids,int x){
        List<String> list=new ArrayList<String>();
        String temp="";
        for (int n=0;n<10000;n++){
           int  a = (int)(Math.random() *ids.length);
           int  b = (int)(Math.random() *ids.length);
            temp=ids[a];
            ids[a]=ids[b];
            ids[b]=temp;
        }
        for (int j=0;j<x;j++){
          list.add(ids[j]);
        }
     return list;
    }
    public  static Object[] getPage(int page_no,Set<String> set){
        //将set装换成数组
        Object[] array =  set.toArray();
        Object  [] newData;
        newData = Arrays.copyOfRange(array, page_no*10, page_no*10+9);
        return newData;
    }
}
