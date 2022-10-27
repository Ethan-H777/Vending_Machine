package vendingMachineSystem.controller;

import java.util.*;

import vendingMachineSystem.VendingMachine;
import vendingMachineSystem.view.CashPaymentView;

public class CashPaymentState extends VendingMachineState {

	Map<String, Integer> itemsToPurchase;
	private VendingMachineState prevState;
	//the prevState is the purchaseItemState
	String[][] itemData;
	String[][] changeData;
	
	public CashPaymentState(VendingMachine vm, Map<String, Integer> itemsToPurchase, VendingMachineState prevState) {
		super(vm);
		this.itemsToPurchase = itemsToPurchase;
		itemData = super.getItemData();
		changeData = super.getCashData();
		this.prevState = prevState;
	}

	@Override
	public void run() {
		CashPaymentView view = new CashPaymentView(this);
		view.display();
	}
	
	public float calculateTotal() {
		float total = 0;
		for (Map.Entry<String, Integer> entry: itemsToPurchase.entrySet()) {
			String itemName = entry.getKey();
			int qty = entry.getValue();
			for (String[] itemLine: itemData) {
				if (itemName.equals(itemLine[1])) {
					total += Float.parseFloat(itemLine[3]) * qty;
				}
			}
			
		}
		return total;
	}
	
	public String[][] getChangeData() {
		return changeData;
	}

	public void changeToPurchaseState(){
		vm.setState(prevState);
	}

}
