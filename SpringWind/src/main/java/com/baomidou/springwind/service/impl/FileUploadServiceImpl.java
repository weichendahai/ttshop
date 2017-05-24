package com.baomidou.springwind.service.impl;

import com.baomidou.springwind.mapper.ServerMapper;
import com.baomidou.springwind.service.FileUploadService;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Decoder;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * <p>
 * 设置首页banner表 服务实现类
 * </p>
 *
 * @author wangzhen
 * @since 2017-03-23
 */
@Service
public class FileUploadServiceImpl implements FileUploadService {
    @Autowired
    private ServerMapper serverMapper ;
/*
*
ftp://192.168.1.222
webadmin
123456
*/
    @Override
    public String getImagePath(String imgStr, String sufix) {
        /*
        *  String imgFilePath = "http://192.168.1.222/shopimg/"+ new Date()+"/"+ UUID.randomUUID()+"."+sufix;
        *  */

        String fileName= UUID.randomUUID()+"."+sufix;
        String path="/shopimg";
        //将字符串转换成流
        InputStream input = new ByteArrayInputStream(generateImage(imgStr));
        boolean b=uploadFile("192.168.1.222",21,"webadmin","123456",path,fileName,input);
        return fileName;
    }
    public static boolean uploadFile(String url,int port,String username, String password, String path, String filename, InputStream input) {
        boolean success = false;
        FTPClient ftp = new FTPClient();
        try {
            int reply;
            ftp.connect(url, port);//连接FTP服务器
            //如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
            ftp.login(username, password);//登录
            reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                return success;
            }
           ftp.changeWorkingDirectory(path);
//            ftp.makeDirectory("test");
            ftp.storeFile(filename, input);
            input.close();
            ftp.logout();
            success = true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException ioe) {
                }
            }
        }
        return success;
    }
    public   byte[] generateImage(String imgStr) {
        if (imgStr == null)
        return null;
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            byte[] b = decoder.decodeBuffer(imgStr);
            for(int i=0;i<b.length;++i)
            {
                if(b[i]<0)
                {//调整异常数据
                    b[i]+=256;
                }
            }
            return b;
            } catch (Exception e) {
            return null;
            }
    }
}
