package vendingMachineSystem.model;

import java.sql.SQLException;
import java.util.List;

public class ChangeModel {
    private Database db;

    public ChangeModel(boolean isTest){
        // conn
        db = Database.getInstance();
        if (isTest){
            db.connect("test_database.db");
        }
        else {
            db.connect("database.db");
        }
    }


    public List<Change> allChanges() throws SQLException {
//        if table was dropped
//        db.addDataChanges();
        List<Change> changes;
        changes = db.getAllChanges();
        return changes;
    }

    public void updateCash(String name, String newQty) throws SQLException{
        db.updateChangeQty(name, newQty);
    }
}
