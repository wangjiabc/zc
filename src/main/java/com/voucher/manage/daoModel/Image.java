package com.voucher.manage.daoModel;

import java.io.Serializable;

import com.voucher.manage.daoSQL.annotations.*;

@DBTable(name="[ZC].[dbo].[Image]")
public class Image implements Serializable{

    private static final long serialVersionUID = 1L;

    @SQLInteger(name="id")
	private Integer id;

    @SQLString(name="GUID")
	private String GUID;

    @SQLString(name="clientInfo_GUID")
	private String clientInfo_GUID;

    @SQLString(name="path")
	private String path;

    @SQLString(name="origName")
	private String origName;

    @SQLString(name="name")
	private String name;

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

	public void setClientInfo_GUID(String clientInfo_GUID){
		this.clientInfo_GUID = clientInfo_GUID;
	}

	public String getClientInfo_GUID(){
		return clientInfo_GUID;
	}

	public void setPath(String path){
		this.path = path;
	}

	public String getPath(){
		return path;
	}

	public void setOrigName(String origName){
		this.origName = origName;
	}

	public String getOrigName(){
		return origName;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
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

