package vendingMachineSystem.controller;

import vendingMachineSystem.VendingMachine;
import vendingMachineSystem.view.LoggedInView;

public class LoggedInState extends VendingMachineState {

    private String role;

    public String getRole(){return role;}
    public LoggedInState(VendingMachine vm, String given_role){
        super(vm);
        this.role = role;
    }

    @Override
    public void run(){
        LoggedInView view = new LoggedInView(this);
        view.display();
    }

    public void clickedPurchase(){
        System.out.println("Clicked Purchase");
    }

}
