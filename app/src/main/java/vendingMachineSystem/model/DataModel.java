package vendingMachineSystem.model;

import java.sql.SQLException;
import java.util.List;

public class DataModel {
    Database db;

    public DataModel(){
        // conn
        db = Database.getInstance();
        db.connect("database.db");
    }

    public List<Product> allProducts() throws SQLException{
        //db.addDataProducts(); // if table was dropped
        List<Product> ls;
        ls = db.getAllProducts();
        return ls;
    }
}
