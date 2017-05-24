package com.baomidou.springwind.controller;

import java.sql.SQLException;
import java.sql.Wrapper;
import java.util.Date;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.springwind.entity.UserRole;
import com.baomidou.springwind.service.IUserRoleService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.kisso.annotation.Action;
import com.baomidou.kisso.annotation.Permission;
import com.baomidou.kisso.common.encrypt.SaltEncoder;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.springwind.entity.User;
import com.baomidou.springwind.service.IRoleService;
import com.baomidou.springwind.service.IUserService;

/**
 * <p>
 * 用户管理相关操作
 * </p>
 *
 *
 * @Author Jack
 * @Date 2016/4/15 15:03
 */
@Controller
@RequestMapping("/perm/user")
public class UserController extends BaseController {

	@Autowired
	private IUserService userService;

	@Autowired
	private IRoleService roleService;

	@Autowired
	private IUserRoleService userRoleService;

	@Permission("2001")
	@RequestMapping("/list")
	public String list(Model model) {
		return "/user/list";
	}

    @Permission("2001")
    @RequestMapping("/edit")
    public String edit(Model model, Long id ) {
    	if ( id != null ) {
			model.addAttribute("user", userService.selectById(id));
		}
    	model.addAttribute("roleList", roleService.selectList(null));
		Long userRoleId = 1L;
if (id != null && id !=0){
	UserRole userRole = userRoleService.selectByUserId(id);
	userRoleId = userRole.getRid();
}
		model.addAttribute("userRoleId",userRoleId);
        return "/user/edit";
    }
    
	@ResponseBody
	@Permission("2001")
	@RequestMapping("/editUser")//"id=2&loginName=yunying&password=Q23t99&rid=%E5%95%86%E5%9F%8E%E8%BF%90%E8%90%A5"
	public String editUser( Long id ,String loginName,String password , Long rid ) {
		boolean rlt = false;
		boolean rlt2= false;
		if ( id != null ) {
			User user = userService.selectById(id);
			if (user!=null){
				if (!StringUtils.isEmpty(password)){
					user.setPassword(SaltEncoder.md5SaltEncode(loginName,password));
				}
				user.setLoginName(loginName);
				user.setLastTime(new Date());
				rlt = userService.updateById(user);
				if (rlt){
					UserRole userRoleEntity = new UserRole();
					userRoleEntity.setUid(user.getId());
					UserRole userRole = userRoleService.selectOne(new EntityWrapper<UserRole>(userRoleEntity));
					userRole.setRid(rid);
					rlt2 = userRoleService.insertOrUpdate(userRole);
				}
			}else{
				callbackSuccess(false);
			}
		}else {
			User user = new User();
			user.setPassword(SaltEncoder.md5SaltEncode(loginName, password));
			user.setLoginName(loginName);
			user.setCrTime(new Date());
			user.setLastTime(user.getCrTime());
			rlt = userService.insert(user);
			if (rlt){
				UserRole userRole = new UserRole();
				userRole.setRid(rid);
				userRole.setUid(user.getId());
				rlt2 = userRoleService.insert(userRole);
			}else{
				callbackSuccess(false);
			}
		}
		return callbackSuccess(rlt&&rlt2);
	}
//	public String editUser( User user ) {
//		return callbackSuccess(false);
//	}
	@ResponseBody
	@Permission("2001")
	@RequestMapping("/getUserList")
	public String getUserList() {
		Page<User> page = getPage();
		return jsonPage(userService.selectPage(page, null));
	}

	@ResponseBody
	@Permission("2001")
	@RequestMapping("/delUser/{userId}")
	public String delUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
		return Boolean.TRUE.toString();
	}

	@ResponseBody
	@Permission("2001")
	@RequestMapping("/{userId}")
	public User getUser(@PathVariable Long userId) {
		return userService.selectById(userId);
	}


	/**
	 * 设置头像
	 */
	@Permission(action = Action.Skip)
	@RequestMapping(value = "/setAvatar", method = RequestMethod.GET)
	public String setAvatar() {
		return "/user/avatar";
	}


}
