package vendingMachineSystem.model;

import java.sql.SQLException;
import java.util.List;

public class ManageUserModel {

    Database db;

    public ManageUserModel(){
        db = Database.getInstance();
        db.connect("database.db");
    }


    public String[][] getAllUsers(String except) throws SQLException{
        return db.getAllUsers(except);
    }

    public String[] getAllUsernames(String except) throws SQLException{
        return db.getAllUsernames(except);
    }

    public void removeUser(String username) throws SQLException{
        db.removeUser(username);
    }

    public void createUser(String username, String password, String type) throws SQLException {
        db.addUser(username,password,type);
    }

}
