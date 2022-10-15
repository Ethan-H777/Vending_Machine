package vendingMachineSystem.controller;

import vendingMachineSystem.VendingMachine;
import vendingMachineSystem.view.DefaultView;
import vendingMachineSystem.view.LoggedInView;
import vendingMachineSystem.view.LoginView;

public class LoggedInState extends VendingMachineState {

    public LoggedInState(VendingMachine vm) {
        super(vm);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void run() {
        LoggedInView view = new LoggedInView(this);
        view.display();
    }

}