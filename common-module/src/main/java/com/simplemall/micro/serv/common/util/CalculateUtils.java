package com.simplemall.micro.serv.common.util;

import java.math.BigDecimal;

/**
 * 计算工具类
 * @author gbf
 *
 */
public class CalculateUtils {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}
	/**
	 * 加法 
	 * @param v1
	 * @param v2
	 */
	public static String add(String v1, String v2){
		 BigDecimal b1 = new BigDecimal(v1);
	     BigDecimal b2 = new BigDecimal(v2);
	     //BigDecimal c= b1.add(b2);
	     return b1.add(b2).toString();  
	}
	
//	/**
//	 * 加法 
//	 * @param d1
//	 * @param d2
//	 */
//	public static Double add(Double d1,Double d2){
//		 BigDecimal b1 = new BigDecimal(d1);  
//	     BigDecimal b2 = new BigDecimal(d2);  
//	     //BigDecimal c= b1.add(b2);
//	     return b1.add(b2).doubleValue();  
//	}
	
	
	/**
	 * 减法 
	 * @param v1 被减数
	 * @param v2 减数
	 */
	public static String subtract(String v1, String v2){
		 BigDecimal b1 = new BigDecimal(v1);
	     BigDecimal b2 = new BigDecimal(v2);
	     //BigDecimal c= b1.add(b2);
	     return b1.subtract(b2).toString();  
	}
	
//	/**
//	 * 减法 
//	 * @param d1 被减数
//	 * @param d2 减数
//	 */
//	public static Double subtract(Double d1,Double d2){
//		 BigDecimal b1 = new BigDecimal(d1);  
//	     BigDecimal b2 = new BigDecimal(d2);  
//	     //BigDecimal c= b1.add(b2);
//	     return b1.subtract(b2).doubleValue();  
//	}
	
	
	/**
	 * 乘法 
	 * @param v1 被乘数
	 * @param v2 乘数
	 * @param scale 保留的小数点位数
	 */
	public static String multiply(String v1, String v2, int scale){
		 BigDecimal b1 = new BigDecimal(v1);
	     BigDecimal b2 = new BigDecimal(v2);
	     return b1.multiply(b2).setScale(scale, BigDecimal.ROUND_HALF_UP).toString();
	}
	
//	/**
//	 * 乘法 
//	 * @param d1 被乘数
//	 * @param d2 乘数
//	 * @param scale 保留的小数点位数
//	 */
//	public static Double multiply(Double d1,Double d2,int scale){
//		 BigDecimal b1 = new BigDecimal(d1);  
//	     BigDecimal b2 = new BigDecimal(d2);  
//	     return b1.multiply(b2).setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();  
//	}
	
	
	/**
	 * 除法 
	 * @param v1 被除数
	 * @param v2 除数
	 * @param scale 保留的小数点位数
	 */
	public static String divide(String v1, String v2, int scale){
		 BigDecimal b1 = new BigDecimal(v1);
	     BigDecimal b2 = new BigDecimal(v2);
	     return b1.divide(b2,scale, BigDecimal.ROUND_HALF_UP).toString();
	}
	
//	/**
//	 * 除法 不要double类型 返回是防止结果太小 ,系统用科学计数法表示结果
//	 * @param d1 被除数
//	 * @param d2 除数
//	 * @param scale 保留的小数点位数
//	 */
//	public static Double divide(Double d1,Double d2,int scale){
//		BigDecimal b1 = new BigDecimal(d1);  
//	    BigDecimal b2 = new BigDecimal(d2);  
//	    return b1.divide(b2,scale,BigDecimal.ROUND_HALF_UP).doubleValue();  
//	}

}
