package com.voucher.manage.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.voucher.manage.dao.UserDAO;
import com.voucher.manage.daoModel.Users;
import com.voucher.manage.tools.Constants;
import com.voucher.manage.tools.Md5;
import com.voucher.sqlserver.context.Connect;

@Controller
@RequestMapping("/seller")
public class SellerController {

	ApplicationContext applicationContext=new Connect().get();
	
	UserDAO userDao=(UserDAO) applicationContext.getBean("dao");
		
	/**
	 * 鍟嗗鐧诲綍
	 * @param campusAdmin
	 * @param password
	 * */
	@RequestMapping("/toLogin")
	public @ResponseBody Map<String, Object> toLogin(
			@RequestParam String campusAdmin, @RequestParam String password,
			HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (campusAdmin != null && password != null
				&& !campusAdmin.trim().equals("")
				&& !password.trim().equals("")) {
			Users users = userDao.selectUsersByCampusAdmin(campusAdmin);
			if (users != null) {
				if (users.getPassword().equals(Md5.GetMD5Code(password))) {
					map.put(Constants.STATUS, Constants.SUCCESS);
					map.put(Constants.MESSAGE, "登陆成功");
					map.put("type", users.getType());
					HttpSession session = request.getSession();
					session.setAttribute("type", users.getType());
					session.setAttribute("campusAdmin",
							users.getCampusAdmin());
					session.setAttribute("cityId", users.getCityId());
					Date date = new Date();
				//	sellerService.updateLastLoginTime(date, campusAdmin);
				} else {
					map.put(Constants.STATUS, Constants.FAILURE);
					map.put(Constants.MESSAGE, "用户名或者密码错误");
				}
			} else {
				map.put(Constants.STATUS, Constants.FAILURE);
				map.put(Constants.MESSAGE, "用户名或者密码错误");
			}
		}

		return map;
	}
	
	/**
	 * 鏍规嵁鍟嗗id鏌ユ壘鍟嗗鏁版嵁
	 *
	 * */
	
	@RequestMapping("/getSellerById")
	public @ResponseBody Map<String, Object> getSellerById(String campusAdmin)
	{
		Map<String, Object> map = new HashMap<String, Object>();

		return map;
	}
	
	/**
	 * 妫�鏌ヨ璐﹀彿鏄惁娉ㄥ唽杩�
	 * @param campusAdmin
	 * @return
	 */
	
	@RequestMapping("/checkSellerIsExist")
	public @ResponseBody Map<String, Object> checkSellerIsExist(String campusAdmin)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		Users users = null;
		if(users==null)
		{
			map.put(Constants.STATUS, Constants.SUCCESS);
			map.put(Constants.MESSAGE, "璇ョ敤鎴峰悕涓嶅瓨鍦紝鍙互娉ㄥ唽");
		}
		else
		{
			map.put(Constants.STATUS, Constants.FAILURE);
			map.put(Constants.MESSAGE, "璇ョ敤鎴峰悕宸插瓨鍦紝璇锋崲涓�涓悕瀛�");			
		}
		
		return map;	
	}
	
	
	@RequestMapping("/getCampusAdmin")
	public @ResponseBody JSONArray getCampusAdmin(HttpServletRequest request){
		List<Users> users;
		
		HttpSession session=request.getSession();  //取得session的type变量，判断是否为公众号管理员
		String campusAdmin=session.getAttribute("campusAdmin").toString();
		String type=session.getAttribute("type").toString();
		
		
		if(type.equals("0")){
          users=null;
		}else {
		  users=null;
		}
		return (JSONArray) JSON.toJSON(users);
		
	}
}