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
public class WilcoxTestService {
	
	private static Logger logger = LoggerFactory.getLogger(WilcoxTestService.class);
	
	/**
	 * wilcox.test函数执行操作
	 * @param conn
	 * @param modelColumnNameArray
	 * @param dataList
	 * @return
	 * @throws Exception
	 */
	public Map wilcoxTest(RConnection conn,String [] modelColumnNameArray, 
			List<double[]>dataList, Map <String,String>ttestParamMap) throws Exception{
		/*
		 * wilcox.test命令
		 * wilcox.test(x,y,alternative = "two.sided", mu = "0", paired = "FALSE", conf.level = "0.95")
		 */
		StringBuffer wilcoxTestCommond = new StringBuffer();
		wilcoxTestCommond.append("wilcox.test(");
		
		for(int i=0;i<dataList.size();i++){
			double[] array = dataList.get(i);
			conn.assign(modelColumnNameArray[i], array);
			wilcoxTestCommond.append(modelColumnNameArray[i]).append(",");
		}
		
		wilcoxTestCommond.append("alternative = \"").append(ttestParamMap.get("alternative")).append("\", ");
		wilcoxTestCommond.append("mu = ").append(ttestParamMap.get("mu")).append(", ");
		wilcoxTestCommond.append("paired = ").append(ttestParamMap.get("paired")).append(", ");
		wilcoxTestCommond.append("conf.level = ").append(ttestParamMap.get("confLevel")).append(") ");
		
		REXP rexp = RserveUtil.parseAndEval(conn, wilcoxTestCommond.toString());
		
		/*
		 * 画图,最新需求不需要图
		 * plot(table(x),xlab='value',ylab='number',type='l'))
		 * line(table(y),type="l")
		 * legend('bottomright',c('PSE','PSW'))
		 */
		/*String imgName = IdGen.uuid()+".jpeg";
		RserveUtil.generateJpeg(conn, imgName);
		StringBuffer legendCommond = new StringBuffer();
		legendCommond.append("legend('bottomright',c(");
		for(int j=0;j<modelColumnNameArray.length;j++){
			if(j==0){
				StringBuffer plotCommond = new StringBuffer();
				plotCommond.append("plot(table(").append(modelColumnNameArray[j])
					.append("),xlab='value',ylab='number',type='l')");
				RserveUtil.parseAndEval(conn, plotCommond.toString());
			}else{
				StringBuffer lineCommond = new StringBuffer();
				lineCommond.append("lines(table(").append(modelColumnNameArray[j])
					.append("),type='l')");
				RserveUtil.parseAndEval(conn, lineCommond.toString());
			}
			legendCommond.append("'").append(modelColumnNameArray[j]).append("',");
		}
		String legendCommondStr = legendCommond.substring(0,legendCommond.lastIndexOf(","))+"))";
		RserveUtil.parseAndEval(conn, legendCommondStr);
		RserveUtil.devOff(conn);*/
		
		/*
		 * 提取需要的结果值
		 */
		RList rList = rexp.asList();
		String pValue = rList.at("p.value").asString();
		//String [] confInt = rList.at("conf.int").asStrings();
		
		/*
		 * 返回结果
		 */
		Map result = new HashMap();
		result.put("pValue", pValue);
		//result.put("confInt", confInt);
//		result.put("imgUrl", Global.getConfig("rserve.remote.filePath")+imgName);
		
		return result;
	}
	
	/**
	 * wilcox.test函数执行操作
	 * @param conn
	 * @param modelColumnNameArray
	 * @param dataList
	 * @return
	 * @throws Exception
	 */
	public Map wilcoxTest(RConnection conn,List<double[]>dataList, Map <String,String>ttestParamMap) throws Exception{
		/*
		 * wilcox.test命令
		 * wilcox.test(x,y,alternative = "two.sided", mu = "0", paired = "FALSE", conf.level = "0.95")
		 */
		StringBuffer wilcoxTestCommond = new StringBuffer();
		wilcoxTestCommond.append("wilcox.test(");
		
		for(int i=0;i<dataList.size();i++){
			double[] array = dataList.get(i);
			conn.assign("wilcox_data_"+i, array);
			wilcoxTestCommond.append("wilcox_data_"+i).append(",");
		}
		
		wilcoxTestCommond.append("alternative = \"").append(ttestParamMap.get("alternative")).append("\", ");
		wilcoxTestCommond.append("mu = ").append(ttestParamMap.get("mu")).append(", ");
		wilcoxTestCommond.append("paired = ").append(ttestParamMap.get("paired")).append(", ");
		wilcoxTestCommond.append("conf.level = ").append(ttestParamMap.get("confLevel")).append(") ");
		
		REXP rexp = RserveUtil.parseAndEval(conn, wilcoxTestCommond.toString());
		
		/*
		 * 提取需要的结果值
		 */
		RList rList = rexp.asList();
		String pValue = rList.at("p.value").asString();
		//String [] confInt = rList.at("conf.int").asStrings();
		
		/*
		 * 返回结果
		 */
		Map result = new HashMap();
		result.put("pValue", pValue);
		//result.put("confInt", confInt);
//		result.put("imgUrl", Global.getConfig("rserve.remote.filePath")+imgName);
		
		return result;
	}
	
}
