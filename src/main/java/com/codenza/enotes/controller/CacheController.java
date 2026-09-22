package com.codenza.enotes.controller;

import java.util.Collection;

import org.springframework.cache.Cache;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.codenza.enotes.endpoints.CacheEndpoint;
import com.codenza.enotes.service.CacheManagerService;
import com.codenza.enotes.util.CommonUtil;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class CacheController implements CacheEndpoint {
	
	private final CacheManagerService cacheService;

	@Override
	public ResponseEntity<?> getAllCache() {
		Collection<String> allCache = cacheService.getAllCache();
		return CommonUtil.createBuildResponse(allCache, HttpStatus.OK) ;
	}

	@Override
	public ResponseEntity<?> getCache(String cache_name) {
		Cache cacheName = cacheService.getCacheName(cache_name);
		return CommonUtil.createBuildResponse(cacheName, HttpStatus.OK) ;
	}

	@Override
	public ResponseEntity<?> removeAllCache() {
		cacheService.removeAllCache();
		return CommonUtil.createBuildResponseMessage("All cache removed..",HttpStatus.OK);
	}

}
