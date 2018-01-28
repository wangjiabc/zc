package com.voucher.manage.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.voucher.manage.dao.LoanDao;
import com.voucher.manage.dao.UserDAO;
import com.voucher.manage.daoModel.ClientInfo;
import com.voucher.manage.daoModel.LoanDeal;
import com.voucher.manage.daoModel.Repayment;
import com.voucher.sqlserver.context.Connect;

@Controller
@RequestMapping("/repay")
public class RepayController {

	public final static String filePath=System.getProperty("user.home")+"\\Desktop\\pasoft\\ZC\\";
	
    ApplicationContext applicationContext=new Connect().get();
	
    LoanDao loanDao=(LoanDao)applicationContext.getBean("loanDao");
    
    UserDAO userDao=(UserDAO) applicationContext.getBean("dao");
	
	@RequestMapping("/getTodayRepayment")
	public @ResponseBody Map getTodayRepayment(@RequestParam Integer limit,@RequestParam Integer offset,String sort,String order,
			String search,HttpServletRequest request){
		
		HttpSession session=request.getSession();  //取得session的type变量，判断是否为公众号管理员
		String campusAdmin=(String) session.getAttribute("campusAdmin");
		Integer type=(Integer) session.getAttribute("type");
		
		if(order!=null&&order.equals("asc")){
			order="asc";
		}
	
		if(order!=null&&order.equals("desc")){
			order="desc";
		}
		
		Map searchMap=new HashMap<>();
		
		if(search!=null&&!search.trim().equals("")){
			search="%"+search+"%";  
			searchMap.put("[Repayment].userName like ", search);
		}
		
		SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd ");
		
		Date date=new Date();
		
		String repayTime=sdf.format(date);
		
		searchMap.put("convert(varchar(11),[Repayment].repaytime,120)=",repayTime);
		
		if(type!=0){
			searchMap.put("[Repayment].campusAdmin =",campusAdmin);
		}
		
		Map map=loanDao.getAllRepayMent(limit, offset, sort, order, searchMap);
		
		return map;
		
	}
	
	@RequestMapping("/getTomorrowRepayment")
	public @ResponseBody Map getTomorrowRepayment(@RequestParam Integer limit,@RequestParam Integer offset,String sort,String order,
			String search,HttpServletRequest request){
		
		HttpSession session=request.getSession();  //取得session的type变量，判断是否为公众号管理员
		String campusAdmin=(String) session.getAttribute("campusAdmin");
		Integer type=(Integer) session.getAttribute("type");
		
		if(order!=null&&order.equals("asc")){
			order="asc";
		}
	
		if(order!=null&&order.equals("desc")){
			order="desc";
		}
		
		Map searchMap=new HashMap<>();
		
		if(search!=null&&!search.trim().equals("")){
			search="%"+search+"%";  
			searchMap.put("[Repayment].userName like ", search);
		}
		
		SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd ");
		
		Calendar calendar = Calendar.getInstance();
		
		calendar.add(Calendar.DATE,1);
		
		Date date=calendar.getTime();
		
		String repayTime=sdf.format(date);
		
		searchMap.put("convert(varchar(11),[Repayment].repaytime,120)=",repayTime);
		
		if(type!=0){
			searchMap.put("[Repayment].campusAdmin =",campusAdmin);
		}
		
		Map map=loanDao.getAllRepayMent(limit, offset, sort, order, searchMap);
		
		return map;
		
	}
	
	
	@RequestMapping("/getAllLoanDeal")
	public @ResponseBody Map getAllLoanDeal(@RequestParam Integer limit,@RequestParam Integer offset,String sort,String order,
			String search,HttpServletRequest request){
		
		HttpSession session=request.getSession();  //取得session的type变量，判断是否为公众号管理员
		String campusAdmin=(String) session.getAttribute("campusAdmin");
		Integer type=(Integer) session.getAttribute("type");
		
		order="desc";
		sort="status";
		
		if(order!=null&&order.equals("asc")){
			order="asc";
		}
	
		if(order!=null&&order.equals("desc")){
			order="desc";
		}
		
		Map searchMap=new HashMap<>();
		
		if(search!=null&&!search.trim().equals("")){
			search="%"+search+"%";  
			searchMap.put("[LoanDeal].userName like ", search);
		}
		
				
		if(type!=0){
			searchMap.put("[Repayment].campusAdmin =",campusAdmin);
		}
		
		Map map=loanDao.getAllLoanDeal(limit, offset, sort, order, searchMap);
		
		return map;
	}
	
	@RequestMapping("/updateRepayment")
	public @ResponseBody Integer updateRepayment(@RequestParam String loan_GUID, 
			@RequestParam Double repay,@RequestParam String shouldtime,
			@RequestParam Double advance,String remark,
			@RequestParam String repaytime,HttpServletRequest request){
		
		HttpSession session=request.getSession();  //取得session的type变量，判断是否为公众号管理员
		String campusAdmin=(String) session.getAttribute("campusAdmin");
		
		Repayment repayment=new Repayment();
		
		repayment.setStatus(1);
		repayment.setRepay(repay);
		
		Date date=new Date();
		
		repayment.setGivetime(date);
		repayment.setTransact(campusAdmin);
		if(advance!=null){
			repayment.setAdvance(advance);
		}
		repayment.setRemark(remark);
		Date shoulddate = null;
		Date repaydate = null;
		DateFormat fmt =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			shoulddate = fmt.parse(shouldtime);
			repaydate = fmt.parse(repaytime);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String shouldTime= sdf.format(shoulddate);
		String repayTime= sdf.format(repaydate);
		
		String[] where={"[Repayment].loan_GUID=",loan_GUID};
		
		repayment.setWhere(where);
		
		int status=loanDao.updateRepayMent(repayment);
		System.out.println("status="+status);
		if(status>1){ 		//更新总还款金额
			
			Map searchMap=new HashMap<>();
			
			searchMap.put("[Repayment].loan_GUID=",loan_GUID);
			searchMap.put("[Repayment].status=","1");
			
			Map repayMentMap=loanDao.getAllRepayMent(1, 0, null, null, searchMap);
			
			List repayMentList=(List) repayMentMap.get("rows");
			
			int count=(int) repayMentMap.get("total");
			
			Double allRepay=loanDao.getAllRepay(loan_GUID);
			Double allOverdue=loanDao.getAllOverdue(loan_GUID);
			LoanDeal loanDeal=new LoanDeal();
			
			loanDeal.setAllrepay(allRepay);
			loanDeal.setShould_overdue(allOverdue);
			
			String[] where2={"[LoanDeal].loan_GUID=",loan_GUID};
			
			loanDeal.setWhere(where2);
			
			status=loanDao.updateLoanDeal(loanDeal);
			
			if(status==1){		//更新总还款状态
				
				Map searchMap2=new HashMap<>();
				
				searchMap2.put("[LoanDeal].loan_GUID=",loan_GUID);
				
				Map loanDealMap=loanDao.getAllLoanDeal(1, 0, null, null, searchMap2);
				
				List loanDealList=(List) loanDealMap.get("rows");
				
				LoanDeal loanDeal2=null;
				
				try{
					loanDeal2=(LoanDeal) loanDealList.get(0);
				}catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				
				Double should_repay=loanDeal2.getShould_repay();
				
				String GUID=loanDeal2.getGUID();
				
				int nper=loanDeal2.getNper();
				
					LoanDeal loanDeal3=new LoanDeal();
					loanDeal3.setStatus(1);
					String[] where3={"[LoanDeal].loan_GUID=",loan_GUID};
					loanDeal3.setWhere(where3);
					loanDao.updateLoanDeal(loanDeal3);
				    
					Map searchMap3=new HashMap<>();
					
					searchMap3.put("[LoanDeal].GUID=",GUID);				
					searchMap3.put("[LoanDeal].status>", "1");
					
					Map loanDealMap2=loanDao.getAllLoanDeal(10, 0, null, null, searchMap3);
					
					int count2=(int) loanDealMap2.get("total");
					
					if(count2==0){
						ClientInfo clientInfo=new ClientInfo();
						
						clientInfo.setStatus(0);
						
						String[] where4={"[clientInfo].GUID=",GUID};
						
						clientInfo.setWhere(where4);
						
						userDao.updateClientInfoByGUID(clientInfo);
	
					}
					
				}
	
		 }
		
		return status;
		
	}
	
}
