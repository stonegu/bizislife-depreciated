package com.bizislife.core.service;

import org.apache.http.impl.client.HttpClients;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.*;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Service
public class ApiServiceImpl implements ApiService {

	@Override
	public <T> T getForObjectFromHttps(String urlOverHttps, final List<String> trustedIps, Class<T> responseType) throws NoSuchAlgorithmException, KeyManagementException {
		
        SSLContext sc = SSLContext.getInstance("TLS");
        sc.init(null, new TrustManager[] { new X509TrustManager() {
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return null;
            }

            public void checkClientTrusted(
                    java.security.cert.X509Certificate[] certs, String authType) {
            }

            public void checkServerTrusted(
                    java.security.cert.X509Certificate[] certs, String authType) {
            }
        } }, null);

        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setHttpClient(
        	HttpClients.custom().setSSLHostnameVerifier(
        		new HostnameVerifier() {
		            @Override
		            public boolean verify(String hostname, SSLSession session) {
		                if (trustedIps.contains(hostname)) {
		                    return true;
		                }
		                return false;
		            }
		        }
        	)
	        .setSslcontext(sc).build());
        RestTemplate restTemplate = new RestTemplate(requestFactory);
		return restTemplate.getForObject(urlOverHttps, responseType);
	}

}
