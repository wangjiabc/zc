package com.voucher.manage.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.voucher.manage.dao.UserDAO;
import com.voucher.manage.daoModel.ClientInfo;
import com.voucher.manage.daoModel.Image;
import com.voucher.manage.tools.MyTestUtil;
import com.voucher.sqlserver.context.Connect;


@Controller
@RequestMapping("/clientinfo")
public class ClientInfoController {

	public final static String filePath=System.getProperty("user.home")+"\\Desktop\\pasoft\\ZC\\";
	
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
			clientInfo.setRemark(jsonObject.getString("remark"));

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
			clientInfo.setKinshipRelation(jsonObject.getString("kinshipRelation"));
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

			
			clientInfo.setEmergencyName1(jsonObject.getString("emergencyName1"));
			clientInfo.setEmergencyRelation1(jsonObject.getString("emergencyRelation1"));
			clientInfo.setEmergencyUnitName1(jsonObject.getString("emergencyUnitName1"));
			clientInfo.setEmergencyPhoneNum1(jsonObject.getString("emergencyPhoneNum1"));
			clientInfo.setEmergencyAddress1(jsonObject.getString("emergencyAddress1"));
			
			clientInfo.setReference(jsonObject.getString("reference"));
			clientInfo.setSalesman(jsonObject.getString("salesman"));
			clientInfo.setFuzai(jsonObject.getString("fuzai"));
			clientInfo.setCompany(jsonObject.getString("company"));
			clientInfo.setCompany1(jsonObject.getString("company1"));
			clientInfo.setProductName(jsonObject.getString("productName"));
			clientInfo.setProductName1(jsonObject.getString("productName1"));
			clientInfo.setMoney(jsonObject.getString("money"));
			clientInfo.setMoney1(jsonObject.getString("money1"));
			clientInfo.setRepay(jsonObject.getString("repay"));
			clientInfo.setRepay1(jsonObject.getString("repay1"));
			
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
		
		Date date=new Date();
		clientInfo.setInsertTime(date);
		
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
			clientInfo.setRemark(jsonObject.getString("remark"));

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
			clientInfo.setKinshipRelation(jsonObject.getString("kinshipRelation"));
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

			
			clientInfo.setEmergencyName1(jsonObject.getString("emergencyName1"));
			clientInfo.setEmergencyRelation1(jsonObject.getString("emergencyRelation1"));
			clientInfo.setEmergencyUnitName1(jsonObject.getString("emergencyUnitName1"));
			clientInfo.setEmergencyPhoneNum1(jsonObject.getString("emergencyPhoneNum1"));
			clientInfo.setEmergencyAddress1(jsonObject.getString("emergencyAddress1"));
			
			clientInfo.setReference(jsonObject.getString("reference"));
			clientInfo.setSalesman(jsonObject.getString("salesman"));
			clientInfo.setFuzai(jsonObject.getString("fuzai"));
			clientInfo.setCompany(jsonObject.getString("company"));
			clientInfo.setCompany1(jsonObject.getString("company1"));
			clientInfo.setProductName(jsonObject.getString("productName"));
			clientInfo.setProductName1(jsonObject.getString("productName1"));
			clientInfo.setMoney(jsonObject.getString("money"));
			clientInfo.setMoney1(jsonObject.getString("money1"));
			clientInfo.setRepay(jsonObject.getString("repay"));
			clientInfo.setRepay1(jsonObject.getString("repay1"));
			
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
		
		Date date=new Date();
		clientInfo.setUpdateTime(date);
		
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
			String search,String search2,HttpServletRequest request) {
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

		if(search2!=null&&!search2.trim().equals("")){
			searchMap.put("[ZC].[dbo].[clientInfo].status = ", search2);
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
	
	@RequestMapping(value="/getById")
	public @ResponseBody ClientInfo getClientInfoById(@RequestParam String id,HttpServletRequest request){
		ClientInfo clientInfo=userDao.getClientInfoById(id);
		
		return clientInfo;
	}
	
	@RequestMapping(value="/getByGuid")
	public @ResponseBody ClientInfo getClientInfoByGuid(@RequestParam String guid,HttpServletRequest request){
		ClientInfo clientInfo=userDao.getClientInfoByGUID(guid);
		
		return clientInfo;
	}
	
	@RequestMapping(value="/getQuery")
	public @ResponseBody Map getClientInfoQuery(@RequestParam String username,@RequestParam String userPhoneNum, 
			@RequestParam String useridCard,HttpServletRequest request){
		ClientInfo clientInfo=userDao.getClientInfoQuery(username, userPhoneNum, useridCard);
		
		Map map=new HashMap<>();
		
		if(clientInfo!=null){
			map.put("state", "succeed");
			map.put("id", clientInfo.getId());
		}else{
			map.put("state", "false");
		}
		
		return map;
	}
	
	@RequestMapping(value="/getAllQuery")
	public @ResponseBody Map getClientInfoAllQuery(@RequestParam String username,
			@RequestParam String useridCard,@RequestParam Integer limit,@RequestParam Integer offset,String sort,String order,
			String search,HttpServletRequest request){
		if(order!=null&&order.equals("asc")){
			order="asc";
		}
	
		if(order!=null&&order.equals("desc")){
			order="desc";
		}
		
		Map searchMap=new HashMap<>();
		
		searchMap.put("[ZC].[dbo].[clientInfo].useridCard = ", useridCard);
		searchMap.put("[ZC].[dbo].[clientInfo].username = ", username);
		
		if(search!=null&&!search.trim().equals("")){
			search="%"+search+"%";  
			searchMap.put("[ZC].[dbo].[clientInfo].userName like ", search);
		}		

		
		System.out.println("search="+search);
		Map map=userDao.getAllClientInfo(limit, offset, null, order, searchMap);
		
		return map;
	}
	
	@RequestMapping(value="/delete")
	public @ResponseBody Integer delete(@RequestParam String guid){
				
		return userDao.deleteClientInfo(guid);
		
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
	            String upimage = null;
                System.out.println("length="+file.length);
	            try {
	                for (int i = 0; i < file.length; i++) {
	                    if (!file[i].isEmpty()) {

	                        //上传文件，随机名称，";"分号隔开
	                       // fileName.add(FileUtil.uploadImage(request, "/upload/"+"/", file[i], true));
	                    	upimage=uploadImage(request, "/upload/", file[i],uuid, true);
	                    	fileName.add(upimage);
	                    }
	                }

	                //上传成功
	                if(fileName!=null&&fileName.size()>0&&!upimage.equals("error")){
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
            String[] typeImg={"gif","png","jpg","jpeg","ico"};

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
                        
                        copyFile(path+origName, filePath+origName);
                        
                        System.out.println("图片上传成功:"+path+origName);                       
                        System.out.println("图片上传成功2:"+filePath+origName);
                        System.out.println("图片上传成功:"+fileSrc);
                        return fileSrc;
                    }
                }
            return "error";
        }catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }
	
    public void copyFile(String oldPath, String newPath) { 
        try { 
            int bytesum = 0; 
            int byteread = 0; 
            File oldfile = new File(oldPath); 
            if (oldfile.exists()) { //文件存在时 
                InputStream inStream = new FileInputStream(oldPath); //读入原文件 
                FileOutputStream fs = new FileOutputStream(newPath); 
                byte[] buffer = new byte[1444]; 
                int length; 
                while ( (byteread = inStream.read(buffer)) != -1) { 
                    bytesum += byteread; //字节数 文件大小 
                    System.out.println(bytesum); 
                    fs.write(buffer, 0, byteread); 
                } 
                inStream.close(); 
            } 
        } 
        catch (Exception e) { 
            System.out.println("复制单个文件操作出错"); 
            e.printStackTrace(); 
        } 
    } 
    
}
