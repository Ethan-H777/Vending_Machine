package vendingMachineSystem.controller;

import vendingMachineSystem.VendingMachine;

public abstract class VendingMachineState {
	
	VendingMachine vm;
	
	public VendingMachineState(VendingMachine vm) {
		this.vm = vm;
	}
	
	public abstract void run();
	
	public String readInput() {
		return "0";
	}
	
}