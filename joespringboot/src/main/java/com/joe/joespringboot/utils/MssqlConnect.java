package com.joe.joespringboot.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MssqlConnect {
	private MssqlConnect() {}
	
	public static Connection getConnection(String prefix) {
        return SingletonInstance.connectionMap.get(prefix);
    }
	
	public static void closeConnection() {
		Set<Entry<String, Connection>> set = SingletonInstance.connectionMap.entrySet();
		for (Entry<String, Connection> entry : set) {
			closeConnection(entry.getKey());
		}
	}
	
	public static void closeConnection(String prefix) {
		try {
			SingletonInstance.connectionMap.get(prefix).close();
			System.out.println("------------------> " + prefix + " 连接已关闭");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	
	private static class SingletonInstance{
		private static final Pattern PASSWORD_REGEX_PATTERN = Pattern.compile("(Password|Pwd)=(.*);");
		
        static Map<String, Connection> connectionMap = new HashMap<String, Connection>();
        
        static{
        	initConnection();
        }
        
        public static void initConnection() {
            Properties properties = ReadConfig.load("application-dev.properties");
//            String[] list = properties.getProperty("jdbclist").split(",");
            
            //创建与MySQL数据库的连接类的实例
    		try {
//    			for (String string : list) {
                	String url = properties.getProperty("spring.datasource.url");
                    String username = properties.getProperty("spring.datasource.username");
                    String password = properties.getProperty("spring.datasource.password");
                    Class.forName(properties.getProperty("spring.datasource.driver-class-name"));
                    if (url.equalsIgnoreCase("specialurl")) {
                    	password = getPwd(SqlDbUtils.decodeConnectionString("Password=("+password+");"));
                    }
                    Connection conn = DriverManager.getConnection(url, username, password);
                    connectionMap.put(url, conn);
                    System.out.println("------------------> 初始化 " + url + " 连接成功");
//    			}
                
    		} catch (ClassNotFoundException e1) {
    			e1.printStackTrace();
    		} catch (Exception e) {
    			e.printStackTrace();
    		}
        }
        
        private static String getPwd(String connectionString) throws Exception{
            Matcher matcher = PASSWORD_REGEX_PATTERN.matcher(connectionString);

            final int pwdGroup = 2;

            while (matcher.find()) {

                String password = matcher.group(pwdGroup);

                if (password == null || password.isEmpty()) {
                    continue;
                }

                if (password.startsWith("'") && password.endsWith("'")) {
                    password = password.substring(1, password.length() - 1);
                }
                return  password;
            }
            return "";
        }
        
    }
	
	public static void main(String[] args) {}
    
}