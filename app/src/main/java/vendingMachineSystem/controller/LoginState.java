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

	public void changeToLoggedInPage() {
		vm.setState(new LoggedInState(vm));
	}

	public void changeToRegistrationPage() {
		vm.setState(new RegistrationState(vm));
	}

}