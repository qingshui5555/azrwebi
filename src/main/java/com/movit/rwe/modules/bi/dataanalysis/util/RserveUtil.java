package com.movit.rwe.modules.bi.dataanalysis.util;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.rosuda.REngine.REXP;
import org.rosuda.REngine.Rserve.RConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.movit.rwe.common.config.Global;
import com.movit.rwe.common.utils.I18NUtils;
import com.movit.rwe.common.utils.SpringContextHolder;

public class RserveUtil {
	
	private static Logger logger = LoggerFactory.getLogger(RserveUtil.class);
	private static I18NUtils i18NUtils = (I18NUtils)SpringContextHolder.getBean("i18NUtils");
	
	/**
	 * 捕获R命令执行异常的工具方法
	 * @param conn R连接
	 * @param command 运行的R命令
	 * @return 
	 * @throws Exception
	 */
	public static REXP parseAndEval(RConnection conn,String command) throws Exception {
		String commondStr = "try("+command+",silent=TRUE)";
		REXP rexp = conn.parseAndEval(commondStr);
		//捕获执行命令的异常
		if (rexp.inherits("try-error")){
			logger.error("执行R命令异常："+rexp.asString());
//			throw new Exception("执行R命令["+command+"]异常："+rexp.asString());
			throw new Exception(i18NUtils.get("da.exception.anexceptionoccurred", command,rexp.asString()));
		}
		return rexp;
	}
	
	/**
	 * 生成jpeg图片
	 * @param conn
	 * @param imgName
	 * @throws Exception
	 */
	public static void generateJpeg(RConnection conn,String imgName) throws Exception{
		//图片默认480*480px
		String jpegCommond = "jpeg(file='"+Global.getConfig("rserve.local.filePath")+imgName+"',width=500,height=400)";
		//解决Error in .External2(C_X11, paste("jpeg::", quality, ":", filename, sep = ""),  : 无法打开JPEG设备
		//当出现这种错误执行另外的命令
		try{
			RserveUtil.parseAndEval(conn, jpegCommond);
		}catch(Exception e){
			if(e.toString().indexOf("无法打开JPEG设备")!=-1){
				String libraryCairoCommond = "library(Cairo)";
				RserveUtil.parseAndEval(conn, libraryCairoCommond);
//				String fontsCommond = "CairoFonts(regular = 'WenQuanYi Micro Hei',bold = 'WenQuanYi Micro Hei')";
//				RserveUtil.parseAndEval(conn, fontsCommond);
				String cairoJPEGCommond = "CairoJPEG(file='"+Global.getConfig("rserve.local.filePath")+imgName+"',width=500,height=400)";
				RserveUtil.parseAndEval(conn, cairoJPEGCommond);
			}else{
				throw new Exception(e);
			}
		}
		//边距默认从bottom, left, top, right分别为5,4,4,2
		String parCommond = "par(family = 'WenQuanYi Micro Hei',mar=c(5,4,0,2)+0.1)";
		RserveUtil.parseAndEval(conn, parCommond);
	}
	
	/**
	 * 关闭资源
	 * @param conn
	 * @throws Exception
	 */
	public static void devOff(RConnection conn) throws Exception{
		String devOffCommond = "dev.off()";
		RserveUtil.parseAndEval(conn, devOffCommond);
	}
	
	/**
	 * 将pr值根据范围（Signif. codes:  0 '***' 0.001 '**' 0.01 '*' 0.05 '.' 0.1 ' ' 1）得出对应的符号
	 * @param pr
	 * @return
	 */
	public static String[] getSignifResults(double []pr){
		String []signifResults = new String[pr.length];
		for(int i=0;i<pr.length;i++){
			double d = pr[i];
				if(d>=0&&d<=0.001){
					signifResults[i]="***";
				}else if(d>0.001&&d<=0.01){
					signifResults[i]="**";
				}else if(d>0.01&&d<=0.05){
					signifResults[i]="*";
				}else if(d>0.05&&d<=0.1){
					signifResults[i]=".";
				}else if(d>0.1&&d<=1){
					signifResults[i]=" ";
				}else{
					signifResults[i]="";
				}
		}
		return signifResults;
	}
	
	/**
	 * 将一维double类型数组转化为一维String类型数组
	 * @param doubleArray
	 * @return
	 */
	public static String[] doubleArrayToString(double []doubleArray){
		String[] stringArray = new String[doubleArray.length];
		for(int i=0;i<doubleArray.length;i++){
			stringArray[i] = String.valueOf(doubleArray[i]);
		}
		return stringArray;
	}
	
	/**
	 * 将二维double类型数组转化为二维String类型数组
	 * @param doubleArray
	 * @return
	 */
	public static String[][] doubleArrayToString(double [][]doubleArray){
		String[][] stringArray = new String[doubleArray.length][doubleArray[0].length];
		for(int i=0;i<doubleArray.length;i++){
			for(int j=0;j<doubleArray[i].length;j++){
				stringArray[i][j] = String.valueOf(doubleArray[i][j]);
			}
		}
		return stringArray;
	}
	
	/**
	 * 将一维String类型数组转化为一维double类型数组
	 * @param doubleArray
	 * @return
	 */
	public static double[] stringArrayToDouble(String []stringArray){
		double[] doubleArray = new double[stringArray.length];
		for(int i=0;i<stringArray.length;i++){
			doubleArray[i] = Double.parseDouble(stringArray[i]);
		}
		return doubleArray;
	}
	
	/**
	 * 将一维object类型数组转化为一维double类型数组
	 * @param stringArray
	 * @return
	 */
	public static double[] objectArrayToDoubleArray(Object []objectArray){
		double[] doubleArray = new double[objectArray.length];
		for(int i=0;i<objectArray.length;i++){
			doubleArray[i] = Double.parseDouble(objectArray[i].toString());
		}
		return doubleArray;
	}
	
	/**
	 * 将一维object类型数组转化为一维string类型数组
	 * @param stringArray
	 * @return
	 */
	public static String[] objectArrayToStringArray(Object []objectArray){
		String[] stringArray = new String[objectArray.length];
		for(int i=0;i<objectArray.length;i++){
			stringArray[i] = objectArray[i].toString();
		}
		return stringArray;
	}
	
	/**
	 * 将List<Map>行数据按照colName顺序转化为列数据List<double[]>形式
	 * @param colName 列名
	 * @param list 数据
	 * @return
	 * @throws Exception
	 */
	public static List<double[]> rowToDoubleColumn(String[]colName, List<Map> data) throws Exception{
		if(colName==null||colName.length==0){
			throw new Exception("参数colName不能为空!");
		}
		if(data==null||data.size()==0){
			throw new Exception("参数list不能为空!");
		}
//		Map value = new HashMap <String,double[]>();
		List result = new ArrayList <double[]>();
		
		for(String key:colName){
			double []array = new double[data.size()];
			for(int i=0;i<data.size();i++){
				HashMap hm = (HashMap)data.get(i);
				double d ;
				try{
					d = Double.parseDouble(hm.get(key).toString());
				}catch(Exception e){
					throw new Exception("第["+(i+1)+"]行,["+key+":"+hm.get(key)+"]数据异常!");
				}
				array[i]=d;
			}
//			value.put(key, array);
			result.add(array);
		}
		return result;
	}
	
	/**
	 * 将List<Map>行数据按照colName顺序转化为列数据List<double[]>形式
	 * @param colName 列名
	 * @param list 数据
	 * @return
	 * @throws Exception
	 */
	public static List<String[]> rowToStringColumn(String[]colName, List<Map> data) throws Exception{
		if(colName==null||colName.length==0){
			throw new Exception("参数colName不能为空!");
		}
		if(data==null||data.size()==0){
			throw new Exception("参数list不能为空!");
		}
//		Map value = new HashMap <String,double[]>();
		List result = new ArrayList <double[]>();
		
		for(String key:colName){
			String []array = new String[data.size()];
			for(int i=0;i<data.size();i++){
				HashMap hm = (HashMap)data.get(i);
				String d = null;
				try{
					d = hm.get(key).toString();
				}catch(Exception e){
					throw new Exception("第["+(i+1)+"]行,["+key+":"+hm.get(key)+"]数据异常!");
				}
				array[i]=d;
			}
//			value.put(key, array);
			result.add(array);
		}
		return result;
	}
	
	/**
	 * 保留两位小数
	 * 如果四舍五入后结果小于小于0.01，则输出“<0.01”
	 * @param value
	 * @return
	 */
	public static String keepTwoDecimal(double value){
		DecimalFormat df=new DecimalFormat("0.00");
		df.setRoundingMode(RoundingMode.HALF_UP);
//		NumberFormat df=NumberFormat.getNumberInstance();
//		df.setMaximumFractionDigits(2);
//		df.setRoundingMode(RoundingMode.HALF_UP);
		if(Double.isNaN(value)){
			return "";
		}
		if(value==0){
			return "0";
		}
		String result = df.format(value);
		double d = Double.parseDouble(result);
		if(value>0){
			if(d>=0&&d<0.01){
				return "<0.01";
			}
		}else{
			if(d>-0.01&&d<=0){
				return ">-0.01";
			}
		}
		return result;
	}
	
	/**
	 * 将字符串数组进行分组并输出分组后的数量
	 * @param array
	 * @return
	 */
	public static HashMap<String,Integer> arrayGroup(String[] array){
		HashMap<String,Integer> result = new HashMap<String,Integer>();
		for(int i=0;i<array.length;i++){
			if(result.containsKey(array[i])){
				int value = result.get(array[i])+1;
				result.put(array[i], value);
			}else{
				result.put(array[i], 1);
			}
		}
		return result;
	}
	public static void main(String[]args){
//		String [][]s;
//		s = doubleArrayToString(new double[][]{{1,2,3,4,5},{2,2,2,1,3},{3,2,3,2,1}});
//		System.out.println(keepTwoDecimal(0.0049));
		
		//System.out.println(arrayGroup(new String[]{"1","4","2","2","4","6","7","1","1","7"}));
//		String [] a = getSignifResults(new double[]{0,0.001,0.01,0.05,0.1,1});
//		for(String s:a){
//			System.out.println(s);
//		}
//		System.out.println(-0.01>-0.1);
		System.out.println(keepTwoDecimal(0.475f));
	}
}
