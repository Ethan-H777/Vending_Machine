package vendingMachineSystem.controller;

import vendingMachineSystem.VendingMachine;
import vendingMachineSystem.view.DefaultView;
import vendingMachineSystem.view.LoggedInView;
import vendingMachineSystem.view.LoginView;


public class LoggedInState extends VendingMachineState {

    private String role;

    public String getRole(){return role;}
    public LoggedInState(VendingMachine vm, String role){
        super(vm);
        this.role = role;
    }

    @Override
    public void run(){
        LoggedInView view = new LoggedInView(this);
        view.display();
    }

    public void clickedPurchase(){
        // (STUB) TODO: purchase page
        System.out.println("Clicked Purchase");
    }

    public void clickedModifyRestock(){
        // (STUB) TODO: purchase page
        System.out.println("Clicked Modify/Restock");
    }
    public void clickedUpdateChange(){
        // (STUB) TODO: update change page
        System.out.println("Clicked Update Change");
    }
    public void clickedManageUsers(){
        // (STUB) TODO: manage users page
        System.out.println("Clicked Manage Users");
    }
    public void clickedReports(){
        // (STUB) TODO: reports page
        System.out.println("Reports");
    }

    public void clickedCancel(){
        role = null; // update this when roles are updated?
        vm.setState( new DefaultState(vm) );
    }

}
