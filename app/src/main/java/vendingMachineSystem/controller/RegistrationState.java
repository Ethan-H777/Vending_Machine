package vendingMachineSystem.controller;

        import vendingMachineSystem.VendingMachine;
        import vendingMachineSystem.view.DefaultView;
        import vendingMachineSystem.view.LoginView;
        import vendingMachineSystem.view.RegistrationView;

public class RegistrationState extends VendingMachineState {

    public RegistrationState(VendingMachine vm) {
        super(vm);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void run() {
        RegistrationView view = new RegistrationView(this);
        view.display();
    }

    public void changeToLoggedInPage() {
        vm.setState(new LoggedInState(vm,"CUSTOMER"));
    }

    public boolean createAccount(String username, String password){

        return false;
    }

}
