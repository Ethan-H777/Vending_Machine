package vendingMachineSystem.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import vendingMachineSystem.controller.DefaultState;

public class DefaultView extends AbstractView {

	DefaultState state;
	
	public DefaultView(DefaultState state) {
		this.state = state;
	}

	@Override
	public void display() {
		//TODO Example code
		Window window = Window.getInstance();
		
		Panel p = new Panel();
		p.setLayout(new BorderLayout());
		p.add(new JLabel("Default Landing Page"), BorderLayout.NORTH);
		
		JButton loginButton = new JButton("Login");
		loginButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				DefaultView.this.state.changeToLoginPage();
			}
			
		});
		
		p.add(loginButton, BorderLayout.SOUTH);
		window.updateWindow(p);
	}

}
