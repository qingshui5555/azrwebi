package com.movit.rwe.modules.bi.base.entity.hive;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by Mike on 2016/4/25.
 */
public class DemographicBar implements Serializable {

    private static final long serialVersionUID = -1L;

    private static final String GENDER_MALE_KEY = "男";
    private static final String GENDER_FEMALE_KEY = "女";
    private static final String GENDER_OTHER_KEY = "其他";

    private static final String AGEGROUP_1_key = "1-17";
    private static final String AGEGROUP_2_key = "18-34";
    private static final String AGEGROUP_3_key = "35-44";
    private static final String AGEGROUP_4_key = "45-54";
    private static final String AGEGROUP_5_key = "55-64";
    private static final String AGEGROUP_6_key = "65-74";
    private static final String AGEGROUP_7_key = "75-84";
    private static final String AGEGROUP_8_key = "85+";
    private static final String AGEGROUP_9_key = "其他";

    private String name;

    private Integer val;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getVal() {
        return val;
    }

    public void setValue(Integer val) {
        this.val = val;
    }
}
