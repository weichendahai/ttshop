package com.baomidou.springwind.entity.vo;

import com.baomidou.springwind.entity.ShopItem;
import com.baomidou.springwind.entity.ShopItemPrice;

import java.util.List;

/**
 * Created by Woody on 2017/5/3.
 * 页面修改商品信息使用Model
 */
public class ItemEditModel {
    private ShopItem shopItem;
    private List<ItemPropertyModel> itemPropertyModels;
    private List<ShopItemPrice> shopItemPrices;

    public ShopItem getShopItem() {
        return shopItem;
    }

    public void setShopItem(ShopItem shopItem) {
        this.shopItem = shopItem;
    }

    public List<ItemPropertyModel> getItemPropertyModels() {
        return itemPropertyModels;
    }

    public void setItemPropertyModels(List<ItemPropertyModel> itemPropertyModels) {
        this.itemPropertyModels = itemPropertyModels;
    }

    public List<ShopItemPrice> getShopItemPrices() {
        return shopItemPrices;
    }

    public void setShopItemPrices(List<ShopItemPrice> shopItemPrices) {
        this.shopItemPrices = shopItemPrices;
    }
}
