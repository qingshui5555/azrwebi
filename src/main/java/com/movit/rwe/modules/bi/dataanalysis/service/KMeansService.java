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
import com.movit.rwe.common.utils.IdGen;
import com.movit.rwe.modules.bi.dataanalysis.util.RserveUtil;

@Service
public class KMeansService {
	
	private static Logger logger = LoggerFactory.getLogger(KMeansService.class);
	@Autowired
	private DataAnalysisService dataAnalysisService;
	
	/**
	 * kmeans函数执行操作
	 * @param conn
	 * @param modelColumnNameArray
	 * @param dataList
	 * @return
	 * @throws Exception
	 */
	public Map kmeans(RConnection conn,String [] modelColumnNameArray, 
			List<double[]>dataList, Map <String,String>paramMap) throws Exception{
		/*
		 * 执行rbind命令
		 * x <- rbind(matrix(rnorm(100, sd = 0.3), ncol = 2), matrix(rnorm(100, mean = 1, sd = 0.3), ncol = 2), matrix(rnorm(100, mean = 2, sd = 0.3), ncol = 2))
		 */
		StringBuffer matrixCommond = new StringBuffer();
		
		for(int i=0;i<dataList.size();i++){
			double[] array = dataList.get(i);
			conn.assign(modelColumnNameArray[i], array);
			matrixCommond.append("matrix(").append(modelColumnNameArray[i]).append(", ncol = 2),");
		}
		String matrixCommondStr = matrixCommond.substring(0,matrixCommond.lastIndexOf(","));
		
		StringBuffer rbindCommond = new StringBuffer();
		rbindCommond.append("rbind_result <- rbind(").append(matrixCommondStr).append(")");
		REXP rb = RserveUtil.parseAndEval(conn, rbindCommond.toString());
		
		/*
		 * 执行kmeans命令
		 * cl <- kmeans(x, 2, nstart=5)
		 */
		StringBuffer kmeansCommond = new StringBuffer();
		kmeansCommond.append("kmeans_result <- kmeans(rbind_result,")
			.append(paramMap.get("centers")).append(",")
			.append("nstart=5").append(")");
		REXP rexp = RserveUtil.parseAndEval(conn, kmeansCommond.toString());
		
		/*
		 * 画图
		 * plot(x, col = cl$cluster)
		 * points(cl$centers, col = 1:2:3, pch = 8, cex = 2)
		 */
		String imgName = IdGen.uuid()+".jpeg";
		RserveUtil.generateJpeg(conn, imgName);
//		String plotCommond = "plot(rbind_result, col = kmeans_result$cluster, xlab='"+modelColumnNameArray[0]+"', ylab='"+modelColumnNameArray[1]+"')";
		String plotCommond = "plot(rbind_result, col = kmeans_result$cluster)";
		RserveUtil.parseAndEval(conn, plotCommond);
		String pointsCommond = "points(kmeans_result$centers, col=1:999, pch = 8, cex = 2)";
		RserveUtil.parseAndEval(conn, pointsCommond);
		RserveUtil.devOff(conn);
		
		/*
		 * 提取需要的结果值
		 */
		RList rList = rexp.asList();
		REXP centersRexp = rList.at("centers");
		//获取centers的列头名称
		REXPList tableRexpList = centersRexp._attr();
		RList dimnamesRList = tableRexpList.asList();
		REXP dimnamesRexp = dimnamesRList.at("dimnames");
		RList dimnamesRListList = dimnamesRexp.asList();
		String []rowNameArray = dimnamesRListList.at(0).asStrings();
		
		double [][] centers = centersRexp.asDoubleMatrix();
		String [] cluster = rList.at("cluster").asStrings();
		
//		String [][] centersStr = new String[centers.length][centers[0].length];
//		for(int i=0;i<centers.length;i++){
//			for(int j=0;j<centers[i].length;j++){
//				centersStr[i][j] = RserveUtil.keepTwoDecimal(centers[i][j]);
//			}
//		}
		
		/*
		 * 返回结果
		 */
		Map result = new HashMap();
		result.put("centers", centers);
		result.put("cluster", cluster);
		result.put("imgUrl", Global.getConfig("rserve.remote.filePath")+imgName);
		result.put("rowNameArray", rowNameArray);
		result.put("colNameArray", rowNameArray);
		
		return result;
	}
	
}
