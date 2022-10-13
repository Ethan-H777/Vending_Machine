package vendingMachineSystem.view;

import java.awt.BorderLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;

import vendingMachineSystem.controller.*;

public class LoginView extends AbstractView {

	LoginState state;
	
	public LoginView(LoginState state) {
		this.state = state;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void display() {
		// TODO Auto-generated method stub
		Window window = Window.getInstance();
		
		Panel p = new Panel();
		p.setLayout(new BorderLayout());
		p.add(new JLabel("Login Page"), BorderLayout.NORTH);
		window.updateWindow(p);
	}

}
