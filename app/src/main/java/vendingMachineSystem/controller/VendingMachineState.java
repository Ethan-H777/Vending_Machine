package vendingMachineSystem.controller;

import vendingMachineSystem.VendingMachine;

public abstract class VendingMachineState {
	
	VendingMachine vm;
	
	public VendingMachineState(VendingMachine vm) {
		this.vm = vm;
		if (!(this instanceof DefaultState)) {
			//TODO we can add a thread to monitor the timeout and switch states to the default state on timeout
//			Thread thread = new Thread();
//			thread.run();
		}
	}
	
	public abstract void run();
	
	public void inputEntered() {
		//TODO this should be called after every action to reset the timeout period
	}
	
	public String readInput() {
		return "0";
	}
	
}