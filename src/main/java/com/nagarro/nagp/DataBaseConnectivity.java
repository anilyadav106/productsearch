package com.nagarro.nagp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DataBaseConnectivity {
	
	
	private static final String DB = "productdetails";
	private static final String USERNAME = "anildemo";
	private static final String PASSWORD = "@anildemo";
	protected static ResultSet rs = null;
	protected static Connection con = null;	
	

	
	public static  void connectDatabase() throws ClassNotFoundException {
		

		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			String connectionUrl = "jdbc:sqlserver://ANILKUMAR;databaseName="+DB+";trustServerCertificate=true;";

			con = DriverManager.getConnection(connectionUrl, USERNAME, PASSWORD);
			 
		} catch (SQLException e) {
			 System.out.println("There is some error while connecting to database> "+ e.getMessage());
			e.printStackTrace();
		}
		  
	} 

}
