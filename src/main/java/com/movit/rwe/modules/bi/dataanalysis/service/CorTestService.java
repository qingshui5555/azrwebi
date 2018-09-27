package com.movit.rwe.modules.bi.dataanalysis.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.rosuda.REngine.REXP;
import org.rosuda.REngine.RList;
import org.rosuda.REngine.Rserve.RConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.movit.rwe.common.config.Global;
import com.movit.rwe.common.utils.IdGen;
import com.movit.rwe.modules.bi.dataanalysis.util.RserveUtil;

@Service
public class CorTestService {
	
	private static Logger logger = LoggerFactory.getLogger(CorTestService.class);
	
	
	/**
	 * cor.test函数执行操作
	 * @param conn
	 * @param modelColumnNameArray
	 * @param dataList
	 * @return
	 * @throws Exception
	 */
	public Map corTest(RConnection conn,String [] modelColumnNameArray, 
			List<double[]>dataList, Map <String,String>paramMap) throws Exception{
		/*
		 * cor.test(x, y, method = "spearm", alternative = "g",conf.level = 0.95)
		 */
		StringBuffer corTestCommond = new StringBuffer();
		corTestCommond.append("cor.test(");
		
		for(int i=0;i<dataList.size();i++){
			double[] array = dataList.get(i);
			conn.assign(modelColumnNameArray[i], array);
			corTestCommond.append(modelColumnNameArray[i]).append(",");
		}
		
		corTestCommond.append("method = '").append(paramMap.get("method")).append("', ");
		corTestCommond.append("alternative = '").append(paramMap.get("alternative")).append("', ");
		corTestCommond.append("conf.level = ").append(paramMap.get("confLevel")).append(") ");
		
		REXP rexp = RserveUtil.parseAndEval(conn, corTestCommond.toString());
		
		/*
		 * 画图
		 * plot(x, col = cl$cluster)
		 * points(cl$centers, col = 1:2:3, pch = 8, cex = 2)
		 */
		String imgName = IdGen.uuid()+".jpeg";
		RserveUtil.generateJpeg(conn, imgName);
		StringBuffer plotCommond = new StringBuffer();
		plotCommond.append("plot(").append(modelColumnNameArray[0]).append(",")
			.append(modelColumnNameArray[1]).append(",type='p')");
		RserveUtil.parseAndEval(conn, plotCommond.toString());
		RserveUtil.devOff(conn);
		
		//提取需要的结果值
		RList rList = rexp.asList();
		double pValue = rList.at("p.value").asDouble();
		double estimate = rList.at("estimate").asDouble();
		
		//返回结果
		Map result = new HashMap();
		result.put("pValue", pValue);
		result.put("estimate", estimate);
		result.put("imgUrl", Global.getConfig("rserve.remote.filePath")+imgName);
		
		return result;
	}
	
	/**
	 * cor.test函数执行操作
	 * @param conn
	 * @param dataList
	 * @return
	 * @throws Exception
	 */
	public Map corTest(RConnection conn, List<double[]>dataList, 
			Map <String,String>paramMap) throws Exception{
		StringBuffer vectorCommon = new StringBuffer();
		
		for(int i=0;i<dataList.size();i++){
			double[] array = dataList.get(i);
			conn.assign("cortest_data_"+i, array);
			vectorCommon.append("cortest_data_"+i).append(",");
		}
		/*
		 * cor.test(x, y, method = "spearm", alternative = "g",conf.level = 0.95)
		 */
		StringBuffer corTestCommond = new StringBuffer();
		corTestCommond.append("cor.test(").append(vectorCommon)
			.append("method = '").append(paramMap.get("method")).append("', ")
			.append("alternative = '").append(paramMap.get("alternative")).append("', ")
			.append("conf.level = ").append(paramMap.get("confLevel")).append(") ");
		
		REXP rexp = RserveUtil.parseAndEval(conn, corTestCommond.toString());
		
		/*
		 * 画图
		 * plot(x, col = cl$cluster)
		 * points(cl$centers, col = 1:2:3, pch = 8, cex = 2)
		 */
		String imgName = IdGen.uuid()+".jpeg";
		RserveUtil.generateJpeg(conn, imgName);
		StringBuffer plotCommond = new StringBuffer();
		plotCommond.append("plot(").append(vectorCommon).append(" type='p')");
		RserveUtil.parseAndEval(conn, plotCommond.toString());
		RserveUtil.devOff(conn);
		
		//提取需要的结果值
		RList rList = rexp.asList();
		double pValue = rList.at("p.value").asDouble();
		double estimate = rList.at("estimate").asDouble();
		
		//返回结果
		Map result = new HashMap();
		result.put("pValue", pValue);
		result.put("estimate", estimate);
		result.put("imgUrl", Global.getConfig("rserve.remote.filePath")+imgName);
		
		return result;
	}
	
}
