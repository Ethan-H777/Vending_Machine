package vendingMachineSystem.controller;

import vendingMachineSystem.VendingMachine;
import vendingMachineSystem.view.DefaultView;
import vendingMachineSystem.view.LoginView;

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
	public void changeToLoggedInPage(){
		this.checkTimedOut(200);

		// TODO: Get role based on login details

		vm.setState( new LoggedInState(vm, "SELLER"));
		//vm.setState( new LoggedInState(vm, "CUSTOMER"));
	}
}
