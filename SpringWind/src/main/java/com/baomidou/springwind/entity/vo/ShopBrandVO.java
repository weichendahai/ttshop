package com.baomidou.springwind.entity.vo;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;

/**
 * 品牌信息VO
 * Created by Woody on 2017/4/6.
 */
public class ShopBrandVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String manufactor;
    private String brandName;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 厂家
     */
    public String getManufactor() {
        return manufactor;
    }

    public void setManufactor(String manufactor) {
        this.manufactor = manufactor;
    }

    /**
     * 品牌名称
     */
    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }
}
