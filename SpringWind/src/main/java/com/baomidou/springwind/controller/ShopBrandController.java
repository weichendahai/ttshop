package com.baomidou.springwind.controller;

import com.baomidou.kisso.annotation.Permission;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.springwind.entity.ShopBrand;
import com.baomidou.springwind.entity.ShopItemProperties;
import com.baomidou.springwind.entity.vo.ShopBrandVO;
import com.baomidou.springwind.service.IShopBrandService;
import com.baomidou.springwind.service.IShopManufactorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 品牌表 前端控制器
 * </p>
 *
 * @author Woody
 * @since 2017-03-21
 */
@Controller
@RequestMapping("/item/brand")
public class ShopBrandController extends BaseController{

    @Autowired
    private IShopBrandService shopBrandService;

    @Autowired
    private IShopManufactorService shopManufactorService;

    @Permission("5005")
    @RequestMapping("/list")
    public String list(Model model) {
        return "/item/brandList";
    }

    //获取商品规格列表
    @ResponseBody
    @Permission("5005")
    @RequestMapping("/getBrandList")
    public String getShopItemBrandList() {
        Page<ShopBrand> page = getPage();
        Page pageRet= shopBrandService.selectPage(page, null);
        List<ShopBrand> shopBrands = pageRet.getRecords();
        if (shopBrands !=null && shopBrands.size() !=0 ){
            List<ShopBrandVO> retList = new ArrayList<ShopBrandVO>(shopBrands.size());
            for (int i = 0; i < shopBrands.size(); i++) {
                ShopBrand sb = shopBrands.get(i);
                ShopBrandVO vo = new ShopBrandVO();
                vo.setId(sb.getId());
                vo.setBrandName(sb.getBrandName());
                vo.setManufactor(shopManufactorService.selectById(sb.getManufactorId()).getManufactor());
                retList.add(vo);
            }
            pageRet.setRecords(retList);
        }
        return jsonPage(pageRet);
    }

    //跳转到添加或者编辑页面
    @Permission("5005")
    @RequestMapping("/edit")
    public String edit(Model model,Long brandId){
        if ( brandId != null ) {
            model.addAttribute("brand", shopBrandService.selectById(brandId));
        }
        model.addAttribute("manufactors", shopManufactorService.selectAll());
        return "/item/brandEdit";
    }

    //编辑品牌
    @ResponseBody
    @Permission("5005")
    @RequestMapping("/addOrEditSubmit")
    public String editShopItemBrand(ShopBrand brand){
        boolean edit = shopBrandService.insertOrUpdate(brand);
        return  callbackSuccess(edit);
    }

    //删除品牌
    @ResponseBody
    @Permission("5005")
    @RequestMapping("/delItemBrand")
    public String delShopItemBrand(Long brandId){
        boolean del = false;
        if(brandId!=null){
            del = shopBrandService.deleteById(brandId);
        }
        return  callbackSuccess(del);
    }
}
