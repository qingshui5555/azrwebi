/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.movit.rwe.common.utils;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Cache工具类
 *
 * @author ThinkGem
 * @version 2013-5-29
 */
public class EhCacheUtils {

	private static CacheManager cacheManager = ((CacheManager) SpringContextHolder.getBean("cacheManager"));

	private static final String SYS_CACHE = "sysCache";

	/**
	 * 获取SYS_CACHE缓存
	 *
	 * @param key
	 * @return
	 */
	public static Object get(String key) {
		return get(SYS_CACHE, key);
	}

	/**
	 * 写入SYS_CACHE缓存
	 *
	 * @param key
	 * @return
	 */
	public static void put(String key, Object value) {
		put(SYS_CACHE, key, value);
	}

	/**
	 * 从SYS_CACHE缓存中移除
	 *
	 * @param key
	 * @return
	 */
	public static void remove(String key) {
		remove(SYS_CACHE, key);
	}

	/**
	 * 获取缓存
	 *
	 * @param cacheName
	 * @param key
	 * @return
	 */
	public static Object get(String cacheName, String key) {
		Element element = getCache(cacheName).get(key);
		return element == null ? null : element.getObjectValue();
	}

	/**
	 * 获取缓存
	 *
	 * @param cacheName
	 * @param keyList
	 * @return
	 */
	public static List<Object> getValuesByKeys(String cacheName, List<String> keyList) {
		List<Object> result = new ArrayList<Object>();
		Map<Object, Element> map = getCache(cacheName).getAll(keyList);
		for (Map.Entry<Object, Element> entry : map.entrySet()) {
			Element element = entry.getValue();
			if (element != null) {
				result.add(element.getObjectValue());
			}
		}
		return result;
	}

	/**
	 * 获取缓存
	 *
	 * @param cacheName
	 * @return
	 */
	public static List<Object> getValues(String cacheName) {
		List<Object> result = new ArrayList<Object>();
		Cache cache = getCache(cacheName);
		Map<Object, Element> map = cache.getAll(cache.getKeys());
		for (Map.Entry<Object, Element> entry : map.entrySet()) {
			Element element = entry.getValue();
			result.add(element.getObjectValue());
		}
		return result;
	}

	/**
	 * 获取缓存
	 *
	 * @param cacheName
	 * @return
	 */
	public static List<String> getKeys(String cacheName) {
		return getCache(cacheName).getKeys();
	}

	/**
	 * 获取缓存
	 *
	 * @param cacheName
	 * @param keys
	 * @return
	 */
	public static List<Object> getValuesWithKeys(String cacheName, String... keys) {
		List<Object> result = new ArrayList<Object>();
		Cache cache = getCache(cacheName);
		List<Object> keyList = new ArrayList<Object>();
		for (Object object : cache.getKeys()) {
			String key = (String) object;
			boolean flag = true;
			for (String keyStr : keys) {
				if (!key.toLowerCase().contains(keyStr.toLowerCase())) {
					flag = false;
					break;
				}
			}
			if (flag) {
				keyList.add(key);
			}
		}

		Map<Object, Element> map = cache.getAll(keyList);
		for (Map.Entry<Object, Element> entry : map.entrySet()) {
			Element element = entry.getValue();
			result.add(element.getObjectValue());
		}
		return result;
	}

	/**
	 * 写入缓存
	 *
	 * @param cacheName
	 * @param key
	 * @param value
	 */
	public static void put(String cacheName, String key, Object value) {
		Element element = new Element(key, value);
		getCache(cacheName).put(element);
	}

	/**
	 * 从缓存中移除
	 *
	 * @param cacheName
	 * @param key
	 */
	public static void remove(String cacheName, String key) {
		getCache(cacheName).remove(key);
	}

	/**
	 * 获得一个Cache，没有则创建一个。
	 *
	 * @param cacheName
	 * @return
	 */
	private static Cache getCache(String cacheName) {
		Cache cache = cacheManager.getCache(cacheName);
		if (cache == null) {
			cacheManager.addCache(cacheName);
			cache = cacheManager.getCache(cacheName);
			cache.getCacheConfiguration().setEternal(true);
		}
		return cache;
	}

	public static CacheManager getCacheManager() {
		return cacheManager;
	}

}
