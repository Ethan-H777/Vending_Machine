package vendingMachineSystem.controller;

import java.time.LocalDateTime;

import vendingMachineSystem.VendingMachine;
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
	
}