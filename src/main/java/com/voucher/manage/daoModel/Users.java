package com.voucher.manage.daoModel;

import java.util.Date;

import java.io.Serializable;
import java.text.SimpleDateFormat;

import com.voucher.manage.daoSQL.annotations.*;

import javafx.beans.property.SimpleStringProperty;

@DBTable(name="[ZC].[dbo].[Users]")
public class Users implements Serializable{

    private static final long serialVersionUID = 1L;

    @SQLInteger(name="id")
	private Integer id;

    @SQLString(name="campusAdmin")
	private String campusAdmin;

    @SQLString(name="password")
	private String password;

    @SQLInteger(name="type")
	private Integer type;

    @SQLInteger(name="state")
	private Integer state;
    
    @SQLInteger(name="cityId")
	private Integer cityId;

    @SQLInteger(name="telePhone")
	private Integer telePhone;

    @SQLString(name="address")
	private String address;

    @SQLDateTime(name="lastLoginDate")
	private Date lastLoginDate;
    
    @SQLDateTime(name="registerTime")
	private Date registerTime;

    @SQLInteger(name="campusId")
	private Integer campusId;

	public void setId(Integer id){
		this.id = id;
	}

	public Integer getId(){
		return id;
	}

	public void setCampusAdmin(String campusAdmin){
		this.campusAdmin = campusAdmin;
	}

	public String getCampusAdmin(){
		return campusAdmin;
	}

	public void setPassword(String password){
		this.password = password;
	}

	public String getPassword(){
		return password;
	}

	
	public void setState(Integer state){
		this.state = state;
	}

	public Integer getState(){
		return state;
	}
	
	public void setType(Integer type){
		this.type = type;
	}

	public Integer getType(){
		return type;
	}

	public void setCityId(Integer cityId){
		this.cityId = cityId;
	}

	public Integer getCityId(){
		return cityId;
	}

	public void setTelePhone(Integer telePhone){
		this.telePhone = telePhone;
	}

	public Integer getTelePhone(){
		return telePhone;
	}

	public void setAddress(String address){
		this.address = address;
	}

	public String getAddress(){
		return address;
	}

	public void setLastLoginDate(Date lastLoginDate){
		this.lastLoginDate = lastLoginDate;
	}

	public Date getLastLoginDate(){
		return lastLoginDate;
	}

	public void setRegisterTime(Date registerTime){
		this.registerTime = registerTime;
	}

	public Date getRegisterTime(){
		return registerTime;
	}

	public void setCampusId(Integer campusId){
		this.campusId = campusId;
	}

	public Integer getCampusId(){
		return campusId;
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

