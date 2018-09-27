package com.movit.rwe.modules.bi.base.entity.hive;

/**
 * 
 * @Project : az-rwe-bi-web
 * @Title : SideEffection.java
 * @Package com.movit.rwe.modules.treatment.entity
 * @Description : 副作用
 * @author Yuri.Zheng 
 * @email Yuri.Zheng@movit-tech.com
 * @date 2016年3月1日 下午2:23:06
 *
 */
public class SideEffection {

	private String guid;
	
	private String effect;
	
	private String parentEffectGuid;
	
	private String categoryPath;
	
	private String medDoseGuid;
	
	private String description;

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getEffect() {
		return effect;
	}

	public void setEffect(String effect) {
		this.effect = effect;
	}

	public String getParentEffectGuid() {
		return parentEffectGuid;
	}

	public void setParentEffectGuid(String parentEffectGuid) {
		this.parentEffectGuid = parentEffectGuid;
	}

	public String getCategoryPath() {
		return categoryPath;
	}

	public void setCategoryPath(String categoryPath) {
		this.categoryPath = categoryPath;
	}

	public String getMedDoseGuid() {
		return medDoseGuid;
	}

	public void setMedDoseGuid(String medDoseGuid) {
		this.medDoseGuid = medDoseGuid;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
