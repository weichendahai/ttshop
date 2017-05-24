package com.baomidou.springwind.service;

/**
 * <p>
 * 品牌表 服务类
 * </p>
 *
 * @author Yanghu
 * @since 2017-03-21
 */
public interface FileUploadService{
  //图片存放ftp服务器
   String  getImagePath(String imgStr,String sufix);
}
