package com.voucher.manage.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.voucher.manage.dao.LoanDao;
import com.voucher.manage.dao.UserDAO;
import com.voucher.manage.daoModel.ClientInfo;
import com.voucher.manage.daoModel.LoanDeal;
import com.voucher.manage.daoModel.ProductInfo;
import com.voucher.manage.daoModel.Repayment;
import com.voucher.manage.tools.MyTestUtil;
import com.voucher.sqlserver.context.Connect;

@Controller
@RequestMapping("/loan")
public class LoanController {

	public final static String filePath=System.getProperty("user.home")+"\\Desktop\\pasoft\\ZC\\";
	
    ApplicationContext applicationContext=new Connect().get();
	
    LoanDao loanDao=(LoanDao)applicationContext.getBean("loanDao");
    
    UserDAO userDao=(UserDAO) applicationContext.getBean("dao");
    
    private final static int general=0;    
    private final static int complete=1;
    private final static int notPass=2;
    private final static int generalRepay=3;
    private final static int overdueRepay=4;
    private final static int overdueClient=5;    
    private final static int callClient=6;
    private final static int badClient=7;

    
	@RequestMapping("/getAllProduct")
	public @ResponseBody Map getAllProduct(@RequestParam Integer limit,@RequestParam Integer offset,String sort,String order,
			String search,HttpServletRequest request){
		if(order!=null&&order.equals("asc")){
			order="asc";
		}
	
		if(order!=null&&order.equals("desc")){
			order="desc";
		}
		
		Map searchMap=new HashMap<>();
		
		if(search!=null&&!search.trim().equals("")){
			search="%"+search+"%";  
			searchMap.put("[ProductInfo].proName like ", search);
		}	
		
		Map map=loanDao.getAllProduct(limit, offset, sort, order, searchMap);
		
		return map;
	}
	
	@RequestMapping("/getProduct")
	public @ResponseBody JSONArray getProduct(){
		
		Map map=loanDao.getAllProduct(2000, 0, null, null, new HashMap<>());
		
		List list=(List) map.get("rows");
		
		return (JSONArray) JSON.toJSON(list);
	}
	
	@RequestMapping("/insertProduct")
	public @ResponseBody Integer insertProduct(@RequestParam String productData){
		
		JSONObject jsonObject;
		
		ProductInfo productInfo=new ProductInfo();
		
		try{
			
			jsonObject= new JSONObject(productData);

			UUID uuid=UUID.randomUUID();
			
			productInfo.setProName(jsonObject.getString("name"));
			productInfo.setPro_GUID(uuid.toString());
			productInfo.setRepay_type(jsonObject.getInt("repay_type"));
			productInfo.setInterest(jsonObject.getDouble("interest"));
			
			Date date=new Date();
			
			productInfo.setDatetime(date);
			
		 }catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		 }
		
		return loanDao.insertProduct(productInfo);
	}
	
	
	@RequestMapping("/delProduct")
	public @ResponseBody Integer delProduct(@RequestParam String pro_GUIDs){
		ProductInfo productInfo=new ProductInfo();
		
		String[] pro_GUIDString = pro_GUIDs.split(",");
		
		int status = 0;
		try{
		  for (String pro_GUID : pro_GUIDString) {
		
			String[] where={"[ProductInfo].pro_GUID=",pro_GUID};
		
			Map searchMap=new HashMap<>();
			
			searchMap.put("[LoanDeal].pro_GUID=",pro_GUID);
			searchMap.put("[LoanDeal].status>",""+notPass+"");
 			
			int count=(int) loanDao.getAllLoanDeal(1, 0, null, null, searchMap).get("total");
			
			System.out.println("count="+count);
			
			if(count>0){
				return 2;
			}
			productInfo.setWhere(where);
		
			status=loanDao.deleteProduct(productInfo);
		   }
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return 0;
		}
		return status;
		
	}
	
	@RequestMapping("/insertLoanDeal")
	public @ResponseBody Integer insertLoanDeal(@RequestParam String loanDealData,
			HttpServletRequest request){
		
		  JSONObject jsonObject;
		  
		  String campusAdmin;
		  String GUID=null;
		  ProductInfo productInfo=null;
		  String proName;
		  String pro_GUID;
		  Double money = null; //本金
		  Double interest; //利息
		  int nper = 0;   //期数
		  int cycle = 0;  //周期长度
		  Double stage; //每期本金
		  Double should_repay;//全部应还金额
		  Double nper_interest;//每期应还金额
		  LoanDeal loanDeal=new LoanDeal();
		  Repayment repayment=new Repayment();
		  
		  UUID uuid=UUID.randomUUID();
		 
		  Calendar calendar = Calendar.getInstance();
		  
		  Date date=calendar.getTime();
		  
		  Date shouldtime;
		  Date repaytime;
		  
		  HttpSession session=request.getSession();  //取得session的type变量，判断是否为公众号管理员
		  campusAdmin=(String) session.getAttribute("campusAdmin");
		  
		  loanDeal.setLoan_GUID(uuid.toString());
		  loanDeal.setCampusAdmin(campusAdmin);
		  loanDeal.setDatetime(date);
		  
		  repayment.setLoan_GUID(uuid.toString());
		  repayment.setCampusAdmin(campusAdmin);
		  repayment.setDatetime(date);
		  
		  try{
			  jsonObject=new JSONObject(loanDealData);
			  pro_GUID=jsonObject.getString("pro_GUID");
			  Map searchMap=new HashMap<>();
			  searchMap.put("[ProductInfo].pro_GUID=", pro_GUID);
			  List list=(List) loanDao.getAllProduct(1, 0, null, null, searchMap).get("rows");
			  productInfo=(ProductInfo) list.get(0);

			  if(productInfo.getRepay_type()==0){
			  
				  GUID=jsonObject.getString("GUID");
				  money=jsonObject.getDouble("money");
				  nper=jsonObject.getInt("nper");
				  cycle=jsonObject.getInt("cycle");
				  stage=money/nper;
				  interest=money*productInfo.getInterest()/10000;
				  should_repay=money+nper*cycle*interest;
				  nper_interest=cycle*interest;
			  
				  loanDeal.setGUID(jsonObject.getString("GUID"));
				  loanDeal.setUserName(jsonObject.getString("username"));
				  loanDeal.setUserPhoneNum(jsonObject.getString("userPhoneNum"));
				  loanDeal.setUseridCard(jsonObject.getString("useridCard"));
				  loanDeal.setPro_GUID(pro_GUID);
				  loanDeal.setProName(productInfo.getProName());
				  loanDeal.setMoney(money);
				  loanDeal.setStage(stage);
				  loanDeal.setInterest(interest);
				  loanDeal.setNper(nper);
				  loanDeal.setCycle(cycle);
				  loanDeal.setShould_repay(should_repay);
				  loanDeal.setDatetime(date);
				  loanDeal.setStatus(generalRepay);
				  loanDeal.setRemark(jsonObject.getString("remark"));
			  
				  repayment.setGUID(jsonObject.getString("GUID"));
				  repayment.setUserName(jsonObject.getString("username"));
				  repayment.setUserPhoneNum(jsonObject.getString("userPhoneNum"));
				  repayment.setUseridCard(jsonObject.getString("useridCard"));
				  repayment.setPro_GUID(pro_GUID);
				  repayment.setProName(productInfo.getProName());
				  repayment.setMoney(money);
				  repayment.setStage(stage);
				  repayment.setInterest(interest);
				  repayment.setCycle(cycle);
				  repayment.setNper_interest(nper_interest);
				  repayment.setDatetime(date);
				  repayment.setStatus(generalRepay);
			  }else if(productInfo.getRepay_type()==1){
				  GUID=jsonObject.getString("GUID");
				  money=jsonObject.getDouble("money");
				  nper=jsonObject.getInt("nper");
				  cycle=jsonObject.getInt("cycle");
				  stage=0.0;
				  interest=money*productInfo.getInterest()/10000;
				  if(nper>1){
					  should_repay=money+(nper-1)*cycle*interest;
				  }else {
					  should_repay=money+nper*cycle*interest;
				  }				  
				  nper_interest=cycle*interest;
			  
				  loanDeal.setGUID(jsonObject.getString("GUID"));
				  loanDeal.setUserName(jsonObject.getString("username"));
				  loanDeal.setUserPhoneNum(jsonObject.getString("userPhoneNum"));
				  loanDeal.setUseridCard(jsonObject.getString("useridCard"));
				  loanDeal.setPro_GUID(pro_GUID);
				  loanDeal.setProName(productInfo.getProName());
				  loanDeal.setMoney(money);
				  loanDeal.setStage(stage);
				  loanDeal.setInterest(interest);
				  loanDeal.setNper(nper);
				  loanDeal.setCycle(cycle);
				  loanDeal.setShould_repay(should_repay);
				  loanDeal.setDatetime(date);
				  loanDeal.setStatus(generalRepay);
				  loanDeal.setRemark(jsonObject.getString("remark"));
			  
				  repayment.setGUID(jsonObject.getString("GUID"));
				  repayment.setUserName(jsonObject.getString("username"));
				  repayment.setUserPhoneNum(jsonObject.getString("userPhoneNum"));
				  repayment.setUseridCard(jsonObject.getString("useridCard"));
				  repayment.setPro_GUID(pro_GUID);
				  repayment.setProName(productInfo.getProName());
				  repayment.setMoney(money);
				  repayment.setStage(stage);
				  repayment.setInterest(interest);
				  repayment.setCycle(cycle);
				  repayment.setNper_interest(nper_interest);
				  repayment.setDatetime(date);
				  repayment.setStatus(generalRepay);
				  
			  }else{
				  return 0;
			  }
			  			  
		  }catch (Exception e) {
			// TODO: handle exception
		    e.printStackTrace();
		  }
		  
		  
		  int status=0;
		  int i=0;
		  

		  if(productInfo.getRepay_type()==0){
			  for(;i<nper;i++){			  
			  
				  shouldtime=calendar.getTime();
			  
				  calendar.add(Calendar.DATE,cycle);
			  
				  repaytime=calendar.getTime();
			  
				  repayment.setShouldtime(shouldtime);
				  repayment.setRepaytime(repaytime);
				  status=loanDao.insertRepayMent(repayment);
				  if(status==0){
					  TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					  return 0;
				  }
			  }
		  }else if(productInfo.getRepay_type()==1){
			  if(nper<=1){
				  shouldtime=calendar.getTime();
				  
				  calendar.add(Calendar.DATE,cycle);
			  
				  repaytime=calendar.getTime();
			  
				  repayment.setShouldtime(shouldtime);
				  repayment.setRepaytime(repaytime);
				  repayment.setStage(money);
				  status=loanDao.insertRepayMent(repayment);
				  if(status==0){
					  TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					  return 0;
				  }
			  }else{
				  for(;i<(nper-1);i++){			  
				  
					  shouldtime=calendar.getTime();
			  
					  calendar.add(Calendar.DATE,cycle);
					  
					  repaytime=calendar.getTime();
			  
					  repayment.setShouldtime(shouldtime);
					  repayment.setRepaytime(repaytime);
					  					  
					  status=loanDao.insertRepayMent(repayment);
					  if(status==0){
						  TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					  	return 0;
					  }
				  }
				  //最后一期不算利息
				  shouldtime=calendar.getTime();
				  calendar.add(Calendar.DATE,cycle);				  
				  repaytime=calendar.getTime();		  
				  repayment.setShouldtime(shouldtime);
				  repayment.setRepaytime(repaytime);
				  
				  repayment.setInterest(0.0);
				  repayment.setNper_interest(0.0);
				  repayment.setStage(money);
				  status=loanDao.insertRepayMent(repayment);
				  if(status==0){
					  TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					  return 0;
				  }
			  }
			  
		  }
	      status=loanDao.insertLoanDeal(loanDeal);
	      if(status==0){
			  TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			  return 0;
		  }
	      
	      ClientInfo clientInfo=new ClientInfo();
	      
	      clientInfo.setStatus(generalRepay);
	      
	      String[] where={"[clientInfo].GUID=",GUID};
	      clientInfo.setWhere(where);
	      
	      userDao.updateClientInfoByGUID(clientInfo);
	      
	      return status;
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
		
		searchMap.put("LoanDeal.status>", "1");
		
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
	
	
	@RequestMapping("/getAllRepayment")
	public @ResponseBody Map getAllRepayment(@RequestParam Integer limit,@RequestParam Integer offset,String sort,String order,
			String search,String search2,String search3,String search4,
			String search5,HttpServletRequest request){
		
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
		
		if(search2!=null&&!search2.trim().equals("")){
			searchMap.put("[Repayment].status=", search2);
			order="desc";
			sort="status";
		}
		
		if(search3!=null&&!search3.trim().equals("")){
			searchMap.put("[Repayment].GUID=", search3);
			order="desc";
			sort="status";
		}
		
		if(search4!=null&&!search4.trim().equals("")){
			searchMap.put("[Repayment].loan_GUID=", search4);
			order="desc";
			sort="status";
		}
		
		if(type!=0){
			searchMap.put("[Repayment].campusAdmin =",campusAdmin);
		}
		
		Map map=loanDao.getAllRepayMent(limit, offset, sort, order, searchMap);
		
		return map;
		
	}
	
	@RequestMapping("/updateRepayment")
	public @ResponseBody Integer updateRepayment(@RequestParam String loan_GUID, 
			@RequestParam Double repay,@RequestParam String shouldtime,
			@RequestParam Double overdue,String remark,
			@RequestParam String repaytime,HttpServletRequest request){
		
		HttpSession session=request.getSession();  //取得session的type变量，判断是否为公众号管理员
		String campusAdmin=(String) session.getAttribute("campusAdmin");
		
		Repayment repayment=new Repayment();
		
		repayment.setStatus(1);
		repayment.setRepay(repay);
		
		Date date=new Date();
		
		repayment.setGivetime(date);
		repayment.setTransact(campusAdmin);
		if(overdue!=null){
			repayment.setOverdue(overdue);
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
		
		String[] where={"[Repayment].loan_GUID=",loan_GUID,"convert(varchar(20),[Repayment].shouldtime,120)=",shouldTime,
				"convert(varchar(20),[Repayment].repaytime,120)=",repayTime};
		repayment.setWhere(where);
		
		System.out.println("shouldtime="+shouldTime);
		System.out.println("repaytime="+repayTime);
		int status=loanDao.updateRepayMent(repayment);
		
		if(status==1){ 		//更新总还款金额
			
			Map searchMap=new HashMap<>();
			
			searchMap.put("[Repayment].loan_GUID=",loan_GUID);
			searchMap.put("[Repayment].status=","1");
			
			Map repayMentMap=loanDao.getAllRepayMent(1, 0, null, null, searchMap);
			
			List repayMentList=(List) repayMentMap.get("rows");
			
			int count=(int) repayMentMap.get("total");
			
			/*
			Repayment repayment2=null;
			
			try{
			  repayment2=(Repayment) repayMentList.get(0);
			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			
			Double stage=repayment2.getStage();
			Double interest=repayment2.getInterest();
			*/
			
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
				
				if(nper==count){
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
			
		}else{
			 TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		
		return status;
		
	}
	
	@RequestMapping("/upOverdueRepayment")
	public @ResponseBody Integer upOverdueRepayment(HttpServletRequest request){
		
		HttpSession session=request.getSession();  //取得session的type变量，判断是否为公众号管理员
		String campusAdmin=(String) session.getAttribute("campusAdmin");
		Integer type=(Integer) session.getAttribute("type");
		
		Repayment repayment=new Repayment();
		
		Date date=new Date();
		
		SimpleDateFormat sdf  =   new  SimpleDateFormat( " yyyy-MM-dd HH:mm:ss " ); 
		String time = sdf.format(date);
		
		String[] where={"[Repayment].repaytime < ",time,
				"[Repayment].status >","1"};		
		
		repayment.setStatus(3);
		
		repayment.setWhere(where);
		
		loanDao.updateRepayMent(repayment);
		
		Repayment repayment2=new Repayment();
		
		Map searchMap=new HashMap<>();
		searchMap.put("[Repayment].status >",""+generalRepay+"");
		
		if(type!=0){
			searchMap.put("[Repayment].campusAdmin =",campusAdmin);
		}
		
		Map RepaymentMap=loanDao.getAllRepayMent(10, 0, null, null, searchMap);
		
		List list=(List) RepaymentMap.get("rows");
		
		Iterator<Repayment> iterator=list.iterator();
		
		while (iterator.hasNext()) {
		    Repayment repayment3=iterator.next();
			
		    String GUID=repayment3.getGUID();
		    String loan_GUID=repayment3.getLoan_GUID();
		    
		    ClientInfo clientInfo=new ClientInfo();
		    clientInfo.setStatus(3);		    
		    String[] where1={"[clientInfo].GUID=",GUID};
		    clientInfo.setWhere(where1);
		    userDao.updateClientInfoByGUID(clientInfo);
		    
		    LoanDeal loanDeal=new LoanDeal();
		    loanDeal.setStatus(3);
		    String[] where2={"[LoanDeal].loan_GUID=",loan_GUID};
		    loanDeal.setWhere(where2);
		    loanDao.updateLoanDeal(loanDeal);		    
		}
		
		return 0;
	}
	
	
	@RequestMapping("/overdueRepayment")
	public @ResponseBody Map overdueRepayment(HttpServletRequest request){
		HttpSession session=request.getSession();  //取得session的type变量，判断是否为公众号管理员
		String campusAdmin=(String) session.getAttribute("campusAdmin");
		Integer type=(Integer) session.getAttribute("type");
		
		Map searchMap=new HashMap<>();
		Map allSearchMap=new HashMap<>();
		searchMap.put("[Repayment].status >",""+generalRepay+"");
		
		if(type!=0){
			searchMap.put("[Repayment].campusAdmin =",campusAdmin);
			allSearchMap.put("[Repayment].campusAdmin =",campusAdmin);
		}		
		
		Map allRepaymentMap=loanDao.getAllRepayMent(1, 0, null, null, allSearchMap);
		
		Map RepaymentMap=loanDao.getAllRepayMent(1, 0, null, null, searchMap);
		
		int allCount=(int) allRepaymentMap.get("total");
		
		int count=(int) RepaymentMap.get("total");
		
		Map map=new HashMap<>();
		
		map.put("all", allCount);
		
		map.put("count", count);
		
		return map;
		
	}
	
	@RequestMapping("/needRepayment")
	public @ResponseBody Map needRepayment(HttpServletRequest request){
		HttpSession session=request.getSession();  //取得session的type变量，判断是否为公众号管理员
		String campusAdmin=(String) session.getAttribute("campusAdmin");
		Integer type=(Integer) session.getAttribute("type");
		
		Map searchMap=new HashMap<>();
		Map allSearchMap=new HashMap<>();
		searchMap.put("[Repayment].status =",""+generalRepay+"");
		
		if(type!=0){
			searchMap.put("[Repayment].campusAdmin =",campusAdmin);
			allSearchMap.put("[Repayment].campusAdmin =",campusAdmin);
		}		
		
		Map allRepaymentMap=loanDao.getAllRepayMent(1, 0, null, null, allSearchMap);
		
		Map RepaymentMap=loanDao.getAllRepayMent(1, 0, null, null, searchMap);
		
		int allCount=(int) allRepaymentMap.get("total");
		
		int count=(int) RepaymentMap.get("total");
		
		Map map=new HashMap<>();
		
		map.put("all", allCount);
		
		map.put("count", count);
		
		return map;
	}
	
	@RequestMapping("/successRepayment")
	public @ResponseBody Map successRepayment(HttpServletRequest request){
		HttpSession session=request.getSession();  //取得session的type变量，判断是否为公众号管理员
		String campusAdmin=(String) session.getAttribute("campusAdmin");
		Integer type=(Integer) session.getAttribute("type");
		
		Map searchMap=new HashMap<>();
		Map allSearchMap=new HashMap<>();
		searchMap.put("[Repayment].status =","1");
		
		if(type!=0){
			searchMap.put("[Repayment].campusAdmin =",campusAdmin);
			allSearchMap.put("[Repayment].campusAdmin =",campusAdmin);
		}		
		
		Map allRepaymentMap=loanDao.getAllRepayMent(1, 0, null, null, allSearchMap);
		
		Map RepaymentMap=loanDao.getAllRepayMent(1, 0, null, null, searchMap);
		
		int allCount=(int) allRepaymentMap.get("total");
		
		int count=(int) RepaymentMap.get("total");
		
		Map map=new HashMap<>();
		
		map.put("all", allCount);
		
		map.put("count", count);
		
		return map;
	}
	
}
