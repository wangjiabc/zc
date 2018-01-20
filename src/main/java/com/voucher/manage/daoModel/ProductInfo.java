package com.voucher.manage.daoModel;

import java.util.Date;

import java.io.Serializable;

import com.voucher.manage.daoSQL.annotations.*;

@DBTable(name="[ZC].[dbo].[ProductInfo]")
public class ProductInfo implements Serializable{

    private static final long serialVersionUID = 1L;

    @SQLInteger(name="id")
	private Integer id;

    @SQLString(name="pro_GUID")
	private String pro_GUID;

    @SQLString(name="proName")
	private String proName;

    @SQLDouble(name="money")
	private Double money;

    @SQLInteger(name="nper")
	private Integer nper;

    @SQLInteger(name="cycle")
	private Integer cycle;

    @SQLDouble(name="stage")
	private Double stage;

    @SQLDouble(name="interest")
	private Double interest;

    @SQLDouble(name="should_repay")
	private Double should_repay;

    @SQLDateTime(name="datetime")
	private Date datetime;

	public void setId(Integer id){
		this.id = id;
	}

	public Integer getId(){
		return id;
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

	public void setInterest(Double interest){
		this.interest = interest;
	}

	public Double getInterest(){
		return interest;
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

