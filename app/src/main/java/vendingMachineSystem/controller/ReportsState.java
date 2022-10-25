package vendingMachineSystem.controller;


import vendingMachineSystem.VendingMachine;
import vendingMachineSystem.view.LoggedInView;
import vendingMachineSystem.view.ReportsView;

public class ReportsState extends VendingMachineState {

        private String role;

        public String getRole(){return role;}
        public ReportsState(VendingMachine vm, String role){
            super(vm);
            this.role = role;
        }

    @Override
    public void run(){
        ReportsView view = new ReportsView(this);
        view.display();
    }

    public void clickedCancel(){
        vm.setState( new LoggedInState(vm, role) );
    }

    }
