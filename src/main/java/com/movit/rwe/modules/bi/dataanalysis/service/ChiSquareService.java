package com.movit.rwe.modules.bi.dataanalysis.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.rosuda.REngine.REXP;
import org.rosuda.REngine.REXPList;
import org.rosuda.REngine.RList;
import org.rosuda.REngine.Rserve.RConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.movit.rwe.common.utils.I18NUtils;
import com.movit.rwe.modules.bi.da.test.rresult.DaException;
import com.movit.rwe.modules.bi.dataanalysis.util.RserveUtil;

@Service
public class ChiSquareService {
	
	private static Logger logger = LoggerFactory.getLogger(ChiSquareService.class);
	@Autowired
	private DataAnalysisService dataAnalysisService;
	@Autowired
	private I18NUtils i18NUtils;
	/**
	 * chisq.test函数执行操作
	 * @param conn
	 * @param modelColumnNameArray
	 * @param dataList
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public Map chisqTest(RConnection conn,String [] modelColumnNameArray, 
			List<String[]>dataList, Map <String,String>paramMap) throws Exception{
		/*
		 * 执行table命令
		 * t <- table(CHD,Smoke,deparse.level=2)
		 */
		StringBuffer tableCommond = new StringBuffer();
		tableCommond.append("chisq_table_result <- table(");
		for(int i=0;i<dataList.size();i++){
			String[] array = dataList.get(i);
			conn.assign(modelColumnNameArray[i], array);
			tableCommond.append(modelColumnNameArray[i]).append(",");
		}
		tableCommond.append("deparse.level=2)");
		REXP tableRexp = RserveUtil.parseAndEval(conn, tableCommond.toString());
		REXPList tableRexpList = tableRexp._attr();
		RList dimnamesRList = tableRexpList.asList();
		REXP dimnamesRexp = dimnamesRList.at("dimnames");
		RList dimnamesRListList = dimnamesRexp.asList();
		String []rowNameArray = dimnamesRListList.at(0).asStrings();
		String []colNameArray = dimnamesRListList.at(1).asStrings();
		double [][] tableArray = tableRexp.asDoubleMatrix();
		//检验table中的值是否都大于5
		boolean flag = true;
//		outer:
//		for(int i=0;i<tableArray.length;i++){
//			for(int j=0;j<tableArray[i].length;j++){
//				if(tableArray[i][j]<=5){
//					flag = false;
//					break outer;
//				}
//			}
//		}
		if(!flag){
			throw new DaException(i18NUtils.get("da.exception.usefishertocalculate", tableCommond.toString()),"",null);
		}
		/*
		 * 执行chisq.test命令
		 * result <- fisher.test(t)
		 */
		String chisqTestCommond = "chisq_result <- chisq.test(chisq_table_result)";
		REXP chisqTestRexp = RserveUtil.parseAndEval(conn, chisqTestCommond);
		
		/*
		 * 提取需要的结果值
		 */
		RList rList = chisqTestRexp.asList();
		double pValue = rList.at("p.value").asDouble();
		double xsquared = rList.at("statistic").asDouble();
		int df = rList.at("parameter").asInteger();

		/*
		 * 返回结果
		 */
		Map result = new HashMap();
		result.put("tableArray", tableArray);
		result.put("pValue", pValue);
		result.put("xsquared", xsquared);
		result.put("df", df);
		result.put("rowNameArray", rowNameArray);
		result.put("colNameArray", colNameArray);
		
		return result;
	}
	
}
