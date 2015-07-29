package com.bizislife.core.service;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public interface ApiService {
	<T> T getForObjectFromHttps(String urlOverHttps, final List<String> trustedIps, Class<T> responseType) throws NoSuchAlgorithmException, KeyManagementException;

}
