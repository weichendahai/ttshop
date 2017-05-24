package com.baomidou.springwind.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.springwind.entity.*;
import com.baomidou.springwind.mapper.*;
import com.baomidou.springwind.redis.SerializeAPI;
import com.baomidou.springwind.redis.redisUtil;
import com.baomidou.springwind.service.IShopDeliverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.Date;
import java.util.List;

/**
 *
 * Role 表数据服务层接口实现类
 *
 */
@Service
public class ShopDeliverSrviceImpl implements IShopDeliverService {
  @Autowired
  private ShopOrderMapper shopOrderMapper;
 @Autowired
 private ShopUserAddrMapper shopUserAddrMapper;
	@Autowired
	private ShopItemMapper shopItemMapper;
	@Autowired
	private ShopOrderItemMapper shopOrderItemMapper;
	@Autowired
	private ShopManufactorMapper shopManufactorMapper;
	@Autowired
	private ShopOrderItemPropertiesMapper shopOrderItemPropertiesMapper;
	@Autowired
	private ShopItemPropertiesMapper shopItemPropertiesMapper;
	@Autowired
	private ShopUserRefundMapper shopUserRefundMapper;
	@Autowired
	private ShopUserRefundFeedbackMapper shopUserRefundFeedbackMapper;
	private  static String auth_id1="1d03d316d855a560";
	private  static String auth_id2="re"+auth_id1;
	public  String getDeliveryOrders1() {
		return null;
	}

	@Override
	public String getDeliveryOrders(String auth_id) {
		JSONObject param =new JSONObject();
		try{
			//存入redis时间
			Jedis client =redisUtil.getJedis();
			if (client.exists(auth_id1)){
				String start=client.get(auth_id1);
				Date stardate=redisUtil.String2Date(start);
				long cd=new Date().getTime()-stardate.getTime();
				if (auth_id1.equals(auth_id)&& cd>60*1000) {
					JSONArray jb = new JSONArray();
					param.put("result", "0");
					List<ShopOrder> sos= shopOrderMapper.selectUnDeliver();
					JSONObject order=new JSONObject();
					JSONArray jorder =new JSONArray();
					JSONObject content=new JSONObject();
					for (ShopOrder so:sos) {
						order.put("order_id", so.getId());
						//根据user_id获取联系人信息
						ShopUserAddr sua = shopUserAddrMapper.selectById(so.getOrderAddrId());
						order.put("contact", SerializeAPI.toUtf8(sua.getContact()));
						order.put("address", SerializeAPI.toUtf8(sua.getAddress()));
						order.put("mobile_phone", sua.getMobilePhone());
						JSONObject items = new JSONObject();
						//获取订单的信息
						ShopOrderItem soi = new ShopOrderItem();
						soi.setOrderId(so.getId());
						List<ShopOrderItem> soims = shopOrderItemMapper.selectList(new EntityWrapper<ShopOrderItem>(soi));
						JSONArray jsonArray = new JSONArray();
						for (ShopOrderItem soim : soims) {
							ShopItem si = shopItemMapper.selectById(soim.getItemId());
							//order.put("items,ja);
							ShopManufactor sm = shopManufactorMapper.selectById(si.getManufactorId());
							ShopOrderItemProperties soips = new ShopOrderItemProperties();
							soips.setOrderItemId(soim.getId());
							List<ShopOrderItemProperties> Soips = shopOrderItemPropertiesMapper.selectList(new EntityWrapper<ShopOrderItemProperties>(soips));
							StringBuffer sb = new StringBuffer();
							int count = 0;
							for (ShopOrderItemProperties Soip : Soips) {
								count = Soip.getCount();
								ShopItemProperties SIP = new ShopItemProperties();
								SIP.setItemId(Soip.getItemId());
								SIP.setPropertyKey(Soip.getPropertyKey());
								SIP.setPropertyValue(Soip.getPropertyValue());
								ShopItemProperties SIPS = shopItemPropertiesMapper.selectOne(SIP);
								sb.append(SIPS.getPropertyDesc() + " ");
							}
							items.put("manufactor", SerializeAPI.toUtf8(sm.getManufactor()));
							items.put("item_name", SerializeAPI.toUtf8(si.getItemName()));
							items.put("item_property_desc", SerializeAPI.toUtf8(sb.toString()));
							items.put("item_count", count);
							String str = JSON.toJSONString(items, SerializerFeature.DisableCircularReferenceDetect);
							jsonArray.add(JSON.parseObject(str));
						}
						order.put("items",jsonArray);
						String str1 = JSON.toJSONString(order, SerializerFeature.DisableCircularReferenceDetect);
						jorder.add(JSON.parseObject(str1));
					}
					param.put("content",jorder);
					client.del(auth_id1);
					client.set(auth_id,redisUtil.Date2String(new Date()));
					return JSON.toJSONString(param);
				}else {
					param.put("result","1");
					if (auth_id1.equals(auth_id)){
						param.put("content",SerializeAPI.toUtf8("访问太频繁小于一分钟,稍后访问"));

					}else {
						param.put("content",SerializeAPI.toUtf8("无效的auth_id"+auth_id));

					}
				}
			}else {
				//判断时间
				JSONArray jb = new JSONArray();
				param.put("result", "0");
				List<ShopOrder> sos= shopOrderMapper.selectUnDeliver();
				JSONObject order=new JSONObject();
				JSONArray jorder =new JSONArray();
				JSONObject content=new JSONObject();
				for (ShopOrder so:sos) {
					order.put("order_id", so.getId());
					//根据user_id获取联系人信息
					ShopUserAddr sua = shopUserAddrMapper.selectById(so.getOrderAddrId());
					order.put("contact", SerializeAPI.toUtf8(sua.getContact()));
					order.put("address", SerializeAPI.toUtf8(sua.getAddress()));
					order.put("mobile_phone", sua.getMobilePhone());
					JSONObject items = new JSONObject();
					//获取订单的信息
					ShopOrderItem soi = new ShopOrderItem();
					soi.setOrderId(so.getId());
					List<ShopOrderItem> soims = shopOrderItemMapper.selectList(new EntityWrapper<ShopOrderItem>(soi));
					JSONArray jsonArray = new JSONArray();
					for (ShopOrderItem soim : soims) {
						ShopItem si = shopItemMapper.selectById(soim.getItemId());
						//order.put("items,ja);
						ShopManufactor sm = shopManufactorMapper.selectById(si.getManufactorId());
						ShopOrderItemProperties soips = new ShopOrderItemProperties();
						soips.setOrderItemId(soim.getId());
						List<ShopOrderItemProperties> Soips = shopOrderItemPropertiesMapper.selectList(new EntityWrapper<ShopOrderItemProperties>(soips));
						StringBuffer sb = new StringBuffer();
						int count = 0;
						for (ShopOrderItemProperties Soip : Soips) {
							count = Soip.getCount();
							ShopItemProperties SIP = new ShopItemProperties();
							SIP.setItemId(Soip.getItemId());
							SIP.setPropertyKey(Soip.getPropertyKey());
							SIP.setPropertyValue(Soip.getPropertyValue());
							ShopItemProperties SIPS = shopItemPropertiesMapper.selectOne(SIP);
							sb.append(SIPS.getPropertyDesc() + " ");
						}
						items.put("manufactor", SerializeAPI.toUtf8(sm.getManufactor()));
						items.put("item_name", SerializeAPI.toUtf8(si.getItemName()));
						items.put("item_property_desc", SerializeAPI.toUtf8(sb.toString()));
						items.put("item_count", count);
						String str = JSON.toJSONString(items, SerializerFeature.DisableCircularReferenceDetect);
						jsonArray.add(JSON.parseObject(str));
					}
					order.put("items",jsonArray);
					String str1 = JSON.toJSONString(order, SerializerFeature.DisableCircularReferenceDetect);
					jorder.add(JSON.parseObject(str1));
				}
				param.put("content",jorder);
				client.set(auth_id,redisUtil.Date2String(new Date()));
			}
			redisUtil.returnResource(client);
			return  JSON.toJSONString(param);
		}catch (Exception e){
        e.printStackTrace();
			return JSON.toJSONString("调用接口是错误");
		}
	}
/**/
	@Override
	public String orderdeliver(String order_id, String express_code) {
		//发货操作
		JSONObject param=new JSONObject();
		param.put("result","0");
		//修改订单状态，将快递单号插入订单表
		ShopOrder so=new ShopOrder();
		so.setId(Long.parseLong(order_id));
		so.setOrderState(2);
		so.setExpressCode(express_code);
		shopOrderMapper.updateById(so);
		param.put("content","");
		String str=JSON.toJSONString(param);
		return str;
	}
/*退货*/
	@Override
	public String refundFeedBack(String refund_id, String type, String message) {
		//发货操作
		JSONObject param=new JSONObject();
		try{
			param.put("result","0");
			//修改退货列表的状态
			ShopUserRefund sur=new ShopUserRefund();
			sur.setId(Integer.parseInt(refund_id));
			if (type.isEmpty()==false&&Integer.parseInt(type)==0){
				sur.setRefundState(1);
			}else {
				sur.setRefundState(2);
			}
		shopUserRefundMapper.updateById(sur);
			ShopUserRefundFeedback surf=new ShopUserRefundFeedback();
			surf.setUserRefundId(Integer.parseInt(refund_id));
			surf.setCreateDate(new Date());
			surf.setMessage(message);
			surf.setFrom(2);
			surf.setImageAddr(null);
			shopUserRefundFeedbackMapper.insert(surf);
			param.put("content","");
			String str=JSON.toJSONString(param);
			return str;
		}catch (Exception e){
			e.printStackTrace();
			param.put("result",1);
			param.put("content",SerializeAPI.toUtf8("退货访问异常"));
			return JSON.toJSONString(param);
		}
	}

	@Override
	public String getUserreFunds(String auth_id) {
		JSONObject param=new JSONObject();
		try{
           Jedis client=redisUtil.getJedis();
			if (client.exists(auth_id2)){
				String start=client.get(auth_id2);
				Date stardate=redisUtil.String2Date(start);
				long cd=new Date().getTime()-stardate.getTime();
				if (auth_id1.equals(auth_id)&& cd>60*1000) {
					List <ShopUserRefund> surs=shopUserRefundMapper.selectBytype();
					JSONObject reOrder=new JSONObject();
					JSONArray reOder=new JSONArray();
					param.put("result",0);
					for (ShopUserRefund sur:surs){
						JSONArray jsonArray=new JSONArray();
						JSONObject items=new JSONObject();
						reOrder.put("refund_id",sur.getId());
						ShopOrder so=shopOrderMapper.selectById(sur.getOrderId());
						ShopUserAddr sua=shopUserAddrMapper.selectById(so.getOrderAddrId());
						reOrder.put("contact",SerializeAPI.toUtf8(sua.getContact()));
						reOrder.put("address",SerializeAPI.toUtf8(sua.getAddress()));
						reOrder.put("mobile_phone",sua.getMobilePhone());
						reOrder.put("message",SerializeAPI.toUtf8(sur.getInfo()));
						reOrder.put("image_addr",sur.getImageAddr());
						ShopOrderItemProperties s1=new ShopOrderItemProperties();
						s1.setOrderItemId((long)sur.getOrderItemId());
					List<ShopOrderItemProperties>  SOIPs=shopOrderItemPropertiesMapper.selectList(new EntityWrapper<ShopOrderItemProperties>(s1));
						int count = 0;
						StringBuffer sb=new StringBuffer();
						for (ShopOrderItemProperties Soip : SOIPs) {
							count = Soip.getCount();
							ShopItemProperties SIP = new ShopItemProperties();
							SIP.setItemId(Soip.getItemId());
							SIP.setPropertyKey(Soip.getPropertyKey());
							SIP.setPropertyValue(Soip.getPropertyValue());
							ShopItemProperties SIPS = shopItemPropertiesMapper.selectOne(SIP);
							sb.append(SIPS.getPropertyDesc() + " ");
						}
						ShopItem si = shopItemMapper.selectById(sur.getItemId());
						//order.put("items,ja);
						ShopManufactor sm = shopManufactorMapper.selectById(si.getManufactorId());
						items.put("manufactor", SerializeAPI.toUtf8(sm.getManufactor()));
						items.put("item_name", SerializeAPI.toUtf8(si.getItemName()));
						items.put("item_property_desc", SerializeAPI.toUtf8(sb.toString()));
						items.put("item_count", count);
						String str = JSON.toJSONString(items, SerializerFeature.DisableCircularReferenceDetect);
						jsonArray.add(JSON.parseObject(str));
						/*reOrder.put("items",)*/
						reOrder.put("items",jsonArray);
					}

					String str = JSON.toJSONString(reOrder, SerializerFeature.DisableCircularReferenceDetect);
					reOder.add(JSON.parseObject(str));
					param.put("content",reOder);
					return JSON.toJSONString(param);
				}else {
					param.put("result","1");
					if (auth_id2.equals("re"+auth_id)){
						param.put("content",SerializeAPI.toUtf8("访问太频繁小于一分钟,稍后访问"));

					}else {
						param.put("content",SerializeAPI.toUtf8("无效的auth_id"+auth_id));

					}
					return JSON.toJSONString(param);
				}
			}else {
				//redis不存在记录
				List <ShopUserRefund> surs=shopUserRefundMapper.selectBytype();
				JSONObject reOrder=new JSONObject();
				JSONArray reOder=new JSONArray();
				param.put("result",0);
				for (ShopUserRefund sur:surs){
					JSONArray jsonArray=new JSONArray();
					JSONObject items=new JSONObject();
					reOrder.put("refund_id",sur.getId());
					ShopOrder so=shopOrderMapper.selectById(sur.getOrderId());
					ShopUserAddr sua=shopUserAddrMapper.selectById(so.getOrderAddrId());
					reOrder.put("contact",SerializeAPI.toUtf8(sua.getContact()));
					reOrder.put("address",SerializeAPI.toUtf8(sua.getAddress()));
					reOrder.put("mobile_phone",sua.getMobilePhone());
					reOrder.put("message",SerializeAPI.toUtf8(sur.getInfo()));
					reOrder.put("image_addr",sur.getImageAddr());
					ShopOrderItemProperties s1=new ShopOrderItemProperties();
					s1.setOrderItemId((long)sur.getOrderItemId());
					List<ShopOrderItemProperties>  SOIPs=shopOrderItemPropertiesMapper.selectList(new EntityWrapper<ShopOrderItemProperties>(s1));
					int count = 0;
					StringBuffer sb=new StringBuffer();
					for (ShopOrderItemProperties Soip : SOIPs) {
						count = Soip.getCount();
						ShopItemProperties SIP = new ShopItemProperties();
						SIP.setItemId(Soip.getItemId());
						SIP.setPropertyKey(Soip.getPropertyKey());
						SIP.setPropertyValue(Soip.getPropertyValue());
						ShopItemProperties SIPS = shopItemPropertiesMapper.selectOne(SIP);
						sb.append(SIPS.getPropertyDesc() + " ");
					}
					ShopItem si = shopItemMapper.selectById(sur.getItemId());
					//order.put("items,ja);
					ShopManufactor sm = shopManufactorMapper.selectById(si.getManufactorId());
					items.put("manufactor", SerializeAPI.toUtf8(sm.getManufactor()));
					items.put("item_name", SerializeAPI.toUtf8(si.getItemName()));
					items.put("item_property_desc", SerializeAPI.toUtf8(sb.toString()));
					items.put("item_count", count);
					String str = JSON.toJSONString(items, SerializerFeature.DisableCircularReferenceDetect);
					jsonArray.add(JSON.parseObject(str));
					reOrder.put("items",jsonArray);
				}

				String str = JSON.toJSONString(reOrder, SerializerFeature.DisableCircularReferenceDetect);
				reOder.add(JSON.parseObject(str));
				param.put("content",reOder);
				client.set(auth_id2, redisUtil.Date2String(new Date()));
			}
			redisUtil.returnResource(client);
			return JSON.toJSONString(param);
		}catch (Exception e){
			e.printStackTrace();
			param.put("result",1);
			param.put("content",SerializeAPI.toUtf8("调用退款列表时异常")) ;
			return  JSON.toJSONString(param);
		}
	}

}