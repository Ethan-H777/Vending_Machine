package vendingMachineSystem.controller;

import java.util.*;

import vendingMachineSystem.VendingMachine;
import vendingMachineSystem.view.CardPaymentView;
import vendingMachineSystem.view.CashPaymentView;

public class CardPaymentState extends VendingMachineState {

	Map<String, Integer> itemsToPurchase;
	String[][] itemData;
	String[][] changeData;
	
	public CardPaymentState(VendingMachine vm, Map<String, Integer> itemsToPurchase) {
		super(vm);
		this.itemsToPurchase = itemsToPurchase;
		itemData = super.getItemData();
		changeData = super.getCashData();
	}

	@Override
	public void run() {
		CardPaymentView view = new CardPaymentView(this);
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

}
