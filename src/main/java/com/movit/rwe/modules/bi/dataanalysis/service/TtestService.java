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

import com.movit.rwe.modules.bi.dataanalysis.util.RserveUtil;

@Service
public class TtestService {
	
	private static Logger logger = LoggerFactory.getLogger(TtestService.class);
	
	/**
	 * t.test函数执行操作
	 * @param conn
	 * @param modelColumnNameArray
	 * @param dataList
	 * @return
	 * @throws Exception
	 */
	public Map ttest(RConnection conn,String [] modelColumnNameArray, 
			List<double[]>dataList, Map <String,String>ttestParamMap) throws Exception{
		
		//t.test命令：t.test(x,y,alternative = "two.sided", mu = "0", paired = "FALSE", conf.level = "0.95") 
		StringBuffer ttestCommond = new StringBuffer();
		ttestCommond.append("t.test(");
		
		for(int i=0;i<dataList.size();i++){
			double[] array = dataList.get(i);
			conn.assign(modelColumnNameArray[i], array);
			ttestCommond.append(modelColumnNameArray[i]).append(",");
		}
		
		ttestCommond.append("alternative = \"").append(ttestParamMap.get("alternative")).append("\", ");
		ttestCommond.append("mu = ").append(ttestParamMap.get("mu")).append(", ");
		ttestCommond.append("paired = ").append(ttestParamMap.get("paired")).append(", ");
		ttestCommond.append("conf.level = ").append(ttestParamMap.get("confLevel")).append(") ");
		
		REXP rexp = RserveUtil.parseAndEval(conn, ttestCommond.toString());
		
		//画图，最新需求不需要图
		/*String imgName = IdGen.uuid()+".jpeg";
		RserveUtil.generateJpeg(conn, imgName);
		String plotCommond = "plot(table(x),xlab='value',ylab='number',main='',sub='',type='l',lty=1)";
		RserveUtil.parseAndEval(conn, plotCommond);
		RserveUtil.devOff(conn);*/
		
		//提取需要的结果值
		RList rList = rexp.asList();
		double pValue = rList.at("p.value").asDouble();
		String [] confInt = rList.at("conf.int").asStrings();
		
		//返回结果
		Map result = new HashMap();
		result.put("pValue", pValue);
		result.put("confInt", confInt);
//		result.put("imgUrl", Global.getConfig("rserve.remote.filePath")+imgName);
		
		return result;
	}
	
	/**
	 * t.test函数执行操作(最新修改20161014)
	 * @param conn
	 * @param modelColumnNameArray
	 * @param dataList
	 * @return
	 * @throws Exception
	 */
	public Map ttest(RConnection conn,List<double[]>dataList, Map <String,String>ttestParamMap) throws Exception{
		
		/**
		 * t.test命令：t.test(x,y,alternative = "two.sided", mu = "0", paired = "FALSE", conf.level = "0.95") 
		 */
		StringBuffer ttestCommond = new StringBuffer();
		ttestCommond.append("t.test(");
		
		for(int i=0;i<dataList.size();i++){
			double[] array = dataList.get(i);
			conn.assign("ttest_data_"+i, array);
			ttestCommond.append("ttest_data_"+i).append(",");
		}
		
		ttestCommond.append("alternative = \"").append(ttestParamMap.get("alternative")).append("\", ");
		ttestCommond.append("mu = ").append(ttestParamMap.get("mu")).append(", ");
		ttestCommond.append("paired = ").append(ttestParamMap.get("paired")).append(", ");
		ttestCommond.append("conf.level = ").append(ttestParamMap.get("confLevel")).append(") ");
		
		REXP rexp = RserveUtil.parseAndEval(conn, ttestCommond.toString());
		
		//画图，最新需求不需要图
		/*String imgName = IdGen.uuid()+".jpeg";
		RserveUtil.generateJpeg(conn, imgName);
		String plotCommond = "plot(table(x),xlab='value',ylab='number',main='',sub='',type='l',lty=1)";
		RserveUtil.parseAndEval(conn, plotCommond);
		RserveUtil.devOff(conn);*/
		
		//提取需要的结果值
		RList rList = rexp.asList();
		double pValue = rList.at("p.value").asDouble();
		String [] confInt = rList.at("conf.int").asStrings();
		
		//返回结果
		Map result = new HashMap();
		result.put("pValue", pValue);
		result.put("confInt", confInt);
//		result.put("imgUrl", Global.getConfig("rserve.remote.filePath")+imgName);
		
		return result;
	}
	
}
