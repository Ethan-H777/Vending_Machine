package vendingMachineSystem.controller;

import vendingMachineSystem.VendingMachine;
import vendingMachineSystem.view.RestockView;

public class RestockState extends VendingMachineState{
    private String role;

    public RestockState(VendingMachine vm, String role) {
        super(vm);
        this.role = role;
    }

    @Override
    public void run() {
        RestockView view = new RestockView(this);
        view.display();
    }

    public void changeToLoggedInState(){
        vm.setState(new LoggedInState(vm, this.role));
    }
}
