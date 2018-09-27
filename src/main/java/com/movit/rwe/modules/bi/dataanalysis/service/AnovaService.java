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
import com.movit.rwe.modules.bi.da.enums.DataTypeEnum;
import com.movit.rwe.modules.bi.da.test.dataframe.DataFrameGroup;
import com.movit.rwe.modules.bi.dataanalysis.util.RserveUtil;

@Service
public class AnovaService {
	
	private static Logger logger = LoggerFactory.getLogger(AnovaService.class);
	
	/**
	 * aov函数执行操作
	 * @param conn
	 * @param modelColumnNameArray
	 * @param dataList
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public Map aov(RConnection conn,String [] modelColumnNameArray, 
			List<double[]>dataList, Map <String,String>paramMap) throws Exception{
		/*
		 * 生成data frame数据集
		 */
		StringBuffer dataFrameCommond = new StringBuffer();
		dataFrameCommond.append("aov_data_frame <- data.frame(");
		
		for(int i=0;i<dataList.size();i++){
			double[] array = dataList.get(i);
			conn.assign(modelColumnNameArray[i], array);
			dataFrameCommond.append(modelColumnNameArray[i]).append("=")
				.append(modelColumnNameArray[i]).append(",");
		}
		String dataFrameCommondStr = dataFrameCommond.substring(0, dataFrameCommond.length()-1)+")";
		RserveUtil.parseAndEval(conn, dataFrameCommondStr);
		
		/*
		 * 执行aov命令
		 * result <- aov(yield ~ block + N + P + K, npk)
		 */
		StringBuffer aovCommond = new StringBuffer();
		aovCommond.append("aov_result <- aov(").append(paramMap.get("formula"))
			.append(",aov_data_frame)");
		RserveUtil.parseAndEval(conn, aovCommond.toString());
		
		String summaryCommond = "summary_result <- summary(aov_result)";
		 RserveUtil.parseAndEval(conn, summaryCommond);
		
		String prCommond = "summary_result[[1]]$`Pr(>F)`";
		REXP rexp = RserveUtil.parseAndEval(conn, prCommond);
		/*
		 * 画图
		 * plot(table(x),xlab='value',ylab='number',type='l'))
		 * line(table(y),type="l")
		 * legend('bottomright',c('PSE','PSW'))
		 */
		String imgName = IdGen.uuid()+".jpeg";
		RserveUtil.generateJpeg(conn, imgName);
		StringBuffer legendCommond = new StringBuffer();
		legendCommond.append("legend('bottomright',c(");
		for(int j=0;j<modelColumnNameArray.length;j++){
			if(j==0){
//				String plotCommond = "plot(table(x),xlab=\"value\",ylab=\"number\",main=\"\",sub=\"\",type=\"l\",lty=1)";
				StringBuffer plotCommond = new StringBuffer();
				plotCommond.append("plot(table(").append(modelColumnNameArray[j])
					.append("),xlab='value',ylab='number',type='l',col=1)");
				RserveUtil.parseAndEval(conn, plotCommond.toString());
			}else{
				StringBuffer lineCommond = new StringBuffer();
				lineCommond.append("lines(table(").append(modelColumnNameArray[j])
					.append("),type='l',col=2:999)");
				RserveUtil.parseAndEval(conn, lineCommond.toString());
			}
			legendCommond.append("'").append(modelColumnNameArray[j]).append("',");
		}
		String legendCommondStr = legendCommond.substring(0,legendCommond.lastIndexOf(","))+"),";
		legendCommondStr += "lty=1,col=1:999)";
		RserveUtil.parseAndEval(conn, legendCommondStr);
		RserveUtil.devOff(conn);
		
		/*
		 * 提取需要的结果值
		 */
		double []pValueArr = rexp.asDoubles();
		//去除最后一个NAN值
		double [] pValue = new double[pValueArr.length-1];
		for(int k=0;k<pValue.length;k++){
			pValue[k] = pValueArr[k];
		}
		String [] SignificanceLevel =  RserveUtil.getSignifResults(pValue);
		String SignificanceCodes = " 0 '***' 0.001 '**' 0.01 '*' 0.05 '.' 0.1 ' ' 1 ";
		
		/*
		 * 返回结果
		 */
		Map result = new HashMap();
		result.put("pValue", pValue);
		result.put("SignificanceLevel", SignificanceLevel);
		result.put("SignificanceCodes", SignificanceCodes);
		result.put("imgUrl", Global.getConfig("rserve.remote.filePath")+imgName);
		
		return result;
	}
	
	/**
	 * anova函数执行操作
	 * @param conn
	 * @param modelColumnNameArray
	 * @param dataList
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public Map anova(RConnection conn,List<DataFrameGroup>dataList, Map <String,String>paramMap) throws Exception{
		/*
		 * 生成data frame数据集
		 */
		StringBuffer dataFrameCommond = new StringBuffer();
		dataFrameCommond.append("aov_data_frame <- data.frame(");
		
		for(int i=0;i<dataList.size();i++){
			DataFrameGroup dataFrameGroup = dataList.get(i);
			if(DataTypeEnum.Num.equals(dataFrameGroup.getDataType())){
				double[] array = RserveUtil.objectArrayToDoubleArray(dataFrameGroup.getValueArray());
				conn.assign(dataFrameGroup.getColumn(), array);
			}else{
				String[] array = RserveUtil.objectArrayToStringArray(dataFrameGroup.getValueArray());
				conn.assign(dataFrameGroup.getColumn(), array);
			}
			
			//拼接data.frame命令中的参数
			dataFrameCommond.append(dataFrameGroup.getColumn()).append("=")
				.append(dataFrameGroup.getColumn()).append(",");
		}
		String dataFrameCommondStr = dataFrameCommond.substring(0, dataFrameCommond.length()-1)+")";
		RserveUtil.parseAndEval(conn, dataFrameCommondStr);
		/*
		 * 拟合模型执行lm
		 */
		StringBuffer lmCommond = new StringBuffer();
		lmCommond.append("lm_result<-lm(").append(paramMap.get("formula")).append(",aov_data_frame)");
		RserveUtil.parseAndEval(conn, lmCommond.toString());
		
		/*
		 * 执行aov命令
		 * result <- aov(yield ~ block + N + P + K, npk)
		 */
		StringBuffer aovCommond = new StringBuffer();
//		aovCommond.append("aov_result <- aov(").append(paramMap.get("formula"))
//			.append(",aov_data_frame)");
		aovCommond.append("aov_result<-anova(lm_result)");
		REXP rexp = RserveUtil.parseAndEval(conn, aovCommond.toString());
		
//		String summaryCommond = "summary_result <- summary(aov_result)";
//		REXP r1 = RserveUtil.parseAndEval(conn, summaryCommond);
//		
//		String prCommond = "summary_result[[1]]$`Pr(>F)`";
//		REXP rexp = RserveUtil.parseAndEval(conn, prCommond);
		/*
		 * 画图,旧实现是展示数据的频度线
		 * plot(table(x),xlab='value',ylab='number',type='l'))
		 * line(table(y),type="l")
		 * legend('bottomright',c('PSE','PSW'))
		 
		String imgName = IdGen.uuid()+".jpeg";
		RserveUtil.generateJpeg(conn, imgName);
		StringBuffer legendCommond = new StringBuffer();
		legendCommond.append("legend('bottomright',c(");
		for(int j=0;j<dataList.size();j++){
			String cname = dataList.get(j).getColumn();
			if("group_column_".equals(cname)){
				cname="Group";
			}
			if(j==0){
//				String plotCommond = "plot(table(x),xlab=\"value\",ylab=\"number\",main=\"\",sub=\"\",type=\"l\",lty=1)";
				StringBuffer plotCommond = new StringBuffer();
				plotCommond.append("plot(table(").append(dataList.get(j).getColumn())
					.append("),xlab='value',ylab='number',type='l',col=1)");
				RserveUtil.parseAndEval(conn, plotCommond.toString());
			}else{
				StringBuffer lineCommond = new StringBuffer();
				lineCommond.append("lines(table(").append(dataList.get(j).getColumn())
					.append("),type='l',col=2:999)");
				RserveUtil.parseAndEval(conn, lineCommond.toString());
			}
			legendCommond.append("'").append(cname).append("',");
		}
		String legendCommondStr = legendCommond.substring(0,legendCommond.lastIndexOf(","))+"),";
		legendCommondStr += "lty=1,col=1:999)";
		RserveUtil.parseAndEval(conn, legendCommondStr);
		RserveUtil.devOff(conn);
		*/
		
		/*
		 * 画图
		 * 20170123修改
		 * 展示公式结果和组之间的箱线图
		 */
		String imgName = IdGen.uuid()+".jpeg";
		RserveUtil.generateJpeg(conn, imgName);
		StringBuffer plotCommond = new StringBuffer();
		plotCommond.append("plot(aov_data_frame$").append(dataList.get(dataList.size()-1).getColumn()).append("~factor(aov_data_frame$group)")
			.append(",xlab='group',ylab='").append(dataList.get(dataList.size()-1).getColumn()).append("')");
		RserveUtil.parseAndEval(conn, plotCommond.toString());
		RserveUtil.devOff(conn);
		
		/*
		 * 提取需要的结果值
		 */
		RList rList = rexp.asList();
		double []pValueArr = rList.at("Pr(>F)").asDoubles();
//		double []pValueArr = rexp.asDoubles();
		//去除最后一个NAN值
		double [] pValue = new double[pValueArr.length-1];
		for(int k=0;k<pValue.length;k++){
			pValue[k] = pValueArr[k];
		}
		String [] SignificanceLevel =  RserveUtil.getSignifResults(pValue);
		String SignificanceCodes = " 0 '***' 0.001 '**' 0.01 '*' 0.05 '.' 0.1 ' ' 1 ";
		
		/*
		 * 返回结果
		 */
		Map result = new HashMap();
		result.put("pValue", pValue);
		result.put("SignificanceLevel", SignificanceLevel);
		result.put("SignificanceCodes", SignificanceCodes);
		result.put("imgUrl", Global.getConfig("rserve.remote.filePath")+imgName);
		
		return result;
	}
	
}
