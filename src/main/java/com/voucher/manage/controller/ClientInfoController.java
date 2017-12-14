package com.voucher.manage.controller;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alibaba.fastjson.JSONArray;
import com.google.gson.Gson;
import com.voucher.manage.dao.UserDAO;
import com.voucher.manage.daoModel.ClientInfo;
import com.voucher.manage.tools.MyTestUtil;
import com.voucher.sqlserver.context.Connect;


@Controller
@RequestMapping("/clientinfo")
public class ClientInfoController {

    ApplicationContext applicationContext=new Connect().get();
	
	UserDAO userDao=(UserDAO) applicationContext.getBean("dao");
	
	@RequestMapping(value = "/insert")
	public @ResponseBody Integer insert(@RequestParam String clientInfoData,
			HttpServletRequest request){

		JSONObject jsonObject; 
		
		ClientInfo clientInfo=new ClientInfo();	
		Gson gson = new Gson();
		
		try {
			jsonObject= new JSONObject(clientInfoData);
	//		clientInfo=gson.fromJson(clientInfoData,ClientInfo.class);
			clientInfo.setUserName(jsonObject.getString("username"));
			clientInfo.setUserOldname(jsonObject.getString("oldname"));
			clientInfo.setUserAge(Integer.parseInt(jsonObject.getString("userage")));
			clientInfo.setUserSex(jsonObject.getString("usersex"));
			clientInfo.setUserEducation(jsonObject.getString("userEducation"));
			clientInfo.setUserMarriage(jsonObject.getString("userMarriage"));
			clientInfo.setUseridCard(jsonObject.getString("useridCard"));
			clientInfo.setUserProvideNum(Integer.parseInt(jsonObject.getString("userProvideNum")));
			clientInfo.setUserChildrenNum(Integer.parseInt(jsonObject.getString("userChildrenNum")));
			clientInfo.setUsercrAdress(jsonObject.getString("usercrAdress"));
			clientInfo.setUserHouseState(jsonObject.getString("userHouseState"));
			clientInfo.setUserNowAdress(jsonObject.getString("userNowAdress"));
			clientInfo.setUserPhoneNum(jsonObject.getString("userPhoneNum"));
			clientInfo.setUserTelNum(jsonObject.getString("userTelNum"));
			clientInfo.setUserWechat(jsonObject.getString("userWechat"));
			clientInfo.setUserqqNum(jsonObject.getString("userqqNum"));
			clientInfo.setUserSocialBase(jsonObject.getString("userSocialBase"));
			clientInfo.setUserCreditCard(jsonObject.getString("userCreditCard"));
			clientInfo.setUserPhoneMonad(jsonObject.getString("userPhoneMonad"));
			clientInfo.setUserBankOpen(jsonObject.getString("userBankOpen"));
			clientInfo.setInsCompany(jsonObject.getString("insCompany"));
			clientInfo.setInsNum(Integer.parseInt(jsonObject.getString("insNum")));
			clientInfo.setAttatchNum(Integer.parseInt(jsonObject.getString("attatchNum")));
			clientInfo.setPayOntime(jsonObject.getString("payOntime"));
			clientInfo.setPayMode(jsonObject.getString("payMode"));
			clientInfo.setInsMoney(jsonObject.getString("insMoney"));
			clientInfo.setPayTerm(Integer.parseInt(jsonObject.getString("payTerm")));
			clientInfo.setUnitName(jsonObject.getString("unitName"));
			clientInfo.setUnitAddress(jsonObject.getString("unitAddress"));
			clientInfo.setUnitPhone(jsonObject.getString("unitPhone"));
			clientInfo.setPostcode(jsonObject.getString("postcode"));
			clientInfo.setDepartment(jsonObject.getString("department"));
			clientInfo.setDuty(jsonObject.getString("duty"));
			clientInfo.setWorkTime(jsonObject.getString("workTime"));
			clientInfo.setIndustry(jsonObject.getString("industry"));
			clientInfo.setUnitNature(jsonObject.getString("unitNature"));
			clientInfo.setWorkNature(jsonObject.getString("workNature"));
			clientInfo.setMonthSalary(jsonObject.getString("monthSalary"));
			clientInfo.setPayDay(jsonObject.getString("payDay"));
			clientInfo.setOtherIncome(jsonObject.getString("otherIncome"));
			clientInfo.setBorrowUse(jsonObject.getString("borrowUse"));
			clientInfo.setApplyMoney(Integer.parseInt(jsonObject.getString("applyMoney")));
			clientInfo.setWeekRepayment(Integer.parseInt(jsonObject.getString("weekRepayment")));
			clientInfo.setApplyTime(jsonObject.getString("applyTime"));

			clientInfo.setKinshipName(jsonObject.getString("kinshipName"));
//			clientInfo.setKinshipRelation(jsonObject.getString("kinshipRelation"));
			clientInfo.setKinshipUnitName(jsonObject.getString("kinshipUnitName"));
			clientInfo.setKinshipPhoneNum(jsonObject.getString("kinshipPhoneNum"));
			clientInfo.setKinshipAddress(jsonObject.getString("kinshipAddress"));

			clientInfo.setEmergencyName(jsonObject.getString("emergencyName"));
			clientInfo.setEmergencyRelation(jsonObject.getString("emergencyRelation"));
			clientInfo.setEmergencyUnitName(jsonObject.getString("emergencyUnitName"));
			clientInfo.setEmergencyPhoneNum(jsonObject.getString("emergencyPhoneNum"));
			clientInfo.setEmergencyAddress(jsonObject.getString("emergencyAddress"));
			
			clientInfo.setColleagueName(jsonObject.getString("colleagueName"));
			clientInfo.setColleagueRelation(jsonObject.getString("colleagueRelation"));
			clientInfo.setColleagueUnitName(jsonObject.getString("colleagueUnitName"));
			clientInfo.setColleaguePhoneNum(jsonObject.getString("colleaguePhoneNum"));
			clientInfo.setColleagueAddress(jsonObject.getString("colleagueAddress"));
//			clientInfo=new ClientInfo(jsonObject.getString("borrowUse"),Integer.parseInt(jsonObject.getString("applyMoney")),Integer.parseInt(jsonObject.getString("weekRepayment")), jsonObject.getString("applyTime"),jsonObject.getString("colleagueName"),jsonObject.getString("colleagueRelation") ,
//					jsonObject.getString("payMode"), jsonObject.getString("insMoney"), jsonObject.getString("payTerm"), jsonObject.getString("unitName"),jsonObject.getString("unitAddress") , jsonObject.getString("unitPhone"),jsonObject.getString("postcode") , jsonObject.getString("department"), jsonObject.getString("duty"),jsonObject.getString("workTime") , 
//					jsonObject.getString("industry"), jsonObject.getString("unitNature"),jsonObject.getString("workNature") , jsonObject.getString("monthSalary"),jsonObject.getString("payDay") , jsonObject.getString("otherIncome"), jsonObject.getString("receptionType"), jsonObject.getString("receptionPerson"), jsonObject.getString("jobNum"),
//					jsonObject.getString("source"),jsonObject.getString("borrowType") , jsonObject.getString("manager"),jsonObject.getString("workPhoneNum") , jsonObject.getString("number"), jsonObject.getString("interviewPerson"),jsonObject.getString("userName") , jsonObject.getString("userOldname"), jsonObject.getString("userAge"), jsonObject.getString("userSex"), 
//					jsonObject.getString("userEducation"), jsonObject.getString("userMarriage"),jsonObject.getString("useridCard") , jsonObject.getString("userProvideNum"), jsonObject.getString("userChildrenNum"), jsonObject.getString("usercrAdress"), jsonObject.getString("userHouseState"),jsonObject.getString("userNowAdress") ,
//					jsonObject.getString("userPhoneNum"), jsonObject.getString("userTelNum"), jsonObject.getString("userWechat"), jsonObject.getString("userqqNum"), jsonObject.getString("userSocialBase"), jsonObject.getString("userCreditCard"), jsonObject.getString("userPhoneMonad"), jsonObject.getString("userBankOpen"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		MyTestUtil.print(clientInfo);

		int i=0;
		try{
		  i=userDao.insertIntoClientInfo(clientInfo);
		}catch (Exception e) {
			// TODO: handle exception
			return i;
		}
		return i;
		
	}
	
	@RequestMapping(value = "/getAll")
	public @ResponseBody List getAllClientInfo(Integer limit,Integer offset,String sort,String order,
			String search,HttpServletRequest request) {
		
	
		if(order!=null&&order.equals("asc")){
			order="asc";
		}
	
		if(order!=null&&order.equals("desc")){
			order="desc";
		}
		
		if(search!=null&&!search.trim().equals("")){
			search="%"+search+"%";  
		}		
		
		Map map=userDao.getAllClientInfo(limit, offset, null, order, search);
		
		List list=(List) map.get("rows");
		
		return list;
	}
	
	
	@RequestMapping(value="/delete")
	public @ResponseBody Integer delete(@RequestParam Integer id){
				
		return userDao.deleteClientInfo(id);
		
	}
	
	@RequestMapping(value="inputImage")
	public void uploadFilesSpecifyPath(HttpServletRequest request, String Filedata) throws Exception {  
	    MultipartFile mf = null;  
	    File mff = null;  
	  /*  if (!(request instanceof MultipartHttpServletRequest)) {  
	        String errorMsg = "your post form is not support ENCTYPE='multipart/form-data' ";  

	        throw new RuntimeException(errorMsg);  
	    }  */
	    MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;  
	    List<MultipartFile> multipartFiles = multipartRequest.getFiles(Filedata);  
	    if (null != multipartFiles && multipartFiles.size() > 0) {  
	        mf = multipartFiles.get(0);  
	       System.out.println(Filedata);
	        File source = new File("C:\\Users\\WangJing\\Desktop\\bb\\2\\a");  
	        try {  
	            mf.transferTo(source);  
	            mff = source;  
	        } catch (Exception e) {  
	            String errorMsg = "Upload file " + source.getAbsoluteFile() + " Error!" + e.getMessage();  
	            throw new RuntimeException(errorMsg);  
	  
	        }  
	    }  
 
	}  
}
