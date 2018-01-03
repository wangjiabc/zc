package com.voucher.manage.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.http.message.BasicNameValuePair;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.JsonNode;
import com.voucher.manage.dao.UserDAO;
import com.voucher.manage.daoImpl.UserDAOImpl;
import com.voucher.manage.daoModel.MoblieReport;
import com.voucher.manage.tools.LocalFile;
import com.voucher.sqlserver.context.Connect;

import common.JsonUtils;
import common.StringUtils;
import credit.AbstractCredit;
import credit.MobileDemo;

@Controller
@RequestMapping("/credit")
public class CreditController {

	ApplicationContext applicationContext=new Connect().get();
	
	UserDAO userDao=(UserDAO) applicationContext.getBean("dao");		
	
	UserDAOImpl userDAOImpl=new UserDAOImpl();

	@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS, value = "prototype")
	@RequestMapping(value = "/taskMobile")
	public @ResponseBody JSONObject taskMobile(@RequestParam String username,@RequestParam String password,
			@RequestParam String identityName,@RequestParam String identityCardNo,
			HttpServletRequest request){
		   String json;
		   JSONObject jsonObject;
	     
		   MobileDemo mobileDemo=new MobileDemo();
		   
		   try {
			   json=mobileDemo.process(username, password, identityName, identityCardNo);
			   System.out.println("json==="+json);
			   jsonObject=JSONObject.parseObject(json);
			   
			   HttpSession session=request.getSession();  //取得session的type变量，判断是否为公众号管理员
			   String campusAdmin=(String) session.getAttribute("campusAdmin");
			   
			   JsonNode rootNode = JsonUtils.toJsonNode(json);
			   String sign = JsonUtils.getJsonValue(rootNode, "sign");
		        String token = JsonUtils.getJsonValue(rootNode, "token");
		        String code =JsonUtils.getJsonValue(rootNode, "code");
		       
		        List<BasicNameValuePair> reqParam = new ArrayList<BasicNameValuePair>();
		        reqParam.add(new BasicNameValuePair("apiKey", AbstractCredit.apiKey));//API授权
		        reqParam.add(new BasicNameValuePair("token", token));//请求标识
		        reqParam.add(new BasicNameValuePair("sign", sign));//请求参数签名
		        
		       //受理成功才启动线程
			   if(code.equals("0010"))
		        userDAOImpl.timer(reqParam,campusAdmin,username,password,identityName,identityCardNo);
			   
			   return jsonObject;
		   } catch (Exception e) {
			// TODO Auto-generated catch block
			   e.printStackTrace();
	           json="{'code':'0044','msg':'获取运营商信息异常'}";
               jsonObject=JSONObject.parseObject(json);
			   return jsonObject;
		   }
		   		   
	}
	
	
	@RequestMapping(value = "/status")
	public  @ResponseBody JSONObject  status(){
		  String json=null;
		   JSONObject jsonObject = null;
		   
		   try {
			 json=userDAOImpl.queue2.take();
		   } catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		   }
		  
		   jsonObject=JSONObject.parseObject(json);
		   
		   return jsonObject;
	}
		
	
	@RequestMapping(value = "/input")
	public @ResponseBody String input(@RequestParam String json,@RequestParam String s){
		   String code;
		   System.out.println("s="+s);
		   
		   try {
			  code=AbstractCredit.sendInput(json,s);
			   System.out.println("code="+code);
			   userDAOImpl.queue.put(code);
			   return "code";
		   	} catch (Exception e) {
			// TODO Auto-generated catch block
		   		e.printStackTrace();
		   		 code="0044";
				 return code;
		   }
		   		   
	 }
	
	
	@RequestMapping(value = "/getReport")
	public @ResponseBody JSONObject getReport(@RequestParam String uuid,HttpServletRequest request){
		try{

			MoblieReport moblieReport=new MoblieReport();
			
			moblieReport.setLimit(10);
			moblieReport.setOffset(0);
			moblieReport.setNotIn("id");
			
			String[] where={"GUID =", uuid};
			
			moblieReport.setWhere(where);
			
			List<MoblieReport> list=userDao.getReport(moblieReport);
			
			moblieReport=list.get(0);
			
			String fileName=moblieReport.getGUID()+".json";
			
			String report=LocalFile.getDatafromFile(userDAOImpl.filePath, fileName);
			
			JSONObject jsonObject=JSONObject.parseObject(report);
			
			return jsonObject;
			
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			
			JSONObject jsonObject=JSONObject.parseObject(e.getMessage());
			
			return jsonObject;
		}
	}
	
	@RequestMapping(value = "/getReportByIdCard")
	public @ResponseBody Map getReportByIdCard(@RequestParam String useridCard,@RequestParam Integer limit,@RequestParam Integer offset,String sort,String order,
			String search,HttpServletRequest request){

		try{
			
			if(order!=null&&order.equals("asc")){
				order="asc";
			}
		
			if(order!=null&&order.equals("desc")){
				order="desc";
			}
			
			Map searchMap=new HashMap<>();
			
			searchMap.put("[ZC].[dbo].[MoblieReport].useridCard=",useridCard);
			
			if(search!=null&&!search.trim().equals("")){
				search="%"+search+"%";  
				searchMap.put("[ZC].[dbo].[MoblieReport].userName like ", search);
			}		
			
						
			Map map=userDao.selectMobileReportByUseridCard(limit, offset, sort, order, searchMap);
			
			/*
			List<MoblieReport> list=(List<MoblieReport>) map.get("rows");
					
			moblieReport=list.get(0);
			
			String fileName=moblieReport.getGUID()+".json";
			
			String report=LocalFile.getDatafromFile(filePath, fileName);
			
			JSONObject jsonObject=JSONObject.parseObject(report);
			*/
						
			return map;
			
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();

			return null;
		}
	}
	
	
	
	
	 
}