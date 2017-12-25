package com.voucher.manage.controller;

import java.sql.Clob;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.catalina.startup.Tool;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.JsonNode;
import com.voucher.manage.dao.UserDAO;
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
	
	public final static String path="\\Desktop\\pasoft\\ZC\\report\\";
	
	public final static String filePath=System.getProperty("user.home")+path;
	
	private MobileDemo mobileDemo=new MobileDemo();
	
	public BlockingQueue<String> queue;

	public BlockingQueue<String> queue2;
	
	@RequestMapping(value = "/taskMobile")
	public @ResponseBody JSONObject taskMobile(@RequestParam String username,@RequestParam String password,
			@RequestParam String identityName,@RequestParam String identityCardNo,
			HttpServletRequest request){
		   String json;
		   JSONObject jsonObject;
		   
		   try {
			   json=mobileDemo.process(username, password, identityName, identityCardNo);
			   System.out.println("json==="+json);
			   jsonObject=JSONObject.parseObject(json);
			   
			   queue=new ArrayBlockingQueue<>(1);
			   
			   queue2=new ArrayBlockingQueue<>(1);
			   
			   HttpSession session=request.getSession();  //取得session的type变量，判断是否为公众号管理员
			   String campusAdmin=(String) session.getAttribute("campusAdmin");
			   
			   timer(campusAdmin,username,password,identityName,identityCardNo);
			   
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
	public synchronized @ResponseBody JSONObject  status(){
		  String json=null;
		   JSONObject jsonObject = null;
		   
		   try {
			 json=queue2.take();
		   } catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		   }
		  
		   jsonObject=JSONObject.parseObject(json);
		   
		   return jsonObject;
	}
		
	
	@RequestMapping(value = "/input")
	public @ResponseBody String input(@RequestParam String s){
		   String code;
		   System.out.println("s="+s);
		   try {
			   code=mobileDemo.sendInput(s);
			   System.out.println("code="+code);
			   queue.put(code);
			   return code;
		   	} catch (Exception e) {
			// TODO Auto-generated catch block
		   		e.printStackTrace();
		   		 code="0044";
				 return code;
		   }
		   		   
	 }
	
		
	
	@RequestMapping(value = "/getReport")
	public @ResponseBody JSONObject getReport(@RequestParam String userName,HttpServletRequest request){
		try{

			MoblieReport moblieReport=new MoblieReport();
			
			moblieReport.setLimit(10);
			moblieReport.setOffset(0);
			moblieReport.setNotIn("id");
			
			String[] where={"userName =",userName};
			
			moblieReport.setWhere(where);
			
			Map map=userDao.selectAllMobileReport(moblieReport);
			
			List<MoblieReport> list=(List<MoblieReport>) map.get("rows");
			
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
	
	
	public void timer(String campusAdmin,String username,String password,
			String identityName,String identityCardNo) {
        System.out.println("轮循 start");
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
        long startTime=System.currentTimeMillis();   //获取开始时间 
        
        public void run() {

                try {
                    //循环取的状态，查询结果
                    //停止循环(发送短信失败或信息查询成功)
                    if (roundRobin(startTime,campusAdmin,username,password,
                				identityName,identityCardNo)) {

                        System.out.println("轮循 end");
                        System.out.println("获取信息结束");
                        timer.cancel();// 停止定时器

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    //异常
                }
            }
        }, 0, AbstractCredit.timeInterval);
    }
	
	 public boolean roundRobin(long startTime,String campusAdmin,String username,String password,
				String identityName,String identityCardNo) throws Exception {

	        //状态查询
	        String json = AbstractCredit.httpClient.doPost(AbstractCredit.apiUrlStatus, mobileDemo.getReqParam());
	        JsonNode rootNode = JsonUtils.toJsonNode(json);
	        String token = JsonUtils.getJsonValue(rootNode, "token");
	        String code =JsonUtils.getJsonValue(rootNode, "code");
	        String msg = JsonUtils.getJsonValue(rootNode, "msg");
	        long endTime=System.currentTimeMillis(); //获取结束时间  
	        System.out.println("程序运行时间： "+(endTime-startTime)+"ms"); 
	        System.out.println("循环取的状态:" + code);
	        System.out.println("循环取的信息:" + json);
	             
	        if("2010".equals(code)){
	        	queue2.put(json);
	        	return true;
	        }
	        
	        if("1013".equals(code)){
	        	queue2.put(json);
	        	return true;
	        }
	        
	        if (StringUtils.isBlank(code)) {
	            //未取到结果集
	            return false;
	        }

	        //0开头标处理成功相关
	        if (code.startsWith("0")) {

	            if ("0100".startsWith(code)) {//登陆成功
	                System.out.println("对象结果查询 >>>>>" + msg);
	                return false;
	            } else if ("0006".equals(code)) {//等待输入信息

	                JsonNode inputNode = rootNode.get("input");
	                
	                queue2.put(json);
	                
	                //获取等待输入类型
	                String type = JsonUtils.getJsonValue(inputNode, "type");
	                //图片验证码和二维码为base64编码的图片
	                String value = JsonUtils.getJsonValue(inputNode, "value");
	                //保存到本地作识别用-根据实际业务场景处理
	                if (StringUtils.isNotBlank(value)) {
	                    StringUtils.GenerateImage(value, token + ".jpeg");
	                }

	                //是否需要提交信息
	                boolean bInput = false;
	                if("sms".equals(type)){//短信验证码
	                	System.out.println("请提交收到的短信验证码 >>>>>");
	                    bInput = true;
	                }else if("img".equals(type)){//图片验证码
	                	System.out.println("请提交识别出的图片验证码 >>>>>");
	                    bInput = true;
	                }else if("qr".equals(type)){//二维码
	                	System.out.println("请扫描收到的图片二维码 >>>>>");
	                    System.out.println("二维码保存在当前程序跟目录下,二维码名称为：【"+token + ".jpeg"+"】 >>>>>");
	                    bInput = false;
	                }else if("idp".equals(type)){//独立密码
	                	System.out.println("请提交您的独立密码 >>>>>");
	                    bInput = true;
	                }
	                
	                if (bInput) {
	                    //等待输入信息
	                    code=queue.take();
	                	System.out.println("发送输入信息后查询状态：" + code);
	                    if ("0009".equals(code)) {//结果    >>>>> 成功或失败
	                        //继续轮训
	                        return false;
	                    } else {
	                        //发送失败
	                        return true;
	                    }
	                } else {
	                    //继续轮训
	                    return false;
	                }
	            } else if ("0000".startsWith(code)) {//成功
	            	 System.out.println("运营商报告结果查询开始 >>>>>");
	                 String report=mobileDemo.getReport();	                 
	                 System.out.println("运营商报告结果查询结束 >>>>>");

	            /*     System.out.println("运营商报告原始数据结果查询开始 >>>>>");
	                 mobileDemo.getData();
	                 System.out.println("运营商报告原始数据结果查询结束 >>>>>");
                  */

	                MoblieReport moblieReport=new MoblieReport();
	      			
	      			UUID uuid=UUID.randomUUID();
	      			
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
	                 
	                queue2.put(json);
	                 
	                //发送对象结果查询
	                return true;
	            }
	            //其他情况继续轮训
	            else {
	            	queue2.put(json);
	                return false;
	            }
	        }
	        
	        queue2.put(json);
	      //其他异常停止循环
	        return true;
	 }
	 
}