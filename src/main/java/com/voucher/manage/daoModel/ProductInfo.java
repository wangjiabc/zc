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

    @SQLInteger(name="repay_type")
	private Integer repay_type;

    @SQLDouble(name="interest")
	private Double interest;

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

	public void setRepay_type(Integer repay_type){
		this.repay_type = repay_type;
	}

	public Integer getRepay_type(){
		return repay_type;
	}

	public void setInterest(Double interest){
		this.interest = interest;
	}

	public Double getInterest(){
		return interest;
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

