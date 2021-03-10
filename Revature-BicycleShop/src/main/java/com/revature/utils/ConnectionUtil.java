package com.revature.utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class ConnectionUtil {

	private static ConnectionUtil cu = null;
	private static Properties properties;
	
	private ConnectionUtil() {
		properties = new Properties();
		
		
		try {
			InputStream dbProperties = ConnectionUtil.class.getClassLoader().getResourceAsStream("database.properties");
			properties.load(dbProperties);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public static synchronized ConnectionUtil getConnectionUtil() {
		if (cu == null) {
			cu = new ConnectionUtil();
		}
		return cu;
	}
	
	
	public Connection getConnection() {
		Connection conn = null;
		
		try {
			//String drv = "org.postgresql.Driver";
			//String url = "jdbc:postgresql://localhost:5432/postgres?currentSchema=bikeproject";
			//String usr = "postgres";
			//String pwd = "password";
			
			//Class.forName(properties.getProperty("drv"));
			//conn = DriverManager.getConnection(properties.getProperty("url"), properties.getProperty("usr"), properties.getProperty("pwd"));
			
			Class.forName(properties.getProperty("drv"));
			conn = DriverManager.getConnection(properties.getProperty("url"), properties.getProperty("usr"), properties.getProperty("psw"));
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return conn;
	}
}
