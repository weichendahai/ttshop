package com.baomidou.springwind.service.impl;

import com.baomidou.springwind.entity.ShopItem;
import com.baomidou.springwind.entity.ShopItemProperties;
import com.baomidou.springwind.entity.ShopOrderItem;
import com.baomidou.springwind.entity.ShopOrderItemProperties;
import com.baomidou.springwind.entity.po.PoShopOrderItem;
import com.baomidou.springwind.mapper.ShopItemMapper;
import com.baomidou.springwind.mapper.ShopItemPropertiesMapper;
import com.baomidou.springwind.mapper.ShopOrderItemMapper;
import com.baomidou.springwind.mapper.ShopOrderItemPropertiesMapper;
import com.baomidou.springwind.service.IShopOrderItemService;
import com.baomidou.springwind.service.support.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 订单商品关系表 服务实现类
 * </p>
 *
 * @author Yanghu
 * @since 2017-03-21
 */
@Service
public class ShopOrderItemServiceImpl extends BaseServiceImpl<ShopOrderItemMapper, ShopOrderItem> implements IShopOrderItemService {

    @Autowired
    private ShopOrderItemMapper shopOrderItemMapper;

    @Autowired
    private ShopItemPropertiesMapper shopItemPropertiesMapper;

    @Autowired
    private ShopOrderItemPropertiesMapper shopOrderItemPropertiesMapper;

    @Override
    public List<ShopOrderItem> selectOrderItemListByOrderId(Long orderId) {
        List<ShopOrderItem> list = shopOrderItemMapper.selectOrderItemListByOrderId(orderId);
        return list;
    }

    @Override
    public List<PoShopOrderItem> selectMultiOrderItemList(Map<String, Object> map) {
        List<PoShopOrderItem> list = baseMapper.selectMultiOrderItemList(map);
        List<PoShopOrderItem> properties = baseMapper.selectMultiOrderItemPropertiesList(map);
        for(PoShopOrderItem poSoi:list){
            map.put("itemId",poSoi.getItemId());
            /*poSoi.setCount(baseMapper.selectMultiOrderItemCount(map));*/
            StringBuilder sb = new StringBuilder();
            Map<String,Object> map1 = new HashMap<String, Object>();
            map1.put("item_id",poSoi.getItemId());
            map1.put("order_id",poSoi.getOrderId());
            List<ShopOrderItemProperties> orderItemPropertiesList = shopOrderItemPropertiesMapper.selectByMap(map1);
            /*Map<String, Object> map1 = new HashMap<String, Object>();
            map1.put("item_id",poSoi.getItemId());*/
            /*List<ShopItemProperties> propertiesList = shopItemPropertiesMapper.selectByMap(map1);*/
            for(ShopOrderItemProperties p:orderItemPropertiesList){
                    Map<String,Object> map2 = new HashMap<String, Object>();
                        map2.put("item_id",p.getItemId());
                        map2.put("property_key",p.getPropertyKey());
                        map2.put("property_value",p.getPropertyValue());
                List<ShopItemProperties> propertiesList = shopItemPropertiesMapper.selectByMap(map2);
                if(propertiesList!=null&&propertiesList.size()>0){
                    sb.append(propertiesList.get(0).getPropertyDesc()+",");
                }
            }
            /*for(ShopOrderItemProperties soip:orderItemPropertiesList){
                if((soip.getOrderId()==poSoi.getOrderId())&&(soip.getItemId()==poSoi.getItemId())){
                    count+=soip.getCount();
                }
            }*/
            poSoi.setCount(orderItemPropertiesList.get(0).getCount());
            poSoi.getShopItemProperties().setPropertyDesc(sb.toString().substring(0,sb.toString().length()-1));
        }
        return list;
    }

}
