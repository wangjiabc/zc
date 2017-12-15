package com.voucher.manage.daoImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.voucher.manage.dao.UserDAO;
import com.voucher.manage.daoModel.ClientInfo;
import com.voucher.manage.daoModel.Users;
import com.voucher.manage.daoSQL.DeleteExe;
import com.voucher.manage.daoSQL.InsertExe;
import com.voucher.manage.daoSQL.SelectExe;

public class UserDAOImpl extends JdbcDaoSupport implements UserDAO{

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
			String order, String search) {
		// TODO Auto-generated method stub
		Users users=new Users();
		
		users.setLimit(limit);
		users.setOffset(offset);
		users.setSort(sort);
		users.setOrder(order);
		users.setNotIn("id");
		
        if(search!=null){
        	search="%"+search+"%";
        	String[] where={"campusAdmin like ",search};
        	users.setWhere(where);
        }
				
		
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
	public Map getAllClientInfo(Integer limit, Integer offset, String sort, String order, String search) {
		// TODO Auto-generated method stub
		ClientInfo clientInfo=new ClientInfo();
		
		clientInfo.setLimit(limit);
		clientInfo.setOffset(offset);
		clientInfo.setNotIn("id");
		


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
 

}
