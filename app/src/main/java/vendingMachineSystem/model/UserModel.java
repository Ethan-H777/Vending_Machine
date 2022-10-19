package vendingMachineSystem.model;

import vendingMachineSystem.VendingMachine;

import java.sql.DriverManager;
import java.sql.SQLException;

public class UserModel {

    Database db;

    public UserModel(){
        db = Database.getInstance();
        db.connect("database.db");
    }

    public void createUser(String username, String password, String type) throws SQLException {
        db.addUser(username,password,type);
    }


}
