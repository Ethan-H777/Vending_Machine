package vendingMachineSystem.model;

import vendingMachineSystem.VendingMachine;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

public class TransactionModel {

    Database db = Database.getInstance();
    String user;
    String reason;

    public TransactionModel(){

    }
    
    public TransactionModel(String user, String reason) {
    	this.user = user;
    	this.reason = reason;
    }

    public void addFailedTransaction() {
    	try {
    		db.addFailedTransaction(user, reason);    		
    	} catch (SQLException e) {
    		System.err.println(e);
    	}
    }

}
