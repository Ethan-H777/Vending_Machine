package vendingMachineSystem.controller;

import vendingMachineSystem.VendingMachine;
import vendingMachineSystem.view.PurchaseItemView;

public class PurchaseItemState extends VendingMachineState {

	public PurchaseItemState(VendingMachine vm) {
		super(vm);
	}

	@Override
	public void run() {
        PurchaseItemView view = new PurchaseItemView(this);
        view.display();

	}

}
