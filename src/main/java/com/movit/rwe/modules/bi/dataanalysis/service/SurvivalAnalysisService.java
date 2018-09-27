package com.movit.rwe.modules.bi.dataanalysis.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.rosuda.REngine.REXP;
import org.rosuda.REngine.RList;
import org.rosuda.REngine.Rserve.RConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.movit.rwe.common.config.Global;
import com.movit.rwe.common.utils.IdGen;
import com.movit.rwe.modules.bi.dataanalysis.common.RserveConnectionFactory;
import com.movit.rwe.modules.bi.dataanalysis.entity.QueryDataFrameDto;
import com.movit.rwe.modules.bi.dataanalysis.entity.enums.SurvivalAnalysisEnum;
import com.movit.rwe.modules.bi.dataanalysis.util.CheckUtil;
import com.movit.rwe.modules.bi.dataanalysis.util.RserveUtil;

@Service
public class SurvivalAnalysisService {
	
	private static Logger logger = LoggerFactory.getLogger(SurvivalAnalysisService.class);
	@Autowired
	private DataAnalysisService dataAnalysisService;
	
	/**
	 * 执行生存分析命令
	 * 列中time、event为必须,time2为非必须
	 * @param conn R连接
	 * @param modelColumnNameArray 其中标示时间的列必须为time和time2，time为必须
	 * @param dataList 数据
	 * @param groupNameArray 组名，用于途中曲线命名
	 * @return
	 * @throws Exception
	 */
	public Map<String,String> survival(RConnection conn,String [] modelColumnNameArray, 
			List<String[]>dataList,String [] groupNameArray, Map <String,String>paramMap) throws Exception{
		//拼接R命令，组成R中的dataFrame格式
		StringBuffer dfCommond = new StringBuffer();
		dfCommond.append("survival_data_frame <- data.frame(");
		//Surv命令
		StringBuffer survCommond = new StringBuffer();
		survCommond.append("survival_y<-Surv(");
		
		for(int i=0;i<modelColumnNameArray.length;i++){
			//获取time和time2的时间列转化为double数组
			if(SurvivalAnalysisEnum.time.name().equals(modelColumnNameArray[i])||
					SurvivalAnalysisEnum.time2.name().equals(modelColumnNameArray[i])||
					SurvivalAnalysisEnum.event.name().equals(modelColumnNameArray[i])){
				double[] doubleArray = RserveUtil.stringArrayToDouble(dataList.get(i));
				conn.assign(modelColumnNameArray[i], doubleArray);
				//拼接Surv命令中的参数
				survCommond.append("survival_data_frame$").append(modelColumnNameArray[i])
				.append(",");
			}else{
				conn.assign(modelColumnNameArray[i], dataList.get(i));
			}

			//拼接data.frame命令中的参数
			dfCommond.append(modelColumnNameArray[i]).append("=").append(modelColumnNameArray[i])
			.append(",");
			
		}
		//形成dataframe命令:survival_data_frame <- data.frame(time=time,event=event,group=group)
		String dfCommondStr = dfCommond.substring(0, dfCommond.length()-1)+")";
		//执行dataframe初始化命令
		RserveUtil.parseAndEval(conn, dfCommondStr);
		//加载survival包:library(survival)
		String libraryCommond= "library(survival)";
		RserveUtil.parseAndEval(conn, libraryCommond);
				
		//surv命令：survival_y<-Surv(survival_data_frame$time,survival_data_frame$event)
		String survCommondStr = survCommond.substring(0, survCommond.length()-1)+")";
		RserveUtil.parseAndEval(conn, survCommondStr);
		
		//公式：survival_y<-Surv(survival_data_frame$time,survival_data_frame$event)
		String survFitCommond = "survival_kmfit <- survfit(survival_y~survival_data_frame$group)";
		
		RserveUtil.parseAndEval(conn, survFitCommond);
		
		
		//检验生存曲线是否有差异:survdiff(survival_y~survival_data_frame$group)
		String survDiffCommond = "survdiff(survival_y~survival_data_frame$group)";
		REXP survDiffRexp = RserveUtil.parseAndEval(conn, survDiffCommond);		
		//提取需要的结果值
		RList rList = survDiffRexp.asList();
		String chisq = RserveUtil.keepTwoDecimal(rList.at("chisq").asDouble());
		//生成jpeg：jpeg(file='D:/RWE/download/3446ad510ff34697a24dff1b44da3f94.jpeg')
		String imgName = IdGen.uuid()+".jpeg";
		RserveUtil.generateJpeg(conn, imgName);

		//画图
		//plot(survival_kmfit,lty=c('solid','dashed'),col=c('black','grey'),xlab='survival time in days',ylab='survival probabilitie')
		//legend('bottomright',c('(All patients * null)','(((null * null) * null) * null)'),lty=c('solid','dashed'),col=c('black','grey'))
//		String plotCommond = "plot(survival_kmfit,lty=c('solid','dashed'),col=c('black','grey'),"
//				+ "xlab='survival time in days',ylab='survival probabilitie')";
		String plotCommond = "plot(survival_kmfit,col=1:999,"
				+ "xlab='survival time in days',ylab='survival probabilitie')";
		RserveUtil.parseAndEval(conn, plotCommond);
//		String legendCommond = "legend('bottomright',c('PSE','PSW'),"
//				+ "lty=c('solid','dashed'),col=c('black','grey'))";
		StringBuffer legendCommond = new StringBuffer();
		legendCommond.append("legend('bottomright',c(");
		for(String groupName:groupNameArray){
			legendCommond.append("'").append(groupName).append("',");
		}
		String legendCommondStr = legendCommond.substring(0,legendCommond.length()-1);
//		legendCommondStr = legendCommondStr + "),lty=c('solid','dashed'),col=c('black','grey'))";
		legendCommondStr = legendCommondStr + "),lty=c('solid'),col=1:999)";
		String legendChisqCommond = "legend('topright',c('P.Value="+chisq+"'))";
		RserveUtil.parseAndEval(conn, legendCommondStr);
		RserveUtil.parseAndEval(conn, legendChisqCommond);
		//关闭资源:dev.off()
		RserveUtil.devOff(conn);
		
		//返回结果
		Map result = new HashMap();
		result.put("imgUrl", Global.getConfig("rserve.remote.filePath")+imgName);
		result.put("chisq", chisq);
		return result;
	}
	
	/**
	 * 自身特殊校验
	 */
	public void check() throws Exception{
		
	}
	
	public Map execute(HttpServletRequest request) throws Exception{
		//获取查询data frame参数
		QueryDataFrameDto queryDataFrameDto = dataAnalysisService.getDataFrameParam(request);
		String [] cohortIdArray = queryDataFrameDto.getCohortIds();
		String [] cohortNameArray = queryDataFrameDto.getCohortNames();
		String [] modelColumnNameArray = queryDataFrameDto.getModelColumnNames();
		List<Map> dataFrameList = dataAnalysisService.getDataFrame(queryDataFrameDto);
		//获取data analysis参数
		Map <String,String>paramMap = new HashMap<String,String>();
		//String formula = request.getParameter("dataAnalysisParam[formula]");
		
		//校验枚举中配置参数和查询出来的data frame数据
		CheckUtil.checkByEnum(SurvivalAnalysisEnum.values(),modelColumnNameArray, dataFrameList);
		//自身特殊校验
		this.check();
		
//		String userId = null;
		String userId = queryDataFrameDto.getCurrentUser().getId();
		//数据预处理
		//添加group列，由于数据库查询无法直接查询出group，所以在代码逻辑中添加
		String [] modelColumnNameNewArray = new String[modelColumnNameArray.length+1];
		for(int a=0;a<modelColumnNameArray.length;a++){
			modelColumnNameNewArray[a]=modelColumnNameArray[a];
		}
		modelColumnNameNewArray[modelColumnNameArray.length]="group";
		//拆分出需要的数据，放入对应数组
		List<String[]> dataList = RserveUtil.rowToStringColumn(modelColumnNameArray, dataFrameList);
//		List<String[]> dataList = RserveUtil.rowToStringColumn(modelColumnNameNewArray, dataFrameList);
		//添加group名称数据
		String [] groupNameArray = new String[dataFrameList.size()];
		for(int i=0;i<dataFrameList.size();i++){
			Map dataFrameMap = dataFrameList.get(i);
			String groupids = (String)dataFrameMap.get("groupids");
			for(int j=0;j<cohortIdArray.length;j++){
				String cohortId = cohortIdArray[j]+",";
				if(groupids.indexOf(cohortId)>-1){
					groupNameArray[i] = cohortNameArray[j];
				}
			}
		}
		dataList.add(groupNameArray);
		//路由对应的方法
		RConnection conn = null;
		//根据用户ID创建连接
//		conn = RserveConnectionPool.getConnection(userId);
		conn = RserveConnectionFactory.createConnection();
		
		Map result = survival(conn,modelColumnNameNewArray,dataList,cohortNameArray,paramMap);
		//关闭连接
		conn.close();
		return result;
	}
}
