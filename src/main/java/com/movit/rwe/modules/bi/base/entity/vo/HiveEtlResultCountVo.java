package com.movit.rwe.modules.bi.base.entity.vo;

import java.util.Date;

public class HiveEtlResultCountVo {

    private String patientId;

    private Date visitOn;

    private String keyValue;

    private String keyUnit;

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public Date getVisitOn() {
        return visitOn;
    }

    public void setVisitOn(Date visitOn) {
        this.visitOn = visitOn;
    }

    public String getKeyValue() {
        return keyValue;
    }

    public void setKeyValue(String keyValue) {
        this.keyValue = keyValue;
    }

    public String getKeyUnit() {
        return keyUnit;
    }

    public void setKeyUnit(String keyUnit) {
        this.keyUnit = keyUnit;
    }
}
