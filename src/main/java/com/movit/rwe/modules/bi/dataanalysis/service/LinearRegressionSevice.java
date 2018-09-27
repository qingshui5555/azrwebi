package com.movit.rwe.modules.bi.dataanalysis.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.rosuda.REngine.REXP;
import org.rosuda.REngine.Rserve.RConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.movit.rwe.common.config.Global;
import com.movit.rwe.modules.bi.da.enums.DataTypeEnum;
import com.movit.rwe.modules.bi.da.test.dataframe.DataFrameGroup;
import com.movit.rwe.modules.bi.dataanalysis.util.RserveUtil;

@Service
public class LinearRegressionSevice {

	private static Logger logger = LoggerFactory.getLogger(LinearRegressionSevice.class);
	
	/**
	 * LinearRegression函数执行操作
	 * 生成模型并展示模型参数
	 * @param conn
	 * @param modelColumnNameArray
	 * @param dataList
	 * @return
	 * @throws Exception
	 */
	public Map lm(RConnection conn,String [] modelColumnNameArray, 
			List<double[]>dataList, Map <String,String>paramMap) throws Exception{
		/*
		 * 申明向量并构成data frame数据集
		 */
		StringBuffer dataFrameCommond = new StringBuffer();
		dataFrameCommond.append("lm_data_frame <- data.frame(");
		
		for(int i=0;i<dataList.size();i++){
			double[] array = dataList.get(i);
			conn.assign(modelColumnNameArray[i], array);
			//拼接data.frame命令中的参数
			dataFrameCommond.append(modelColumnNameArray[i]).append("=")
				.append(modelColumnNameArray[i]).append(",");
		}
		String dataFrameCommondStr = dataFrameCommond.substring(0,dataFrameCommond.lastIndexOf(","))+")";
		RserveUtil.parseAndEval(conn, dataFrameCommondStr);
		
		/*
		 * 执行lm命令,生成模型
		 * model <- lm(y~x1+x2+x3,training)
		 */
		StringBuffer lmCommond = new StringBuffer();
		lmCommond.append("lm_model <- lm(").append(paramMap.get("formula"))
			.append(",").append("lm_data_frame)");
		RserveUtil.parseAndEval(conn, lmCommond.toString());
		
		/*
		 * 执行step命令，变量筛选
		 * final_model <- step(model)
		 */
		String stepCommond = "lm_final_model <- step(lm_model)";
		RserveUtil.parseAndEval(conn, stepCommond);
		
		/*
		 * 执行summary命令，输出结果
		 * summary(final_model)
		 */
//		String summaryCommond = "summary_result<-summary(lm_model)";
		String summaryCommond = "summary_result<-summary(lm_final_model)";
		REXP rexp = RserveUtil.parseAndEval(conn, summaryCommond);
		
		/*
		 * 提取需要的结果值
		 */
		String estimateCommond = "summary_result$coefficients[,'Estimate']";
		REXP estimateRexp = RserveUtil.parseAndEval(conn, estimateCommond);
		double [] estimate = estimateRexp.asDoubles();
		
		String stdErrorCommond = "summary_result$coefficients[,'Std. Error']";
		REXP stdErrorRexp = RserveUtil.parseAndEval(conn, stdErrorCommond);
		double [] stdError = stdErrorRexp.asDoubles();
		
		String tValueCommond = "summary_result$coefficients[,'t value']";
		REXP tValueRexp = RserveUtil.parseAndEval(conn, tValueCommond);
		double [] tValue = tValueRexp.asDoubles();
		
		String pValueCommond = "summary_result$coefficients[,'Pr(>|t|)']";
		REXP pValueRexp = RserveUtil.parseAndEval(conn, pValueCommond);
		double [] pValue = pValueRexp.asDoubles();
		
		String [] significanceLevel =  RserveUtil.getSignifResults(pValue);
		String significanceCodes = " 0 '***' 0.001 '**' 0.01 '*' 0.05 '.' 0.1 ' ' 1 ";
		
		/*
		 * 保存模型
		 */
		StringBuffer saveImageCommond = new StringBuffer();
		saveImageCommond.append("save.image('")
			.append(Global.getConfig("rserve.local.modelPath"))
			.append(paramMap.get("modelName")).append("')");
		RserveUtil.parseAndEval(conn, saveImageCommond.toString());
		
		/*
		 * 返回结果
		 */
		Map result = new HashMap();
		result.put("estimate", estimate);
		result.put("stdError", stdError);
		result.put("tValue", tValue);
		result.put("pValue", pValue);
		result.put("significanceLevel", significanceLevel);
		result.put("significanceCodes", significanceCodes);
		
		return result;
	}
	
	/**
	 * LinearRegression函数执行操作
	 * 根据生成的模型进行结果预测
	 * @param conn
	 * @param modelColumnNameArray
	 * @param dataList
	 * @return
	 * @throws Exception
	 */
	public Map predict(RConnection conn,String [] modelColumnNameArray, 
			List<double[]>dataList, Map <String,String>paramMap) throws Exception{
		/*
		 * 加载模型
		 * load('/home/Rserve/model/model_123.RData')
		 */
		StringBuffer loadCommond = new StringBuffer();
		loadCommond.append("load('").append(Global.getConfig("rserve.local.modelPath"))
			.append(paramMap.get("modelName")).append("')");
		RserveUtil.parseAndEval(conn, loadCommond.toString());
		
		/*
		 * 申明向量并构成data frame数据集
		 */
		StringBuffer dataFrameCommond = new StringBuffer();
		dataFrameCommond.append("predict_data_frame <- data.frame(");
		
		for(int i=0;i<dataList.size();i++){
			double[] array = dataList.get(i);
			conn.assign(modelColumnNameArray[i], array);
			//拼接data.frame命令中的参数
			dataFrameCommond.append(modelColumnNameArray[i]).append("=")
				.append(modelColumnNameArray[i]).append(",");
		}
		String dataFrameCommondStr = dataFrameCommond.substring(0,dataFrameCommond.lastIndexOf(","))+")";
		RserveUtil.parseAndEval(conn, dataFrameCommondStr);
		
		/*
		 * 执行predict命令,预测结果
		 * predict(final_model,newData)
		 */
		String predictCommond = "predict(lm_final_model,predict_data_frame)";
		REXP rexp = RserveUtil.parseAndEval(conn, predictCommond);
		
		/*
		 * 提取需要的结果值
		 */
		double [] predictResult = rexp.asDoubles();
		
		/*
		 * 返回结果
		 */
		Map result = new HashMap();
		result.put("predictResult", predictResult);
		
		return result;
	}
	
	/**
	 * LinearRegression函数执行操作
	 * 生成模型并展示模型参数
	 * @param conn
	 * @param dataList
	 * @return
	 * @throws Exception
	 */
	public Map lm(RConnection conn,List<DataFrameGroup>dataList, Map <String,String>paramMap) throws Exception{
		/*
		 * 申明向量并构成data frame数据集
		 */
		StringBuffer dataFrameCommond = new StringBuffer();
		dataFrameCommond.append("lm_data_frame <- data.frame(");
		
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
		String dataFrameCommondStr = dataFrameCommond.substring(0,dataFrameCommond.lastIndexOf(","))+")";
		RserveUtil.parseAndEval(conn, dataFrameCommondStr);
		
		/*
		 * 执行lm命令,生成模型
		 * model <- lm(y~x1+x2+x3,training)
		 */
		StringBuffer lmCommond = new StringBuffer();
		lmCommond.append("lm_model <- lm(").append(paramMap.get("formula"))
			.append(",").append("lm_data_frame)");
		RserveUtil.parseAndEval(conn, lmCommond.toString());
		
		/*
		 * 执行step命令，变量筛选
		 * final_model <- step(model)
		 */
		String stepCommond = "lm_final_model <- step(lm_model)";
		RserveUtil.parseAndEval(conn, stepCommond);
		
		/*
		 * 执行summary命令，输出结果
		 * summary(final_model)
		 */
//		String summaryCommond = "summary_result<-summary(lm_model)";
		String summaryCommond = "summary_result<-summary(lm_final_model)";
		REXP rexp = RserveUtil.parseAndEval(conn, summaryCommond);
		
		/*
		 * 提取需要的结果值
		 */
		String estimateCommond = "summary_result$coefficients[,'Estimate']";
		REXP estimateRexp = RserveUtil.parseAndEval(conn, estimateCommond);
		double [] estimate = estimateRexp.asDoubles();
		
		String stdErrorCommond = "summary_result$coefficients[,'Std. Error']";
		REXP stdErrorRexp = RserveUtil.parseAndEval(conn, stdErrorCommond);
		double [] stdError = stdErrorRexp.asDoubles();
		
		String tValueCommond = "summary_result$coefficients[,'t value']";
		REXP tValueRexp = RserveUtil.parseAndEval(conn, tValueCommond);
		double [] tValue = tValueRexp.asDoubles();
		
		String pValueCommond = "summary_result$coefficients[,'Pr(>|t|)']";
		REXP pValueRexp = RserveUtil.parseAndEval(conn, pValueCommond);
		double [] pValue = pValueRexp.asDoubles();
		
		String [] significanceLevel =  RserveUtil.getSignifResults(pValue);
		String significanceCodes = " 0 '***' 0.001 '**' 0.01 '*' 0.05 '.' 0.1 ' ' 1 ";
		
		/*
		 * 保存模型
		 */
		StringBuffer saveImageCommond = new StringBuffer();
		saveImageCommond.append("save.image('")
			.append(Global.getConfig("rserve.local.modelPath"))
			.append(paramMap.get("modelName")).append("')");
		RserveUtil.parseAndEval(conn, saveImageCommond.toString());
		
		/*
		 * 返回结果
		 */
		Map result = new HashMap();
		result.put("estimate", estimate);
		result.put("stdError", stdError);
		result.put("tValue", tValue);
		result.put("pValue", pValue);
		result.put("significanceLevel", significanceLevel);
		result.put("significanceCodes", significanceCodes);
		
		return result;
	}
	
	/**
	 * LinearRegression函数执行操作
	 * 根据生成的模型进行结果预测
	 * @param conn
	 * @param dataList
	 * @return
	 * @throws Exception
	 */
	public Map predict(RConnection conn,List<DataFrameGroup>dataList, Map <String,String>paramMap) throws Exception{
		/*
		 * 加载模型
		 * load('/home/Rserve/model/model_123.RData')
		 */
		StringBuffer loadCommond = new StringBuffer();
		loadCommond.append("load('").append(Global.getConfig("rserve.local.modelPath"))
			.append(paramMap.get("modelName")).append("')");
		RserveUtil.parseAndEval(conn, loadCommond.toString());
		
		/*
		 * 申明向量并构成data frame数据集
		 */
		StringBuffer dataFrameCommond = new StringBuffer();
		dataFrameCommond.append("predict_data_frame <- data.frame(");
		
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
		
		String dataFrameCommondStr = dataFrameCommond.substring(0,dataFrameCommond.lastIndexOf(","))+")";
		RserveUtil.parseAndEval(conn, dataFrameCommondStr);
		
		/*
		 * 执行predict命令,预测结果
		 * predict(final_model,newData)
		 */
		String predictCommond = "predict(lm_final_model,predict_data_frame)";
		REXP rexp = RserveUtil.parseAndEval(conn, predictCommond);
		
		/*
		 * 提取需要的结果值
		 */
		double [] predictResult = rexp.asDoubles();
		
		/*
		 * 返回结果
		 */
		Map result = new HashMap();
		result.put("predictResult", predictResult);
		
		return result;
	}
}
