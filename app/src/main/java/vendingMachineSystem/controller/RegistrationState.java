package vendingMachineSystem.controller;

import vendingMachineSystem.VendingMachine;
import vendingMachineSystem.model.UserModel;
import vendingMachineSystem.view.DefaultView;
import vendingMachineSystem.view.LoginView;
import vendingMachineSystem.view.RegistrationView;

import java.lang.reflect.Type;
import java.sql.SQLException;

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

    public boolean createAccount(String username, String password, String type){
        UserModel userDB = new UserModel();
        try {
            userDB.createUser(username,password,type);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

}
