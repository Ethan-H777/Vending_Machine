package vendingMachineSystem.model;


//import org.json.simple.parser.JSONParser;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
			//dropTable("Users");
			setupUserTable();
			//addDefaultUsers();
			setUpChangeTable();
			setupTransactionTable();
			setupTransactionProductsTable();
			//dropTable("CreditCardList");
			setupCreditCardList();
			//importJsonFile();
			setupCreditCardStored();
        } catch (SQLException e) {
        	e.printStackTrace();
        } catch (Exception e) {
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

	private void setUpChangeTable() throws  SQLException {
		Statement statement = connection.createStatement();

		String changeTableSql = """
			CREATE TABLE IF NOT EXISTS Changes (
			name VARCHAR(10) PRIMARY KEY,
			value FLOAT(5,2) NOT NULL, 
			quantity INTEGER NOT NULL
 	  		)
 	  		;""";

		statement.execute(changeTableSql);
		statement.close();
	}

	private void setupUserTable() throws SQLException {
		Statement statement = connection.createStatement();

		String UserTableSql = """
			CREATE TABLE IF NOT EXISTS Users (
			Username VARCHAR(18) PRIMARY KEY,
			Password VARCHAR(18) NOT NULL,
			Type VARCHAR(20) NOT NULL	
    		);""";

		statement.execute(UserTableSql);
		statement.close();
	}
	
	private void setupTransactionTable() throws SQLException {
		Statement statement = connection.createStatement();

		//I got a bit lazy with the user field, I used a string instead of FK so we don't have
		//to query the user table to get the user id.
		String TransactionTableSql = """
			CREATE TABLE IF NOT EXISTS Transactions (
			id INTEGER PRIMARY KEY,
			User VARCHAR(18),
			Successful INT,
			Date DATETIME DEFAULT CURRENT_TIMESTAMP,
			Money_paid FLOAT(5,2) DEFAULT 0,
			Returned_change FLOAT(5,2) DEFAULT 0,
			Payment_method VARCHAR(10) DEFAULT NULL,
			Cancelled_reason VARCHAR(20) 
    		);""";

		statement.execute(TransactionTableSql);
		statement.close();
	}
	
	private void setupTransactionProductsTable() throws SQLException {
		Statement statement = connection.createStatement();

		String TransactionProductsTableSql = """
			CREATE TABLE IF NOT EXISTS TransactionProducts (
			TransactionId INTEGER,
			Product INTEGER,
			Quantity INT NOT NULL,
			FOREIGN KEY (TransactionId) REFERENCES Transactions(id),
			FOREIGN KEY (Product) REFERENCES Products(id)
    		);""";

		statement.execute(TransactionProductsTableSql);
		statement.close();
	}

	public void addDefaultUsers() throws SQLException {
		Statement statement = connection.createStatement();

		String changeTableSql = """
			INSERT INTO Users(Username, Password, Type) 
				VALUES 
					('billyowner', 'owner123', 'OWNER'),
					('billyseller', 'seller123', 'SELLER'),
					('billycashier', 'cashier123', 'CASHIER'),
					('billy', '123456', 'CUSTOMER')
			;
			""";

		statement.execute(changeTableSql);
		statement.close();
	}

	public void addDataChanges() throws SQLException {
		Statement statement = connection.createStatement();

		String changeTableSql = """
			INSERT INTO Changes(name, value, quantity) 
				VALUES 
					('$100', '100.00', 5),
					('$50', '50.00', 5),
					('$20', '20.00', 5),
					('$10', '10.00', 5),
					('$5', '5.00', 5),
					('$2', '2.00', 5),
					('$1', '1.00', 5),
					('50c', '0.50', 5),
					('20c', '0.20', 5),
					('10c', '0.10', 5),
					('5c', '0.05', 5)
			;
			""";

		statement.execute(changeTableSql);
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
		String name = rs.getString("name");
		statement.close();
		return name;
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
		String password = rs.getString("Password");
		statement.close();
		return password;

	}

	public String getUserType(String username) throws SQLException {
		Statement statement = connection.createStatement();

		String UserTableSql = String.format("""
				SELECT Type
				FROM Users
				WHERE username = '%s';
				""", username);

		ResultSet rs = statement.executeQuery(UserTableSql);
		String type = rs.getString("Type");
		statement.close();
		return type;

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

		statement.close();
		return ret;
	}

	public void updateItemByID(String id, String name, String category, String price, String qty) throws SQLException{
		Statement statement = connection.createStatement();
		String productTableSql = String.format(
				"UPDATE Products SET name='%s', category='%s', price=%s, quantity=%s WHERE id=%s;"
				, name, category, price, qty, id
		);

		statement.execute(productTableSql);
		statement.close();
	}

	public void updateItemID(String name, String newID) throws  SQLException{
		Statement statement = connection.createStatement();
		String productTableSql = String.format(
				"UPDATE Products SET id=%s WHERE name='%s';"
				, newID, name
		);

		statement.execute(productTableSql);
		statement.close();
	}

	public ArrayList<Change> getAllChanges() throws SQLException{

		ArrayList<Change> changes = new ArrayList<Change>();
		Change change;

		Statement statement = connection.createStatement();
		String productTableSql = """
			SELECT * FROM Changes;
			""";

		ResultSet rs = statement.executeQuery(productTableSql);

		while (rs.next()){
			change = new Change();
			change.setName( rs.getString("name") );
			change.setValue( rs.getFloat("value"));
			change.setQty( rs.getInt("quantity"));
			changes.add(change);
		}

		statement.close();
		return changes;
	}

	public void updateChangeQty(String name, String newQty) throws SQLException {
		Statement statement = connection.createStatement();
		String changeTableSql = String.format(
				"UPDATE Changes SET Quantity=%s WHERE Name='%s';", newQty, name
				);
		
		statement.execute(changeTableSql);
		statement.close();

	}
	
	public void addFailedTransaction(String user, String reason) throws SQLException {
		String sql = """
		INSERT INTO Transactions (User, Successful, Cancelled_reason)
		VALUES (?, FALSE, ?)""";
		
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, user);
		statement.setString(2, reason);
		statement.execute();
		statement.close();
	}

	void productsDrop() throws SQLException{
		Statement statement = connection.createStatement();

		String productTableSql = """
			DROP TABLE IF EXISTS Products
 	  		;""";

		statement.execute(productTableSql);
		statement.close();
	}

	void changesDrop() throws SQLException{
		Statement statement = connection.createStatement();

		String productTableSql = """
			DROP TABLE IF EXISTS Changes
 	  		;""";

		statement.execute(productTableSql);
		statement.close();
	}



	void doStatement(String cmd) throws SQLException{
		Statement statement = connection.createStatement();
		statement.execute(cmd);
		statement.close();
	}

	public List<User> getUserReport() throws SQLException{
		List<User> users = new ArrayList<User>();
		User user;

		Statement statement = connection.createStatement();
		String productTableSql = """
			SELECT Username, Type FROM Users;
			""";

		ResultSet rs = statement.executeQuery(productTableSql);
		while (rs.next()){
			user = new User(rs.getString("username"), rs.getString("type"));
			users.add(user);
		}
		statement.close();
		return users;
	}

	private void setupCreditCardList() throws SQLException {
		Statement statement = connection.createStatement();

		String CreditCardListSql = """
			CREATE TABLE IF NOT EXISTS CreditCardList (
			cardID INT PRIMARY KEY,
			name VARCHAR(18),
			number VARCHAR(18)
    		);""";

		statement.execute(CreditCardListSql);
		statement.close();
	}

	private void dropTable(String tableToDrop) throws SQLException {
		Statement statement = connection.createStatement();

		String dropTableSql = String.format("""
		DROP TABLE %s;""", tableToDrop);

		statement.execute(dropTableSql);
		statement.close();
	}

	private void importJsonFile() throws Exception {
		String sql = """
		INSERT INTO CreditCardList (cardID,name, number) VALUES (?, ?, ?);""";
		PreparedStatement statement = connection.prepareStatement(sql);

		JSONParser parser = new JSONParser();
		JSONArray jsonArray = (JSONArray) parser.parse(new FileReader("credit_cards.json"));
		int id = 1;
		for(Object object : jsonArray) {
			JSONObject cardDetail = (JSONObject) object;
			String name = (String) cardDetail.get("name");
			String number = (String) cardDetail.get("number");
			statement.setInt(1,id);
			statement.setString(2, name);
			statement.setString(3, number);
			statement.executeUpdate();
			id +=1;
		}

		statement.close();
	}

	public String getCardNumber(String name, String cardNumber) throws SQLException {
		Statement statement = connection.createStatement();

		String UserTableSql = String.format("""
				SELECT number
				FROM CreditCardList
				WHERE EXISTS
				(SELECT number FROM CreditCardList WHERE (name = '%1$s' AND number = '%2$s'))
				AND
				(name = '%3$s' AND number = '%2$s');
				""", name, cardNumber,name,cardNumber);

		ResultSet rs = statement.executeQuery(UserTableSql);
		String number = rs.getString("number");
		statement.close();
		return number;
	}

	private void setupCreditCardStored() throws SQLException {
		Statement statement = connection.createStatement();

		String CreditCardStoredSql = """
			CREATE TABLE IF NOT EXISTS CreditCardStored (
			username VARCHAR(18) NOT NULL,
			cardName VARCHAR(18) NOT NULL,
			cardNumber VARCHAR(18) NOT NULL,
			PRIMARY KEY (username, cardName, cardNumber)
    		);""";

		statement.execute(CreditCardStoredSql);
		statement.close();
	}

	public void storeCardDetails(String username, String cardName, String cardNum) throws SQLException {
		String sql = """
		INSERT INTO CreditCardStored (username, cardName, cardNumber)
		VALUES (?, ?, ?)""";

		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, username);
		statement.setString(2, cardName);
		statement.setString(3, cardNum);
		statement.execute();
		statement.close();
	}

	public List<String> getCardStoredByUser(String username) throws SQLException{

		List <String> ret = new ArrayList<>();

		Statement statement = connection.createStatement();
		String cardTableSql = String.format("""
			SELECT * FROM CreditCardStored
			WHERE username = '%s';
			""", username);

		ResultSet rs = statement.executeQuery(cardTableSql);

		while (rs.next()){
			String cardNumber = rs.getString("cardNumber");
			String last3Number = cardNumber.substring(cardNumber.length() - 2);
			ret.add(rs.getString("cardName") + "  |  ***" + last3Number);
		}

		statement.close();
		return ret;
	}

}
