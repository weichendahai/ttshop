package com.baomidou.springwind.controller;

import com.baomidou.kisso.annotation.Action;
import com.baomidou.kisso.annotation.Login;
import com.baomidou.kisso.annotation.Permission;
import com.baomidou.springwind.entity.ShopExportOrder;
import com.baomidou.springwind.entity.ShopOrder;
import com.baomidou.springwind.exportex.ExcelUtils;
import com.baomidou.springwind.exportex.JsGridReportBase;
import com.baomidou.springwind.exportex.TableData;
import com.baomidou.springwind.redis.DateUtils;
import com.baomidou.springwind.service.IShopEvaluateBonusService;
import com.baomidou.springwind.service.IShopOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 订单表 前端控制器
 * </p>
 *
 * @author Yanghu
 * @since 2017-03-21
 */
@Controller
@RequestMapping("/order")
public class ShopOrderController extends BaseController {

    @Autowired
    private IShopOrderService shopOrderService;

    @Autowired
    private IShopEvaluateBonusService shopEvaluateBonusService;

    @Permission("7001")
    @RequestMapping("/list")
    public String list(Model model){
        return "/order/list";
    }

    @Permission("7001")
    @RequestMapping("/freight/list")
    public String freight(Model model){
        return "/setting/list";
    }
    /*@ResponseBody
    @Permission("7001")
    @RequestMapping("/getShopOrderList")
    public String getShopOrderList(String sortField,Integer _index,Integer _size){
        List<ShopOrder> records=null;
        if (_index!=null&&_size!=null) {
            records =shopOrderService.selectMultiShopOrderList(_index,_size,null);
        }
        if(sortField != null && sortField != ""){
            records =shopOrderService.selectMultiShopOrderList(_index,_size,sortField);
        }
        Integer total = shopOrderService.selectMultiShopOrderListTotal();
        if(records!=null&&records.size()>0&&total!=null){
            JSONObject jo = new JSONObject();
            jo.put("total",total);
            jo.put("rows",records);
            return toJson(jo);
        }
        return "['':'']";
    }*/

    @Permission("7001")
    @RequestMapping("/freight/updateFreight")
    public void updateFreight(ShopOrder shopOrder){
        boolean updateFreight =false;
        if(shopOrder!=null){
            updateFreight = shopOrderService.updateById(shopOrder);
        }
    }

    @ResponseBody
    @Permission("7001")
    @RequestMapping("/update")
    public String updateOrderState(ShopOrder shopOrder){
        Integer update =0;
        if(shopOrder!=null){
            if(shopOrder.getOrderState()==3){
                shopEvaluateBonusService.getShopEvaluateBonus(shopOrder.getId().toString());
            }
            update = shopOrderService.updateOrderState(shopOrder);
        }
        if(update>=0){
            return  callbackSuccess(true);
        }
        return callbackSuccess(false);
    }

    @ResponseBody
    @Permission("7001")
    @RequestMapping("/getShopOrderList")
    public  String searchShopOrder(String searchKey,String sortField){
        Map<String,Object> map  = new HashMap<String,Object>();
        map.put("_index",getLimitIndex());
        map.put("_size",getLimitSize());
        if(sortField!=null&&!sortField.equals("''")){
            map.put("sortField",sortField);
        }
        if(searchKey!=null&&!searchKey.equals("''")){
            searchKey = searchKey.trim();
                if(searchKey.matches("[0-9]+")){
                    map.put("searchKey",searchKey);
                }else {
                    map.put("nickname",searchKey);
                }
        }
        List<ShopOrder> orders = shopOrderService.selectMultiShopOrderListByMap(map);
        Integer total = shopOrderService.selectMultiShopOrderListTotal(map);
      return   multiToJson(total,orders);
    }

    @ResponseBody
    @Permission("7001")
    @RequestMapping("/getShopOrderFilterList")
    public  String filterShopOrder(String searchKey,String filterState){
        Map<String,Object> map  = new HashMap<String,Object>();
        map.put("_index",getLimitIndex());
        map.put("_size",getLimitSize());
        if(filterState!=null&&!filterState.equals("''")&&!filterState.equals("-1")){
            map.put("filterState",Integer.parseInt(filterState));
        }
        if(searchKey!=null&&!searchKey.equals("")){
            searchKey = searchKey.trim();
            if(searchKey.matches("[0-9]+")){
                map.put("searchKey",searchKey);
            }else {
                map.put("nickname",searchKey);
            }
        }
        List<ShopOrder> orders = shopOrderService.selectMultiShopOrderListByMap(map);
        Integer total = shopOrderService.selectMultiShopOrderListTotal(map);
        return   multiToJson(total,orders);
    }

    /*@Permission("7001")*/
    @Login(action = Action.Skip)
    @Permission(action = Action.Skip)
    @RequestMapping("/orderExport")
    public void orderExport(String searchKey,String filterState,HttpServletRequest request,HttpServletResponse response){
        response.setCharacterEncoding("UTF-8");
        Map<String,Object> map  = new HashMap<String,Object>();
        if(filterState!=null&&!filterState.equals("''")&&!filterState.equals("-1")){
            map.put("filterState",Integer.parseInt(filterState));
        }
        if(searchKey!=null&&!searchKey.equals("")){
                searchKey = searchKey.trim();
            if(searchKey.matches("[0-9]+")){
                map.put("searchKey",searchKey);
            }else {
                map.put("nickname",searchKey);
            }
        }
        List<ShopExportOrder> orders = shopOrderService.exportShopOrderListByMap(map);
        if (orders==null){
            try {
                response.sendError(1,"没有您想要的订单数数据");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
        String title ="ciaotalking_orders_"+ DateUtils.Date2String1(new Date());
        String[] hearders = new String[] {"订单编号", "订单状态", "订单金额","创建时间","修改日期","快递编号","运费","商品名称","商品属性","商品数量","收货人","收货地址","手机号"};//表头数组
        String[] fields = new String[] {"orderCode","orderState","orderTotal","createDate","modifyDate","expressCode", "freight","itemName","propertyDesc","itemCount","contact","address","telephone"};//订单对象属性数组
        TableData td = ExcelUtils.createTableData(orders, ExcelUtils.createTableHeader(hearders), fields);
        JsGridReportBase report = null;
        try {
            report = new JsGridReportBase(request, response);
            report.exportToExcel(title, "admin", td);
        } catch (Exception e) {
            e.printStackTrace();
        }
        }
    }

}
