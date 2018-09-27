package com.movit.rwe.modules.bi.dataanalysis.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import com.movit.rwe.modules.bi.dataanalysis.common.DataAnalysisInterface;

@Service
public class RserveClient implements ApplicationContextAware{
	
	private static Logger logger = LoggerFactory.getLogger(RserveClient.class);

    // Spring应用上下文环境  
    private ApplicationContext applicationContext;  
    /** 
     * 实现ApplicationContextAware接口的回调方法，设置上下文环境 
     *  
     * @param applicationContext 
     */  
    public void setApplicationContext(ApplicationContext applicationContext) {  
    	this.applicationContext = applicationContext;  
    }  
    /** 
     * @return ApplicationContext 
     */  
    public ApplicationContext getApplicationContext() {  
        return applicationContext;  
    }
    /** 
     * 获取对象 
     *  
     * @param name 
     * @return Object
     * @throws BeansException 
     */  
    public Object getBean(String name) throws BeansException {  
        return applicationContext.getBean(name);  
    }

//	public List<Map<String, Object>> toMapList(List<RserveResult> list,RserveResult2MapExcuter excuter ){
//		List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();
//		if(list!=null){
//			for(int i =0;i<list.size();i++){
//				Map<String, Object> mp = excuter.toMap(list.get(i));
//				resultList.add(mp);
//			}
//		}
//		return resultList;
//	}
//
//	public interface RserveResult2MapExcuter{
//		public Map<String,Object> toMap(RserveResult rserveResult);
//	}
	
	public Map calculate(HttpServletRequest request) throws Exception{
		String serviceName = request.getParameter("serviceName");
		DataAnalysisInterface dataAnalysisInterface = (DataAnalysisInterface)this.getBean(serviceName);
		return dataAnalysisInterface.execute(request);
//		//不依赖于servlet,不需要注入的方式。但是需要注意一点，在服务器启动时，Spring容器初始化时，不能通过以下方法获取Spring 容器
//	    WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
	}
}
