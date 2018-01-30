package com.voucher.manage.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.http.message.BasicNameValuePair;
import org.springframework.context.ApplicationContext;
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
import credit.AbstractCredit;
import credit.MobileDemo;

@Controller
@RequestMapping("/credit")
public class CreditController {

	public final static String path="\\Desktop\\pasoft\\ZC\\report\\";
	
	public final static String filePath=System.getProperty("user.home")+path;
	
	ApplicationContext applicationContext=new Connect().get();	
	UserDAO userDao=(UserDAO) applicationContext.getBean("dao");		
	UserDAOImpl userDAOImpl=new UserDAOImpl();


	@RequestMapping(value = "/taskMobile")
	public @ResponseBody JSONObject taskMobile(@RequestParam String username,@RequestParam String password,
			@RequestParam String identityName,@RequestParam String identityCardNo,String clientInfo,
			HttpServletRequest request){
		   String json;
		   JSONObject jsonObject;
	     
		   MobileDemo mobileDemo=new MobileDemo();
		   
		   try {
			   jsonObject=mobileDemo.process(username, password, identityName, identityCardNo,clientInfo);
			   	   
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
	public  @ResponseBody JSONObject  status(@RequestParam String token,
			@RequestParam String username,@RequestParam String password,
			@RequestParam String identityName,@RequestParam String identityCardNo,
			HttpServletRequest request){
		   JSONObject jsonObject = new JSONObject();
		   
		   List<BasicNameValuePair> reqParam = new ArrayList<BasicNameValuePair>();
	        reqParam.add(new BasicNameValuePair("apiKey", AbstractCredit.apiKey));//API授权
	        reqParam.add(new BasicNameValuePair("token", token));//请求标识
	        reqParam.add(new BasicNameValuePair("sign", AbstractCredit.getSign(reqParam)));//请求参数签名
		   
		   String json = AbstractCredit.httpClient.doPost(AbstractCredit.apiUrlStatus, reqParam);
	       JsonNode rootNode = JsonUtils.toJsonNode(json);
	       String code =JsonUtils.getJsonValue(rootNode, "code");
	       String msg = JsonUtils.getJsonValue(rootNode, "msg");
		  
	       jsonObject=JSONObject.parseObject(json);
	      
	      if (code.startsWith("0")) {
	       if ("0000".startsWith(code)) {//成功
          	 System.out.println("运营商报告结果查询开始 >>>>>");
               String report=AbstractCredit.getReport(reqParam);	                 
               System.out.println("运营商报告结果查询结束 >>>>>");

          /*     System.out.println("运营商报告原始数据结果查询开始 >>>>>");
               mobileDemo.getData();
               System.out.println("运营商报告原始数据结果查询结束 >>>>>");
            */

              MoblieReport moblieReport=new MoblieReport();
    			
    		   UUID uuid=UUID.randomUUID();
    			
    		   HttpSession session=request.getSession();  //取得session的type变量，判断是否为公众号管理员
 			   String campusAdmin=(String) session.getAttribute("campusAdmin");
    			
    			moblieReport.setCampusAdmin(campusAdmin);
    			moblieReport.setUserName(identityName);
    			moblieReport.setUserPhoneNum(username);
    			moblieReport.setPassword(password);
    			moblieReport.setUseridCard(identityCardNo);
    			
    			moblieReport.setGUID(uuid.toString());
    			
    			moblieReport.setFilepath(path);
    			
    			Date date=new Date();
    			
    			moblieReport.setDatetime(date);
    			
    			LocalFile.saveDataToFile(filePath,uuid.toString(),report);
    			
    			userDao.insertMobileReport(moblieReport);
    			
    			jsonObject.put("uuid", uuid);
               
            }
	      }
		   
		   return jsonObject;
	}
		
	
	@RequestMapping(value = "/input")
	public @ResponseBody JSONObject input(@RequestParam String json,@RequestParam String s){
		   String result;
		   JSONObject jsonObject = new JSONObject();
		   
		   try {
			   result=AbstractCredit.sendInput(json,s);
               jsonObject=JSONObject.parseObject(result);
			   return jsonObject;
		   	} catch (Exception e) {
			// TODO Auto-generated catch block
		   		e.printStackTrace();
		   		 jsonObject.put("msg", "输入短信错误");
				 return jsonObject;
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
			String report=LocalFile.getDatafromFile(filePath, fileName);			
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