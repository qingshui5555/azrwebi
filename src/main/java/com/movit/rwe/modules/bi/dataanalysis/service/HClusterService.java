package com.movit.rwe.modules.bi.dataanalysis.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.rosuda.REngine.REXP;
import org.rosuda.REngine.RList;
import org.rosuda.REngine.Rserve.RConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.movit.rwe.common.config.Global;
import com.movit.rwe.common.utils.IdGen;
import com.movit.rwe.modules.bi.dataanalysis.util.RserveUtil;

@Service
public class HClusterService {
	
	private static Logger logger = LoggerFactory.getLogger(HClusterService.class);
	@Autowired
	private DataAnalysisService dataAnalysisService;
	
	/**
	 * hclust函数执行操作
	 * 可视化聚类结果
	 * @param conn
	 * @param modelColumnNameArray
	 * @param dataList
	 * @return
	 * @throws Exception
	 */
	public Map hclust(RConnection conn,String [] modelColumnNameArray, 
			List<double[]>dataList, Map <String,String>paramMap) throws Exception{
		/*
		 * 申明向量并构成data frame数据集
		 */
		StringBuffer dataFrameCommond = new StringBuffer();
		dataFrameCommond.append("hclust_data_frame <- data.frame(");
		
		for(int i=0;i<dataList.size();i++){
			double[] array = dataList.get(i);
			conn.assign(modelColumnNameArray[i], array);
			//拼接data.frame命令中的参数
			dataFrameCommond.append(modelColumnNameArray[i]).append("=")
				.append(modelColumnNameArray[i]).append(",");
		}
		String dataFrameCommondStr = dataFrameCommond.substring(0,dataFrameCommond.lastIndexOf(","))+")";
		RserveUtil.parseAndEval(conn, dataFrameCommondStr);
//		
//		/**
//		 * 申明向量并构成矩阵
//		 */
//		StringBuffer matrixCommond = new StringBuffer();
//		matrixCommond.append("hclust_matrix <- cbind(");
//		
//		for(int i=0;i<dataList.size();i++){
//			double[] array = dataList.get(i);
//			conn.assign(modelColumnNameArray[i], array);
//			//拼接data.frame命令中的参数
//			matrixCommond.append(modelColumnNameArray[i]).append("=")
//				.append(modelColumnNameArray[i]).append(",");
//		}
//		String dataFrameCommondStr = matrixCommond.substring(0,matrixCommond.lastIndexOf(","))+")";
//		REXP R = RserveUtil.parseAndEval(conn, dataFrameCommondStr);
//		
		/*
		 * 计算距离矩阵
		 * dist.e=dist(data,method='euclidean')
		 */
		StringBuffer distCommond = new StringBuffer();
		distCommond.append("dist_result <- dist(hclust_data_frame,method='")
			.append(paramMap.get("distanceMethod")).append("')");
		RserveUtil.parseAndEval(conn, distCommond.toString());
		
		/*
		 * 输入距离矩阵
		 * model1=hclust(dist.e,method='ward.D')
		 */
		StringBuffer hclustCommond = new StringBuffer();
		hclustCommond.append("hclust_model <- hclust(dist_result,method='")
			.append(paramMap.get("hclusterMethod")).append("')");
		RserveUtil.parseAndEval(conn, hclustCommond.toString());
		
		/*
		 * 画图
		 * plot(model1)
		 */
		String imgName = IdGen.uuid()+".jpeg";
		RserveUtil.generateJpeg(conn, imgName);
		String plotCommond = "plot(hclust_model,xlab='',main='',sub='')";
		RserveUtil.parseAndEval(conn, plotCommond);
		RserveUtil.devOff(conn);
		
		/*
		 * 根据聚类图主观决定聚成几类
		 * result=cutree(model1,k=3)
		 */
		StringBuffer cutreeCommond = new StringBuffer();
		cutreeCommond.append("cutree_result <- cutree(hclust_model,k=")
			.append(paramMap.get("k")).append(")");
		REXP rexp = RserveUtil.parseAndEval(conn, cutreeCommond.toString());
		
		/*
		 * 提取需要的结果值
		 */
		String [] cutreeArray = rexp.asStrings();
		
		/*
		 * 返回结果
		 */
		Map result = new HashMap();
		result.put("imgUrl", Global.getConfig("rserve.remote.filePath")+imgName);
		result.put("cutreeArray", cutreeArray);
		
		return result;
	}
	
	/**
	 * hclust函数执行操作
	 * 根据聚类图主观决定聚成几类
	 * @param conn
	 * @param modelColumnNameArray
	 * @param dataList
	 * @return
	 * @throws Exception
	 */
	public Map cutree(RConnection conn,String [] modelColumnNameArray, 
			List<double[]>dataList, Map <String,String>paramMap) throws Exception{
		/*
		 * 根据聚类图主观决定聚成几类
		 * result=cutree(model1,k=3)
		 */
		StringBuffer cutreeCommond = new StringBuffer();
		cutreeCommond.append("cutree_result = cutree(hclust_model,k=")
			.append(paramMap.get("k")).append(")");
		REXP rexp = RserveUtil.parseAndEval(conn, cutreeCommond.toString());
		
		//提取需要的结果值
		RList rList = rexp.asList();
		int [] cutreeArray = rexp.asIntegers();
		
		//返回结果
		Map result = new HashMap();
		result.put("", cutreeArray);
		
		return result;
	}
	
}
