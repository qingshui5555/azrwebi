package com.movit.rwe.modules.bi.base.entity.hive;

import java.util.Date;

/**
 * 
 * @Project : az-rwe-bi-web
 * @Title : Patient.java
 * @Package com.movit.rwe.common.entity
 * @Description : 患者
 * @author Yuri.Zheng
 * @email Yuri.Zheng@movit-tech.com
 * @date 2016年3月1日 上午10:57:20
 *
 */
public class Patient {

	/**
	 * 主键
	 */
	private String guid;

	/**
	 * 病人编码
	 */
	private String eCode;

	/**
	 * 研究
	 */
	private String studyId;

	/**
	 * 患者组主键
	 */
	private String groupGuid;

	private String center;

	private String gender;

	private String race;

	/**
	 * 生日
	 */
	private Date birthday;

	/**
	 * 身高
	 */
	private Double height;

	/**
	 * 体重
	 */
	private Double weight;

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String geteCode() {
		return eCode;
	}

	public void seteCode(String eCode) {
		this.eCode = eCode;
	}

	public String getStudyId() {
		return studyId;
	}

	public void setStudyId(String studyId) {
		this.studyId = studyId;
	}

	public String getGroupGuid() {
		return groupGuid;
	}

	public void setGroupGuid(String groupGuid) {
		this.groupGuid = groupGuid;
	}

	public String getCenter() {
		return center;
	}

	public void setCenter(String center) {
		this.center = center;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getRace() {
		return race;
	}

	public void setRace(String race) {
		this.race = race;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Double getHeight() {
		return height;
	}

	public void setHeight(Double height) {
		this.height = height;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

}
