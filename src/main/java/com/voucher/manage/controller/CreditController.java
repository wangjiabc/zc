package com.voucher.manage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

import credit.MobileDemo;

@Controller
@RequestMapping("/credit")
public class CreditController {

	private MobileDemo mobileDemo=new MobileDemo();
	
	@RequestMapping(value = "/taskMobile")
	public @ResponseBody JSONObject taskMobile(@RequestParam String username,@RequestParam String password,
			@RequestParam String identityName,@RequestParam String identityCardNo){
		   String json;
		   JSONObject jsonObject;
		   
		   try {
			   json=mobileDemo.process(username, password, identityName, identityCardNo);
			   System.out.println("json==="+json);
			   jsonObject=JSONObject.parseObject(json);
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
	public @ResponseBody JSONObject status(){
		   String json;
		   JSONObject jsonObject = null;
		   
		   try {
			 json=mobileDemo.queue2.take();
			 System.out.println("status="+json);
			 jsonObject=JSONObject.parseObject(json);
		   } catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		   }
		   
		   return jsonObject;
	}
		
	
	@RequestMapping(value = "/input")
	public @ResponseBody String input(@RequestParam String s){
		   String code;
		   System.out.println("s="+s);
		   try {
			   code=mobileDemo.sendInput(s);
			   System.out.println("code="+code);
			   mobileDemo.queue.put(code);
			   return code;
		   	} catch (Exception e) {
			// TODO Auto-generated catch block
		   		e.printStackTrace();
		   		 code="0044";
				 return code;
		   }
		   
		   
	}
	
}