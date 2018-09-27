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

import com.movit.rwe.common.config.Global;
import com.movit.rwe.modules.bi.dataanalysis.util.RserveUtil;

@Service
public class KsvmService {
	
	private static Logger logger = LoggerFactory.getLogger(KsvmService.class);
	@Autowired
	private DataAnalysisService dataAnalysisService;
	
	
	/**
	 * ksvm函数执行操作
	 * 生成模型并展示模型参数
	 * @param conn
	 * @param modelColumnNameArray
	 * @param dataList
	 * @return
	 * @throws Exception
	 */
	public Map ksvm(RConnection conn,String [] modelColumnNameArray, 
			List<String[]>dataList, Map <String,String>paramMap) throws Exception{
		/*
		 * 生成data frame数据集
		 */
		StringBuffer dataFrameCommond = new StringBuffer();
		dataFrameCommond.append("ksvm_data_frame <- data.frame(");
		
		for(int i=0;i<dataList.size();i++){
			String[] array = dataList.get(i);
			conn.assign(modelColumnNameArray[i], array);
			dataFrameCommond.append(modelColumnNameArray[i]).append("=")
				.append(modelColumnNameArray[i]).append(",");
		}
		String dataFrameCommondStr = dataFrameCommond.substring(0, dataFrameCommond.length()-1)+")";
		RserveUtil.parseAndEval(conn, dataFrameCommondStr);
		
		/*
		 * 加载kernlab包
		 * library(kernlab)
		 * Error in UseMethod("predict") : "predict"没有适用于"c('ksvm', 'vm')"目标对象的方法
		 */
		String libraryCommondStr = "library(kernlab)";
		RserveUtil.parseAndEval(conn, libraryCommondStr);
		
		/*
		 * 训练ksvm模型
		 * filter <- ksvm(type~.,data=spamtrain,kernel="rbfdot",kpar=list(sigma=0.05),C=5,cross=3)
		 */
		StringBuffer ksvmCommond = new StringBuffer();
		ksvmCommond.append("ksvm_model <- ksvm(").append(paramMap.get("formula"))
			.append(",data=ksvm_data_frame,kernel='").append(paramMap.get("kernel"))
			.append("',kpar=list(").append(paramMap.get("kparKey")).append("=")
			.append(paramMap.get("kparValue"))
			.append("),C=").append(paramMap.get("C"))
			.append(",cross=").append(paramMap.get("cross"))
			.append(")");
		RserveUtil.parseAndEval(conn, ksvmCommond.toString());
		
		/*
		 * 执行summary命令，输出结果
		 * summary(final_model)
		 */
		String summaryCommond = "summary_result<-summary(ksvm_model)";
		REXP rexp = RserveUtil.parseAndEval(conn, summaryCommond);
		
		/*
		 * 保存模型
		 */
		StringBuffer saveImageCommond = new StringBuffer();
		saveImageCommond.append("save.image('")
			.append(Global.getConfig("rserve.local.modelPath"))
			.append(paramMap.get("modelName")).append("')");
		RserveUtil.parseAndEval(conn, saveImageCommond.toString());
		
		//提取需要的结果值
		String [] modelResult = rexp.asStrings();
		
		//返回结果
		Map result = new HashMap();
		result.put("modelResult", modelResult);
		
		return result;
	}

	/**
	 * ksvm函数执行操作
	 * 根据生成的模型进行结果预测
	 * @param conn
	 * @param modelColumnNameArray
	 * @param dataList
	 * @return
	 * @throws Exception
	 */
	public Map predict(RConnection conn,String [] modelColumnNameArray, 
			List<String[]>dataList, Map <String,String>paramMap) throws Exception{
		/*
		 * 加载kernlab包
		 * library(kernlab)
		 * 不加载包会报Error in UseMethod("predict") : "predict"没有适用于"c('ksvm', 'vm')"目标对象的方法
		 */
		String libraryCommondStr = "library(kernlab)";
		RserveUtil.parseAndEval(conn, libraryCommondStr);
		
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
			String[] array = dataList.get(i);
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
		String predictCommond = "predict_result <- predict(ksvm_model,predict_data_frame)";
		REXP rexpp = RserveUtil.parseAndEval(conn, predictCommond);
		
		/*
		 * 检查预测结果
		 * table(mailtype,spamtest[,58])
		 * 与结果列进行比较，这里用modelColumnNameArray的最后一列
		 */
		StringBuffer tableCommond = new StringBuffer();
		tableCommond.append("table(predict_result,predict_data_frame$")
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
	
}
