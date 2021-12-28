package com.rcibanque.rcidirect.services.core.config.service;

import java.util.List;

public interface ICacheService {


	List<String[]> getCacheStatistics();

	void clearCache(String pCacheName);

	void clearCache();

	List<String> getCacheNames();

}