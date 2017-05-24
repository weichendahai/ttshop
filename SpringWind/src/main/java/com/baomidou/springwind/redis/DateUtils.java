package com.baomidou.springwind.redis;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by wangzhen on 2017/4/13.
 */
public class DateUtils {
    //获取当前时间的一星期一
    public static String  getMONDAY() {
        Calendar cal =Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY); //获取本周一的日期
        //这种输出的是上个星期周日的日期，因为老外那边把周日当成第一天
       return df.format(cal.getTime());
    }
    //获取上个星期一
    public static String  getPvMONDAY() {
        Calendar cal =Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
         cal.add(Calendar.DATE, -7);
         //获取本周一的日期
        //这种输出的是上个星期周日的日期，因为老外那边把周日当成第一天
        return df.format(cal.getTime());
    }
    public  static String  getSUNDAY() {
        Calendar cal =Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        //增加一个星期，才是我们中国人理解的本周日的日期
        cal.add(Calendar.WEEK_OF_YEAR, 1);
        return df.format(cal.getTime());
    }
    public  static String  getPvSUNDAY() {
        Calendar cal =Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        //增加一个星期，才是我们中国人理解的本周日的日期
        cal.add(Calendar.WEEK_OF_YEAR, 1);
        cal.add(Calendar.DATE, -7);
        return df.format(cal.getTime());
    }
    public static Map<String, String> convertMonthByDate(Date date) {
        Map<String, String > map = new HashMap<String, String >();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        SimpleDateFormat dl = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, 0);
        Date theDate = calendar.getTime();
        // 上个月第一天
        GregorianCalendar gcLast = (GregorianCalendar) Calendar.getInstance();
        gcLast.setTime(theDate);
        gcLast.set(Calendar.DAY_OF_MONTH, 1);
        String day_first = df.format(gcLast.getTime());
        // 上个月最后一天
        calendar.add(Calendar.MONTH, 1); // 加一个月
        calendar.set(Calendar.DATE, 1); // 设置为该月第一天
        calendar.add(Calendar.DATE, -1); // 再减一天即为上个月最后一天
        String day_last = dl.format(calendar.getTime());
        map.put("first", day_first);
        map.put("last", day_last);
        return map;
    }
    public static Map<String, String> convertPvMonthByDate(Date date) {
        Map<String, String > map = new HashMap<String, String >();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        SimpleDateFormat dl = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, -1);
        Date theDate = calendar.getTime();
        // 上个月第一天
        GregorianCalendar gcLast = (GregorianCalendar) Calendar.getInstance();
        gcLast.setTime(theDate);
        gcLast.set(Calendar.DAY_OF_MONTH, 1);
        String day_first = df.format(gcLast.getTime());
        // 上个月最后一天
        calendar.add(Calendar.MONTH, 1); // 加一个月
        calendar.set(Calendar.DATE, 1); // 设置为该月第一天
        calendar.add(Calendar.DATE, -1); // 再减一天即为上个月最后一天
        String day_last = dl.format(calendar.getTime());
        map.put("first", day_first);
        map.put("last", day_last);
        return map;
    }
    public static String  Date2String(Date date )throws Exception{
        SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd" );
        String str=sdf.format(date);
        return str ;
    }
    public static Date  String2Date(String str )throws Exception{
        SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd" );
        Date date = sdf.parse(str.toString());
        return  date;
    }
    public static String  Date2String1(Date date ){
        SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd_HH-mm-ss");
        String str=sdf.format(date);
        return str ;
    }
    public static String  Date2String2(Date date ){
        SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss");
        String str=sdf.format(date);
        return str ;
    }
    public static Date  String2Date1(String str )throws Exception{
        SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd" );
        Date date = sdf.parse(str.toString());
        return  date;
    }
  /*public static void main(String args[]) {
      Map<String,String >  map=convertPvMonthByDate(new Date());
      System.out.print(map.get("first"));
      System.out.print(map.get("last"));
    }*/
    //获取
  public static int  getDiscount(int price,int rate,int value )throws Exception{
       int count=0;
      if ( value> 0) {
          count=price - value;
      } else if (rate> 0) {
          if (price * rate % 100 < 50) {
              count=price * rate / 100;
          } else {
              count=price * rate / 100 + 1;
          }
      } else {
          count=price;
      }
      return  count;
  }
     /*public static void main(String args[]) {
         int i= 0;
         try {
             i = getDiscount(100000,99,0);
         } catch (Exception e) {
             e.printStackTrace();
         }
         System.out.print(i);
    }*/
    /**
     * 生成随机时间
     * @param beginDate
     * @param endDate
     * @return
     */
    public static Date randomDate(String beginDate,String  endDate ){

        try {

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

            Date start = format.parse(beginDate);//构造开始日期

            Date end = format.parse(endDate);//构造结束日期

//getTime()表示返回自 1970 年 1 月 1 日 00:00:00 GMT 以来此 Date 对象表示的毫秒数。

            if(start.getTime() >= end.getTime()){

                return null;

            }

            long date = random(start.getTime(),end.getTime());

            return new Date(date);

        } catch (Exception e) {

            e.printStackTrace();

        }

        return null;

    }

    public static long random(long begin,long end){

        long rtn = begin + (long)(Math.random() * (end - begin));

//如果返回的是开始时间和结束时间，则递归调用本函数查找随机值

        if(rtn == begin || rtn == end){

            return random(begin,end);

        }

        return rtn;

    }
    //由出生日期获得年龄
    public static int getAge(Date birthDay) throws Exception {
        Calendar cal = Calendar.getInstance();
        if (cal.before(birthDay)) {
            throw new IllegalArgumentException( "The birthDay is before Now.It's unbelievable!");
        }
        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH);
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
        cal.setTime(birthDay);

        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

        int age = yearNow - yearBirth;

        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                if (dayOfMonthNow < dayOfMonthBirth) age--;
            }else{
                age--;
            }
        }
        return age;
    }
}
