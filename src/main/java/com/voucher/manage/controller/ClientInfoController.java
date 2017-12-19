package com.voucher.manage.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.aspectj.util.FileUtil;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.alibaba.fastjson.JSONArray;
import com.google.gson.Gson;
import com.voucher.manage.dao.UserDAO;
import com.voucher.manage.daoModel.ClientInfo;
import com.voucher.manage.daoModel.Image;
import com.voucher.manage.tools.MyTestUtil;
import com.voucher.sqlserver.context.Connect;


@Controller
@RequestMapping("/clientinfo")
public class ClientInfoController {

    ApplicationContext applicationContext=new Connect().get();
	
	UserDAO userDao=(UserDAO) applicationContext.getBean("dao");
	
	@RequestMapping(value = "/insert")
	public @ResponseBody Map insert(@RequestParam String clientInfoData,
			HttpServletRequest request){
		String campusAdmin;
		Integer type;
		Integer state;
		
		JSONObject jsonObject; 
		
		ClientInfo clientInfo=new ClientInfo();	
		Gson gson = new Gson();
		
		Map map=new HashMap<>();
		
		try {
			jsonObject= new JSONObject(clientInfoData);
	//		clientInfo=gson.fromJson(clientInfoData,ClientInfo.class);
			clientInfo.setUserName(jsonObject.getString("username"));
			clientInfo.setUserOldname(jsonObject.getString("oldname"));

			try{
			 clientInfo.setUserAge(Integer.parseInt(jsonObject.getString("userage")));
				clientInfo.setUserProvideNum(Integer.parseInt(jsonObject.getString("userProvideNum")));
				clientInfo.setUserChildrenNum(Integer.parseInt(jsonObject.getString("userChildrenNum")));
				clientInfo.setInstime(Integer.parseInt(jsonObject.getString("Instime")));
				clientInfo.setInsNum(Integer.parseInt(jsonObject.getString("insNum")));
				clientInfo.setAttatchNum(Integer.parseInt(jsonObject.getString("attatchNum")));
				clientInfo.setPayTerm(Integer.parseInt(jsonObject.getString("payTerm")));
				clientInfo.setApplyMoney(Integer.parseInt(jsonObject.getString("applyMoney")));
				clientInfo.setWeekRepayment(Integer.parseInt(jsonObject.getString("weekRepayment")));
			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			clientInfo.setUserSex(jsonObject.getString("usersex"));
			clientInfo.setUserEducation(jsonObject.getString("userEducation"));
			clientInfo.setUserMarriage(jsonObject.getString("userMarriage"));
			clientInfo.setUseridCard(jsonObject.getString("useridCard"));


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
	
			clientInfo.setPayOntime(jsonObject.getString("payOntime"));
			clientInfo.setPayMode(jsonObject.getString("payMode"));
			clientInfo.setInsMoney(jsonObject.getString("insMoney"));

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
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map.put("data", "error");
			return map;
		}

		HttpSession session=request.getSession();  //取得session的type变量，判断是否为公众号管理员
		campusAdmin=(String) session.getAttribute("campusAdmin");
		type=(Integer) session.getAttribute("type");
		state=(Integer) session.getAttribute("state");
		
		UUID uuid=UUID.randomUUID();
		
		clientInfo.setGUID(uuid.toString());
		clientInfo.setCampusAdmin(campusAdmin);
				
		System.out.println("type="+type+"      state="+state);
		
		MyTestUtil.print(clientInfo);
		
		try{
		  userDao.insertIntoClientInfo(clientInfo);
		}catch (Exception e) {
			// TODO: handle exception
			map.put("data", "error");
			return map;
		}
		map.put("data", uuid.toString());
		return map;
		
	}
	
	
	@RequestMapping(value = "/update")
	public @ResponseBody Map update(@RequestParam String clientInfoData,@RequestParam String uuid,
			HttpServletRequest request){
		String campusAdmin;
		Integer type;
		Integer state;
		
		JSONObject jsonObject; 
		
		ClientInfo clientInfo=new ClientInfo();	
		Gson gson = new Gson();
		
		Map map=new HashMap<>();
		
		try {
			jsonObject= new JSONObject(clientInfoData);
	//		clientInfo=gson.fromJson(clientInfoData,ClientInfo.class);
			clientInfo.setUserName(jsonObject.getString("username"));
			clientInfo.setUserOldname(jsonObject.getString("oldname"));

			try{
			 clientInfo.setUserAge(Integer.parseInt(jsonObject.getString("userage")));
				clientInfo.setUserProvideNum(Integer.parseInt(jsonObject.getString("userProvideNum")));
				clientInfo.setUserChildrenNum(Integer.parseInt(jsonObject.getString("userChildrenNum")));
				clientInfo.setInstime(Integer.parseInt(jsonObject.getString("Instime")));
				clientInfo.setInsNum(Integer.parseInt(jsonObject.getString("insNum")));
				clientInfo.setAttatchNum(Integer.parseInt(jsonObject.getString("attatchNum")));
				clientInfo.setPayTerm(Integer.parseInt(jsonObject.getString("payTerm")));
				clientInfo.setApplyMoney(Integer.parseInt(jsonObject.getString("applyMoney")));
				clientInfo.setWeekRepayment(Integer.parseInt(jsonObject.getString("weekRepayment")));
			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			clientInfo.setUserSex(jsonObject.getString("usersex"));
			clientInfo.setUserEducation(jsonObject.getString("userEducation"));
			clientInfo.setUserMarriage(jsonObject.getString("userMarriage"));
			clientInfo.setUseridCard(jsonObject.getString("useridCard"));


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
	
			clientInfo.setPayOntime(jsonObject.getString("payOntime"));
			clientInfo.setPayMode(jsonObject.getString("payMode"));
			clientInfo.setInsMoney(jsonObject.getString("insMoney"));

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
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map.put("data", "error");
			return map;
		}

		HttpSession session=request.getSession();  //取得session的type变量，判断是否为公众号管理员
		campusAdmin=(String) session.getAttribute("campusAdmin");
		type=(Integer) session.getAttribute("type");
		state=(Integer) session.getAttribute("state");
		
		
		clientInfo.setGUID(uuid.toString());
		clientInfo.setCampusAdmin(campusAdmin);
				
		System.out.println("type="+type+"      state="+state);
		
		String[] where={"GUID =",uuid};
        clientInfo.setWhere(where);
        		
		try{
		  userDao.updateClientInfoByGUID(clientInfo);
		}catch (Exception e) {
			// TODO: handle exception
			map.put("data", "error");
			return map;
		}
		map.put("data", uuid.toString());
		return map;
		
	}
	
	
	@RequestMapping(value = "/getAll")
	public @ResponseBody Map getAllClientInfo(@RequestParam Integer limit,@RequestParam Integer offset,String sort,String order,
			String search,HttpServletRequest request) {
		Integer type=(Integer) request.getSession().getAttribute("type");
	    String campusAdmin=(String) request.getSession().getAttribute("campusAdmin");
		if(order!=null&&order.equals("asc")){
			order="asc";
		}
	
		if(order!=null&&order.equals("desc")){
			order="desc";
		}
		
		Map searchMap=new HashMap<>();
		
		if(search!=null&&!search.trim().equals("")){
			search="%"+search+"%";  
			searchMap.put("[ZC].[dbo].[clientInfo].userName like ", search);
		}		

		if(type>0){
			searchMap.put("[ZC].[dbo].[clientInfo].campusAdmin =", campusAdmin);
		}
		
		System.out.println("search="+search);
		Map map=userDao.getAllClientInfo(limit, offset, null, order, searchMap);
		
		return map;
	}
	
	@RequestMapping(value="/get")
	public @ResponseBody ClientInfo getClientInfo(@RequestParam String uuid,HttpServletRequest request){
		ClientInfo clientInfo=userDao.getClientInfo(uuid);
		
		return clientInfo;
	}
	
	
	@RequestMapping(value="/delete")
	public @ResponseBody Integer delete(@RequestParam Integer id){
				
		return userDao.deleteClientInfo(id);
		
	}
	
	@RequestMapping(value="/imageUrl")
	public @ResponseBody List<String> imageUrl(@RequestParam String clientInfo_GUID){
		List<Image> list=userDao.selectImageByGUID(clientInfo_GUID);
		
		List<String> images=new ArrayList<>();
		
		Iterator<Image> iterator=list.iterator();
		
		int i=0;
		while(iterator.hasNext()){
			Image image=iterator.next();
			images.add(i, image.getPath()+image.getOrigName());
			i++;
		}
		
		return images;
	}
	
	@RequestMapping(value="inputImage")
	public @ResponseBody Integer uploadFilesSpecifyPath(@RequestParam("file") MultipartFile[] file,@RequestParam String uuid,HttpServletRequest request,HttpServletResponse response) throws Exception {  
		 long startTime=System.currentTimeMillis();   //获取开始时间  
	      /*  if(!file.isEmpty()){  
	            try {  
	                //定义输出流 将文件保存在D盘    file.getOriginalFilename()为获得文件的名字   
	                FileOutputStream os = new FileOutputStream("C:\\Users\\WangJing\\Desktop\\bb\\2\\"+file.getOriginalFilename());  
	                InputStream in = file.getInputStream();  
	                int b = 0;  
	                while((b=in.read())!=-1){ //读取文件   
	                    os.write(b);  
	                }  
	                os.flush(); //关闭流   
	                in.close();  
	                os.close();  
	                  
	            } catch (FileNotFoundException e) {  
	                e.printStackTrace();  
	            } catch (IOException e) {  
	                e.printStackTrace();  
	            }  
	        }  */
		 
		 System.out.println("uuid="+uuid);
		 
		 if(file!=null&&file.length>0){
	            //组合image名称，“;隔开”
	            List<String> fileName =new ArrayList<String>();
                System.out.println("length="+file.length);
	            try {
	                for (int i = 0; i < file.length; i++) {
	                    if (!file[i].isEmpty()) {

	                        //上传文件，随机名称，";"分号隔开
	                       // fileName.add(FileUtil.uploadImage(request, "/upload/"+"/", file[i], true));
	                    	fileName.add(uploadImage(request, "/upload/", file[i],uuid, true));
	                    }
	                }

	                //上传成功
	                if(fileName!=null&&fileName.size()>0){
	                	System.out.println(fileName);
	                    System.out.println("上传成功！");
	                    return 1;
	                 //   Constants.printJson(response, fileName);;
	                }else {
	                  //  Constants.printJson(response, "上传失败！文件格式错误！");
	                }
	            } catch (Exception e) {
	                e.printStackTrace();
	              //  Constants.printJson(response, "上传出现异常！异常出现在：class.UploadController.insert()");
	            }
	        }else {
	         //   Constants.printJson(response, "没有检测到文件！");
	        }
	    
		 
		 
	        long endTime=System.currentTimeMillis(); //获取结束时间  
	        System.out.println("上传文件共使用时间："+(endTime-startTime));  
	        
	        return 0;
 
	} 
	
	
	/**
     * 上传图片
     *  原名称
     * @param request 请求
     * @param path_deposit 存放位置(路径)
     * @param file 文件
     * @param isRandomName 是否随机名称
     * @return 完整文件路径
     */
    public  String uploadImage(HttpServletRequest request,String path_deposit,MultipartFile file,String clientInfo_GUID,boolean isRandomName) {
        //上传
        try {
            String[] typeImg={"gif","png","jpg","jpeg"};

            if(file!=null){
                String origName=file.getOriginalFilename();// 文件原名称
                System.out.println("上传的文件原名称:"+origName);
                // 判断文件类型
                String type=origName.indexOf(".")!=-1?origName.substring(origName.lastIndexOf(".")+1, origName.length()):null;
                    boolean booIsType=false;
                    for (int i = 0; i < typeImg.length; i++) {
                        if (typeImg[i].equals(type.toLowerCase())) {
                            booIsType=true;
                        }
                    }
                    //类型正确
                    if (booIsType) {
                        //存放图片文件的路径
                        String path=request.getSession().getServletContext().getRealPath(path_deposit);
                       
                    	//组合名称
                        String fileSrc=""; 
                        //是否随机名称
                        String uuid=UUID.randomUUID().toString();
                        if(isRandomName){
                            origName=uuid+origName.substring(origName.lastIndexOf("."));
                        }
                        //判断是否存在目录
                        File targetFile=new File(path,origName);
                        System.out.println(path);
                        if(!targetFile.exists()){
                            targetFile.mkdirs();//创建目录
                        }
                        //上传
                        try{
                          file.transferTo(targetFile);
                          
                          Image image=new Image();
                          image.setGUID(uuid);
                          image.setClientInfo_GUID(clientInfo_GUID);
                          image.setPath(path_deposit);
                          image.setOrigName(origName);
                          
                          userDao.insertIntoImage(image);
                        }catch (Exception e) {
							// TODO: handle exception
                        	e.printStackTrace();
						}
                        //完整路径
                        fileSrc=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+path_deposit+origName;
                        System.out.println("图片上传成功:"+fileSrc);
                        return fileSrc;
                    }
                }
            return null;
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
	
}
