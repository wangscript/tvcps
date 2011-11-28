
package com.house.biz.entity;


import com.house.core.entity.GenericEntity;

/**
 * <p>标题: —— 管理员用户实体类</p>
 * <p>描述: —— 简要描述类的职责、实现方式、使用注意事项等</p>
 * <p>模块: 用户管理</p>
 * <p>版权: Copyright (c) 2011 娄伟峰
 * @author 娄伟峰
 * @version 1.0
 * @since 2011-11-8 上午10:41:09
 * @history（历次修订内容、修订人、修订时间等） 
 */

@SuppressWarnings("serial")
public class DemandApplyEntity extends GenericEntity implements java.io.Serializable{
	
	/**
	 * 钟点工需求Id。
	 */
	private String demandApplyId;
	
	/**
	 * 小区代码表。
	 */
	private VillageEntity villageEntity;
	
	/**
	 * 
	 */
	private String evolvecaseId;
	
	/**
	 * 联系人。
	 */
	private String linkman;
	
	/**
	 * 用户名。
	 */
	private String loginName;
	
	private AdminUserEntity adminUserEntity;
	
	/**
	 * 密码。
	 */
	private String passWord;
	
	/**
	 * 电话。
	 */
	private String tel;
	
	/**
	 * 频次。
	 */
	private String rate;
	
	/**
	 * 时长。
	 */
	private String hourLength;
	
	/**
	 * 居家面积。
	 */
	private String houseArea;
	
	/**
	 * 进展情况。
	 */
	private String evolveStatus;
	
	/**
	 * 主要需求说明。
	 */
	private String demandExplain;

	public String getDemandApplyId() {
		return demandApplyId;
	}

	public void setDemandApplyId(String demandApplyId) {
		this.demandApplyId = demandApplyId;
	}

	public VillageEntity getVillageEntity() {
		return villageEntity;
	}

	public void setVillageEntity(VillageEntity villageEntity) {
		this.villageEntity = villageEntity;
	}

	public String getEvolvecaseId() {
		return evolvecaseId;
	}

	public void setEvolvecaseId(String evolvecaseId) {
		this.evolvecaseId = evolvecaseId;
	}

	public String getLinkman() {
		return linkman;
	}

	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public AdminUserEntity getAdminUserEntity() {
		return adminUserEntity;
	}

	public void setAdminUserEntity(AdminUserEntity adminUserEntity) {
		this.adminUserEntity = adminUserEntity;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	public String getHourLength() {
		return hourLength;
	}

	public void setHourLength(String hourLength) {
		this.hourLength = hourLength;
	}

	public String getHouseArea() {
		return houseArea;
	}

	public void setHouseArea(String houseArea) {
		this.houseArea = houseArea;
	}

	public String getEvolveStatus() {
		return evolveStatus;
	}

	public void setEvolveStatus(String evolveStatus) {
		this.evolveStatus = evolveStatus;
	}

	public String getDemandExplain() {
		return demandExplain;
	}

	public void setDemandExplain(String demandExplain) {
		this.demandExplain = demandExplain;
	}
}
