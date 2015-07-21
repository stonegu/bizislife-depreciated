package com.bizislife.core.configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Properties;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

@Configuration
public class ApplicationConfiguration {
	
	private static final Logger logger = LoggerFactory.getLogger(ApplicationConfiguration.class);
	
	/**
	 * Loaded config file.
	 */
	private Properties config;


	@Autowired 
	private ApplicationContext appContext;
	
	@PostConstruct
	protected void init() {
		try {
			Resource res = appContext.getResource("classpath:GlobalConfiguration.properties");
			if (res==null) throw new IllegalArgumentException("Cannot find configuration file: classpath:GlobalConfiguration.properties");
			InputStream in = res.getInputStream();
			Reader r = new InputStreamReader(in,"UTF-8");
			Properties props = new Properties();
			props.load(r);
			r.close();

			config = props;
			
		} catch (java.io.IOException e) {
			throw new IllegalArgumentException("Cannot read GlobalConfiguration.propertires", e);
		}
		
	}
	
	// use this for testing - pass it the filename of the properties file
	public void initFromFile (File filename) {
		try {
			InputStream in =new FileInputStream(filename);
			Reader r = new InputStreamReader(in,"UTF-8");
			Properties props = new Properties();
			props.load(r);
			r.close();

			config = props;
		} catch (java.io.IOException e) {
			throw new IllegalArgumentException("Cannot read GlobalConfiguration.propertires", e);
		}
	}
	
    public String getHostName(){
    	return config.getProperty("hostname");
    }
	
	

}
