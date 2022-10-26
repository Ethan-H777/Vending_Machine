package vendingMachineSystem.controller;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import vendingMachineSystem.VendingMachine;
import vendingMachineSystem.model.Change;
import vendingMachineSystem.model.ChangeModel;
import vendingMachineSystem.model.DataModel;
import vendingMachineSystem.model.Product;
import vendingMachineSystem.view.TimeoutDialog;

public abstract class VendingMachineState {
	
	VendingMachine vm;
	LocalDateTime lastAction;
	
	public VendingMachineState(VendingMachine vm) {
		this.vm = vm;
		this.lastAction = LocalDateTime.now();
	}
	
	public abstract void run();
	
	/**
	 * Switches state to the default state if action has timed out
	 * (Timeout is >120 sec since last action was taken)
	 */
	public void checkTimedOut() {
		checkTimedOut(120);
	}
	
	/**
	 * Switches state to the default state if action has timed out
	 * @param seconds time since last action was performed
	 */
	public void checkTimedOut(long seconds) {
		LocalDateTime currentDateTime = LocalDateTime.now();
		if (currentDateTime.minusSeconds(seconds).compareTo(lastAction) > 0) {
			this.cancelTransaction();
			new TimeoutDialog();
		}
		else
			this.lastAction = currentDateTime;		
	}
	
	public void cancelTransaction() {
		this.vm.setState(new DefaultState(vm));
	}
	
	public String readInput() {
		return "0";
	}

	public String[][] getRecentData(){ // STUB TODO: implement
		String[][] ret = {{"corn","million","like 50 bucks"}};
		return ret;
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
		String[][] ret = new String[ls.size()][5];
		for ( int prod_n = 0; prod_n < ls.size(); prod_n++ ){
			ret[prod_n][0] = ls.get(prod_n).getCategory();
			ret[prod_n][1] = ls.get(prod_n).getName();
			ret[prod_n][2] = Integer.toString(ls.get(prod_n).getQuantity());
			ret[prod_n][3] = Float.toString(ls.get(prod_n).getPrice());
			ret[prod_n][4] = Integer.toString(ls.get(prod_n).getId());
		}

		return ret;
	}

	public String[][] getCashData(){
		// get changes
		ChangeModel cm = new ChangeModel(false);
		List<Change> changes;
		try {
			changes = cm.allChanges();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		// now get 2d list of cash needed
		// name, value, quantity
		String[][] ret = new String[changes.size()][3];
		for ( int n = 0; n < changes.size(); n++ ){
			ret[n][0] = changes.get(n).getName();
			ret[n][1] = Float.toString(changes.get(n).getValue());
			ret[n][2] = Integer.toString(changes.get(n).getQty());
		}

		return ret;
	}
	
}