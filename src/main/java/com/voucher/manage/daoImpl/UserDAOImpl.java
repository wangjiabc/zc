package com.voucher.manage.daoImpl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.voucher.manage.dao.UserDAO;
import com.voucher.manage.daoModel.ClientInfo;
import com.voucher.manage.daoModel.Image;
import com.voucher.manage.daoModel.LoanDeal;
import com.voucher.manage.daoModel.MoblieReport;
import com.voucher.manage.daoModel.Users;
import com.voucher.manage.daoSQL.DeleteExe;
import com.voucher.manage.daoSQL.InsertExe;
import com.voucher.manage.daoSQL.SelectExe;
import com.voucher.manage.daoSQL.UpdateExe;
import com.voucher.manage.tools.MyTestUtil;
import com.voucher.manage.tools.TransMapToString;

public class UserDAOImpl extends JdbcDaoSupport implements UserDAO{
		
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
		MyTestUtil.print(clientInfo);
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
	public Integer deleteClientInfo(String guid) {
		// TODO Auto-generated method stub
		String[] where={"[GUID] =",guid};
		
		LoanDeal loanDeal=new LoanDeal();
		
		loanDeal.setWhere(where);
		
		int count=(int) SelectExe.getCount(this.getJdbcTemplate(), loanDeal).get("");
		
		if(count>0){
			return 2;
		}else{
		
			ClientInfo clientInfo=new ClientInfo();

			clientInfo.setWhere(where);
		
			return DeleteExe.get(this.getJdbcTemplate(), clientInfo);
		}
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
	public ClientInfo getClientInfoByIdCard(String guid) {
		// TODO Auto-generated method stub
		ClientInfo clientInfo=new ClientInfo();
		
		clientInfo.setLimit(10);
		clientInfo.setOffset(0);
		clientInfo.setNotIn("id");
		
		String[] where={"GUID = ",guid};
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
	public ClientInfo getClientInfoByGUID(String guid) {
		// TODO Auto-generated method stub
		ClientInfo clientInfo=new ClientInfo();
		
		clientInfo.setLimit(10);
		clientInfo.setOffset(0);
		clientInfo.setNotIn("id");
		
		String[] where={"[GUID] = ",guid};
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

	@Override
	public Integer updateUser(Users users) {
		// TODO Auto-generated method stub
		return UpdateExe.get(this.getJdbcTemplate(), users);
	}

	@Override
	public Integer deleteUser(Users users) {
		// TODO Auto-generated method stub
		return DeleteExe.get(this.getJdbcTemplate(), users);
	}



}
