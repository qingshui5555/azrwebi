package com.movit.rwe.common.config;

public class GlobalConstant {
    public static String STR_ETL_TYPE_DEMOGRAPHIC = "Demographics";
    public static String STR_ETL_TYPE_VITAL_SIGN = "Vital Sign";
    public static String STR_ETL_TYPE_LAB_TEST = "Lab Tests";
    public static String STR_ETL_TYPE_TREATMENT = "Treatment";
    public static String STR_ETL_TYPE_COMORBIDITY = "Comorbidity";
    public static String STR_ETL_TYPE_PAYER_COST = "Payer Cost";
    public static String STR_ETL_TYPE_SURVIVAL_CURVE = "Survival Curve";
    public static String STR_ETL_TYPE_BIOMARKER_TEST = "Biomarker Test";

    public static String STR_INDICATOR_GENDER = "Gender";
    public static String STR_INDICATOR_AGE_GROUP = "Age Group";
    public static String STR_INDICATOR_BMI = "BMI";
    public static String STR_INDICATOR_SBP = "SBP";
    public static String STR_INDICATOR_DBP = "DBP";
    public static String STR_INDICATOR_WAIST = "Waist";
    public static String STR_INDICATOR_CANCER_TYPE = "Cancer Type";
    public static String STR_INDICATOR_CSTAGE = "cStage";
    public static String STR_INDICATOR_PSTAGE = "pStage";

    public static String STR_INDICATOR_LOCATION_COUNTRY = "Location Country";

    public static String STR_ATTR_DEMOGRAPHIC_CONFIG_LIST = "demographicConfigList";
    public static String STR_ATTR_LAB_TEST_CONFIG_LIST = "labTestConfigList";
    public static String STR_ATTR_WORLD_MAP_CONFIG_LIST = "worldMapConfigList";

    public static String STR_ATTR_BIOMARKER_TEST_CONFIG_JSON = "biomarkerTestConfigJson";
    public static String STR_ATTR_BIOMARKER_BOX_CONFIG_JSON = "biomarkerBoxConfigJson";
    public static String STR_ATTR_LAB_TEST_TABLE_LIST = "labTestTableList";
    public static String STR_ATTR_ETL_RESULT_CONFIG_DATA_LIST = "etlResultConfigDataList";
    public static String STR_ATTR_PATIENT_CODE_LIST = "patientCodeList";
    public static String STR_ATTR_DEMOGRAPHIC_DATA_MAP = "demographicDataMap";
    public static String STR_ATTR_DEMOGRAPHIC_DATA_SIZE_MAP = "demographicDataSizeMap";
    public static String STR_ATTR_DEMOGRAPHIC_DATA_UNIT_MAP = "demographicDataUnitMap";
    public static String STR_ATTR_DEMOGRAPHIC_INDICATOR_MAP = "demographicIndicatorMap";
    public static String STR_ATTR_LAB_TEST_INDICATOR_MAP = "labTestIndicatorMap";
    public static String STR_ATTR_WORLD_MAP_INDICATOR_MAP = "worldMapIndicatorMap";
    public static String STR_ATTR_BIOMARKER_TEST_CONFIG_MAP = "biomarkerTestConfigMap";

    public static String STR_ATTR_UNIT = "unit";
    public static String STR_ATTR_CONFIG_VO_LIST = "configVoList";
    public static String STR_ATTR_VIEW_CHART_HEIGHT = "viewChartHeight";
    public static String STR_ATTR_STYLE = "style";
    public static String STR_ATTR_SUCCESS = "success";
    public static String STR_ATTR_COMORBIDITY_DATA_LIST = "comorbidityDataList";
    public static String STR_ATTR_COMORBIDITY_SUB_DATA_LIST = "comorbiditySubDataList";
    public static String STR_ATTR_WORLD_MAP_DATA_LIST = "worldMapDataList";
    public static String STR_ATTR_SURVIVAL_CURVE_DATA_LIST = "survivalCurveDataList";
    public static String STR_ATTR_ID = "id";
    public static String STR_ATTR_ORDER = "order";
    public static String STR_ATTR_RESULT_UNIT = "resultUnit";
    public static String STR_ATTR_LOWER_REF = "lowerRef";
    public static String STR_ATTR_UPPER_REF = "upperRef";
    public static String STR_ATTR_INDICATOR = "indicator";
    public static String STR_ATTR_DESCRIPTION = "description";
    public static String STR_ATTR_LOWER_REF_DEFAULT = "0.0";
    public static String STR_ATTR_UPPER_REF_DEFAULT = "0.0";
    public static String STR_ATTR_SUM_COUNT = "sumCount";

    public static String STR_ATTR_COHORTS = "cohorts";
    public static String STR_ATTR_COHORT_IDS = "cohortIds";
    public static String STR_ATTR_GROUPS = "groups";
    public static String STR_ATTR_STUDY_ID = "studyId";
    public static String STR_ATTR_TAB_ID = "tabId";
    public static String STR_ATTR_VIEW_ID = "viewId";
    public static String STR_ATTR_PATIENT_COUNT = "patientCount";
    public static String STR_ATTR_CONFIG_JSON = "configJson";
    public static String STR_ATTR_ALL_PATIENTS_FLAG = "allPatientsFlag";
    public static String STR_ATTR_IS_PAD = "isPad";
    public static String STR_ATTR_TAB_VIEW_ID = "tabViewId";
    public static String STR_ATTR_NEED_CHOOSE_PATIENT = "needChoosePatient";

    public static String STR_ATTR_ALIAS = "alias";


    public static String STR_REDIRECT_PREFIX = "redirect";

    public static String STR_SPLIT = ",";
    public static String STR_CONNECTOR = "_";

    public static String STR_CACHE_NAME_STUDY = "studyCache";
    public static String STR_CACHE_NAME_COHORT = "cohortCache";
    public static String STR_CACHE_NAME_GLOBAL_CONFIG = "globalConfigCache";
    public static String STR_CACHE_NAME_GLOBAL_GROUP = "globalGroupCache";

    public static String LINE_COLOR = "#3E4345";
    public static String LINE_STYLE = "fixTheme";

    public static String STYLE_WHITE = "white";
    public static String STYLE_BLACK = "black";

    public static Integer LEVEL_ALL = 0;
    public static Integer LEVEL_ONE = 1;
    public static Integer COUNT_ALL = 0;

    public static String STR_CACHE_NAME_PATIENT = "patientCache";
    public static String STR_PATIENT_ATTR_REFERENCE_CODE = "Reference Code";

    public static String[] vitalStageGroup = new String[]{"cStage", "pStage"};

    public static String[] colorGroup = new String[] { "#2A3034", "#c9449d", "#4486c7", "#c98643", "#4f4f92", "black" };

    public static String STR_CACHE_NAME_TREATMENT = "treatmentJourneyCache";
    public static String STR_TREATMENT_ATTR_LINE = "Line Of Treatment";
    public static String STR_TREATMENT_ATTR_THERAPY_TYPE = "Therapy Type";
    public static String STR_TREATMENT_ATTR_PRODUCT = "Product";
    public static String STR_TREATMENT_ATTR_DAILY_DOSE = "Daily Dose";
    public static String STR_TREATMENT_ATTR_OTHER_PRODUCT = "Other Product";
    public static String STR_TREATMENT_ATTR_START_DATE = "Start Date";
    public static String STR_TREATMENT_ATTR_END_DATE = "End Date";
    public static String STR_TREATMENT_ATTR_ON_GOING = "On Going";

    public static String STR_DEFAULT_YES = "YES";
}
