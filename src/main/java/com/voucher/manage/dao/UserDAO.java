package com.voucher.manage.dao;

import java.util.List;
import java.util.Map;

import com.voucher.manage.daoModel.ClientInfo;
import com.voucher.manage.daoModel.Users;

public interface UserDAO {

	public Integer selectRepeatAdmin(String username);
	
	public Integer insertIntoUser(Users users);
	
	public Users selectUsersByCampusAdmin(String username);
	
	public Map getAllFullUser(Integer limit, Integer offset, String sort,
			String order,String search);
		
	public Integer insertIntoClientInfo(ClientInfo clientInfo);
	
	public Map getAllClientInfo(Integer limit, Integer offset, String sort,
			String order,String search);
	
	public Integer deleteClientInfo(Integer id);
	
}
