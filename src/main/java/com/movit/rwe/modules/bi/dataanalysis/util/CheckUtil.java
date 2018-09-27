package com.movit.rwe.modules.bi.dataanalysis.util;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.movit.rwe.modules.bi.dataanalysis.common.DataAnalysisEnumInterface;
import com.movit.rwe.modules.bi.dataanalysis.entity.enums.SurvivalAnalysisEnum;

public class CheckUtil {
	
	/**
	 * 校验
	 * @param modelColumnNameArray
	 * @param dataFrameList
	 * @throws Exception
	 */
	public static void checkByEnum(Enum[] enumArray,String [] modelColumnNameArray,List<Map> dataFrameList ) throws Exception{
		if(modelColumnNameArray==null){
			throw new Exception("参数modelColumnNameArray为空");
		}
		if(dataFrameList==null){
			throw new Exception("参数dataFrameList为空");
		}
		
		for(Enum saenum:enumArray){
			String enumName = saenum.name();
			DataAnalysisEnumInterface daei = (DataAnalysisEnumInterface)saenum;
			String type = daei.getType();
			boolean isNotNull = daei.isNotNull();
			boolean isRequiredInDataFrame = daei.isRequiredInDataFrame();
			boolean modelRequiredflag = false;
//			boolean dataRequiredflag = false;
			//取数据集中的第一条数据进行列的核对
			Map firstMap = dataFrameList.get(0);
			//不为空校验
			if(isNotNull){
				if(StringUtils.isBlank(daei.getValue())){
					throw new Exception("枚举["+enumName+"]value值不能为空");
				}
			}
//			if(!"serviceName".equals(enumName)&&
//					!"jspName".equals(enumName)){
				if(isRequiredInDataFrame){
					for(String str:modelColumnNameArray){
						if(str.equals(enumName)){
							modelRequiredflag = true;
							break;
						}
					}
					if(!modelRequiredflag){
						throw new Exception("选择的列中缺少["+enumName+"]列！");
					}
					//校验选择的列在数据中是否都含有
					
					for(String column:modelColumnNameArray){
						if(!firstMap.containsKey(column)){
							throw new Exception("data frame中缺少页面选择列["+column+"]");
						}
					}
					
					if("string".equals(type)||"number".equals(type)){
						for(int l=0;l<dataFrameList.size();l++){
							Map map = dataFrameList.get(l);
							String value = null;
							try{
								value = map.get(enumName).toString();
							}catch(Exception e){
								throw new Exception("DataFrame中第["+(l+1)+"]行,["+enumName+":"+map.get(enumName)+"]为非字符串!");
							}
							try{
								Double.parseDouble(value);
							}catch(Exception e){
								throw new Exception("DataFrame中第["+(l+1)+"]行,["+enumName+":"+map.get(enumName)+"]为非数字!");
							}
						}
					}
					
				}else{
					//如果在dataframe中为非必须，但是data frame中又有该列
					if(firstMap.containsKey(enumName)){
						if("string".equals(type)||"number".equals(type)){
							for(int l=0;l<dataFrameList.size();l++){
								Map map = dataFrameList.get(l);
								String value = null;
								try{
									value = map.get(enumName).toString();
								}catch(Exception e){
									throw new Exception("DataFrame中第["+(l+1)+"]行,["+enumName+":"+map.get(enumName)+"]为非字符串!");
								}
								try{
									Double.parseDouble(value);
								}catch(Exception e){
									throw new Exception("DataFrame中第["+(l+1)+"]行,["+enumName+":"+map.get(enumName)+"]为非数字!");
								}
							}
						}
					}
					
				}
				
//			}
		}
	}
}
