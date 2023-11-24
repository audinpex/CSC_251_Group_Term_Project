package csc251.team.project.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
	

	public class DatabaseConnection {
	    public static boolean VerifyDBClassLoaded() {
	        try {
	            Class.forName("org.apache.derby.jdbc.ClientDriver");
	            return true;
	        } catch (ClassNotFoundException e) {
	            return false;
	        }
	    }
	    public static Connection getConnection() {
	        try {
	    		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/car_dealership", "scott", "tiger");
	            return conn;
	        } catch (SQLException e) {
	            return null;
	        }
	    }
	 }
 