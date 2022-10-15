package vendingMachineSystem.model;

import vendingMachineSystem.VendingMachine;

import java.sql.*;

public class Database {

	String dbUrl;
	Connection connection;
	static Database database;
	
	public static Database getInstance() {
		if (database == null)
			database = new Database();
		return database;
	}
	
	private Database() {

	}
	
	public void connect(String name) {
		dbUrl = "jdbc:sqlite:" + System.getProperty("user.dir") + "/" + name;
        try {
        	connection = DriverManager.getConnection(dbUrl);
        	setupProductTable();
        } catch (SQLException e) {
        	e.printStackTrace();
        }
	}
	
	private void setupProductTable() throws SQLException {
		Statement statement = connection.createStatement();
		
		String productTableSql = """
			CREATE TABLE IF NOT EXISTS Products (
			id integer PRIMARY KEY,
			name CHAR(50) NOT NULL    		
    		);""";
		
		statement.execute(productTableSql);
		statement.close();
	}
	
	//Dummy method to show how to add values
	public void addDataProducts() throws SQLException {
		Statement statement = connection.createStatement();
		
		String productTableSql = """
			INSERT INTO Products(name) VALUES ('Drinks');
			""";
		
		statement.execute(productTableSql);
		statement.close();
	}
	
	//Dummy method to show how to retrieve values	
	public String retrieveDataProducts() throws SQLException {
		Statement statement = connection.createStatement();
		
		String productTableSql = """
			SELECT * FROM Products;
			""";
		
		ResultSet rs = statement.executeQuery(productTableSql);
		return rs.getString("name");
		
	}
	
}
