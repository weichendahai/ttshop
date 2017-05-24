package com.baomidou.springwind.entity.po;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.springwind.entity.ShopItem;

import java.io.Serializable;

/**
 * Created by Server on 2017/4/4.
 */
public class PoBanner implements Serializable {
    private Integer id;
    /**
     * 商品id
     */


    private String itemName;


    /**
     * 排序因子
     */
    @TableField("sort_factor")
    private Integer sortFactor;
    /**
     * 海报地址
     */
    @TableField("poster_image_addr")
    private String posterImageAddr;

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public Integer getSortFactor() {
        return sortFactor;
    }

    public void setSortFactor(Integer sortFactor) {
        this.sortFactor = sortFactor;
    }

    public String getPosterImageAddr() {
        return posterImageAddr;
    }

    public void setPosterImageAddr(String posterImageAddr) {
        this.posterImageAddr = posterImageAddr;
    }
}
