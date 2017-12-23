package com.voucher.manage.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.voucher.manage.daoModel.ClientInfo;
import com.voucher.manage.daoModel.Image;
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
	
	public ClientInfo getClientInfoQuery(String username,String userPhoneNum,String useridCard);
	
	public Integer deleteClientInfo(Integer id);
	
	public Integer insertIntoImage(Image image);
	
	public List<Image> selectImageByGUID(String clientInfo_GUID);
}
