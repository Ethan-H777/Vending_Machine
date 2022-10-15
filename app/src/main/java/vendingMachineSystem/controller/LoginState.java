package vendingMachineSystem.controller;

import vendingMachineSystem.VendingMachine;
import vendingMachineSystem.view.DefaultView;
import vendingMachineSystem.view.LoginView;

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
	
	public void changeToLoginPage() {
		this.checkTimedOut(1); //Please change this this was done for testing only
	}

}
