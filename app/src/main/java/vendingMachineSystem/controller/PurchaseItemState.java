package vendingMachineSystem.controller;

import java.util.Map;

import vendingMachineSystem.VendingMachine;
import vendingMachineSystem.model.TransactionModel;
import vendingMachineSystem.view.PurchaseItemView;

public class PurchaseItemState extends VendingMachineState {

	VendingMachineState prevState;
	int timeoutPeriodSeconds = 120;
	
	public PurchaseItemState(VendingMachine vm, VendingMachineState prevState) {
		super(vm);
		this.prevState = prevState;
	}

	@Override
	public void run() {
        PurchaseItemView view = new PurchaseItemView(this);
        view.display();

	}
	
	public void changeToCashPaymentPage(Map<String, Integer> itemsToPurchase) {
		vm.setState(new CashPaymentState(vm, itemsToPurchase));
	}
	
	public void changeToCardPaymentPage(Map<String, Integer> itemsToPurchase) {
		vm.setState(new CardPaymentState(vm, itemsToPurchase));
	}

	public void cancelTransaction() {
		TransactionModel tm = new TransactionModel(vm.getUserName(), "Cancelled");
		tm.addFailedTransaction();
		vm.setState(prevState);
	}
	
	public boolean checkTransactionTimeout() {
		boolean timedout = super.checkTimedOut(timeoutPeriodSeconds);
		if (timedout) {
			TransactionModel tm = new TransactionModel(vm.getUserName(), "Timed out");
			tm.addFailedTransaction();
		}
		return timedout;
	}
	
	public void setTimeout(int sec) {
		this.timeoutPeriodSeconds = sec;
	}
	
	
}
