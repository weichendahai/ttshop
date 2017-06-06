package com.baomidou.springwind.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by wangzhen on 2017/4/1.
 */
public class redisUtil {
    private redisUtil(){

    }

    //Redis服务器IP
    private static String ADDR = "106.14.239.123";
//    //Redis服务器IP
//    private static String ADDR = "ip";
    //Redis的端口号
    private static int PORT = 6379;
    //可用连接实例的最大数目，默认值为8；
    //如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
    private static int MAX_ACTIVE = 1024;
    //控制一个pool最多有多少个状态为idle(空闲的)的jedis实例，默认值也是8。
    private static int MAX_IDLE = 200;
    //等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出JedisConnectionException；
    private static int MAX_WAIT = 10000;

    //在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
    private static boolean TEST_ON_BORROW = true;
    private static JedisPool jedisPool = null;
    /**
     * 初始化Redis连接池
     */
    static {
        try {
            JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxTotal(MAX_ACTIVE);
            config.setMaxIdle(MAX_IDLE);
            config.setMaxWaitMillis(MAX_WAIT);
            config.setTestOnBorrow(TEST_ON_BORROW);
            jedisPool = new JedisPool(config, ADDR, PORT,1000,"srsjredis");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取Jedis实例
     * @return
     */
    public synchronized static Jedis getJedis() {
        try {
            if (jedisPool != null) {
                Jedis resource = jedisPool.getResource();
                return resource;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 释放jedis资源
     * @param jedis
     */
    public static void returnResource(final Jedis jedis) {
        if (jedis != null) {
            jedisPool.returnResourceObject(jedis);
        }
    }
    /*
    * 格式化时间
    * */

    public static String  getFormat(int seconds){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, seconds);
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());
    }
    /*将字符转行成date
    *
    * */
    public static Date  String2Date(String str )throws Exception{
        SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
        Date date = sdf.parse(str.toString());
        return  date;
    }
    public static String  Date2String(Date date )throws Exception{
        SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
        String str=sdf.format(date);
        return str ;
    }
    /*
    * 获取20位随机数
    * */
  public static String getRound(String user_id){
      StringBuffer sb=new StringBuffer();
      SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
      int j=user_id.length();
      sb.append(user_id);
      for (int i =0;i<8-j;i++){
          sb.append("0");
      }
      sb.append(sdf.format(new Date()));
     return sb.toString();
  }
/*
* 获取2个时间的差添加到第三个时间*/
public static String  getCd(String big,String small,String addCd) throws Exception {
    Date firstDate = String2Date(big);
    Date secondDate = String2Date(small);
    Date add=String2Date(addCd);
    long firstDateMilliSeconds =firstDate.getTime();
    long secondDateMilliSeconds = secondDate.getTime();
    long cd=firstDateMilliSeconds-secondDateMilliSeconds;
    long addTime=add.getTime();
    long date=addTime+cd;
    String trgetDate= getStandardTime(date);
    return trgetDate ;
}
    public static String getStandardTime(long timestamp) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String d = format.format(timestamp);
        return d;
    }

}
