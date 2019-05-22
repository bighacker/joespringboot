package com.joe.joespringboot.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ReadConfig {
	private static Logger logger = LoggerFactory.getLogger(ReadConfig.class);
	
	//加载配置文件 方法
	public static Properties load(String url){
		Properties prop = new Properties();
		try {
			InputStream in = ReadConfig.class.getResourceAsStream(url);
			// /加载属性列表
			prop.load(in);
		} catch (IOException e) {
			logger.error(e.getMessage());
		} 
		return prop;
	}
	
	public static void main(String[] args) {
			Properties prop = ReadConfig.load("/application-dev.properties");
			System.out.println( Boolean.parseBoolean(prop.getProperty("spring.datasource.url")) );
	}
	
}
