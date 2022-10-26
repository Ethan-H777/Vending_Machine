package vendingMachineSystem.view;

import java.awt.*;
import javax.swing.*;

import vendingMachineSystem.controller.CashPaymentState;

public class CashPaymentView extends AbstractView {
	
	CashPaymentState state;
	
	
	public CashPaymentView(CashPaymentState state) {
		this.state = state;
	}

	@Override
	public void display() {
		Window window = Window.getInstance();
		
		Panel p = new Panel();
		BoxLayout layout = new BoxLayout(p, BoxLayout.Y_AXIS);
		p.setLayout(layout);
		
		this.addTotalPanel(p);
		
		window.updateWindow(p);

	}
	
	private void addTotalPanel(Panel p) {
		Panel totalPanel = new Panel();
		totalPanel.setPreferredSize(new Dimension(650, 20));
		
		JLabel label = new JLabel("Cash Payment");
		totalPanel.add(label);
		
		JLabel total = new JLabel("Total");
		totalPanel.add(total);
		
		JLabel totalAmount = new JLabel(String.valueOf(state.calculateTotal()));
		totalPanel.add(totalAmount);
		
		p.add(totalPanel);
	}

}
