package vendingMachineSystem.controller;

import vendingMachineSystem.*;
import vendingMachineSystem.model.DataModel;
import vendingMachineSystem.model.Product;
import vendingMachineSystem.view.DefaultView;

import java.sql.SQLException;
import java.util.List;

public class DefaultState extends VendingMachineState {

	public DefaultState(VendingMachine vm) {
		super(vm);
	}

	@Override
	public void run(){
		DefaultView view = new DefaultView(this);
		view.display();
	}
	
	public void changeToLoginPage() {
		vm.setState(new LoginState(vm));
	}

	public String[][] getItemData(){
		// get products
		DataModel dm = new DataModel(false);
		List<Product> ls;
		try {
			 ls = dm.allProducts();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		// now get 2d list of items needed
		// category, item, quantity, price
		String[][] ret = new String[ls.size()][4];
		for ( int prod_n = 0; prod_n < ls.size(); prod_n++ ){
			ret[prod_n][0] = ls.get(prod_n).getCategory();
			ret[prod_n][1] = ls.get(prod_n).getName();
			ret[prod_n][2] = Integer.toString(ls.get(prod_n).getQuantity());
			ret[prod_n][3] = Float.toString(ls.get(prod_n).getPrice());
		}

		return ret;
	}
}
