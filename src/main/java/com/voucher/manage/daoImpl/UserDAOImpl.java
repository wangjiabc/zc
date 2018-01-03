package com.voucher.manage.daoImpl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.apache.http.message.BasicNameValuePair;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.fasterxml.jackson.databind.JsonNode;
import com.voucher.manage.dao.UserDAO;
import com.voucher.manage.daoModel.ClientInfo;
import com.voucher.manage.daoModel.Image;
import com.voucher.manage.daoModel.MoblieReport;
import com.voucher.manage.daoModel.Users;
import com.voucher.manage.daoSQL.DeleteExe;
import com.voucher.manage.daoSQL.InsertExe;
import com.voucher.manage.daoSQL.SelectExe;
import com.voucher.manage.daoSQL.UpdateExe;
import com.voucher.manage.tools.LocalFile;
import com.voucher.manage.tools.TransMapToString;

import common.JsonUtils;
import common.StringUtils;
import credit.AbstractCredit;
import credit.MobileDemo;

public class UserDAOImpl extends JdbcDaoSupport implements UserDAO{

	public final static String path="\\Desktop\\pasoft\\ZC\\report\\";
	
	public final static String filePath=System.getProperty("user.home")+path;
	
	public BlockingQueue<String> queue=new ArrayBlockingQueue<>(3);

	public BlockingQueue<String> queue2=new ArrayBlockingQueue<>(3);
	
	@Override
	public Integer updateState(String campusAdmin, Integer state) {
		// TODO Auto-generated method stub
		Users users=new Users();
		
		users.setState(state);
		
		String[] where={"campusAdmin =", campusAdmin};
		
		users.setWhere(where);
		
		return UpdateExe.get(this.getJdbcTemplate(), users);
	}
	
	@Override
	public Integer updatePassWord(String campusAdmin, String password) {
		// TODO Auto-generated method stub
		Users users=new Users();
		
		users.setPassword(password);
		
		String[] where={"campusAdmin =", campusAdmin};
		
		users.setWhere(where);
		
		return UpdateExe.get(this.getJdbcTemplate(), users);
	}
	
	@Override
	public Integer selectRepeatAdmin(String username) {
		// TODO Auto-generated method stub
		Users users=new Users();
		
		String[] where={"campusAdmin=",username};
		
		users.setWhere(where);
		
		Map map=SelectExe.getCount(this.getJdbcTemplate(), users);
		
		Integer total=(Integer) map.get("");
		
		return total;
	}

	@Override
	public Integer insertIntoUser(Users users) {
		// TODO Auto-generated method stub
		
		return InsertExe.get(this.getJdbcTemplate(), users);
	}

	@Override
	public Users selectUsersByCampusAdmin(String username) {
		// TODO Auto-generated method stub
		Users users=new Users();
		users.setLimit(1000);
		users.setOffset(0);
		users.setNotIn("id");
		
		String[] where={"campusAdmin=",username};
		
		users.setWhere(where);
		
		int i=(int) SelectExe.getCount(this.getJdbcTemplate(), users).get("");
		
		if(i==1){
			List<Users> userlist=SelectExe.get(this.getJdbcTemplate(), users);
		
			Users users2=userlist.get(0);
			
			return users2;
		}
		
		return null;
	}

	@Override
	public Map getAllFullUser(Integer limit, Integer offset, String sort,
			String order, Map searchMap) {
		// TODO Auto-generated method stub
		Users users=new Users();
		
		users.setLimit(limit);
		users.setOffset(offset);
		users.setSort(sort);
		users.setOrder(order);
		users.setNotIn("id");
		
		String[] where=TransMapToString.get(searchMap);	
		users.setWhere(where);
		
        Map<String, Object> map=new HashMap<>();
		
	    List<Users> users2=SelectExe.get(this.getJdbcTemplate(), users);

	    map.put("rows", users2);
	    
		int total=(int) SelectExe.getCount(this.getJdbcTemplate(), users).get("");
		
		map.put("total", total);
		System.out.println("map="+map);
		return map;
	}


	@Override
	public Integer insertIntoClientInfo(ClientInfo clientInfo) {
		// TODO Auto-generated method stub
		
		return InsertExe.get(this.getJdbcTemplate(), clientInfo);
	}

	@Override
	public Integer updateClientInfoByGUID(ClientInfo clientInfo) {
		// TODO Auto-generated method stub
		return UpdateExe.get(this.getJdbcTemplate(), clientInfo);
	}
	
	@Override
	public Map getAllClientInfo(Integer limit, Integer offset, String sort, String order, Map<String, String> search) {
		// TODO Auto-generated method stub
		ClientInfo clientInfo=new ClientInfo();
		
		clientInfo.setLimit(limit);
		clientInfo.setOffset(offset);
		clientInfo.setNotIn("id");
		clientInfo.setSort("id");
		clientInfo.setOrder("desc");
		
		
		if(sort!=null&&sort.equals("id")){
			sort="id";
			clientInfo.setSort(sort);
		}
		
		if(order!=null&&order.equals("asc")){
			order="asc";
			clientInfo.setOrder(order);
		}
		
		if(order!=null&&order.equals("desc")){
			order="desc";
			clientInfo.setOrder(order);
		}
		
		if(!search.isEmpty()){
		    String[] where=TransMapToString.get(search);
		    clientInfo.setWhere(where);
		}
		

		Map<String, Object> map=new HashMap<>();
		
	    List<ClientInfo> clientInfos=SelectExe.get(this.getJdbcTemplate(), clientInfo);

	    map.put("rows", clientInfos);
	    
		int total=(int) SelectExe.getCount(this.getJdbcTemplate(), clientInfo).get("");
		
		map.put("total", total);
		System.out.println("map="+map);
		return map;
	}

	@Override
	public Integer deleteClientInfo(Integer id) {
		// TODO Auto-generated method stub
		ClientInfo clientInfo=new ClientInfo();
		
		String[] where={"id =",String.valueOf(id)};
		
		clientInfo.setWhere(where);
		
		return DeleteExe.get(this.getJdbcTemplate(), clientInfo);
	}

	@Override
	public Integer insertIntoImage(Image image) {
		// TODO Auto-generated method stub
		return InsertExe.get(this.getJdbcTemplate(), image);
	}

	@Override
	public List<Image> selectImageByGUID(String clientInfo_GUID) {
		// TODO Auto-generated method stub
		Image image=new Image();
		image.setClientInfo_GUID(clientInfo_GUID);
		image.setLimit(1000);
		image.setOffset(0);
		image.setNotIn("id");
		
		String[] where={"clientInfo_GUID = ",clientInfo_GUID};
		
		image.setWhere(where);
		
		return SelectExe.get(this.getJdbcTemplate(), image);
	}

	@Override
	public ClientInfo getClientInfo(String uuid) {
		// TODO Auto-generated method stub
		ClientInfo clientInfo=new ClientInfo();
		
		clientInfo.setLimit(10);
		clientInfo.setOffset(0);
		clientInfo.setNotIn("id");
		
		String[] where={"GUID = ",uuid};
		clientInfo.setWhere(where);
		
		ClientInfo clientInfo2=null;
		try{
		  List<ClientInfo> list=SelectExe.get(this.getJdbcTemplate(), clientInfo);
		  clientInfo2=list.get(0);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return clientInfo2;
	}

	@Override
	public ClientInfo getClientInfoById(String id) {
		// TODO Auto-generated method stub
		ClientInfo clientInfo=new ClientInfo();
		
		clientInfo.setLimit(10);
		clientInfo.setOffset(0);
		clientInfo.setNotIn("id");
		
		String[] where={"id = ",id};
		clientInfo.setWhere(where);
		
		ClientInfo clientInfo2=null;
		try{
		  List<ClientInfo> list=SelectExe.get(this.getJdbcTemplate(), clientInfo);
		  clientInfo2=list.get(0);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return clientInfo2;
	}
	
	@Override
	public ClientInfo getClientInfoQuery(String username, String userPhoneNum, String useridCard) {
		// TODO Auto-generated method stub
        ClientInfo clientInfo=new ClientInfo();
		
		clientInfo.setLimit(10);
		clientInfo.setOffset(0);
		clientInfo.setNotIn("id");
		
		String[] where={"userName = ",username,
				" useridCard =",useridCard};
		clientInfo.setWhere(where);
		
		ClientInfo clientInfo2=null;
		try{
		  List<ClientInfo> list=SelectExe.get(this.getJdbcTemplate(), clientInfo);
		  clientInfo2=list.get(0);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return clientInfo2;
	}
	
	@Override
	public Integer updateLastLoginTime(Users users, Date date) {
		// TODO Auto-generated method stub
		Users users2=new Users();
		
		users2.setLastLoginDate(date);
		
		String[] where={"campusAdmin =",users.getCampusAdmin()};
		
		users2.setWhere(where);
		
		return UpdateExe.get(this.getJdbcTemplate(), users2);
	}

	@Override
	public Integer insertMobileReport(MoblieReport moblieReport) {
		// TODO Auto-generated method stub
		return InsertExe.get(this.getJdbcTemplate(), moblieReport);
	}


	@Override
	public Map selectMobileReportByUseridCard(Integer limit, Integer offset, String sort, String order,
			Map<String, String> search) {
		// TODO Auto-generated method stub
		MoblieReport moblieReport=new MoblieReport();
		
		moblieReport.setLimit(limit);
		moblieReport.setOffset(offset);
		moblieReport.setNotIn("id");
		moblieReport.setOrder(order);
		moblieReport.setSort("datetime");
		
		if(!search.isEmpty()){
			String[] where=TransMapToString.get(search);
		    moblieReport.setWhere(where);
		}
		
        Map<String, Object> map=new HashMap<>();
		
		List<MoblieReport> moblieReports=SelectExe.get(this.getJdbcTemplate(), moblieReport);

	    map.put("rows", moblieReports);
	    
		int total=(int) SelectExe.getCount(this.getJdbcTemplate(), moblieReport).get("");
		
		map.put("total", total);
		
		return map;
	}

	@Override
	public Map selectAllMobileReport(Integer limit, Integer offset, String sort, String order,
			Map<String, String> search) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<MoblieReport> getReport(MoblieReport moblieReport) {
		// TODO Auto-generated method stub
		return SelectExe.get(this.getJdbcTemplate(), moblieReport);
	}


	
	public void timer(List<BasicNameValuePair> reqParam,String campusAdmin,String username,String password,
			String identityName,String identityCardNo) {
        System.out.println("轮循 start");
        final Timer timer = new Timer();
        
        timer.schedule(new TimerTask() {
        long startTime=System.currentTimeMillis();   //获取开始时间         
        public void run() {

                try {
                    //循环取的状态，查询结果
                    //停止循环(发送短信失败或信息查询成功)
                    if (roundRobin(reqParam,startTime,campusAdmin,username,password,
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
	
	 public boolean roundRobin(List<BasicNameValuePair> reqParam,long startTime,String campusAdmin,String username,String password,
				String identityName,String identityCardNo) throws Exception {
            System.out.println("mobileDemo="+reqParam);
	        //状态查询
	        String json = AbstractCredit.httpClient.doPost(AbstractCredit.apiUrlStatus, reqParam);
	        JsonNode rootNode = JsonUtils.toJsonNode(json);
	        String token = JsonUtils.getJsonValue(rootNode, "token");
	        String code =JsonUtils.getJsonValue(rootNode, "code");
	        String msg = JsonUtils.getJsonValue(rootNode, "msg");
	        long endTime=System.currentTimeMillis(); //获取结束时间  
	        System.out.println("程序运行时间： "+(endTime-startTime)+"ms"); 
	        System.out.println("循环取的状态:" + code);
	        System.out.println("循环取的信息:" + json);
	         
	        if((endTime-startTime)>201232){
	        	json="{'code':'0044','msg':'错误,系统超时'}";
	        	queue2.put(json);
	        	return true;
	        }
	        
	        System.out.println("json="+json);
	        System.out.println("code="+code);
	        
	        if("2010".equals(code)){
	        	queue2.put(json);
	        	return true;
	        }
	        
	        if("1013".equals(code)){
	        	queue2.put(json);
	        	return true;
	        }
	        
	        if("1109".equals(code)){
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
	                 String report=AbstractCredit.getReport(reqParam);	                 
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
	      			
	      			insertMobileReport(moblieReport);
	                 
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
