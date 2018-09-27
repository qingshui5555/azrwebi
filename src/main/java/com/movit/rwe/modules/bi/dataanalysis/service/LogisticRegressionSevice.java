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
import org.springframework.stereotype.Service;

import com.movit.rwe.common.config.Global;
import com.movit.rwe.modules.bi.da.enums.DataTypeEnum;
import com.movit.rwe.modules.bi.da.test.dataframe.DataFrameGroup;
import com.movit.rwe.modules.bi.dataanalysis.util.RserveUtil;

@Service
public class LogisticRegressionSevice {

	private static Logger logger = LoggerFactory.getLogger(LogisticRegressionSevice.class);
	
	/**
	 * LogisticRegression函数执行操作
	 * 生成模型并展示模型参数
	 * @param conn
	 * @param modelColumnNameArray
	 * @param dataList
	 * @return
	 * @throws Exception
	 */
	public Map glm(RConnection conn,String [] modelColumnNameArray, 
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
		 * 执行glm命令,生成模型
		 * model <- glm(y~x1+x2+x3,family='binomial',training)
		 * family=binomial时公式左边y必须在0-1之间
		 */
		StringBuffer glmCommond = new StringBuffer();
		glmCommond.append("glm_model <- glm(").append(paramMap.get("formula"))
			.append(",family=binomial,").append("lm_data_frame)");
//			.append(",family=gaussian,").append("lm_data_frame)");
		RserveUtil.parseAndEval(conn, glmCommond.toString());
		
		/*
		 * 执行step命令，变量筛选
		 * final_model <- step(model)
		 */
		String stepCommond = "glm_final_model <- step(glm_model)";
		RserveUtil.parseAndEval(conn, stepCommond);
		
		/*
		 * 执行summary命令，输出结果
		 * summary(final_model)
		 */
		String summaryCommond = "summary_result<-summary(glm_final_model)";
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
		
		String zValueCommond = "summary_result$coefficients[,'z value']";
		REXP zValueRexp = RserveUtil.parseAndEval(conn, zValueCommond);
		double [] zValue = zValueRexp.asDoubles();
		
		String pValueCommond = "summary_result$coefficients[,'Pr(>|z|)']";
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
		result.put("tValue", zValue);
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
		 * predict(final_model,newData,type='response')
		 */
		String predictCommond = "predict_result <- predict(glm_final_model,predict_data_frame,type='response')";
		RserveUtil.parseAndEval(conn, predictCommond);
		
		/*
		 * # 以0.5作为阈值，大于0.5为pos，否则为neg
		 * predicted_class <- ifelse(pos_possibility>0.5,'pos','neg')
		 * table(predicted_class,newData$class[1:4])
		 */
		StringBuffer ifelseCommond = new StringBuffer();
		ifelseCommond.append("ifelse_result <- ifelse(predict_result>").append(paramMap.get("threshold"))
			.append(",'pos','neg')");
		RserveUtil.parseAndEval(conn, ifelseCommond.toString());
		
		StringBuffer tableCommond = new StringBuffer();
		tableCommond.append("table(ifelse_result,predict_data_frame$")
			.append(modelColumnNameArray[modelColumnNameArray.length-1]).append(")");
		REXP rexp = RserveUtil.parseAndEval(conn, tableCommond.toString());

		/*
		 * 提取需要的结果值
		 */
		REXPList tableRexpList = rexp._attr();
		RList dimnamesRList = tableRexpList.asList();
		REXP dimnamesRexp = dimnamesRList.at("dimnames");
		RList dimnamesRListList = dimnamesRexp.asList();
		String []rowNameArray = dimnamesRListList.at(0).asStrings();
		String []colNameArray = dimnamesRListList.at(1).asStrings();
		double [][] predictResult = rexp.asDoubleMatrix();
		
		/*
		 * 返回结果
		 */
		Map result = new HashMap();
		result.put("predictResult", predictResult);
		result.put("rowNameArray", rowNameArray);
		result.put("colNameArray", colNameArray);
		
		return result;
	}
	
	/**
	 * LogisticRegression函数执行操作
	 * 生成模型并展示模型参数
	 * @param conn
	 * @param dataList
	 * @return
	 * @throws Exception
	 */
	public Map glm(RConnection conn,List<DataFrameGroup>dataList, Map <String,String>paramMap) throws Exception{
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
		 * 执行glm命令,生成模型
		 * model <- glm(y~x1+x2+x3,family='binomial',training)
		 * family=binomial时公式左边y必须在0-1之间
		 */
		StringBuffer glmCommond = new StringBuffer();
		glmCommond.append("glm_model <- glm(").append(paramMap.get("formula"))
			.append(",family=binomial,").append("lm_data_frame)");
//			.append(",family=gaussian,").append("lm_data_frame)");
		RserveUtil.parseAndEval(conn, glmCommond.toString());
		
		/*
		 * 执行step命令，变量筛选
		 * final_model <- step(model)
		 */
		String stepCommond = "glm_final_model <- step(glm_model)";
		RserveUtil.parseAndEval(conn, stepCommond);
		
		/*
		 * 执行summary命令，输出结果
		 * summary(final_model)
		 */
		String summaryCommond = "summary_result<-summary(glm_final_model)";
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
		
		String zValueCommond = "summary_result$coefficients[,'z value']";
		REXP zValueRexp = RserveUtil.parseAndEval(conn, zValueCommond);
		double [] zValue = zValueRexp.asDoubles();
		
		String pValueCommond = "summary_result$coefficients[,'Pr(>|z|)']";
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
		result.put("tValue", zValue);
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
		 * predict(final_model,newData,type='response')
		 */
		String predictCommond = "predict_result <- predict(glm_final_model,predict_data_frame,type='response')";
		RserveUtil.parseAndEval(conn, predictCommond);
		
		/*
		 * # 以0.5作为阈值，大于0.5为pos，否则为neg
		 * predicted_class <- ifelse(pos_possibility>0.5,'pos','neg')
		 * table(predicted_class,newData$class[1:4])
		 */
		StringBuffer ifelseCommond = new StringBuffer();
		ifelseCommond.append("ifelse_result <- ifelse(predict_result>").append(paramMap.get("threshold"))
			.append(",'pos','neg')");
		RserveUtil.parseAndEval(conn, ifelseCommond.toString());
		
		StringBuffer tableCommond = new StringBuffer();
		tableCommond.append("table(ifelse_result,predict_data_frame$")
			.append(dataList.get(dataList.size()-1).getColumn()).append(")");
		REXP rexp = RserveUtil.parseAndEval(conn, tableCommond.toString());

		/*
		 * 提取需要的结果值
		 */
		REXPList tableRexpList = rexp._attr();
		RList dimnamesRList = tableRexpList.asList();
		REXP dimnamesRexp = dimnamesRList.at("dimnames");
		RList dimnamesRListList = dimnamesRexp.asList();
		String []rowNameArray = dimnamesRListList.at(0).asStrings();
		String []colNameArray = dimnamesRListList.at(1).asStrings();
		double [][] predictResult = rexp.asDoubleMatrix();
		
		/*
		 * 返回结果
		 */
		Map result = new HashMap();
		result.put("predictResult", predictResult);
		result.put("rowNameArray", rowNameArray);
		result.put("colNameArray", colNameArray);
		
		return result;
	}
}
