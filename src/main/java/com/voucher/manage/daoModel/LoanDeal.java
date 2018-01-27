package com.voucher.manage.daoModel;

import java.util.Date;

import java.io.Serializable;

import com.voucher.manage.daoSQL.annotations.*;

@DBTable(name="[ZC].[dbo].[LoanDeal]")
public class LoanDeal implements Serializable{

    private static final long serialVersionUID = 1L;

    @SQLInteger(name="id")
	private Integer id;

    @SQLString(name="GUID")
	private String GUID;

    @SQLString(name="loan_GUID")
	private String loan_GUID;

    @SQLString(name="userName")
	private String userName;

    @SQLString(name="useridCard")
	private String useridCard;

    @SQLString(name="userPhoneNum")
	private String userPhoneNum;

    @SQLString(name="campusAdmin")
	private String campusAdmin;

    @SQLString(name="pro_GUID")
	private String pro_GUID;

    @SQLString(name="proName")
	private String proName;

    @SQLDouble(name="money")
	private Double money;

    @SQLDouble(name="interest")
	private Double interest;

    @SQLInteger(name="nper")
	private Integer nper;

    @SQLInteger(name="cycle")
	private Integer cycle;

    @SQLDouble(name="stage")
	private Double stage;

    @SQLDouble(name="should_repay")
	private Double should_repay;

    @SQLDateTime(name="datetime")
	private Date datetime;

    @SQLDouble(name="allrepay")
	private Double allrepay;

    @SQLDateTime(name="updatetime")
	private Date updatetime;

    @SQLInteger(name="status")
	private Integer status;

    @SQLString(name="remark")
	private String remark;

	public void setId(Integer id){
		this.id = id;
	}

	public Integer getId(){
		return id;
	}

	public void setGUID(String GUID){
		this.GUID = GUID;
	}

	public String getGUID(){
		return GUID;
	}

	public void setLoan_GUID(String loan_GUID){
		this.loan_GUID = loan_GUID;
	}

	public String getLoan_GUID(){
		return loan_GUID;
	}

	public void setUserName(String userName){
		this.userName = userName;
	}

	public String getUserName(){
		return userName;
	}

	public void setUseridCard(String useridCard){
		this.useridCard = useridCard;
	}

	public String getUseridCard(){
		return useridCard;
	}

	public void setUserPhoneNum(String userPhoneNum){
		this.userPhoneNum = userPhoneNum;
	}

	public String getUserPhoneNum(){
		return userPhoneNum;
	}

	public void setCampusAdmin(String campusAdmin){
		this.campusAdmin = campusAdmin;
	}

	public String getCampusAdmin(){
		return campusAdmin;
	}

	public void setPro_GUID(String pro_GUID){
		this.pro_GUID = pro_GUID;
	}

	public String getPro_GUID(){
		return pro_GUID;
	}

	public void setProName(String proName){
		this.proName = proName;
	}

	public String getProName(){
		return proName;
	}

	public void setMoney(Double money){
		this.money = money;
	}

	public Double getMoney(){
		return money;
	}

	public void setInterest(Double interest){
		this.interest = interest;
	}

	public Double getInterest(){
		return interest;
	}

	public void setNper(Integer nper){
		this.nper = nper;
	}

	public Integer getNper(){
		return nper;
	}

	public void setCycle(Integer cycle){
		this.cycle = cycle;
	}

	public Integer getCycle(){
		return cycle;
	}

	public void setStage(Double stage){
		this.stage = stage;
	}

	public Double getStage(){
		return stage;
	}

	public void setShould_repay(Double should_repay){
		this.should_repay = should_repay;
	}

	public Double getShould_repay(){
		return should_repay;
	}

	public void setDatetime(Date datetime){
		this.datetime = datetime;
	}

	public Date getDatetime(){
		return datetime;
	}

	public void setAllrepay(Double allrepay){
		this.allrepay = allrepay;
	}

	public Double getAllrepay(){
		return allrepay;
	}

	public void setUpdatetime(Date updatetime){
		this.updatetime = updatetime;
	}

	public Date getUpdatetime(){
		return updatetime;
	}

	public void setStatus(Integer status){
		this.status = status;
	}

	public Integer getStatus(){
		return status;
	}

	public void setRemark(String remark){
		this.remark = remark;
	}

	public String getRemark(){
		return remark;
	}




/*
*数据库查询参数
*/
    @QualifiLimit(name="limit")
    private Integer limit;
    @QualifiOffset(name="offset")
    private Integer offset;
    @QualifiNotIn(name="notIn")
    private String notIn;
    @QualifiSort(name="sort")
    private String sort;
    @QualifiOrder(name="order")
    private String order;
    @QualifiWhere(name="where")
    private String[] where;
    @QualifiWhereTerm(name="whereTerm")
    private String whereTerm;


	public void setLimit(Integer limit){
		this.limit = limit;
	}

	public Integer getLimit(){
		return limit;
	}

	public void setOffset(Integer offset){
		this.offset = offset;
	}

	public Integer getOffset(){
		return offset;
	}

	public void setNotIn(String notIn){
		this.notIn = notIn;
	}

	public String getNotIn(){
		return notIn;
	}

	public void setSort(String sort){
		this.sort = sort;
	}

	public String getSort(){
		return sort;
	}

	public void setOrder(String order){
		this.order = order;
	}

	public String getOrder(){
		return order;
	}

	public void setWhere(String[] where){
		this.where = where;
	}

	public String[] getWhere(){
		return where;
	}

	public void setWhereTerm(String whereTerm){
		this.whereTerm = whereTerm;
	}

	public String getWhereTerm(){
		return whereTerm;
	}

}

