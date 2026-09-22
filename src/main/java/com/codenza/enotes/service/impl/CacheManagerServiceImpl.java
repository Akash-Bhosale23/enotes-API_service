package com.codenza.enotes.service.impl;

import java.util.Collection;
import java.util.List;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import com.codenza.enotes.service.CacheManagerService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CacheManagerServiceImpl implements CacheManagerService{
	
	private final CacheManager cacheManager;
	
	@Override
	public Collection<String> getAllCache() {
	
		Collection<String> cacheNames = cacheManager.getCacheNames();
		
		for(String cacheName:cacheNames) {
			Cache cache = cacheManager.getCache(cacheName);
			log.info("Cache name: {}",cache);
		}
		return cacheNames;
		
	}

	@Override
	public Cache getCacheName(String cacheName) {

		Cache cache = cacheManager.getCache(cacheName);
		log.info("Cache name: {}",cache);
		return cache;
	}

	@Override
	public void removeAllCache() {

		Collection<String> cacheNames = cacheManager.getCacheNames();
		
		for(String cacheName:cacheNames) {
			Cache cache = cacheManager.getCache(cacheName);
			log.info("Cache name: {}",cache);
			cache.clear();
		}
	}

	@Override
	public void removeCacheByName(List<String> cacheNames) {
		
		for(String cacheName:cacheNames) {
			Cache cache = cacheManager.getCache(cacheName);
			log.info("Cache name: {}",cache);
			cache.clear();
		}
	}
	
	

}
