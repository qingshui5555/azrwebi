package com.movit.rwe.modules.bi.da.test.rresult;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.rosuda.REngine.REXP;

import com.movit.rwe.modules.bi.dataanalysis.util.RserveUtil;

public class RResult {
	
	REXP rexp;
	
	Map<String, Object> resultMap;
	
	public REXP getRexp() {
		return rexp;
	}

	public void setRexp(REXP rexp) {
		this.rexp = rexp;
	}

	public Map<String, Object> getResultMap() {
		return resultMap;
	}

	public void setResultMap(Map<String, Object> resultMap) {
		this.resultMap = resultMap;
	}
	
	
	public String getShowPValue() {
		String pValueResult = "";
		if(resultMap!=null){
			//pValue假设小于 0.005 则统一显示"<0.01"
			String pValueStr = resultMap.get("pValue")==null?"":resultMap.get("pValue").toString();
			if(StringUtils.isNotBlank(pValueStr)){
				double pValue = Double.parseDouble(pValueStr);
				pValueResult = RserveUtil.keepTwoDecimal(pValue);
			}
		}
		return pValueResult;
	}
	
	public String[] getShowPValueArray() {
		String pValueResultArray[] = null;
		if(resultMap!=null){
			if(resultMap.get("pValue")!=null){
				Object [] objectArray = (Object[]) resultMap.get("pValue");
				pValueResultArray = new String[objectArray.length];
				for(int i=0;i<objectArray.length;i++){
					//pValue假设小于 0.005 则统一显示"<0.01"
					String pValueStr = objectArray[i]==null?"":objectArray[i].toString();
					if(StringUtils.isNotBlank(pValueStr)){
						double pValue = Double.parseDouble(pValueStr);
						pValueResultArray[i] = RserveUtil.keepTwoDecimal(pValue);
					}
				}
			}
			
		}
		return pValueResultArray;
	}

}
