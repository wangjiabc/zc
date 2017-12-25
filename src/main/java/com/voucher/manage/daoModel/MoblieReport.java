package com.voucher.manage.daoModel;

import java.util.Date;

import java.io.Serializable;

import com.voucher.manage.daoSQL.annotations.*;

@DBTable(name="[ZC].[dbo].[MoblieReport]")
public class MoblieReport implements Serializable{

    private static final long serialVersionUID = 1L;

    @SQLInteger(name="id")
	private Integer id;

    @SQLString(name="GUID")
	private String GUID;

    @SQLString(name="campusAdmin")
	private String campusAdmin;

    @SQLString(name="userName")
	private String userName;

    @SQLString(name="userPhoneNum")
	private String userPhoneNum;

    @SQLString(name="password")
	private String password;

    @SQLString(name="useridCard")
	private String useridCard;

    @SQLString(name="filepath")
	private String filepath;

    @SQLDateTime(name="datetime")
	private Date datetime;

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

	public void setCampusAdmin(String campusAdmin){
		this.campusAdmin = campusAdmin;
	}

	public String getCampusAdmin(){
		return campusAdmin;
	}

	public void setUserName(String userName){
		this.userName = userName;
	}

	public String getUserName(){
		return userName;
	}

	public void setUserPhoneNum(String userPhoneNum){
		this.userPhoneNum = userPhoneNum;
	}

	public String getUserPhoneNum(){
		return userPhoneNum;
	}

	public void setPassword(String password){
		this.password = password;
	}

	public String getPassword(){
		return password;
	}

	public void setUseridCard(String useridCard){
		this.useridCard = useridCard;
	}

	public String getUseridCard(){
		return useridCard;
	}

	public void setFilepath(String filepath){
		this.filepath = filepath;
	}

	public String getFilepath(){
		return filepath;
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

