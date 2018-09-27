package com.movit.rwe.modules.bi.da.test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.rosuda.REngine.Rserve.RConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.movit.rwe.common.utils.I18NUtils;
import com.movit.rwe.modules.bi.da.enums.ParamSetTypeEnum;
import com.movit.rwe.modules.bi.da.test.dataframe.DataFrameInvoke;
import com.movit.rwe.modules.bi.da.test.dataframe.DataFrameResults;
import com.movit.rwe.modules.bi.da.test.rresult.DaException;
import com.movit.rwe.modules.bi.da.test.rresult.RResult;
import com.movit.rwe.modules.bi.da.test.set.ConhortChosen;
import com.movit.rwe.modules.bi.da.test.set.DataModelParamChosen;
import com.movit.rwe.modules.bi.da.test.set.TestExtendAtt;
import com.movit.rwe.modules.bi.dataanalysis.common.RserveConnectionFactory;
import com.movit.rwe.modules.bi.dataanalysis.service.CorTestService;

/**
 * a)必须选择一个指标，必须选择两个组
 * b)每组数据量必须大于2，否则提示：错误信息：每组的数据量必须大于2
 * c)两组数据量必须相等，如果不相等，则按照数据量小的那组的数据量，到数据量大的那组中随机抽取数据，如A(1, 2, 3) B(1, 2, 3, 4, 5, 6, 7)，处理后结果为A(1, 2, 3) B(2,3,7)
 */
@Service(value="da_test_cortest")
public class CorTest extends TestAbs{

	@Autowired
	private CorTestService corTestService;
	@Autowired
	private I18NUtils i18NUtils;
	
	@Override
	public String getTestName() {
		return "CorTest";
	}

	@Override
	public int getParamCountLimitLeft() {
		return 1;
	}

	@Override
	public int getParamCountLimitRight() {
		return 1;
	}

	@Override
	public 	int getCohortCountLimitLeft() {
		return 2;
	}

	@Override
	public 	int getPCohortCountLimitRight() {
		return 2;
	}
	
	@Override
	public String getTestExtendAttViewModelUrl() {
		return "modules/bi/da/extendAtt/cortest";
	}

	@Override
	public String getTestResultViewModelUrl() {
		return "modules/bi/da/result/cortest";
	}

	@Override
	public ParamSetTypeEnum getParamSetType() {
		return ParamSetTypeEnum.PARAMSET_DEFAULT;
	}

	@Override
	public 	String dataModelParamsChosenCheck(List<DataModelParamChosen> chosenParams) {
		return null;
	}

	@Override
	public String cohortsChosenCheck(ConhortChosen conhortChosen) {
		return null;
	}

	@Override
	public RResult testInvoke(DataFrameResults results, TestExtendAtt eAtt,DataFrameInvoke invoke) {
		try {
			List <double[]>dataList = testHelper.handleSimpleTestParam(results);
			
			for(int i=0;i<dataList.size();i++){
				//数据长度必须大于2
				if(dataList.get(i).length<=1){
					throw new DaException(i18NUtils.get("da.exception.theamountofdata")+" "+i18NUtils.get("da.exception.mustbemorethan", "2"),"",null);
				}
			}
			
			//如果两组数据的数据量是否相等，则按照数据量小的那组的数据量，到数据量大的那组中随机抽取数据
			double []x = dataList.get(0);
			double []y = dataList.get(1);
			if(x.length!=y.length){
				Set<Integer> set = null;
				int index = 0;
				if(x.length<y.length){
					double [] temp = new double[x.length];
					set = getRandomInt(y.length);
					Iterator<Integer> iterator = set.iterator();
					while(iterator.hasNext()){
						Integer setIndex = iterator.next();
						if(index<x.length){
							temp[index] = y[setIndex];
						}
						index++;
					}
					dataList.set(1, temp);
				}else{
					double [] temp = new double[y.length];
					set = getRandomInt(x.length);
					Iterator<Integer> iterator = set.iterator();
					while(iterator.hasNext()){
						Integer setIndex = iterator.next();
						if(index<y.length){
							temp[index] = x[setIndex];
						}
						index++;
					}
					dataList.set(0, temp);
				}
			}
			
			//R连接
			RConnection conn = RserveConnectionFactory.createConnection();
	
			//参数转化
			String method = eAtt.get("method").toString();
			String alternative = eAtt.get("alternative").toString();
			String confLevel = eAtt.get("confLevel").toString();
			
			Map <String,String>paramMap = new HashMap<String,String>();
			paramMap.put("method", method);
			paramMap.put("alternative", alternative);
			paramMap.put("confLevel", confLevel);
			
			//执行方法
			Map result = corTestService.corTest(conn, dataList, paramMap);
			result.put("dataList", dataList);
			//返回结果
			RResult rResult = new RResult();
			rResult.setResultMap(result);
			
			//关闭连接
			conn.close();
			
			return rResult;
		
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
	}

	@Override
	public TestExtendAtt getTestExtendAtt(HttpServletRequest request) {
		String method = request.getParameter("extAtt[method]");
		String alternative = request.getParameter("extAtt[alternative]");
		String confLevel = request.getParameter("extAtt[confLevel]");
		
		TestExtendAtt att = new TestExtendAtt();
		att.put("method", method);
		att.put("alternative", alternative);
		att.put("confLevel", confLevel);
		
		return att;
	}

	/**
	 * 获取0-N个不重复的随机整数
	 * @param n 需要生成的个数
	 * @return
	 */
	public Set<Integer> getRandomInt(int n){
		Set<Integer> set= new LinkedHashSet<Integer>();
		Random random=new Random();
		while(set.size()<n){
			int randomNum = random.nextInt(n);
			set.add(randomNum);
		}
		return set;
	}
}
