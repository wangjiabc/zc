package com.voucher.manage.dao;

import java.util.Map;

import org.springframework.web.bind.annotation.RequestParam;

import com.voucher.manage.daoModel.LoanDeal;
import com.voucher.manage.daoModel.ProductInfo;
import com.voucher.manage.daoModel.Repayment;

public interface LoanDao {
	
	public Map getAllProduct(@RequestParam Integer limit,@RequestParam Integer offset,String sort,String order,
			Map searchMap);
	
	public Integer insertProduct(ProductInfo productInfo);
	
	public Integer deleteProduct(ProductInfo productInfo);
	
	public Integer insertLoanDeal(LoanDeal loanDeal);
	
	public Integer updateLoanDeal(LoanDeal loanDeal);
	
	public Integer insertRepayMent(Repayment repayment);
	
	public Integer updateRepayMent(Repayment repayment);
	
	public Map getAllLoanDeal(@RequestParam Integer limit,@RequestParam Integer offset,String sort,String order,
			Map searchMap);
	
	public Map getAllRepayMent(@RequestParam Integer limit,@RequestParam Integer offset,String sort,String order,
			Map searchMap);
	
	public Double getAllRepay(String loan_GUID);
	
	public Double getAllOverdue(String loan_GUID);
}
