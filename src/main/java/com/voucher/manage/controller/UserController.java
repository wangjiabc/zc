package com.voucher.manage.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
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
import com.voucher.manage.tools.Md5;
import com.voucher.manage.tools.MyTestUtil;
import com.voucher.sqlserver.context.Connect;

@Controller
@RequestMapping("/user")
public class UserController {

	
	ApplicationContext applicationContext=new Connect().get();
	
	UserDAO userDao=(UserDAO) applicationContext.getBean("dao");

	@RequestMapping(value="/getAllUser")
	public @ResponseBody
	Map<String, Object> getAllUser(@RequestParam Integer limit,@RequestParam Integer offset,String sort,String order,
			String search,HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		String type,campusAdmin;
		
		Map searchMap=new HashMap<>();
		
		if(search!=null&&!search.trim().equals("")){
			search="%"+search+"%";
			searchMap.put("campusAdmin like ",search);
		}		
         
		searchMap.put("type >", "0");
		
		HttpSession session=request.getSession();  //取得session的type变量，判断是否为公众号管理员
		type=session.getAttribute("type").toString();
		campusAdmin=session.getAttribute("campusAdmin").toString();
		System.out.println(type);
        if(type.equals("0")){
        	map=userDao.getAllFullUser(limit, offset, sort, order, searchMap);
        }else{
		    return map;
        }

		return map;
	}

	
	/**
	 * 閫�鍑虹櫥褰�
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/logout")
	public  String logout(HttpServletRequest request){	
		request.getSession().removeAttribute("campusAdmin");

		return "redirect:/index.html";
	}
	
	/**
	 * 鑾峰彇鐢ㄦ埛绫诲瀷
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/getUserType")
	public @ResponseBody Short getUserType(HttpServletRequest request){
		return (Short) request.getSession().getAttribute("type");
	}
	
	@RequestMapping(value="/upAtionFormatter")
	public @ResponseBody Integer upAtionFormatter(@RequestParam String campusAdmin,@RequestParam Integer state,
			HttpServletRequest request){
		 return userDao.updateState(campusAdmin, state);
	}
	

	@RequestMapping(value="/updatePassword")
	public @ResponseBody Integer updatePassword(@RequestParam String oldPassword,
			@RequestParam String password,HttpServletRequest request){
		HttpSession session=request.getSession();  //取得session的type变量，判断是否为公众号管理员
		String campusAdmin=(String) session.getAttribute("campusAdmin");
		
		Users users = userDao.selectUsersByCampusAdmin(campusAdmin);
		
		int i;
		
		if (users.getPassword().equals(Md5.GetMD5Code(oldPassword))){
			String passwordMd5=Md5.GetMD5Code(password);
			i=userDao.updatePassWord(campusAdmin, passwordMd5);
			return i;
		}else{
			i=0;
			return i;
		}
	}
	
	
	@RequestMapping(value="/updateUser")
	public @ResponseBody Integer updateUser(@RequestParam String campusAdmin,
			@RequestParam String name,@RequestParam String telePhone,
			@RequestParam String address,HttpServletRequest request){
		    
			Users users=new Users();
			
			users.setName(name);
			users.setTelePhone(telePhone);
			users.setAddress(address);
			
			String[] where={"Users.campusAdmin=",campusAdmin};
			
			users.setWhere(where);
			
			return userDao.updateUser(users);
		
	}
	
	
	@RequestMapping(value="/deleteUser")
	public @ResponseBody Integer deleteUser(@RequestParam String CampusAdmins,
			HttpServletRequest request){
		Integer type;
		
		HttpSession session=request.getSession();  //取得session的type变量，判断是否为公众号管理员
		type=(Integer) session.getAttribute("type");

		if(type>0){
			return 0;
		}
		
		String[] campusAdmins = CampusAdmins.split(",");
		
		int status = 0;
		try{
		  for (String campusAdmin : campusAdmins) {

			Map searchMap=new HashMap<>();
			
			searchMap.put("clientInfo.campusAdmin=",campusAdmin);
 			
			int count=(int) userDao.getAllClientInfo(10, 0, null, null, searchMap).get("total");
			
			System.out.println("count="+count);
			
			if(count>0){
				return 2;
			}else{
			  Users users=new Users();
			
			  String[] where={"Users.campusAdmin=",campusAdmin};
			  
			  users.setWhere(where);
			
			  status=userDao.deleteUser(users);
			  
			}
			
		   }
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return 0;
		}
		
		return status;
		
		
	}
	
}
