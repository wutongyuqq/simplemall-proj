package com.simplemall.micro.serv.common.util;

import java.util.List;
import java.util.Map;


public class ValidateUtils {
	/**
	 * 如果是空或null，返回true
	 * @param validateWord
	 * @return 布尔值
	 */
	public static boolean isEmpty(String validateWord){
		return validateWord == null || validateWord.isEmpty();
	}
	
	/**
	 * 如果字符串非null也非空,返回true;否则返回flase
	 * @param validateWord
	 * @return 布尔值
	 */
	public static boolean isNotEmpty(String validateWord){
		return validateWord != null && !validateWord.isEmpty(); 
	}
	
	/**
	 * 对象不是null则返回true
	 * @param validateObj
	 * @return 布尔值
	 */
	public static boolean isNotNull(Object validateObj){
		return validateObj != null;
	}
	
	/**
	 * 对象为null则返回true
	 * @param validateObj
	 * @return 布尔值
	 */
	public static boolean isNull(Object validateObj){
		return validateObj == null;
	}
	
	/**
	 * 列表为空
	 * @param list
	 * @return
	 */
	public static boolean listIsNull(List<?> list){
		return list == null || list.isEmpty();
	}
	
	/**
	 * 列表不为空
	 * @param list
	 * @return
	 */
	public static boolean listIsNotNull(List<?> list){
		return list != null && !list.isEmpty();
	}
	
	/**
	 * map是否为空
	 * @param map
	 * @return
	 */
	public static boolean mapIsNull(Map<?,?> map){
		return map == null || map.size() < 1;
	}
	
	/**
	 * map是否不为空
	 * @param map
	 * @return
	 */
	public static boolean mapIsNotNull(Map<?,?> map){
		return (!(map == null)) && (map.size() >= 1);
	}
}
