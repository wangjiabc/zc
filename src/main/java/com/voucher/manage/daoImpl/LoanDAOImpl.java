package com.voucher.manage.daoImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.voucher.manage.dao.LoanDao;
import com.voucher.manage.daoModel.LoanDeal;
import com.voucher.manage.daoModel.ProductInfo;
import com.voucher.manage.daoModel.Repayment;
import com.voucher.manage.daoSQL.DeleteExe;
import com.voucher.manage.daoSQL.InsertExe;
import com.voucher.manage.daoSQL.SelectExe;
import com.voucher.manage.daoSQL.SelectSQL;
import com.voucher.manage.daoSQL.UpdateExe;
import com.voucher.manage.tools.TransMapToString;

public class LoanDAOImpl extends JdbcDaoSupport implements LoanDao{

	@Override
	public Map getAllProduct(Integer limit, Integer offset, String sort, String order, Map searchMap) {
		// TODO Auto-generated method stub
		ProductInfo productInfo=new ProductInfo();
		
		productInfo.setLimit(limit);
		productInfo.setOffset(offset);
		productInfo.setNotIn("id");
		
		if(sort!=null&&sort.equals("id")){
			sort="id";
			productInfo.setSort(sort);
		}
		
		if(order!=null&&order.equals("asc")){
			order="asc";
			productInfo.setOrder(order);
		}
		
		if(order!=null&&order.equals("desc")){
			order="desc";
			productInfo.setOrder(order);
		}
		
		if(!searchMap.isEmpty()){
		    String[] where=TransMapToString.get(searchMap);
		    productInfo.setWhere(where);
		}
		
		Map<String, Object> map=new HashMap();
		
		List list=SelectExe.get(this.getJdbcTemplate(), productInfo);
		
		map.put("rows",list);
		 
		int total=(int) SelectExe.getCount(this.getJdbcTemplate(),productInfo).get("");
		
		map.put("total", total);
		
		return map;
	}

	@Override
	public Integer insertProduct(ProductInfo productInfo) {
		// TODO Auto-generated method stub
		return InsertExe.get(this.getJdbcTemplate(), productInfo);
	}

	@Override
	public Integer insertLoanDeal(LoanDeal loanDeal) {
		// TODO Auto-generated method stub
		return InsertExe.get(this.getJdbcTemplate(), loanDeal);
	}

	@Override
	public Integer insertRepayMent(Repayment repayment) {
		// TODO Auto-generated method stub
		return InsertExe.get(this.getJdbcTemplate(), repayment);
	}

	@Override
	public Integer deleteProduct(ProductInfo productInfo) {
		// TODO Auto-generated method stub
		return DeleteExe.get(this.getJdbcTemplate(), productInfo);
	}

	@Override
	public Integer updateLoanDeal(LoanDeal loanDeal) {
		// TODO Auto-generated method stub
		return UpdateExe.get(this.getJdbcTemplate(), loanDeal);
	}

	@Override
	public Integer updateRepayMent(Repayment repayment) {
		// TODO Auto-generated method stub
		return UpdateExe.get(this.getJdbcTemplate(), repayment);
	}

	@Override
	public Map getAllLoanDeal(Integer limit, Integer offset, String sort, String order, Map searchMap) {
		// TODO Auto-generated method stub
		LoanDeal loanDeal=new LoanDeal();
		
		loanDeal.setLimit(limit);
		loanDeal.setOffset(offset);
		loanDeal.setNotIn("id");
		
		if(sort!=null){
			loanDeal.setSort(sort);
		}
		
		if(order!=null){
			loanDeal.setOrder(order);
		}		
		
		if(!searchMap.isEmpty()){
		    String[] where=TransMapToString.get(searchMap);
		    loanDeal.setWhere(where);
		}
		
		Map<String, Object> map=new HashMap();
		
		List list=SelectExe.get(this.getJdbcTemplate(), loanDeal);		
		
		map.put("rows",list);
		 
		int total=(int) SelectExe.getCount(this.getJdbcTemplate(),loanDeal).get("");
		
		map.put("total", total);
		
		return map;
	}

	@Override
	public Map getAllRepayMent(Integer limit, Integer offset, String sort, String order, Map searchMap) {
		// TODO Auto-generated method stub
		Repayment repayment=new Repayment();
		
		repayment.setLimit(limit);
		repayment.setOffset(offset);
		repayment.setNotIn("id");
		
		if(sort!=null&&sort.equals("id")){
			sort="id";
			repayment.setSort(sort);
		}
		
		if(order!=null&&order.equals("asc")){
			order="asc";
			repayment.setOrder(order);
		}
		
		if(order!=null&&order.equals("desc")){
			order="desc";
			repayment.setOrder(order);
		}
		
		if(sort!=null){
			repayment.setSort(sort);
		}
		
		if(order!=null){
			repayment.setOrder(order);
		}
		
		if(!searchMap.isEmpty()){
		    String[] where=TransMapToString.get(searchMap);
		    repayment.setWhere(where);
		}
		
		Map<String, Object> map=new HashMap();
		
		List list=SelectExe.get(this.getJdbcTemplate(), repayment);		
		
		map.put("rows",list);
		 
		int total=(int) SelectExe.getCount(this.getJdbcTemplate(),repayment).get("");
		
		map.put("total", total);
		
		return map;
	}

}
