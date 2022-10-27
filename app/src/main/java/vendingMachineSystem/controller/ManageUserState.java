package vendingMachineSystem.controller;

import vendingMachineSystem.VendingMachine;
import vendingMachineSystem.model.CardModel;
import vendingMachineSystem.model.ManageUserModel;
import vendingMachineSystem.model.TransactionModel;
import vendingMachineSystem.model.UserModel;
import vendingMachineSystem.view.DefaultView;
import vendingMachineSystem.view.ManageUserView;

import java.sql.SQLException;

public class ManageUserState extends VendingMachineState {

	private VendingMachineState prevState;

	int timeoutPeriodSeconds = 120;
	public ManageUserState(VendingMachine vm, VendingMachineState prevState) {
		super(vm);
		this.prevState = prevState;
	}

	@Override
	public void run(){
		ManageUserView view = new ManageUserView(this);
		view.display();
	}

	public String[][] getAllUsers(String except) throws SQLException {
		ManageUserModel db = new ManageUserModel();
		return db.getAllUsers(except);
	}

	public String[] getAllUsernames(String except) throws SQLException {
		ManageUserModel db = new ManageUserModel();
		return db.getAllUsernames(except);
	}

	public void removeUser(String username) throws SQLException{
		ManageUserModel db = new ManageUserModel();
		db.removeUser(username);
	}

	public String getUser(){
		return vm.getUserName();
	}
	public void returnToLoggedInState(){
		vm.setState(prevState);
	}

	public boolean checkTransactionTimeout() {
		boolean timedout = super.checkTimedOut(timeoutPeriodSeconds);
		if (timedout) {
			TransactionModel tm = new TransactionModel(vm.getUserName(), "Timed out");
			tm.addFailedTransaction();
		}
		return timedout;
	}

	public boolean createAccount(String username, String password, String type){
		ManageUserModel db = new ManageUserModel();
		try {
			db.createUser(username,password,type);
			return true;
		} catch (SQLException e) {
			return false;
		}
	}
	public void setTimeout(int sec) {
		this.timeoutPeriodSeconds = sec;
	}
}

