package com.baomidou.springwind.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.springwind.entity.*;
import com.baomidou.springwind.mapper.*;
import com.baomidou.springwind.redis.DateUtils;
import com.baomidou.springwind.redis.SerializeAPI;
import com.baomidou.springwind.redis.redisUtil;
import com.baomidou.springwind.service.IShopCartService;
import com.baomidou.springwind.wx.MeizhuangWx;
import com.baomidou.springwind.wx.SHAWX;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.*;

/**
 *
 * Role 表数据服务层接口实现类
 *
 */
@Service
public class ShopCarteSrviceImpl  implements IShopCartService {
	@Autowired
	private ShopItemMapper shopItemMapper;
	@Autowired
	private ShopItemPropertiesMapper shopItemPropertiesMapper;
	@Autowired
	private ShopOrderMapper shopOrderMapper;
	@Autowired
	private ShopGiftCardMapper shopGiftCardMapper;
	@Autowired
	private ShopCouponMapper shopCouponMapper;
	@Autowired
	private ShopCouponConditionMapper shopCouponConditionMapper;
	@Autowired
	private ShopUserMapper shopUserMapper;
	@Autowired
	private ShopUserAddrMapper shopUserAddrMapper;
	@Autowired
	private ShopUserPromoMapper shopUserPromoMapper;
	@Autowired
	private ShopEvaluateSharedMapper shopEvaluateSharedMapper;
	@Autowired
	private ShopItemEvaluateMapper shopItemEvaluateMapper;
	@Autowired
	private ShopEvaluateBonusMapper shopEvaluateBonusMapper;
	@Autowired
	private ShopSharedBonusMapper shopSharedBonusMapper;
   @Autowired
   private ShopOrderItemMapper shopOrderItemMapper;
	@Autowired
	private ShopOrderItemPropertiesMapper shopOrderItemPropertiesMapper;
	@Autowired
	private ShopItemPriceMapper shopItemPriceMapper;
	@Autowired
	private ShopOptionMapper shopOptionMapper;
	@Autowired
	private ShopItemUserDistributionMapper shopItemUserDistributionMapper;
	/*
	* 添加购物车操作*/
	@Override
	public String addItemtoCart(String user_id, String item_id, String evaluate_shared_id, String property_key, String property_value) {
		JSONObject param = new JSONObject();
		JSONObject content = new JSONObject();
		JSONObject propertys = new JSONObject();
		JSONArray property = new JSONArray();
		try {
			ShopCartInfo shopCartInfo = new ShopCartInfo();
			shopCartInfo.setItemId(item_id);
			shopCartInfo.setEndTime(redisUtil.getFormat(20 * 60));
			shopCartInfo.setEvaluateUserId(evaluate_shared_id);
			ShopItem shopItem = shopItemMapper.selectById(item_id);
			if(property_key== null || property_key.length() <= 0){
				if ("".equals(evaluate_shared_id)||evaluate_shared_id==null){
					shopCartInfo.setItemPrice(shopItem.getPrice());
				}else {
					//过滤他自己分享
					ShopEvaluateShared SES= shopEvaluateSharedMapper.selectById(evaluate_shared_id);
					if(SES.getUserId()==Long.parseLong(user_id)){
						shopCartInfo.setItemPrice(shopItem.getPrice());
					}else {
						int rate=shopItem.getDiscountRate();
						int value1=shopItem.getDiscountValue();
						int price =shopItem.getPrice();
						int discount=DateUtils.getDiscount(price,rate,value1);
						shopCartInfo.setItemPrice(discount);
					}
				}
				shopCartInfo.setItemCount(1);
			}else {
				//解析对应的key，value
				String[] key = property_key.trim().split("-");
				String[] value = property_value.trim().split("-");
				StringBuffer sb = new StringBuffer();
				for (int i = 0; i < key.length; i++) {
					ShopItemProperties ps = shopItemPropertiesMapper.selectByKey(item_id, key[i], value[i]);
					propertys.put("propertyKey", ps.getPropertyKey());
					propertys.put("PropertyValue", ps.getPropertyValue());
					//将字符串拼接
					sb.append(ps.getPropertyKey() + ":" + ps.getPropertyValue() + "/");
					propertys.put("propertyDesc", ps.getPropertyDesc());
					String str12 = JSON.toJSONString(propertys, SerializerFeature.DisableCircularReferenceDetect);
					property.add(JSON.parseObject(str12));
				}
				//ShopItemProperties ps= shopItemPropertiesMapper.selectByKey(item_id, property_key,property_value);
				shopCartInfo.setItemPropertys(property);
			//获取商品数量和价格
			shopCartInfo.setItemCount(1);
			//根据品型号获取价格
			ShopItemPrice shopItemPrice=new ShopItemPrice();
			shopItemPrice.setItemId(Integer.parseInt(item_id));
			shopItemPrice.setPropertyPath(sb.substring(0,sb.length()-1));
            ShopItemPrice Sip= shopItemPriceMapper.selectOne(shopItemPrice);
			if ("".equals(evaluate_shared_id)||evaluate_shared_id==null){
				shopCartInfo.setItemPrice(Sip.getPrice());
			}else {
				//过滤他自己分享
				ShopEvaluateShared SES= shopEvaluateSharedMapper.selectById(evaluate_shared_id);
				if(SES.getUserId()==Long.parseLong(user_id)){
					shopCartInfo.setItemPrice(Sip.getPrice());
				}else {
					int rate=shopItem.getDiscountRate();
					int value1=shopItem.getDiscountValue();
					int price =Sip.getPrice();
					int discount=DateUtils.getDiscount(price,rate,value1);
					shopCartInfo.setItemPrice(discount);
				}

			}
			}
			shopCartInfo.setItemName(shopItem.getItemName());
			shopCartInfo.setItemIcon(shopItem.getItemIconAddr());
/*
			if (shopItem.getDiscountValue() != null && shopItem.getDiscountValue() > 0) {
				shopCartInfo.setItemPrice(shopItem.getPrice() - shopItem.getDiscountValue());
			} else if (shopItem.getDiscountRate() != null && shopItem.getDiscountRate() > 0) {
				if (shopItem.getPrice() * shopItem.getDiscountRate() % 100 < 50) {
					shopCartInfo.setItemPrice(shopItem.getPrice() * shopItem.getDiscountRate() / 100);
				} else {
					shopCartInfo.setItemPrice(shopItem.getPrice() * shopItem.getDiscountRate() / 100 + 1);
				}
			} else {
				shopCartInfo.setItemPrice(shopItem.getPrice());
			}*/
			//查询数据库user_id 对应的值
			String id = (item_id + "," + property_key + "," + property_value).trim();
			shopCartInfo.setCartItemId(id);
			HashMap<String, String> cartMap = new HashMap<String, String>();
			String str = "cart_" + user_id;
			Jedis client = redisUtil.getJedis();
			String JsonStr = JSON.toJSONString(shopCartInfo);
			//判断购物车的数据是否存在记录存在+1，不存在写入
			Set<String> keys = client.hkeys(str);
			if (keys.contains(id)) {
				//获取对象
				String sci = client.hget(str, id);
				ShopCartInfo shopCartInfo1 = JSON.parseObject(sci, ShopCartInfo.class);
				int nowCount = shopCartInfo1.getItemCount()+ 1;
				shopCartInfo1.setItemCount(nowCount);
				String cunz = JSON.toJSONString(shopCartInfo1);
				cartMap.put(id, cunz);
			} else {
				cartMap.put(id, JsonStr);
			}
			client.hmset(str, cartMap);
			redisUtil.returnResource(client);
			//数据以map形势存入数据;
			param.put("result", "0");
			content.put("item_id", user_id);
			content.put("end_time", redisUtil.getFormat(1 * 60 * 60));
			content.put("evaluate_shared_id", evaluate_shared_id);
			param.put("content", content);
			return JSON.toJSONString(param);
		} catch (Exception e) {
			e.printStackTrace();
			param.put("result", "1");
			return JSON.toJSONString(param);
		}
	}

	//获取购物车信息
	@Override
	public String getCartItems(String user_id) {
		JSONObject param = new JSONObject();
		JSONObject content = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		try {
			String str = "cart_" + user_id.toString();
			param.put("result", "0");
			Jedis client = redisUtil.getJedis();
			//根据用户id取获取所有的对应产品信息
			List<String> allItem = client.hvals(str);
			List<ShopCartInfo> lists = JSON.parseArray(allItem.toString(), ShopCartInfo.class);
			if ("".equals(lists) || lists == null) {
				param.put("content", null);
			} else {
				for (ShopCartInfo list : lists) {
					JSONObject cartO = new JSONObject();

					//判断是否倒计时结束
					Long endDate = redisUtil.String2Date(list.getEndTime()).getTime();
					if (endDate <= new Date().getTime()) {
						client.hdel(str, list.getCartItemId());
					} else {
						cartO.put("cart_item_id", list.getCartItemId());
						cartO.put("item_id", list.getItemId());
						cartO.put("item_name", SerializeAPI.toUtf8(list.getItemName()));
						cartO.put("item_icon", list.getItemIcon());
						cartO.put("item_price", list.getItemPrice());
						cartO.put("item_count", list.getItemCount());
						cartO.put("shared_user_id", list.getEvaluateUserId());
						if (list.getItemPropertys()==null){
							cartO.put("item_propertys", "");
						}else {
							List<ShopItemProperties> pros = JSON.parseArray(list.getItemPropertys().toString(), ShopItemProperties.class);
							JSONObject propertys = new JSONObject();
							JSONArray property = new JSONArray();
							for (ShopItemProperties pro : pros) {
								propertys.put("item_property_key", pro.getPropertyKey());
								propertys.put("item_property_value", pro.getPropertyValue());
								propertys.put("item_property_desc", SerializeAPI.toUtf8(pro.getPropertyDesc()));
								String str12 = JSON.toJSONString(propertys, SerializerFeature.DisableCircularReferenceDetect);
								property.add(JSON.parseObject(str12));
							}
							cartO.put("item_propertys", property);
						}
						cartO.put("end_time", redisUtil.String2Date(list.getEndTime()));

						String str12 = JSON.toJSONString(cartO, SerializerFeature.DisableCircularReferenceDetect);
						jsonArray.add(JSON.parseObject(str12));
					}
				}
				redisUtil.returnResource(client);
				content.put("items", jsonArray);
				param.put("content", content);
			}
			return JSON.toJSONString(param);
		} catch (Exception e) {
			e.printStackTrace();
			param.put("result", "1");
			return JSON.toJSONString(param);
		}
	}

	/*
	* 修改购物车数量*/
	@Override
	public String modifyCartItem(String user_id, String item_count, String cartItemId) {
		//根据用户信息获取用户对应的修改数据
		JSONObject param = new JSONObject();
		String str = "cart_" + user_id;
		try {
			param.put("result", "0");
			Jedis client = redisUtil.getJedis();
			//定位对应产品
			if (Integer.parseInt(item_count) == 0) {
				client.hdel(str, cartItemId);
			} else {
				String item = client.hget(str, cartItemId.trim());
				ShopCartInfo shopCartInfo1 = JSON.parseObject(item, ShopCartInfo.class);
				shopCartInfo1.setItemCount(Integer.parseInt(item_count));
				String cunz = JSON.toJSONString(shopCartInfo1);
				Map<String, String> cartMap = new HashMap<String, String>();
				cartMap.put(cartItemId, cunz);
				client.hmset(str, cartMap);
			}
			redisUtil.returnResource(client);
			param.put("content", "");
			return JSON.toJSONString(param);
		} catch (Exception e) {
			e.printStackTrace();
			param.put("result", "1");
			return JSON.toJSONString(param);
		}
	}

	/*
    *停止购物车时间
    * */
	@Override
	public String stopCartCd(String user_id, String cart_item_ids) {
		//获取所有选择的商品信息
		//double start=System.currentTimeMillis();
		JSONObject param = new JSONObject();
		JSONObject content = new JSONObject();
		JSONObject item1 = new JSONObject();
		JSONArray items = new JSONArray();
		JSONObject hb = new JSONObject();
		JSONArray hb1 = new JSONArray();
		JSONObject yx = new JSONObject();
		JSONArray yx1 = new JSONArray();
		JSONArray conditions = new JSONArray();
		JSONObject condition = new JSONObject();
		try {
			//商品总的价格
			int cart_price=0;
			//获取购物车中的对应产品
			String str = "cart_" + user_id;
			String date = redisUtil.getFormat(0);
			String oder = "order_" + user_id;
			param.put("result", "0");
			//根据用户id取得用户的信息
			ShopUser su = shopUserMapper.selectById(user_id);
			ShopUserAddr sa = shopUserAddrMapper.selectById(su.getDefaultAddr());
			content.put("contact", SerializeAPI.toUtf8(sa.getContact()));
			content.put("default_addr", su.getDefaultAddr());
			content.put("address",SerializeAPI.toUtf8(sa.getAddress()));
			content.put("mobile_phone", sa.getMobilePhone());
			String[] ids = cart_item_ids.trim().split("#");
			Jedis client = redisUtil.getJedis();
			for (int i = 0; i < ids.length; i++) {
				//获取购物车中的物品
				String item = client.hget(str, ids[i]);
				client.set(oder, cart_item_ids + "!" + date);
				ShopCartInfo SCI = JSON.parseObject(item, ShopCartInfo.class);
				if (SCI == null) {
					content.put("items", SerializeAPI.toUtf8("购物车中没有商品"));
				} else {
					item1.put("item_id", SCI.getItemId());
					item1.put("item_name",SerializeAPI.toUtf8(SCI.getItemName()));
					item1.put("item_icon", SCI.getItemIcon());
					if(null==SCI.getItemPropertys() || "".equals(SCI.getItemPropertys())){
						item1.put("item_propertys", "");
					}else {
					List<ShopItemProperties> pros = JSON.parseArray(SCI.getItemPropertys().toString(),ShopItemProperties.class);
					JSONObject propertys=new JSONObject();
					JSONArray property=new JSONArray();
					for (ShopItemProperties pro:pros){
						propertys.put("item_property_key", pro.getPropertyKey());
						propertys.put("item_property_value", pro.getPropertyValue());
						propertys.put("item_property_desc", SerializeAPI.toUtf8(pro.getPropertyDesc()));
						String str12 = JSON.toJSONString(propertys, SerializerFeature.DisableCircularReferenceDetect);
						property.add(JSON.parseObject(str12));
					}
					item1.put("item_propertys", property);
					}
					item1.put("item_count", SCI.getItemCount());
					item1.put("item_price", SCI.getItemPrice());
					cart_price+=SCI.getItemCount()*SCI.getItemPrice();
					String str12 = JSON.toJSONString(item1, SerializerFeature.DisableCircularReferenceDetect);
					items.add(JSON.parseObject(str12));
				}
			}
			redisUtil.returnResource(client);
			content.put("items", items);
			ShopOption So1=new ShopOption();
			So1.setOptionKey(3);
			ShopOption SO=shopOptionMapper.selectById(So1);
			So1.setOptionKey(2);
			ShopOption SO2=shopOptionMapper.selectById(So1);
			if (cart_price>Integer.parseInt(SO.getOptionValue())){
				//去表中获取运费的数量
				content.put("freight", 0);
			}else {
				content.put("freight",SO2.getOptionValue());
			}
			//根据用户user_id获取红包和优惠券0:优惠券1：红包
			//获取红包
			int promo_type = 1;
			List<ShopUserPromo> gift = shopUserPromoMapper.getShopUserPromoByType(user_id, 0, promo_type);
			for (ShopUserPromo gift1 : gift) {
				ShopGiftCard sgc = shopGiftCardMapper.selectById(gift1.getPromoId());
				hb.put("gift_card_id", sgc.getId());
				hb.put("gift_card_name",SerializeAPI.toUtf8(sgc.getName()));
				hb.put("gift_card_balance", sgc.getBalance());
				String str13 = JSON.toJSONString(hb, SerializerFeature.DisableCircularReferenceDetect);
				hb1.add(JSON.parseObject(str13));
			}
			content.put("gift_cards", hb1);
			//获取优惠券
			promo_type = 0;
			List<ShopUserPromo> coupons = shopUserPromoMapper.getShopUserPromoByType(user_id, 0, promo_type);
			for (ShopUserPromo cou : coupons) {
				ShopCoupon sc = shopCouponMapper.selectById(cou.getPromoId());
				yx.put("coupon_id", sc.getId());
				yx.put("coupon_name", SerializeAPI.toUtf8(sc.getName()));
				//获取conditions
				List<ShopCouponCondition> Scc = shopCouponConditionMapper.selectByUserId(cou.getPromoId().toString());
				for (ShopCouponCondition scc1 : Scc) {
					condition.put("condition_type", scc1.getConditionType());
					condition.put("condition_desc",SerializeAPI.toUtf8(scc1.getConditionDesc()) );
					condition.put("parameter_first", scc1.getParameterFirst());
					condition.put("parameter_second", scc1.getParameterSecond());
					String str14 = JSON.toJSONString(condition, SerializerFeature.DisableCircularReferenceDetect);
					conditions.add(JSON.parseObject(str14));
				}
				yx.put("conditions", conditions);
				yx.put("grant_date", DateUtils.Date2String(cou.getGrantDate()));
				yx.put("end_date", DateUtils.Date2String(cou.getEndDate()));
				yx.put("state", cou.getState());
				String str13 = JSON.toJSONString(yx, SerializerFeature.DisableCircularReferenceDetect);
				yx1.add(JSON.parseObject(str13));
			}
			content.put("coupons", yx1);
			param.put("content", content);
			return JSON.toJSONString(param);
		} catch (Exception e) {
			e.printStackTrace();
			param.put("result", "1");
			return JSON.toJSONString(param);
		}
	}


	/*
	* 离开订单页面的时候计时重新开始
	* */
	@Override
	public String resumeCartcd(String user_id) {
		JSONObject param = new JSONObject();
		JSONObject content = new JSONObject();
		JSONArray out = new JSONArray();
		String str = "order_" + user_id;
		String str2 = "cart_" + user_id;
		String str14="buy_now"+user_id;
		try {
			Jedis client = redisUtil.getJedis();
			String order = client.get(str);
			if (client.exists(str14)){
				client.del(str14);
			}
			String[] str1 = order.trim().split("!");
			String[] ids = str1[0].trim().split("#");
			for (int i = 0; i < ids.length; i++) {
				//获取购物车中的物品
				String item = client.hget(str2, ids[i]);
				ShopCartInfo SCI = JSON.parseObject(item, ShopCartInfo.class);
				if (SCI == null) {
					param.put("content", SerializeAPI.toUtf8("购物车没有数据需要重新计时"));
				} else {
					String date = redisUtil.Date2String(new Date());
					String endTime = redisUtil.getCd(date, str1[1].toString(), SCI.getEndTime());
					SCI.setEndTime(endTime);
					String cunz = JSON.toJSONString(SCI);
					Map<String, String> cartMap = new HashMap<String, String>();
					cartMap.put(ids[i], cunz);
					client.hmset(str2, cartMap);
				}
			}
			client.del(str);
			redisUtil.returnResource(client);
			param.put("content",SerializeAPI.toUtf8( "重新计时"));
			param.put("result", "0");
			return JSON.toJSONString(param);
		} catch (Exception e) {
			e.printStackTrace();
			param.put("result", "1");
			return JSONObject.toJSONString(param);
		}

	}

	/*
    * 提交订单
    * */
	@Override
	public String submitOrder(String user_id, String order_addr_id, String user_coupon_id, String user_gift_card_id) {
		JSONObject param = new JSONObject();
		try {
			    int count = 0;
		     	param.put("result", "0");
			    Jedis client = redisUtil.getJedis();
			ShopOrder shopOrder = new ShopOrder();
			//不享受折扣
			String str12="buy_now0"+user_id;
			//享受折扣
			String str20="buy_now1"+user_id;
			int Freight = 0;
			if (client.exists(str12)||client.exists(str20)){
				//订单表生成一条数据
				String orderCode = redisUtil.getRound(user_id);
				shopOrder.setOrderCode(orderCode);
				shopOrder.setUserId(Long.parseLong(user_id));
				//1: 待付款2: 待发货3:待收货4:待评价5: 退款
				shopOrder.setOrderState(0);
				shopOrder.setExpressCode(null);
				//运费
				String str13=client.get(str12);
				String[] arr2=str13.split("#");
				//->
			     String[] arr=arr2[0].trim().split(",");
				StringBuffer sb=new StringBuffer();
				ShopItem shopItem = shopItemMapper.selectById1(arr[0]);
				if(arr.length>1){
					String[] arrkey=arr[1].split("-");
					String[] arrvalue=arr[2].split("-");
					for (int i=0;i<arrkey.length;i++){
						sb.append(arrkey[i]+":"+arrvalue[i]+"/");
					}
					ShopItemPrice shopItemPrice=new ShopItemPrice();
					shopItemPrice.setItemId(Integer.parseInt(arr[0]));
					shopItemPrice.setPropertyPath(sb.substring(0, sb.length()-1));
					ShopItemPrice Sipm=shopItemPriceMapper.selectOne(shopItemPrice);
					if (client.exists(str12)){
						count=Sipm.getPrice();
						client.del(str12);
					}else {
						//过滤他自己分享
						int rate=shopItem.getDiscountRate();
						int value=shopItem.getDiscountValue();
						int price =Sipm.getPrice();
						int discount=DateUtils.getDiscount(price,rate,value);
						count=discount;
						client.del(str20);
					}
			}else {
					if (client.exists(str12)){
						count=shopItem.getPrice();
						client.del(str12);
					}else {
						//过滤他自己分享
						int rate=shopItem.getDiscountRate();
						int value=shopItem.getDiscountValue();
						int price =shopItem.getPrice();
						int discount=DateUtils.getDiscount(price,rate,value);
						count=discount;
						client.del(str20);
					}
			}

				//向shop_item_user_distribution写入数据
				ShopItemUserDistribution SIUD=new ShopItemUserDistribution();
				SIUD.setItemId(shopItem.getId());
			ShopUser su=shopUserMapper.selectById(user_id);
				String str=DateUtils.Date2String(su.getDateOfBirth());
				Date birthDate=DateUtils.String2Date(str);
				int age=DateUtils.getAge(birthDate);
				ShopItemUserDistribution shopItemUserDistribution=new ShopItemUserDistribution();
				shopItemUserDistribution.setItemId(shopItem.getId());
			if (age>=0&&age<=18){
				shopItemUserDistribution.setType(1);
				shopItemUserDistribution.setSub(0);
				ShopItemUserDistribution SIUD1=shopItemUserDistributionMapper.selectOne(shopItemUserDistribution);
				ShopItemUserDistribution shopItemUserDistribution1=new ShopItemUserDistribution();
				shopItemUserDistribution1.setId(SIUD1.getId());
				shopItemUserDistribution1.setCount(SIUD1.getCount()+1);
                 shopItemUserDistributionMapper.updateById(shopItemUserDistribution1);
				}else if (age>18&&age<=24){
				shopItemUserDistribution.setType(1);
				shopItemUserDistribution.setSub(1);
				ShopItemUserDistribution SIUD1=shopItemUserDistributionMapper.selectOne(shopItemUserDistribution);
				ShopItemUserDistribution shopItemUserDistribution1=new ShopItemUserDistribution();
				shopItemUserDistribution1.setId(SIUD1.getId());
				shopItemUserDistribution1.setCount(SIUD1.getCount()+1);
				shopItemUserDistributionMapper.updateById(shopItemUserDistribution1);

			}else if (age>24&&age<=30){
				shopItemUserDistribution.setType(1);
				shopItemUserDistribution.setSub(2);
				ShopItemUserDistribution SIUD1=shopItemUserDistributionMapper.selectOne(shopItemUserDistribution);
				ShopItemUserDistribution shopItemUserDistribution1=new ShopItemUserDistribution();
				shopItemUserDistribution1.setId(SIUD1.getId());
				shopItemUserDistribution1.setCount(SIUD1.getCount()+1);
				shopItemUserDistributionMapper.updateById(shopItemUserDistribution1);


			}else if(age>30&&age<=39) {
				shopItemUserDistribution.setType(1);
				shopItemUserDistribution.setSub(3);
				ShopItemUserDistribution SIUD1=shopItemUserDistributionMapper.selectOne(shopItemUserDistribution);
				ShopItemUserDistribution shopItemUserDistribution1=new ShopItemUserDistribution();
				shopItemUserDistribution1.setId(SIUD1.getId());
				shopItemUserDistribution1.setCount(SIUD1.getCount()+1);
				shopItemUserDistributionMapper.updateById(shopItemUserDistribution1);
			}else {
				shopItemUserDistribution.setType(1);
				shopItemUserDistribution.setSub(4);
				ShopItemUserDistribution SIUD1=shopItemUserDistributionMapper.selectOne(shopItemUserDistribution);
				ShopItemUserDistribution shopItemUserDistribution1=new ShopItemUserDistribution();
				shopItemUserDistribution1.setId(SIUD1.getId());
				shopItemUserDistribution1.setCount(SIUD1.getCount()+1);
				shopItemUserDistributionMapper.updateById(shopItemUserDistribution1);
			}
				//shopItemUserDistributionMapper.insert();
				if (su.getSkinType()==0){
					shopItemUserDistribution.setType(0);
					shopItemUserDistribution.setSub(0);
					ShopItemUserDistribution SIUD1=shopItemUserDistributionMapper.selectOne(shopItemUserDistribution);
					ShopItemUserDistribution shopItemUserDistribution1=new ShopItemUserDistribution();
					shopItemUserDistribution1.setId(SIUD1.getId());
					shopItemUserDistribution1.setCount(SIUD1.getCount()+1);
					shopItemUserDistributionMapper.updateById(shopItemUserDistribution1);

				}else if (su.getSkinType()==1){
					shopItemUserDistribution.setType(0);
					shopItemUserDistribution.setSub(1);
					ShopItemUserDistribution SIUD1=shopItemUserDistributionMapper.selectOne(shopItemUserDistribution);
					ShopItemUserDistribution shopItemUserDistribution1=new ShopItemUserDistribution();
					shopItemUserDistribution1.setId(SIUD1.getId());
					shopItemUserDistribution1.setCount(SIUD1.getCount()+1);
					shopItemUserDistributionMapper.updateById(shopItemUserDistribution1);


				}else if (su.getSkinType()==2){
					shopItemUserDistribution.setType(0);
					shopItemUserDistribution.setSub(2);
					ShopItemUserDistribution SIUD1=shopItemUserDistributionMapper.selectOne(shopItemUserDistribution);
					ShopItemUserDistribution shopItemUserDistribution1=new ShopItemUserDistribution();
					shopItemUserDistribution1.setId(SIUD1.getId());
					shopItemUserDistribution1.setCount(SIUD1.getCount()+1);
					shopItemUserDistributionMapper.updateById(shopItemUserDistribution1);


				}else if (su.getSkinType()==3){
					shopItemUserDistribution.setType(0);
					shopItemUserDistribution.setSub(3);
					ShopItemUserDistribution SIUD1=shopItemUserDistributionMapper.selectOne(shopItemUserDistribution);
					ShopItemUserDistribution shopItemUserDistribution1=new ShopItemUserDistribution();
					shopItemUserDistribution1.setId(SIUD1.getId());
					shopItemUserDistribution1.setCount(SIUD1.getCount()+1);
					shopItemUserDistributionMapper.updateById(shopItemUserDistribution1);

				}else {
					shopItemUserDistribution.setType(0);
					shopItemUserDistribution.setSub(4);
					ShopItemUserDistribution SIUD1=shopItemUserDistributionMapper.selectOne(shopItemUserDistribution);
					ShopItemUserDistribution shopItemUserDistribution1=new ShopItemUserDistribution();
					shopItemUserDistribution1.setId(SIUD1.getId());
					shopItemUserDistribution1.setCount(SIUD1.getCount()+1);
					shopItemUserDistributionMapper.updateById(shopItemUserDistribution1);

				}

				/*if (shopItem.getDiscountValue() != null && shopItem.getDiscountValue() > 0) {
					count=shopItem.getPrice() - shopItem.getDiscountValue();
				} else if (shopItem.getDiscountRate() != null && shopItem.getDiscountRate() > 0) {
					if (shopItem.getPrice() * shopItem.getDiscountRate() % 100 < 50) {
						count=shopItem.getPrice() * shopItem.getDiscountRate() / 100;
					} else {
						count=shopItem.getPrice() * shopItem.getDiscountRate() / 100 + 1;
					}
				} else {
					count=shopItem.getPrice();
				}*/
				int temp=count;
				if ("".equals(user_gift_card_id) || user_gift_card_id == null||Integer.parseInt(user_gift_card_id)<0) {

				} else {
					ShopGiftCard sgc = shopGiftCardMapper.selectById(Integer.parseInt(user_gift_card_id));
					count -= sgc.getBalance();
				}
				//优惠券
				if ("".equals(user_coupon_id) || user_coupon_id == null||Integer.parseInt(user_coupon_id)<0) {

				} else {
					ShopCoupon sc = shopCouponMapper.selectById(Integer.parseInt(user_coupon_id));
					ShopCouponCondition sccEntity=new ShopCouponCondition();
					sccEntity.setCouponId(sc.getId());
					ShopCouponCondition Scc = shopCouponConditionMapper.selectOne(sccEntity);
					count -= Scc.getParameterSecond();
				}
				ShopOption So1=new ShopOption();
				So1.setOptionKey(3);
				ShopOption SO=shopOptionMapper.selectById(So1);
				So1.setOptionKey(2);
				ShopOption SO2=shopOptionMapper.selectById(So1);
				if (count<Integer.parseInt(SO.getOptionValue())){
					Freight=Integer.parseInt(SO2.getOptionValue());
				}
				count+=Freight;
				shopOrder.setOrderTotal(count);
				shopOrder.setOrderAddrId(Integer.parseInt(order_addr_id));
				shopOrder.setFreight(Freight);
				shopOrder.setUserCouponId(Integer.parseInt(user_coupon_id));
				shopOrder.setUserGiftCardId(Integer.parseInt(user_gift_card_id));
				shopOrder.setCreateDate(new Date());
				shopOrder.setModifyDate(null);
				shopOrderMapper.insert(shopOrder);
				//是否回产生佣金
				if(arr2.length==1){
				}else {
					client.set("shopbouns_"+shopOrder.getId(),arr2[1]);
				}
				param.put("order_id",shopOrder.getId());
				ShopOrderItem sio=new ShopOrderItem();
				sio.setItemId(Integer.parseInt(arr[0]));
				//sio.setCount(1);
				if (arr.length>1){

				}else {
					sio.setCount(1);
				}
				sio.setOrderId(shopOrder.getId());
				sio.setPrice(temp);
				sio.setState(0);
				shopOrderItemMapper.insert(sio);
				if (arr.length>1){
				Long OrderItemID=sio.getId();
				String []keys=arr[1].split("-");
				String[] values=arr[2].split("-");
				for (int i=0;i<keys.length;i++){
					ShopOrderItemProperties soips=new ShopOrderItemProperties();
					soips.setItemId(Integer.parseInt(arr[0]));
					soips.setPrice(temp);
					soips.setOrderItemId(OrderItemID);
					soips.setPropertyKey(Integer.parseInt(keys[i]));
					soips.setPropertyValue(Integer.parseInt(values[i]));
					soips.setOrderId(shopOrder.getId());
					soips.setCount(1);
					shopOrderItemPropertiesMapper.insert(soips);
				}
				}
			}else {
				String oder = "order_" + user_id;
				String str15=client.get(oder);
				String[] str16 = str15.trim().split("!");
				String[] str1=str16[0].split("#");
				String str = "cart_" + user_id;
				//订单表生成一条数据
				String orderCode = redisUtil.getRound(user_id);
				shopOrder.setOrderCode(orderCode);
				shopOrder.setUserId(Long.parseLong(user_id));
				//1: 待付款2: 待发货3:待收货4:待评价5: 退款
				shopOrder.setOrderState(0);
				shopOrder.setExpressCode(null);
				//运费
				//计算钱的方法
				for (int i = 0; i < str1.length; i++) {
					String item = client.hget(str, str1[i]);
					ShopCartInfo shopCartInfo = JSON.parseObject(item, ShopCartInfo.class);
					int price = shopCartInfo.getItemPrice()*shopCartInfo.getItemCount();
					count += price;
				}
				//-红包  查询红包表
				if ("".equals(user_gift_card_id) || user_gift_card_id == null||Integer.parseInt(user_gift_card_id)<0) {

				} else {
					ShopGiftCard sgc = shopGiftCardMapper.selectById(Integer.parseInt(user_gift_card_id));
					count -=sgc.getBalance();
					shopOrder.setUserGiftCardId(Integer.parseInt(user_gift_card_id));
				}
				//优惠券
				if ("".equals(user_coupon_id) || user_coupon_id == null||Integer.parseInt(user_coupon_id)<0) {

				} else {
					ShopCoupon sc = shopCouponMapper.selectById(Integer.parseInt(user_coupon_id));
					ShopCouponCondition sccEntity=new ShopCouponCondition();
					sccEntity.setCouponId(sc.getId());
					ShopCouponCondition Scc = shopCouponConditionMapper.selectOne(sccEntity);
					count -= Scc.getParameterSecond();
					shopOrder.setUserCouponId(Integer.parseInt(user_coupon_id));
				}
				ShopOption So1=new ShopOption();
				So1.setOptionKey(3);
				ShopOption SO=shopOptionMapper.selectById(So1);
				So1.setOptionKey(2);
				ShopOption SO2=shopOptionMapper.selectById(So1);
				if (count<Integer.parseInt(SO.getOptionValue())){
					Freight=Integer.parseInt(SO2.getOptionValue());
				}
				shopOrder.setOrderTotal(count+Freight);
				shopOrder.setOrderAddrId(Integer.parseInt(order_addr_id));
				shopOrder.setFreight(Freight);
				shopOrder.setCreateDate(new Date());
				shopOrder.setModifyDate(null);
				shopOrderMapper.insert(shopOrder);
				for (int i = 0; i < str1.length; i++) {
					String item = client.hget(str, str1[i]);
					ShopCartInfo shopCartInfo = JSON.parseObject(item, ShopCartInfo.class);
					if (shopCartInfo.getEvaluateUserId()==null||shopCartInfo.getEvaluateUserId().length()<=0){
					}else {
						client.set("shopbouns_"+shopOrder.getId(),shopCartInfo.getEvaluateUserId());
					}
				}
				param.put("order_id",shopOrder.getId());
				for (int j=0;j<str1.length;j++) {
					String[] arr = str1[j].split(",");
					String item = client.hget(str, str1[j]);
					ShopCartInfo shopCartInfo = JSON.parseObject(item, ShopCartInfo.class);
					ShopOrderItem sio = new ShopOrderItem();
					sio.setItemId(Integer.parseInt(arr[0]));
					//sio.setCount(shopCartInfo.getItemCount());
					sio.setOrderId(shopOrder.getId());
					sio.setPrice(shopCartInfo.getItemPrice());
					if (arr.length > 1) {

					} else {
						sio.setCount(shopCartInfo.getItemCount());
					}
					sio.setState(0);
					shopOrderItemMapper.insert(sio);
					if (arr.length <=1) {

					} else {
						Long orderItemId = sio.getId();
						String[] keys = arr[1].split("-");
						String[] values = arr[2].split("-");
						for (int i = 0; i < keys.length; i++) {
							ShopOrderItemProperties soips = new ShopOrderItemProperties();
							soips.setItemId(Integer.parseInt(arr[0]));
							soips.setOrderItemId(orderItemId);
							soips.setPrice(shopCartInfo.getItemPrice());
							soips.setCount(shopCartInfo.getItemCount());
							soips.setPropertyKey(Integer.parseInt(keys[i]));
							soips.setPropertyValue(Integer.parseInt(values[i]));
							soips.setOrderId(shopOrder.getId());
							shopOrderItemPropertiesMapper.insert(soips);
						}
					}
				}
				/*for (int i = 0; i < str1.length; i++) {
					String item = client.hget(str, str1[i]);
					ShopCartInfo shopCartInfo = JSON.parseObject(item, ShopCartInfo.class);
					if(shopCartInfo.getEvaluateUserId()!=null && shopCartInfo.getEvaluateUserId().length()!=0 &&Integer.parseInt(shopCartInfo.getEvaluateUserId())>0) {
					}
					//删除购物车中的对应数据
				}*/
				for (int k = 0; k < str1.length; k++) {
					client.hdel(str, str1[k]);
				}
				client.del(oder);
			}
			redisUtil.returnResource(client);
			param.put("content", "");
			return JSON.toJSONString(param);
		} catch (Exception e) {
			e.printStackTrace();
			param.put("result", "1");
			param.put("content", SerializeAPI.toUtf8("提交异常"));
			return JSON.toJSONString(param);
		}
	}
	@Override
	public String getSampleList(String user_id, String page_no, String list_type) {
		try {
			if (Integer.parseInt(list_type) == 1) {
				//将自己的试用信息显示出来

			} else {
				//将全部的试用信息展示出来

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	//立即购买
	@Override
	public String buyNow(String item_id, String user_id,String evaluate_shared_id) {
		JSONObject param = new JSONObject();
		JSONObject content = new JSONObject();
		JSONObject hb = new JSONObject();
		JSONArray hb1 = new JSONArray();
		JSONObject condition = new JSONObject();
		JSONObject yx = new JSONObject();
		JSONObject properties = new JSONObject();
		JSONObject item1 = new JSONObject();
		JSONArray ja = new JSONArray();
		JSONArray jb = new JSONArray();
		JSONArray jc = new JSONArray();
		try {
			//获取购物车中的对应产品
			Jedis client=redisUtil.getJedis();
			//String id = (item_id + "," + property_key + "," + property_value).trim();
			param.put("result", "0");
			//根据用户id取得用户的信息
			ShopUser su = shopUserMapper.selectById(user_id);
			ShopUserAddr sa = shopUserAddrMapper.selectById(su.getDefaultAddr());
			content.put("contact", SerializeAPI.toUtf8(sa.getContact()));
			content.put("default_addr", su.getDefaultAddr());
			content.put("address", SerializeAPI.toUtf8(sa.getAddress()));
			content.put("mobile_phone",  sa.getMobilePhone());
			String[] arr = item_id.split(",");
			//获取对应的id
			ShopItem si = shopItemMapper.selectById(arr[0]);
			item1.put("item_id", si.getId());
			item1.put("item_name",SerializeAPI.toUtf8(si.getItemName()));
			item1.put("item_icon", si.getItemIconAddr());
			StringBuffer sb=new StringBuffer();
			StringBuffer sb1=new StringBuffer();
			StringBuffer sb3=new StringBuffer();
			Boolean  flag=true;
			if (arr.length>1){
			//根据产品的key获取对应的desc
		String[] keys1=arr[1].split("-");
		String[] values=arr[2].split("-");
			JSONArray jd=new JSONArray();
			for (int i=0;i<keys1.length;i++){
				ShopItemProperties soip=new ShopItemProperties();
				soip.setItemId(Integer.parseInt(arr[0]));
				soip.setPropertyKey(Integer.parseInt(keys1[i]));
				soip.setPropertyValue(Integer.parseInt(values[i]));
				ShopItemProperties Sips=shopItemPropertiesMapper.selectOne(soip);
				properties.put("item_property_key", Sips.getPropertyKey());
				sb.append(Sips.getPropertyKey() + "-");
				properties.put("item_property_value",Sips.getPropertyValue());
				sb1.append(Sips.getPropertyValue()+"-");
				sb3.append( Sips.getPropertyKey()+":"+Sips.getPropertyValue()+"/");
				properties.put("item_property_desc",SerializeAPI.toUtf8(Sips.getPropertyDesc()));
				String str12= JSON.toJSONString(properties,SerializerFeature.DisableCircularReferenceDetect);
				jd.add(JSON.parseObject(str12));
			}

				ShopItemPrice shopItemPrice=new ShopItemPrice();
				shopItemPrice.setItemId(Integer.parseInt(arr[0]));
				shopItemPrice.setPropertyPath(sb3.substring(0,sb3.length()-1));
				ShopItemPrice Sip= shopItemPriceMapper.selectOne(shopItemPrice);
				if ("".equals(evaluate_shared_id)||evaluate_shared_id==null){
					item1.put("item_price", Sip.getPrice());
				}else {
					//过滤他自己分享
					ShopEvaluateShared SES= shopEvaluateSharedMapper.selectById(evaluate_shared_id);
					if(SES.getUserId()==Long.parseLong(user_id)){
						item1.put("item_price", Sip.getPrice());
						ShopOption So1=new ShopOption();
						So1.setOptionKey(3);
						ShopOption SO=shopOptionMapper.selectById(So1);
						if (Sip.getPrice()>Integer.parseInt(SO.getOptionValue())){
							flag=false;
						}
					}else {
						int rate=si.getDiscountRate();
						int value=si.getDiscountValue();
						int price =Sip.getPrice();
						int discount=DateUtils.getDiscount(price,rate,value);
						item1.put("item_price", discount);
						ShopOption So1=new ShopOption();
						So1.setOptionKey(3);
						ShopOption SO=shopOptionMapper.selectById(So1);
						if (Sip.getPrice() > Integer.parseInt(SO.getOptionValue())){
							flag=false;
						}
					}
				}
			item1.put("item_propertys",jd);
			}else {

				if ("".equals(evaluate_shared_id)||evaluate_shared_id==null) {
					item1.put("item_price", si.getPrice());
				}else {
					//过滤他自己分享
					ShopEvaluateShared SES= shopEvaluateSharedMapper.selectById(evaluate_shared_id);
					if(SES.getUserId()==Long.parseLong(user_id)){
						item1.put("item_price", si.getPrice());
						ShopOption So1=new ShopOption();
						So1.setOptionKey(3);
						ShopOption SO=shopOptionMapper.selectById(So1);
						if (si.getPrice()>Integer.parseInt(SO.getOptionValue())){
							flag=false;
						}
					}else {
						int rate=si.getDiscountRate();
						int value=si.getDiscountValue();
						int price =si.getPrice();
						int discount=DateUtils.getDiscount(price,rate,value);
						item1.put("item_price", discount);
						ShopOption So1=new ShopOption();
						So1.setOptionKey(3);
						ShopOption SO=shopOptionMapper.selectById(So1);
						if (si.getPrice()>Integer.parseInt(SO.getOptionValue())){
							flag=false;
						}
					}
				}
				item1.put("item_propertys","");
			}
			//根据品型号获取价格
			//item1.put("item_price", si.getPrice());
			String str12 = JSON.toJSONString(item1, SerializerFeature.DisableCircularReferenceDetect);
			ja.add(JSON.parseObject(str12));
			content.put("items", ja);
			//根据用户user_id获取红包和优惠券0:优惠券1：红包
			//获取红包
			int promo_type = 1;
			ShopOption So1=new ShopOption();
			So1.setOptionKey(2);
			ShopOption SO=shopOptionMapper.selectById(So1);
			if (flag==false){
				content.put("freight",0);
			}else {
				content.put("freight",SO.getOptionValue());
			}

			List<ShopUserPromo> gift = shopUserPromoMapper.getShopUserPromoByType(user_id, 0, promo_type);
			for (ShopUserPromo gift1 : gift) {
				ShopGiftCard sgc = shopGiftCardMapper.selectById(gift1.getPromoId());
				hb.put("gift_card_id", sgc.getId());
				hb.put("gift_card_name", SerializeAPI.toUtf8(sgc.getName()));
				hb.put("gift_card_balance", sgc.getBalance());
				String str13 = JSON.toJSONString(hb, SerializerFeature.DisableCircularReferenceDetect);
				hb1.add(JSON.parseObject(str13));
			}
			content.put("gift_cards", hb1);
			//获取优惠券
			promo_type = 0;
			List<ShopUserPromo> coupons = shopUserPromoMapper.getShopUserPromoByType(user_id, 0, promo_type);
			for (ShopUserPromo cou : coupons) {
				ShopCoupon sc = shopCouponMapper.selectById(cou.getPromoId());
				yx.put("coupon_id", sc.getId());
				yx.put("coupon_name", SerializeAPI.toUtf8(sc.getName()));
				//获取conditions
				List<ShopCouponCondition> Scc = shopCouponConditionMapper.selectByUserId(cou.getPromoId().toString());
				for (ShopCouponCondition scc1 : Scc) {
					condition.put("condition_type", scc1.getConditionType());
					condition.put("condition_desc",SerializeAPI.toUtf8(scc1.getConditionDesc()));
					condition.put("parameter_first", scc1.getParameterFirst());
					condition.put("parameter_second", scc1.getParameterSecond());
					String str156 = JSON.toJSONString(condition, SerializerFeature.DisableCircularReferenceDetect);
					jc.add(JSON.parseObject(str156));
				}
				yx.put("conditions", jc);
				yx.put("grant_date", DateUtils.Date2String(cou.getGrantDate()));
				yx.put("end_date", DateUtils.Date2String(cou.getEndDate()));
				yx.put("state", cou.getState());
				String str13 = JSON.toJSONString(yx, SerializerFeature.DisableCircularReferenceDetect);
				jb.add(JSON.parseObject(str13));
			}
			content.put("coupons", jb);
			param.put("content",content);
			//将信息加入购物车
			String str = "cart_" + user_id;
			HashMap<String, String> cartMap = new HashMap<String, String>();
			//判断购物车的数据是否存在记录存在+1，不存在写入
			Set<String> keys = client.keys(str);
			if (keys.contains(item_id)) {
				//获取对象
				addItemtoCart( user_id, si.getId().toString(), evaluate_shared_id, sb.substring(0,sb.length()-1).toString() ,sb1.substring(0,sb1.length()-1).toString());
			} else {
				String str14="buy_now0"+user_id;
				String str15="buy_now1"+user_id;
				//判断他是否享受优惠
				if ("".equals(evaluate_shared_id)||evaluate_shared_id==null){
					client.set(str14,item_id+"#"+evaluate_shared_id );
				}else {
					//过滤他自己分享
					ShopEvaluateShared SES= shopEvaluateSharedMapper.selectById(evaluate_shared_id);
					if(SES.getUserId()==Long.parseLong(user_id)){
						client.set(str14,item_id+"#"+evaluate_shared_id);
					}else {
						client.set(str15,item_id+"#"+evaluate_shared_id);
					}
				}


			}
			redisUtil.returnResource(client);
			return JSON.toJSONString(param);
		} catch (Exception e) {
			e.printStackTrace();
			param.put("result", "1");
			return JSON.toJSONString(param);
		}
	}
//调用wx接口
	@Override
	public String isUserLogin(String code) {
		JSONObject param=new JSONObject();
		//JSONObject content=new JSONObject();
	try {
		param.put("result",0);
		Map<String,String > map = MeizhuangWx.getOpenid(code);
		ShopUser su=new ShopUser();
		su.setOpenId(map.get("openid"));
		int i= shopUserMapper.selectCount(new EntityWrapper<ShopUser>(su));
		if (i>0){
			ShopUser su1=new ShopUser();
			su1.setOpenId(map.get("openid"));
			ShopUser su2=shopUserMapper.selectOne(su1);
			Jedis client=redisUtil.getJedis();
			String str = "cart_" + su2.getId().toString();
			List<String> allItem = client.hvals(str);
			List<ShopCartInfo> lists = JSON.parseArray(allItem.toString(), ShopCartInfo.class);
			int item_count=0;
			for (ShopCartInfo list:lists){
				item_count+=list.getItemCount();
			}
			param.put("item_count",item_count);
			param.put("user_id",su2.getId());
			return  JSON.toJSONString(param);
		}else {
			//根据openid获取对应的数据
			ShopUser su3=new ShopUser();
			su3.setOpenId(map.get("openid"));
			su3.setNickname("游客");
			su3.setHeadImageAddr("http://img.ciaotalking.com/shopimg/20170512/88327beb-4ace-42f3-a7c2-4f31f9214734.jpeg");
			su3.setSex(1);
			su3.setMobilePhone(null);
			su3.setDateOfBirth(DateUtils.String2Date("2005-07-07"));
			su3.setSkinType(1);
			su3.setEmail("1234567@qq.com");
			su3.setDefaultAddr(-1);
			su3.setShardCount(0);
			su3.setEvaluateCount(0);
			su3.setShardBonus(0);
			su3.setEvaluateBonus(0);
			su3.setUnionId(null);
			su3.setState(0);
			shopUserMapper.insert(su3);
			long id =su3.getId();
            param.put("user_id",id);
			param.put("item_count", 0);
			return  JSON.toJSONString(param);
		}

	}catch (Exception e){
		e.printStackTrace();
		param.put("result" ,1);
		return JSON.toJSONString(param);
	}
}
//获取微信sdk
	@Override
	public String getWxSdk(String url) {
		String jsapi_ticket= null;
		JSONObject param=new JSONObject();
		JSONObject content=new JSONObject();
		try {
			param.put("result",0);
			jsapi_ticket = MeizhuangWx.getWxSdk();
			String noncestr = UUID.randomUUID().toString().replace("-", "").substring(0, 16);
			String timestamp = Long.toString(System.currentTimeMillis() / 1000);
			String str = "jsapi_ticket="+jsapi_ticket+"&noncestr="+noncestr+"&timestamp="+timestamp+"&url="+url;
			String signature = new SHAWX().getDigestOfString(str.getBytes());
			param.put("timestamp",timestamp);
			param.put("noncestr",noncestr);
			param.put("signature",signature);
			return JSON.toJSONString(param);
		} catch (Exception e) {
			e.printStackTrace();
			param.put("result",1);
			return JSON.toJSONString(param);
		}
	}
//微信支付
	@Override
	public String payForWx(String order_id,String ip,String wxurl) {
		JSONObject param=new JSONObject();
		String jsapi_ticket= "";
		try{
			jsapi_ticket = MeizhuangWx.getWxSdk();
			String noncestr = UUID.randomUUID().toString().replace("-", "").substring(0, 16);
			String timestamp = Long.toString(System.currentTimeMillis() / 1000);
			//根据用户订单id
			param.put("result","0");
			ShopOrder so=shopOrderMapper.selectById(order_id);
			ShopUser su=shopUserMapper.selectById(so.getUserId());
			String openId=su.getOpenId();
			String price=so.getOrderTotal().toString();
			String userID=su.getId().toString();
			String str = "jsapi_ticket="+jsapi_ticket+"&noncestr="+noncestr+"&timestamp="+timestamp+"&url="+wxurl;
			String signature = new SHAWX().getDigestOfString(str.getBytes());
			//String signature = MeizhuangWx.SHA1(str);
			//String signature= SHA1.getSHA1();
			param.put("signature",signature);
			String  prepay_id = MeizhuangWx.getPrepayId(openId,order_id,price,ip,userID);
			param.put("timeStamp", timestamp);
			param.put("nonceStr",noncestr);
			param.put("package","prepay_id=" + prepay_id);
			param.put("signType","MD5");
			Map<String, String> payMap = new HashMap<String, String>();
			payMap.put("appId", MeizhuangWx.getAppid());
			payMap.put("timeStamp",timestamp);
			payMap.put("nonceStr",noncestr);
			payMap.put("package", "prepay_id=" + prepay_id);
			payMap.put("signType", "MD5");
			String paySign = MeizhuangWx.getSign(payMap, MeizhuangWx.getPay_sign());
			param.put("paySign",paySign);
		return JSON.toJSONString(param);
		}catch (Exception e){
			e.printStackTrace();
			param.put("result","1");
			return JSON.toJSONString(param);
		}
	}
//判断微信支付是否成功
	@Override
	public String paySuccess(String order_id, String success,String trans_number) {
		JSONObject param=new JSONObject();
			param.put("result",0);
				ShopOrder so=new ShopOrder();
				so.setId(Long.parseLong(order_id));
				so.setTransNumber(trans_number);
				so.setOrderState(1);
				shopOrderMapper.updateById(so);
		        return JSON.toJSONString(param);
	}
}