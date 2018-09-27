package com.movit.rwe.modules.bi.da.enums;

import org.apache.commons.lang.StringUtils;

/**
 * 
  * @ClassName: DaTestEnum
  * @Description: DataAnalysis test enum
  * @author wade.chen@movit-tech.com
  * @date 2016年5月20日 上午11:12:36
  *
 */
public enum DaTestEnum {
	TTest("T-Test","da_test_ttest",TestTypeEnum.NOMAL,0),
	WilcoxTest("Wilcox","da_test_wilcoxtest",TestTypeEnum.ABNOMAL,1),
	ANOVA("ANOVA","da_test_anova",TestTypeEnum.NOMAL,2),
	ChiSquare("Chi-Square","da_test_chisquare",TestTypeEnum.ABNOMAL,3),
	Fisher("Fisher","da_test_fisher",TestTypeEnum.ABNOMAL,4),
	LineRegression("LinearRegression","da_test_lineregression",TestTypeEnum.NOMAL,5),
	LogisticRegression("LogisticRegression","da_test_logisticregression",TestTypeEnum.ABNOMAL,6),
	Survival("Survival","da_test_survival",TestTypeEnum.ABNOMAL,7),
//	AssociationRule("Association Rule","da_test_associationrule",8),
	KMeans("K-Means","da_test_kmeans",TestTypeEnum.ABNOMAL,9),
	HCluster("H-Cluster","da_test_hcluster",TestTypeEnum.ABNOMAL,10),
//	DecisionTree("Decision Tree","da_test_decisiontree",11)，
	CorTest("CorTest","da_test_cortest",TestTypeEnum.ABNOMAL,12),
	Ksvm("Ksvm","da_test_ksvm",TestTypeEnum.ABNOMAL,13),
	;
	
	String name;
	
	String beanName;
	
	TestTypeEnum type;
	
	int sort;
	
	private DaTestEnum(String name, String beanName,TestTypeEnum type, int sort) {
		this.name = name;
		this.beanName = beanName;
		this.type = type;
		this.sort = sort;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBeanName() {
		return beanName;
	}

	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}
	
	public TestTypeEnum getType() {
		return type;
	}

	public void setType(TestTypeEnum type) {
		this.type = type;
	}

	public static DaTestEnum loadByName(String name){
		if(StringUtils.isEmpty(name)){
			return null;
		}
		
		for(DaTestEnum dte : DaTestEnum.values()){
			if(dte.getName().equals(name)){
				return dte;
			}
		}
		return null;
	}

}
