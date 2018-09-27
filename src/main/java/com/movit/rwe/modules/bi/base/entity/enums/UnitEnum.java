package com.movit.rwe.modules.bi.base.entity.enums;

import com.movit.rwe.common.utils.StringUtils;

public enum UnitEnum {
    AR_CR_S("ACR", "mg/g", "mg/mmol", 0.113),
    AR_CR_T("ACR", "mg/mmol", "mg/g", 1/0.113),
    HDL_S("HDL", "mg/dL", "mmol/L", 0.02586),
    HDL_T("HDL", "mmol/L", "mg/dL", 1/0.02586),
    LDL_S("LDL", "mg/dL", "mmol/L", 0.02586),
    LDL_T("LDL", "mmol/L", "mg/dL", 1/0.02586),
    Total_S("TC", "mg/dL", "mmol/L", 0.02586),
    Total_T("TC", "mmol/L", "mg/dL", 1/0.02586),
    Triglycerides_S("TG", "mg/dL", "mmol/L", 0.01129),
    Triglycerides_T("TG", "mmol/L", "mg/dL", 1/0.01129),
    FPG_S("FPG", "mg/dL", "mmol/L", 0.05556),
    FPG_T("FPG", "mmol/L", "mg/dL", 1/0.05556),
    BUN_S("BUN", "mg/dL", "mmol/L", 0.3571),
    BUN_T("BUN", "mmol/L", "mg/dL", 1/0.3571),
    Potassium_S("K", "mg/dL", "mmol/L or mEq/L", 0.2557),
    Potassium_T("K", "mmol/L or mEq/L", "mg/dL", 1/0.2557),
    Sodium_S("Na", "mg/dL", "mmol/L or mEq/L", 0.435),
    Sodium_T("Na", "mmol/L or mEq/L", "mg/dL", 1/0.435),
    Calcium_S("Ca", "mg/dL", "mmol/L or mEq/L", 0.25),
    Calcium_T("Ca", "mmol/L or mEq/L", "mg/dL", 1/0.25),
    Chloride_S("Ci", "mg/dL", "mmol/L or mEq/L", 0.282),
    Chloride_T("Ci", "mmol/L or mEq/L", "mg/dL", 1/0.282),
    Magnesium_S("Mg", "mg/dL", "mmol/L or mEq/L", 0.4114),
    Magnesium_T("Mg", "mmol/L or mEq/L", "mg/dL", 1/0.4114),
    SerumPhosphorus_S("P", "mg/dL", "mmol/L or mEq/L", 0.32285),
    SerumPhosphorus_T("P", "mmol/L or mEq/L", "mg/dL", 1/0.32285),
    SerumCR_S("SCR", "mg/dL", "µmol/L", 88.4),
    SerumCR_T("SCR", "µmol/L", "mg/dL", 1/88.4),
    UricAcid_S("UA", "mg/dL", "µmol/L", 59.48),
    UricAcid_T("UA", "µmol/L", "mg/dL", 1/59.48),
    Albuminuria_S("ALB", "mg/L", "µmol/L", 0.015),
    Albuminuria_T("ALB", "µmol/L", "mg/L", 1/0.015),
    AlanineTransaminase_S("ALT", "IU/L", "µmol/L*s", 0.0167),
    AlanineTransaminase_T("ALT", "µmol/L*s", "IU/L", 1/0.0167),
    AspartateTransaminase_S("AST", "IU/L", "µmol/L*s", 0.0167),
    AspartateTransaminase_T("AST", "µmol/L*s", "IU/L", 1/0.0167),
    GGT_S("GGT", "IU/L", "µmol/L*s", 0.0167),
    GGT_T("GGT", "µmol/L*s", "IU/L", 1/0.0167),
    SerumAlbumin_S("S-ALB", "g/dL", "g/L", 10.0),
    SerumAlbumin_T("S-ALB", "g/L", "g/dL", 1/10.0),
    ;

    String indicator;
    String sourceUnit;
    String targetUnit;
    Double factor;

    UnitEnum(String indicator, String sourceUnit, String targetUnit, Double factor){
        this.indicator = indicator;
        this.sourceUnit = sourceUnit;
        this.targetUnit = targetUnit;
        this.factor = factor;
    }

    public static UnitEnum load(String indicator, String sourceUnit){
        if(StringUtils.isBlank(indicator)){
            return null;
        }
        if(StringUtils.isBlank(sourceUnit)){
            return null;
        }
        for(UnitEnum unitEnum : UnitEnum.values()){
            if(indicator.equals(unitEnum.getIndicator()) && sourceUnit.equals(unitEnum.getSourceUnit())){
                return unitEnum;
            }
        }

        return null;
    }

    public String getIndicator() {
        return indicator;
    }

    public void setIndicator(String indicator) {
        this.indicator = indicator;
    }

    public String getSourceUnit() {
        return sourceUnit;
    }

    public void setSourceUnit(String sourceUnit) {
        this.sourceUnit = sourceUnit;
    }

    public String getTargetUnit() {
        return targetUnit;
    }

    public void setTargetUnit(String targetUnit) {
        this.targetUnit = targetUnit;
    }

    public Double getFactor() {
        return factor;
    }

    public void setFactor(Double factor) {
        this.factor = factor;
    }
}
