package com.voucher.manage.daoModel;

import java.io.Serializable;
import java.util.Date;

import com.voucher.manage.daoSQL.annotations.*;

@DBTable(name="[ZC].[dbo].[clientInfo]")
public class ClientInfo implements Serializable{

    private static final long serialVersionUID = 1L;

    @SQLInteger(name="id")
	private Integer id;

    @SQLString(name="GUID")
	private String GUID;

    @SQLString(name="campusAdmin")
	private String campusAdmin;

    @SQLString(name="borrowUse")
	private String borrowUse;

    @SQLInteger(name="applyMoney")
	private Integer applyMoney;

    @SQLInteger(name="weekRepayment")
	private Integer weekRepayment;

    @SQLString(name="applyTime")
	private String applyTime;

    @SQLString(name="colleagueName")
	private String colleagueName;

    @SQLString(name="colleagueRelation")
	private String colleagueRelation;

    @SQLString(name="remark")
	private String remark;
    
    @SQLString(name="colleagueUnitName")
	private String colleagueUnitName;

    @SQLString(name="colleaguePhoneNum")
	private String colleaguePhoneNum;

    @SQLString(name="colleagueAddress")
	private String colleagueAddress;

    @SQLString(name="emergencyName")
	private String emergencyName;

    @SQLString(name="emergencyRelation")
	private String emergencyRelation;

    @SQLString(name="emergencyUnitName")
	private String emergencyUnitName;

    @SQLString(name="emergencyPhoneNum")
	private String emergencyPhoneNum;

    @SQLString(name="emergencyAddress")
	private String emergencyAddress;

    @SQLString(name="kinshipName")
	private String kinshipName;

    @SQLString(name="kinshipRelation")
	private String kinshipRelation;

    @SQLString(name="kinshipUnitName")
	private String kinshipUnitName;

    @SQLString(name="kinshipPhoneNum")
	private String kinshipPhoneNum;

    @SQLString(name="kinshipAddress")
	private String kinshipAddress;

    @SQLString(name="insCompany")
	private String insCompany;

    @SQLInteger(name="insNum")
	private Integer insNum;

    @SQLInteger(name="attatchNum")
	private Integer attatchNum;

    @SQLString(name="payOntime")
	private String payOntime;

    @SQLString(name="payMode")
	private String payMode;

    @SQLString(name="insMoney")
	private String insMoney;

    @SQLInteger(name="payTerm")
	private Integer payTerm;

    @SQLString(name="unitName")
	private String unitName;

    @SQLString(name="unitAddress")
	private String unitAddress;

    @SQLString(name="unitPhone")
	private String unitPhone;

    @SQLString(name="postcode")
	private String postcode;

    @SQLString(name="department")
	private String department;

    @SQLString(name="duty")
	private String duty;

    @SQLString(name="workTime")
	private String workTime;

    @SQLInteger(name="instime")
	private Integer instime;

    @SQLString(name="industry")
	private String industry;

    @SQLString(name="unitNature")
	private String unitNature;

    @SQLString(name="workNature")
	private String workNature;

    @SQLString(name="monthSalary")
	private String monthSalary;

    @SQLString(name="payDay")
	private String payDay;

    @SQLString(name="otherIncome")
	private String otherIncome;

    @SQLString(name="receptionType")
	private String receptionType;

    @SQLString(name="receptionPerson")
	private String receptionPerson;

    @SQLString(name="JobNum")
	private String JobNum;

    @SQLString(name="source")
	private String source;

    @SQLString(name="borrowType")
	private String borrowType;

    @SQLString(name="manager")
	private String manager;

    @SQLString(name="workPhoneNum")
	private String workPhoneNum;

    @SQLString(name="Number")
	private String Number;

    @SQLString(name="interviewPerson")
	private String interviewPerson;

    @SQLString(name="userName")
	private String userName;

    @SQLString(name="userOldname")
	private String userOldname;

    @SQLInteger(name="userAge")
	private Integer userAge;

    @SQLString(name="userSex")
	private String userSex;

    @SQLString(name="userEducation")
	private String userEducation;

    @SQLString(name="userMarriage")
	private String userMarriage;

    @SQLString(name="useridCard")
	private String useridCard;

    @SQLInteger(name="userProvideNum")
	private Integer userProvideNum;

    @SQLInteger(name="userChildrenNum")
	private Integer userChildrenNum;

    @SQLString(name="usercrAdress")
	private String usercrAdress;

    @SQLString(name="userHouseState")
	private String userHouseState;

    @SQLString(name="userNowAdress")
	private String userNowAdress;

    @SQLString(name="userPhoneNum")
	private String userPhoneNum;

    @SQLString(name="userTelNum")
	private String userTelNum;

    @SQLString(name="userWechat")
	private String userWechat;

    @SQLString(name="userqqNum")
	private String userqqNum;

    @SQLString(name="userSocialBase")
	private String userSocialBase;

    @SQLString(name="userCreditCard")
	private String userCreditCard;

    @SQLString(name="userPhoneMonad")
	private String userPhoneMonad;

    @SQLString(name="userBankOpen")
	private String userBankOpen;

    @SQLDateTime(name="updateTime")
	private Date updateTime;

    @SQLDateTime(name="insertTime")
	private Date insertTime;
    
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

	public void setBorrowUse(String borrowUse){
		this.borrowUse = borrowUse;
	}

	public String getBorrowUse(){
		return borrowUse;
	}

	public void setApplyMoney(Integer applyMoney){
		this.applyMoney = applyMoney;
	}

	public Integer getApplyMoney(){
		return applyMoney;
	}

	public void setWeekRepayment(Integer weekRepayment){
		this.weekRepayment = weekRepayment;
	}

	public Integer getWeekRepayment(){
		return weekRepayment;
	}

	public void setApplyTime(String applyTime){
		this.applyTime = applyTime;
	}

	public String getApplyTime(){
		return applyTime;
	}

	public void setColleagueName(String colleagueName){
		this.colleagueName = colleagueName;
	}

	public String getColleagueName(){
		return colleagueName;
	}

	public void setColleagueRelation(String colleagueRelation){
		this.colleagueRelation = colleagueRelation;
	}

	public String getColleagueRelation(){
		return colleagueRelation;
	}

	public void setRemark(String remark){
		this.remark = remark;
	}

	public String getRemark(){
		return remark;
	}
	
	public void setColleagueUnitName(String colleagueUnitName){
		this.colleagueUnitName = colleagueUnitName;
	}

	public String getColleagueUnitName(){
		return colleagueUnitName;
	}

	public void setColleaguePhoneNum(String colleaguePhoneNum){
		this.colleaguePhoneNum = colleaguePhoneNum;
	}

	public String getColleaguePhoneNum(){
		return colleaguePhoneNum;
	}

	public void setColleagueAddress(String colleagueAddress){
		this.colleagueAddress = colleagueAddress;
	}

	public String getColleagueAddress(){
		return colleagueAddress;
	}

	public void setEmergencyName(String emergencyName){
		this.emergencyName = emergencyName;
	}

	public String getEmergencyName(){
		return emergencyName;
	}

	public void setEmergencyRelation(String emergencyRelation){
		this.emergencyRelation = emergencyRelation;
	}

	public String getEmergencyRelation(){
		return emergencyRelation;
	}

	public void setEmergencyUnitName(String emergencyUnitName){
		this.emergencyUnitName = emergencyUnitName;
	}

	public String getEmergencyUnitName(){
		return emergencyUnitName;
	}

	public void setEmergencyPhoneNum(String emergencyPhoneNum){
		this.emergencyPhoneNum = emergencyPhoneNum;
	}

	public String getEmergencyPhoneNum(){
		return emergencyPhoneNum;
	}

	public void setEmergencyAddress(String emergencyAddress){
		this.emergencyAddress = emergencyAddress;
	}

	public String getEmergencyAddress(){
		return emergencyAddress;
	}

	public void setKinshipName(String kinshipName){
		this.kinshipName = kinshipName;
	}

	public String getKinshipName(){
		return kinshipName;
	}

	public void setKinshipRelation(String kinshipRelation){
		this.kinshipRelation = kinshipRelation;
	}

	public String getKinshipRelation(){
		return kinshipRelation;
	}

	public void setKinshipUnitName(String kinshipUnitName){
		this.kinshipUnitName = kinshipUnitName;
	}

	public String getKinshipUnitName(){
		return kinshipUnitName;
	}

	public void setKinshipPhoneNum(String kinshipPhoneNum){
		this.kinshipPhoneNum = kinshipPhoneNum;
	}

	public String getKinshipPhoneNum(){
		return kinshipPhoneNum;
	}

	public void setKinshipAddress(String kinshipAddress){
		this.kinshipAddress = kinshipAddress;
	}

	public String getKinshipAddress(){
		return kinshipAddress;
	}

	public void setInsCompany(String insCompany){
		this.insCompany = insCompany;
	}

	public String getInsCompany(){
		return insCompany;
	}

	public void setInsNum(Integer insNum){
		this.insNum = insNum;
	}

	public Integer getInsNum(){
		return insNum;
	}

	public void setAttatchNum(Integer attatchNum){
		this.attatchNum = attatchNum;
	}

	public Integer getAttatchNum(){
		return attatchNum;
	}

	public void setPayOntime(String payOntime){
		this.payOntime = payOntime;
	}

	public String getPayOntime(){
		return payOntime;
	}

	public void setPayMode(String payMode){
		this.payMode = payMode;
	}

	public String getPayMode(){
		return payMode;
	}

	public void setInsMoney(String insMoney){
		this.insMoney = insMoney;
	}

	public String getInsMoney(){
		return insMoney;
	}

	public void setPayTerm(Integer payTerm){
		this.payTerm = payTerm;
	}

	public Integer getPayTerm(){
		return payTerm;
	}

	public void setUnitName(String unitName){
		this.unitName = unitName;
	}

	public String getUnitName(){
		return unitName;
	}

	public void setUnitAddress(String unitAddress){
		this.unitAddress = unitAddress;
	}

	public String getUnitAddress(){
		return unitAddress;
	}

	public void setUnitPhone(String unitPhone){
		this.unitPhone = unitPhone;
	}

	public String getUnitPhone(){
		return unitPhone;
	}

	public void setPostcode(String postcode){
		this.postcode = postcode;
	}

	public String getPostcode(){
		return postcode;
	}

	public void setDepartment(String department){
		this.department = department;
	}

	public String getDepartment(){
		return department;
	}

	public void setDuty(String duty){
		this.duty = duty;
	}

	public String getDuty(){
		return duty;
	}

	public void setWorkTime(String workTime){
		this.workTime = workTime;
	}

	public String getWorkTime(){
		return workTime;
	}

	public void setInstime(Integer instime){
		this.instime = instime;
	}

	public Integer getInstime(){
		return instime;
	}

	public void setIndustry(String industry){
		this.industry = industry;
	}

	public String getIndustry(){
		return industry;
	}

	public void setUnitNature(String unitNature){
		this.unitNature = unitNature;
	}

	public String getUnitNature(){
		return unitNature;
	}

	public void setWorkNature(String workNature){
		this.workNature = workNature;
	}

	public String getWorkNature(){
		return workNature;
	}

	public void setMonthSalary(String monthSalary){
		this.monthSalary = monthSalary;
	}

	public String getMonthSalary(){
		return monthSalary;
	}

	public void setPayDay(String payDay){
		this.payDay = payDay;
	}

	public String getPayDay(){
		return payDay;
	}

	public void setOtherIncome(String otherIncome){
		this.otherIncome = otherIncome;
	}

	public String getOtherIncome(){
		return otherIncome;
	}

	public void setReceptionType(String receptionType){
		this.receptionType = receptionType;
	}

	public String getReceptionType(){
		return receptionType;
	}

	public void setReceptionPerson(String receptionPerson){
		this.receptionPerson = receptionPerson;
	}

	public String getReceptionPerson(){
		return receptionPerson;
	}

	public void setJobNum(String JobNum){
		this.JobNum = JobNum;
	}

	public String getJobNum(){
		return JobNum;
	}

	public void setSource(String source){
		this.source = source;
	}

	public String getSource(){
		return source;
	}

	public void setBorrowType(String borrowType){
		this.borrowType = borrowType;
	}

	public String getBorrowType(){
		return borrowType;
	}

	public void setManager(String manager){
		this.manager = manager;
	}

	public String getManager(){
		return manager;
	}

	public void setWorkPhoneNum(String workPhoneNum){
		this.workPhoneNum = workPhoneNum;
	}

	public String getWorkPhoneNum(){
		return workPhoneNum;
	}

	public void setNumber(String Number){
		this.Number = Number;
	}

	public String getNumber(){
		return Number;
	}

	public void setInterviewPerson(String interviewPerson){
		this.interviewPerson = interviewPerson;
	}

	public String getInterviewPerson(){
		return interviewPerson;
	}

	public void setUserName(String userName){
		this.userName = userName;
	}

	public String getUserName(){
		return userName;
	}

	public void setUserOldname(String userOldname){
		this.userOldname = userOldname;
	}

	public String getUserOldname(){
		return userOldname;
	}

	public void setUserAge(Integer userAge){
		this.userAge = userAge;
	}

	public Integer getUserAge(){
		return userAge;
	}

	public void setUserSex(String userSex){
		this.userSex = userSex;
	}

	public String getUserSex(){
		return userSex;
	}

	public void setUserEducation(String userEducation){
		this.userEducation = userEducation;
	}

	public String getUserEducation(){
		return userEducation;
	}

	public void setUserMarriage(String userMarriage){
		this.userMarriage = userMarriage;
	}

	public String getUserMarriage(){
		return userMarriage;
	}

	public void setUseridCard(String useridCard){
		this.useridCard = useridCard;
	}

	public String getUseridCard(){
		return useridCard;
	}

	public void setUserProvideNum(Integer userProvideNum){
		this.userProvideNum = userProvideNum;
	}

	public Integer getUserProvideNum(){
		return userProvideNum;
	}

	public void setUserChildrenNum(Integer userChildrenNum){
		this.userChildrenNum = userChildrenNum;
	}

	public Integer getUserChildrenNum(){
		return userChildrenNum;
	}

	public void setUsercrAdress(String usercrAdress){
		this.usercrAdress = usercrAdress;
	}

	public String getUsercrAdress(){
		return usercrAdress;
	}

	public void setUserHouseState(String userHouseState){
		this.userHouseState = userHouseState;
	}

	public String getUserHouseState(){
		return userHouseState;
	}

	public void setUserNowAdress(String userNowAdress){
		this.userNowAdress = userNowAdress;
	}

	public String getUserNowAdress(){
		return userNowAdress;
	}

	public void setUserPhoneNum(String userPhoneNum){
		this.userPhoneNum = userPhoneNum;
	}

	public String getUserPhoneNum(){
		return userPhoneNum;
	}

	public void setUserTelNum(String userTelNum){
		this.userTelNum = userTelNum;
	}

	public String getUserTelNum(){
		return userTelNum;
	}

	public void setUserWechat(String userWechat){
		this.userWechat = userWechat;
	}

	public String getUserWechat(){
		return userWechat;
	}

	public void setUserqqNum(String userqqNum){
		this.userqqNum = userqqNum;
	}

	public String getUserqqNum(){
		return userqqNum;
	}

	public void setUserSocialBase(String userSocialBase){
		this.userSocialBase = userSocialBase;
	}

	public String getUserSocialBase(){
		return userSocialBase;
	}

	public void setUserCreditCard(String userCreditCard){
		this.userCreditCard = userCreditCard;
	}

	public String getUserCreditCard(){
		return userCreditCard;
	}

	public void setUserPhoneMonad(String userPhoneMonad){
		this.userPhoneMonad = userPhoneMonad;
	}

	public String getUserPhoneMonad(){
		return userPhoneMonad;
	}

	public void setUserBankOpen(String userBankOpen){
		this.userBankOpen = userBankOpen;
	}

	public String getUserBankOpen(){
		return userBankOpen;
	}


	public void setUpdateTime(Date updateTime){
		this.updateTime = updateTime;
	}

	public Date getUpdateTime(){
		return updateTime;
	}

	public void setInsertTime(Date insertTime){
		this.insertTime = insertTime;
	}

	public Date getInsertTime(){
		return insertTime;
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

