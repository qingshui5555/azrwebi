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

import com.movit.rwe.modules.bi.dataanalysis.util.RserveUtil;

@Service
public class FisherService {
	
	private static Logger logger = LoggerFactory.getLogger(FisherService.class);
	@Autowired
	private DataAnalysisService dataAnalysisService;
	
	/**
	 * fisher.test函数执行操作
	 * @param conn
	 * @param modelColumnNameArray
	 * @param dataList
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public Map fisherTest(RConnection conn,String [] modelColumnNameArray, 
			List<String[]>dataList, Map <String,String>paramMap) throws Exception{
		/*
		 * 执行table命令
		 * t <- table(CHD,Smoke,deparse.level=2)
		 */
		StringBuffer tableCommond = new StringBuffer();
		tableCommond.append("fisher_table_result <- table(");
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
		
		/*
		 * 执行chisq.test命令
		 * result <- fisher.test(t)
		 */
		String fisherCommond = "fisher_result <- fisher.test(fisher_table_result)";
		REXP fisherRexp = RserveUtil.parseAndEval(conn, fisherCommond);
		
		/*
		 * 提取需要的结果值
		 */
		RList rList = fisherRexp.asList();
		double pValue = rList.at("p.value").asDouble();
		//不用获取
		//double [] confInt = rList.at("conf.int").asDoubles();
		//double estimate = rList.at("estimate").asDouble();

		/*
		 * 返回结果
		 */
		Map result = new HashMap();
		result.put("tableArray", tableArray);
		result.put("pValue", pValue);
		//result.put("confInt", confInt);
		//result.put("estimate", estimate);
		result.put("rowNameArray", rowNameArray);
		result.put("colNameArray", colNameArray);
		
		return result;
	}
	
}
