package com.movit.rwe.modules.bi.base.entity.vo;

import com.movit.rwe.modules.bi.base.entity.hive.HiveEtlResultNew;
import com.movit.rwe.modules.bi.base.entity.hive.Treatment;

import java.util.List;
import java.util.Set;

public class HiveResultVo extends HiveEtlResultNew {

    private String configId;

    private String upperRef;

    private String lowerRef;

    private Set<String> unitSet;

    private String referenceCode;

    private List<Treatment> treatmentJourneyList;

    private Integer order;

    private Integer patientCount;

    public String getConfigId() {
        return configId;
    }

    public void setConfigId(String configId) {
        this.configId = configId;
    }

    public String getUpperRef() {
        return upperRef;
    }

    public void setUpperRef(String upperRef) {
        this.upperRef = upperRef;
    }

    public String getLowerRef() {
        return lowerRef;
    }

    public void setLowerRef(String lowerRef) {
        this.lowerRef = lowerRef;
    }

    public Set<String> getUnitSet() {
        return unitSet;
    }

    public void setUnitSet(Set<String> unitSet) {
        this.unitSet = unitSet;
    }

    public String getReferenceCode() {
        return referenceCode;
    }

    public void setReferenceCode(String referenceCode) {
        this.referenceCode = referenceCode;
    }

    public List<Treatment> getTreatmentJourneyList() {
        return treatmentJourneyList;
    }

    public void setTreatmentJourneyList(List<Treatment> treatmentJourneyList) {
        this.treatmentJourneyList = treatmentJourneyList;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public Integer getPatientCount() {
        return patientCount;
    }

    public void setPatientCount(Integer patientCount) {
        this.patientCount = patientCount;
    }
}
