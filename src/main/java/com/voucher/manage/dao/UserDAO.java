package com.voucher.manage.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.voucher.manage.daoModel.ClientInfo;
import com.voucher.manage.daoModel.Image;
import com.voucher.manage.daoModel.MoblieReport;
import com.voucher.manage.daoModel.Users;

public interface UserDAO {

	public Integer updateLastLoginTime(Users users,Date date);
	
	public Integer updateState(String campusAdmin,Integer state);
	
	public Integer updatePassWord(String campusAdmin, String password); 
	
	public Integer selectRepeatAdmin(String username);
	
	public Integer insertIntoUser(Users users);
	
	public Users selectUsersByCampusAdmin(String username);
	
	public Map getAllFullUser(Integer limit, Integer offset, String sort,
			String order,Map searchMap);
		
	public Integer insertIntoClientInfo(ClientInfo clientInfo);
	
	public Integer updateClientInfoByGUID(ClientInfo clientInfo);
	
	public Map getAllClientInfo(Integer limit, Integer offset, String sort,
			String order,Map<String, String> search);
	
	public ClientInfo getClientInfo(String uuid);
	
	public ClientInfo getClientInfoById(String id);
	
	public ClientInfo getClientInfoByIdCard(String useridCard);
	
	public ClientInfo getClientInfoByGUID(String guid);
	
	public ClientInfo getClientInfoQuery(String username,String userPhoneNum,String useridCard);
	
	public Integer deleteClientInfo(String guid);
	
	public Integer insertIntoImage(Image image);
	
	public List<Image> selectImageByGUID(String clientInfo_GUID);
	
	public Integer insertMobileReport(MoblieReport moblieReport);
	
	public List<MoblieReport> getReport(MoblieReport moblieReport);
	
	public Map selectMobileReportByUseridCard(Integer limit, Integer offset, String sort,
			String order,Map<String, String> search);
	
	public Map selectAllMobileReport(Integer limit, Integer offset, String sort,
			String order,Map<String, String> search);
	
	public Integer updateUser(Users users);
	
	public Integer deleteUser(Users users);
	
}
