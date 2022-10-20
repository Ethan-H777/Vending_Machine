package vendingMachineSystem.model;

import vendingMachineSystem.VendingMachine;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

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
			setupUserTable();
        } catch (SQLException e) {
        	e.printStackTrace();
        }
	}
	
	private void setupProductTable() throws SQLException {

		Statement statement = connection.createStatement();
		
		String productTableSql = """
			CREATE TABLE IF NOT EXISTS Products (
			id integer PRIMARY KEY,
			name VARCHAR(50) NOT NULL,
			category VARCHAR(50) NOT NULL,
			quantity INTEGER NOT NULL,
			price FLOAT(5,2) CHECK (price > 0) NOT NULL
 	  		)
 	  		;""";
		
		statement.execute(productTableSql);
		statement.close();
	}

	private void setupUserTable() throws SQLException {
		Statement statement = connection.createStatement();

		String UserTableSql = """
			CREATE TABLE IF NOT EXISTS Users (
			Username VARCHAR(18) PRIMARY KEY,
			Password VARCHAR(18) NOT NULL,
			Type VARCHAR(20) NOT NULL,
			CreditCardNum  INT,
			HistoryID INT    		
    		);""";

		statement.execute(UserTableSql);
		statement.close();
	}
	
	//Dummy method to show how to add values
	public void addDataProducts() throws SQLException {
		Statement statement = connection.createStatement();
		
		String productTableSql = """
			INSERT INTO Products(id, name, category, quantity, price) 
				VALUES 
					(1, 'Pebis', 'Drinks', 5, 1.10),
					(2, 'Conk', 'Drinks', 5, 1.20),
					(3, 'Spronk', 'Drinks', 5, 1.25),
					(4, 'Fronta', 'Drinks', 5, 1.20),
					(5, 'Cabdury', 'Chocolates', 5, 5.6),
					(6, 'Skites', 'Candies', 5, 4.3),
					(7, 'Prinkles', 'Chips', 5, 3),
					(8, 'Alan', 'Chocolates', 5, 1.20)
			;
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

	public void addUser(String username, String password, String type) throws SQLException {
		Statement statement = connection.createStatement();

		String userTableSql = String.format("""
				INSERT INTO Users(Username, Password, Type) 
				VALUES ('%1$s', '%2$s','%3$s');
				""", username, password, type);

		statement.execute(userTableSql);
		statement.close();

	}

	public String getPassword(String username) throws SQLException {
		Statement statement = connection.createStatement();

		String UserTableSql = String.format("""
				SELECT Password
				FROM Users
				WHERE EXISTS
				(SELECT username FROM Users WHERE username = '%1$s')
				AND
				username = '%2$s';
				""", username, username);

		ResultSet rs = statement.executeQuery(UserTableSql);
		return rs.getString("Password");

	}

	public String getUserType(String username) throws SQLException {
		Statement statement = connection.createStatement();

		String UserTableSql = String.format("""
				SELECT Type
				FROM Users
				WHERE username = '%s';
				""", username);

		ResultSet rs = statement.executeQuery(UserTableSql);
		return rs.getString("Type");

	}

	public List<Product> getAllProducts() throws SQLException{

		List<Product> ret = new ArrayList<Product>();
		Product prod;

		Statement statement = connection.createStatement();
		String productTableSql = """
			SELECT * FROM Products;
			""";

		ResultSet rs = statement.executeQuery(productTableSql);

		while (rs.next()){
			prod = new Product();
			prod.setId( rs.getInt("id") );
			prod.setName( rs.getString("name"));
			prod.setCategory( rs.getString("category"));
			prod.setQuantity( rs.getInt("quantity"));
			prod.setPrice( rs.getFloat("price"));
			ret.add(prod);
		}

		return ret;
	}

}
