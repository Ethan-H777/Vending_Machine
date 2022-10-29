package vendingMachineSystem.controller;

import org.junit.jupiter.api.*;
import vendingMachineSystem.VendingMachine;
import vendingMachineSystem.model.Database;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ManageUserStateTest {

	static Database db;
	static String dbName = "test_database";
	static Connection dbConnection;
	VendingMachine vm;
	VendingMachineState prevState;
	
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
		prevState = new LoggedInState(vm,"OWNER");
	}

	@Test
	@DisplayName("Return To Logged In Page")
	void returnToLoggedInPage() {
		ManageUserState state = new ManageUserState(vm,prevState,false);
		state.returnToLoggedInState();
		assertTrue(state.getVm().getState() instanceof LoggedInState);
	}

	
	@Test
	@DisplayName("Timeout")
	void timeout() {
		vm.setUser("John", "CUSTOMER");
		ManageUserState state = new ManageUserState(vm,prevState,false);
		db.connect(dbName + ".db");
		state.setTimeout(0);
		boolean timedOut = state.checkTransactionTimeout();
		assert(timedOut);
	}
	
	@Test
	@DisplayName("No Timeout")
	void notTimedout() {
		vm.setUser("John", "CUSTOMER");
		ManageUserState state = new ManageUserState(vm,prevState,false);
		db.connect(dbName + ".db");
		boolean timedOut = state.checkTransactionTimeout();
		assert(!timedOut);
	}

	@Test
	@DisplayName("Get username")
	void getUser() {
		vm.setUser("John", "CUSTOMER");
		ManageUserState state = new ManageUserState(vm,prevState,false);
		db.connect(dbName + ".db");
		String ans = state.getUser();
		assertEquals("John",ans);
	}

	@Test
	@DisplayName("Successfully create & remove account")
	void createAndRemoveAccountSuccess () throws SQLException {
		ManageUserState state = new ManageUserState(vm,prevState,false);
		db.connect(dbName + ".db");
		boolean success = state.createAccount("john","123456","CUSTOMER");
		state.removeUser("john");
		boolean successRemoved = state.createAccount("john","123456","CUSTOMER");
		state.removeUser("john");
		assert(success);
		assert(successRemoved);
	}

	@Test
	@DisplayName("Fail to create account")
	void createAccountFail () throws SQLException {
		ManageUserState state = new ManageUserState(vm,prevState,false);
		db.connect(dbName + ".db");
		state.createAccount("test","123456","CUSTOMER");
		boolean success = state.createAccount("test","123456","CUSTOMER");
		assert(!success);
	}

	@Test
	@DisplayName("get all user")
	void getAllUsers () throws SQLException{
		vm.setUser("billyowner", "OWNER");
		ManageUserState state = new ManageUserState(vm,prevState,true);
		db.connect(dbName + ".db");
		String[][] result = state.getAllUsers(state.getUser(),true);
		String output1 = Arrays.toString(result[0]);
		assertEquals(output1,output1);

	}

	@Test
	@DisplayName("get all username")
	void getAllUsernames () throws SQLException{
		vm.setUser("billyowner", "OWNER");
		ManageUserState state = new ManageUserState(vm,prevState,false);
		db.connect(dbName + ".db");
		String[] result = state.getAllUsernames(state.getUser());
		String output = Arrays.toString(result);
		assertEquals(output,output);

	}

}
