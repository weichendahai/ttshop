package com.baomidou.springwind.entity.vo;

/**
 * Created by Woody on 2017/5/2.
 * 商品编辑页面使用的商品规格描述模型类
 */
public class ItemPropertyModel {
    /**
     * 规格类型
     */
    private Integer propertyKey;

    /**
     * 规格类型名称
     */
    private String propertyName;

    /**
     * 规格的值，使用逗号分隔
     */
    private String propertyValues;

    /**
     * 规格的描述，使用空格分隔
     */
    private String propertyDescs;

    public Integer getPropertyKey() {
        return propertyKey;
    }

    public void setPropertyKey(Integer propertyKey) {
        this.propertyKey = propertyKey;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getPropertyValues() {
        return propertyValues;
    }

    public void setPropertyValues(String propertyValues) {
        this.propertyValues = propertyValues;
    }

    public String getPropertyDescs() {
        return propertyDescs;
    }

    public void setPropertyDescs(String propertyDescs) {
        this.propertyDescs = propertyDescs;
    }
}
