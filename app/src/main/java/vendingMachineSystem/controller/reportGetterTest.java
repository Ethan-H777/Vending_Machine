package vendingMachineSystem.controller;

import org.junit.jupiter.api.*;

import vendingMachineSystem.VendingMachine;
import vendingMachineSystem.model.Database;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;

import static org.junit.jupiter.api.Assertions.*;

public class reportGetterTest {

    static Database db;
    static String dbName = "test_database";
    static Connection dbConnection;
    VendingMachine vm;

    @BeforeAll
    static void initialiseDB() throws Exception {
        db = Database.getInstance();
        db.connect(dbName + ".db");
        String dbUrl = "jdbc:sqlite:" + System.getProperty("user.dir") + "/" + dbName + ".db";
        dbConnection = DriverManager.getConnection(dbUrl);
    }

    @AfterAll
    static void cleanup() throws Exception {
        File dbFile = new File(System.getProperty("user.dir") + "/" + dbName + ".db");
        dbFile.delete();
        dbConnection.close();
    }
    @BeforeEach
    void initialiseClasses() {
        vm = new VendingMachine();
    }

    @Test
    void changeRep(){
        try{
            ReportsState rs = new ReportsState(vm, "OWNER");
            rs.changeReport(true);
        } catch (Exception e) {
            assertTrue(false); // if there is an error, test fails
        }
    }
}
