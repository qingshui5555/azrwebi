package com.movit.rwe.modules.bi.base.entity.enums;

public enum EtlEnum {
	etl_treatment_config("etl_treatment_config"),
	etl_biomarker_test_config("etl_biomarker_test_config"),
	etl_diagnose_config("etl_diagnose_config"),
	etl_lab_test_config("etl_lab_test_config"), 
	etl_med_dosing_schedule_config("etl_med_dosing_schedule_config"),
	etl_side_effection_config("etl_side_effection_config"),
	etl_surgery_config("etl_surgery_config"),
	etl_treatment_journey_config("etl_treatment_journey_config"),
	etl_payer_cost_config("etl_payer_cost_config")
	;
	
	private String tableName;

	private EtlEnum(String tableName) {
		this.tableName = tableName;
	}

	public String getTableName() {
		return tableName;
	}

	public String getName() {
		return name();
	}
}
