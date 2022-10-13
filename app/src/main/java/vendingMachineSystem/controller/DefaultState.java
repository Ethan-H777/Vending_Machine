package vendingMachineSystem.controller;

import vendingMachineSystem.*;
import vendingMachineSystem.view.DefaultView;

public class DefaultState extends VendingMachineState {
	
	public DefaultState(VendingMachine vm) {
		super(vm);
	}

	@Override
	public void run() {
		DefaultView view = new DefaultView(this);
		view.display();
	}
	
	public void changeToLoginPage() {
		vm.setState(new LoginState(vm));
	}

}
