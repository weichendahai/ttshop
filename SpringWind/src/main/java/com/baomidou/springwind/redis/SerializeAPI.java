package com.baomidou.springwind.redis;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.io.*;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangzhen on 2017/4/4.
 */
public class SerializeAPI {
    public static byte[] serialize(Object object) {
        ObjectOutputStream oos = null;
        ByteArrayOutputStream baos = null;
        try {
//序列化
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            byte[] bytes = baos.toByteArray();
            return bytes;
        } catch (Exception e) {
        }
        return null;
    }

    public static Object unserialize(byte[] bytes) {
        ByteArrayInputStream bais = null;
        try {
//反序列化
            bais = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream(bais);
            return ois.readObject();
        } catch (Exception e) {
        }
        return null;
    }
    //编码格式
    public static String toUtf8(String str) {
        try {
            str=URLEncoder.encode(str,"utf-8");
            str=str.replaceAll("\\+",  "%20");
            return str;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }
    /*将json转换成map*/
    public static Map<String ,String > json2Map(String str) {
        HashMap<String,String> map = JSON.parseObject(str, new TypeReference<HashMap<String,String>>(){});
        return map;
    }
}
