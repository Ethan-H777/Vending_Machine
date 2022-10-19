package vendingMachineSystem.controller;

import vendingMachineSystem.VendingMachine;
import vendingMachineSystem.model.UserModel;
import vendingMachineSystem.view.DefaultView;
import vendingMachineSystem.view.LoginView;

import java.sql.SQLException;
import java.util.List;

public class LoginState extends VendingMachineState {

	public LoginState(VendingMachine vm) {
		super(vm);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run() {
		LoginView view = new LoginView(this);
		view.display();
	}


	public void changeToRegistrationPage() {
		vm.setState(new RegistrationState(vm));
	}

	public boolean verifyAccount(String username, String password){
		return false;
	}
	public void changeToLoggedInPage(String type){
		this.checkTimedOut(200);
		vm.setState( new LoggedInState(vm, type));
	}

	public String getPassword(String username) throws SQLException {
		UserModel userDB = new UserModel();
		String pw = userDB.getPassword(username);
		return pw;
	}

	public String getUserType(String username) throws SQLException {
		UserModel userDB = new UserModel();
		String type = userDB.getUserType(username);
		return type;
	}

}
