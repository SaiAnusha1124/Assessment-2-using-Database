package com.atmecs.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import com.microsoft.sqlserver.jdbc.SQLServerDriver;

public class SqlConnection {
	String databaseValues;
	Connection connection = null;
	Statement statement = null;
	ResultSet result = null;

	public SqlConnection() {

		try {
			DriverManager.registerDriver(new SQLServerDriver());
			System.out.println("Driver loaded");
		} catch (SQLException e) {
			System.out.println(" driver loading problem");
		}
		try {
			connection = DriverManager.getConnection(
					"jdbc:sqlserver://ATMECSINLT-083\\SQLEXPRESS;database=Assessment2;integratedSecurity=true");
			System.out.println("connection established");
		} catch (Exception d) {
			System.out.println("connection problem");
			d.printStackTrace();
		}
	}

	public String getData(String columnName, String tableName) {
		try {
			statement = connection.createStatement();
			result = statement.executeQuery("SELECT " + columnName + " FROM " + tableName + ";");
		} catch (SQLException f) {
			System.out.println("query couldnt be executed");
			f.printStackTrace();
		}
		try {
			while (result.next()) {
				databaseValues = result.getString(1).toString();
			}
		} catch (Exception f) {
			System.out.println("result not found");
			f.printStackTrace();
		}
		return databaseValues;
	}
}
