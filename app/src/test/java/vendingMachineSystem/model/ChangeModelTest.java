package vendingMachineSystem.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class ChangeModelTest {
    @BeforeEach
    void prepare(){
        Database testdb = Database.getInstance();
        testdb.connect("test_database.db");
        try {
            testdb.productsDrop();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
