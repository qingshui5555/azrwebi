package com.movit.rwe.modules.bi.base.entity.vo;

import java.io.Serializable;
import java.math.BigDecimal;

public class HiveLabTestCountVo implements Serializable {

    private static final long serialVersionUID = -1L;

    private String type;

    private String indicator;

    private String description;

    private String unit;

    private Integer pCount;

    private Integer vCount;

    private Integer nCount;

    private BigDecimal rSum;

    private BigDecimal rMax;

    private BigDecimal rMin;

    private Integer vMin;

    private Integer vMax;

    private BigDecimal popt;

    private BigDecimal ponr;

    private String ac;

    private String ar;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIndicator() {
        return indicator;
    }

    public void setIndicator(String indicator) {
        this.indicator = indicator;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Integer getpCount() {
        return pCount;
    }

    public void setpCount(Integer pCount) {
        this.pCount = pCount;
    }

    public Integer getvCount() {
        return vCount;
    }

    public void setvCount(Integer vCount) {
        this.vCount = vCount;
    }

    public Integer getnCount() {
        return nCount;
    }

    public void setnCount(Integer nCount) {
        this.nCount = nCount;
    }

    public BigDecimal getrSum() {
        return rSum;
    }

    public void setrSum(BigDecimal rSum) {
        this.rSum = rSum;
    }

    public BigDecimal getrMax() {
        return rMax;
    }

    public void setrMax(BigDecimal rMax) {
        this.rMax = rMax;
    }

    public BigDecimal getrMin() {
        return rMin;
    }

    public void setrMin(BigDecimal rMin) {
        this.rMin = rMin;
    }

    public Integer getvMin() {
        return vMin;
    }

    public void setvMin(Integer vMin) {
        this.vMin = vMin;
    }

    public Integer getvMax() {
        return vMax;
    }

    public void setvMax(Integer vMax) {
        this.vMax = vMax;
    }

    public BigDecimal getPopt() {
        return popt;
    }

    public void setPopt(BigDecimal popt) {
        this.popt = popt;
    }

    public BigDecimal getPonr() {
        return ponr;
    }

    public void setPonr(BigDecimal ponr) {
        this.ponr = ponr;
    }

    public String getAc() {
        return ac;
    }

    public void setAc(String ac) {
        this.ac = ac;
    }

    public String getAr() {
        return ar;
    }

    public void setAr(String ar) {
        this.ar = ar;
    }
}
