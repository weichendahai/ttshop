package com.baomidou.springwind.wx;

import com.baomidou.springwind.redis.SerializeAPI;
import jodd.util.StringUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * Created by wangzhen on 2017/4/24.
 */
/*AppID(应用ID)wx37b6eaccc6264950*/

/*AppSecret 81a78fe1e128ee103a3dea5629460766*/
public class MeizhuangWx {
    private static final Log logger = LogFactory.getLog(MeizhuangWx.class);
    public static String auth_url;
    public static String pay_sign;
    public static String  appid;
    public static String  mch_id;
    public static String  pay_url;
    public static String secret;
    public  static String info_url;
    public static String token_url1;
    public static String ticket_url;
    public static String url1;
    public static String notify_url;
/*配置文件信息-start*/

    public static String getNotify_url() {
        return notify_url;
    }

    public static void setNotify_url(String notify_url) {
        MeizhuangWx.notify_url = notify_url;
    }

    public static String getAuth_url() {
        return auth_url;
    }

    public static void setAuth_url(String auth_url) {
        MeizhuangWx.auth_url = auth_url;
    }

    public static String getPay_sign() {
        return pay_sign;
    }

    public static void setPay_sign(String pay_sign) {
        MeizhuangWx.pay_sign = pay_sign;
    }

    public static String getAppid() {
        return appid;
    }

    public static void setAppid(String appid) {
        MeizhuangWx.appid = appid;
    }

    public static String getMch_id() {
        return mch_id;
    }

    public static void setMch_id(String mch_id) {
        MeizhuangWx.mch_id = mch_id;
    }

    public static String getPay_url() {
        return pay_url;
    }

    public static void setPay_url(String pay_url) {
        MeizhuangWx.pay_url = pay_url;
    }

    public static String getSecret() {
        return secret;
    }

    public static void setSecret(String secret) {
        MeizhuangWx.secret = secret;
    }

    public static String getInfo_url() {
        return info_url;
    }

    public static void setInfo_url(String info_url) {
        MeizhuangWx.info_url = info_url;
    }

    public static String getToken_url1() {
        return token_url1;
    }

    public static void setToken_url1(String token_url1) {
        MeizhuangWx.token_url1 = token_url1;
    }

    public static String getTicket_url() {
        return ticket_url;
    }

    public static void setTicket_url(String ticket_url) {
        MeizhuangWx.ticket_url = ticket_url;
    }

    public static String getUrl1() {
        return url1;
    }

    public static void setUrl1(String url1) {
        MeizhuangWx.url1 = url1;
    }
/*配置文件信息-end*/


    public static Map getOpenid(String  code ) throws  Exception {
        String authUrl = auth_url+"?appid="+appid+"&secret="+secret+"&code="+code+"&grant_type=authorization_code";
        String authResp = HttpUtils.httpGet(authUrl);
        //json转换成map
        Map<String ,String> authMap = SerializeAPI.json2Map(authResp);
        String openid = authMap.get( "openid" );
        String acess_token=authMap.get("access_token");
        Map<String ,String > opneId=new HashMap<String, String>();
        opneId.put("openid",openid);
        opneId.put("acess_token",acess_token);

        //经openid插入user表中
        return opneId ;
    }
    public static Map<String,String> getUserInfo(String  openid,String accessaToken ) throws  Exception {
        String authUrl = info_url+accessaToken+"&openid="+openid+"&lang=zh_CN";
        String authResp = HttpUtils.httpGet(authUrl);
        //json转换成map
        Map<String ,String> authMap = SerializeAPI.json2Map(authResp);
        //经openid插入user表中
        return authMap ;
    }

    public static String getWxSdk() {
        String authUrl = token_url1+"appid="+appid+"&secret="+secret;
        String authResp ="";
        authResp = HttpUtils.httpGet(authUrl);
        //json转换成map
        logger.info(authResp);
        Map<String ,String> authMap = SerializeAPI.json2Map(authResp);
        String acess_token=authMap.get("access_token");
      //经openid插入user表中
        String sktic_url=url1+acess_token + "&type=jsapi";
        String ticket=HttpUtils.httpGet(sktic_url);
        Map<String ,String> ticketMap = SerializeAPI.json2Map(ticket);
        String tickets=ticketMap.get("ticket");
        logger.info(tickets);
        return tickets;
    }
    //SHA1加密
    public static String SHA1(String str) {
            try {
                MessageDigest digest = java.security.MessageDigest
                        .getInstance("SHA-1"); //如果是SHA加密只需要将"SHA-1"改成"SHA"即可
                digest.update(str.getBytes());
                byte messageDigest[] = digest.digest();
                // Create Hex String
                StringBuffer hexStr = new StringBuffer();
                // 字节数组转换为 十六进制 数
                for (int i = 0; i < messageDigest.length; i++) {
                    String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
                    if (shaHex.length() < 2) {
                        hexStr.append(0);
                    }
                    hexStr.append(shaHex);
                }
                return hexStr.toString();

            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            return null;
        }
     //获取package
    public static String getPrepayId(String openid,String order_id ,String price,String ip,String userID) {
        Map<String ,String> result=new HashMap();
        String noncestr = UUID.randomUUID().toString().replace("-", "").substring(0,16);
       // String timestamp = Long.toString(System.currentTimeMillis() / 1000);
        Map<String, String> paraMap = new HashMap<String, String>();
        paraMap.put("appid",  appid);
        paraMap.put("mch_id", mch_id);
        paraMap.put("nonce_str", noncestr);
        paraMap.put("body", "美妆商城-交易支付");
        paraMap.put("out_trade_no", order_id);
        paraMap.put("total_fee", price);
        paraMap.put("spbill_create_ip", ip);
        paraMap.put("notify_url", notify_url);
        paraMap.put( "trade_type", "JSAPI");
        paraMap.put( "openid", openid);
        paraMap.put("attach", "1406"+";"+userID) ;
        String sign =  getSign(paraMap, pay_sign);
        paraMap.put("sign", sign);
        String prepay_id = "";
        try {
        String xml =  ArrayToXml(paraMap, false);
        logger.debug("2 wx xml "+ xml);
        String xmlStr = HttpUtils.post(pay_url, xml);
        logger.debug("from wx xml "+xmlStr);
        Map<String, String> map = doXMLParse(xmlStr);
        prepay_id = (String) map.get("prepay_id");
        result.put("prepay_id",prepay_id);
        }catch ( Exception e){
            logger.error("error" , e);
        }
        // 预付商品id

       /* Map<String, String> paraMap = new HashMap<String, String>();
        paraMap.put("appid",  appid);
       paraMap.put("mch_id", mch_id);
        paraMap.put("nonce_str", noncestr);
        paraMap.put("body", "123");
        // 位数太少不行，需要修改规则，调整order_id的生成规则
        paraMap.put("out_trade_no", order_id);
        paraMap.put("total_fee", price);
        paraMap.put("spbill_create_ip", ip);
        paraMap.put("notify_url", "http://h5.ciaotalking.com/server/apppaynotify");
        paraMap.put( "trade_type", "JSAPI");
        paraMap.put( "openid", openid);
        paraMap.put("attach", "123456"+";"+userID);
        Map<String, String> payMap = new HashMap<String, String>();
        payMap.put("appId", appid);
        payMap.put("timeStamp",timestamp);
        payMap.put("nonceStr",noncestr );
        payMap.put("package", "prepay_id=" + prepay_id);
        payMap.put("signType", "MD5");
        String sign =  getSign(payMap, pay_sign);
        paraMap.put("sign", sign);
        String xml =  ArrayToXml(paraMap, false);*/
     /*   try {

        xmlStr = HttpUtils.post(pay_url, xml);
            logger.debug("from wx xml "+xmlStr);
        // 预付商品id
            String prepay_id = "";
            Map<String, String> map = doXMLParse(xmlStr);
            prepay_id = (String) map.get("prepay_id");
             result.put("sign",sign);
             result.put("prepay_id",prepay_id);
        }catch (Exception e){
            logger.error("error" , e);
        }*/
             return  prepay_id;
    }
    /*获取签名
    * MD5加密*/
    public static  String getSign(Map<String, String> params, String paternerKey)  {
        String string1 = createSign(params, false);
        String stringSignTemp = string1 + "&key=" + paternerKey;
        String signValue =  DigestUtils.md5Hex(stringSignTemp).toUpperCase();
        return signValue;
    }
    /*
    * 构造签名*/
    public static String createSign(Map<String, String> params, boolean encode)  {
        Set<String> keysSet = params.keySet();
        Object[] keys = keysSet.toArray();
        Arrays.sort(keys);
        StringBuffer temp = new StringBuffer();
        boolean first = true;
        for (Object key : keys) {
            if (key == null || StringUtil.isEmpty(params.get(key))) // 参数为空参与签名
                continue;
            if (first) {
                first = false;
            } else {
                temp.append("&");
            }
            temp.append(key).append("=");
            Object value = params.get(key);
            String valueString = "";
            if (null != value) {
                valueString = value.toString();
            }
            if (encode) {
                try {
                    temp.append(URLEncoder.encode(valueString, "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            } else {
                temp.append(valueString);
            }
        }
        return temp.toString();
    }
    public static String ArrayToXml(Map<String, String> parm, boolean isAddCDATA) {
        StringBuffer strbuff = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><xml>");
        if (parm != null && !parm.isEmpty()) {
            for (Map.Entry<String, String> entry : parm.entrySet()) {
                strbuff.append("<").append(entry.getKey()).append(">");
                if (isAddCDATA) {
                    strbuff.append("<![CDATA[");
                    if (StringUtil.isNotEmpty(entry.getValue())) {
                        strbuff.append(entry.getValue());
                    }
                    strbuff.append("]]>");
                } else {
                    if (StringUtil.isNotEmpty(entry.getValue())) {
                        strbuff.append(entry.getValue());
                    }
                }
                strbuff.append("</").append(entry.getKey()).append(">");
            }
        }
        return strbuff.append("</xml>").toString();
    }
    public  static String inputStream2String(InputStream in) throws IOException {
        if(in == null)
            return "";
        StringBuffer out = new StringBuffer();
        byte[] b = new byte[4096];
        for (int n; (n = in.read(b)) != -1;) {
            out.append(new String(b, 0, n, "UTF-8"));
        }
        return out.toString();
    }
    public  static Map<String, String> doXMLParse(String xml)  {

        InputStream inputStream = new ByteArrayInputStream(xml.getBytes());

        Map<String, String> map = null;
try {
        XmlPullParser pullParser =  XmlPullParserFactory.newInstance().newPullParser();
          pullParser.setInput(inputStream, "UTF-8"); // 为xml设置要解析的xml数据

        int eventType = pullParser.getEventType();

        while (eventType != XmlPullParser.END_DOCUMENT) {
            switch (eventType) {
                case XmlPullParser.START_DOCUMENT:
                    map = new HashMap<String, String>();
                    break;

                case XmlPullParser.START_TAG:
                    String key = pullParser.getName();
                    if (key.equals("xml"))
                        break;

                    String value = pullParser.nextText();
                    map.put(key, value);

                    break;

                case XmlPullParser.END_TAG:
                    break;

            }

            eventType = pullParser.next();

        }

}catch (Exception e){
    e.printStackTrace();
}
        return map;
    }
}
     /*   public static void main(String[] args) throws Exception {
        String code="00125WT821T11O0ajeR82Kr0U8225WTW";
        Map<String ,String > map= getOpenid(code);
        String   authMap =getUserInfo(map.get("openid"),map.get("acess_token"));
        }
*/
