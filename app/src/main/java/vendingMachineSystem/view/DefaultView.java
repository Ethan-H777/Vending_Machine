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
		//p.setLayout(new BorderLayout());
		p.setLayout(null);
		p.add(new JLabel("Default Landing Page"), BorderLayout.NORTH);
		
		JButton loginButton = new JButton("Login/Register");
		loginButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				DefaultView.this.state.changeToLoginPage();
			}
			
		});

		loginButton.setBounds( 150, 250, 350, 25 );
		p.add(loginButton);

		//menu
		JLabel menuLabel = new JLabel("Menu");
		Dimension size = menuLabel.getPreferredSize();
		menuLabel.setBounds(0,0,size.width,size.height);
		p.add(menuLabel);
		String[][] data = state.getItemData();
		String[] names = {"Category", "Item", "Quantity", "Price"};
		JTable tab = new JTable(data, names);
		JScrollPane tab_scroller = new JScrollPane(tab);
		tab_scroller.setBounds(0,20,650,125);
		p.add(tab_scroller);

		//recent purchases
		JLabel recLabel = new JLabel("Recent Purchases");
		Dimension rec_size = recLabel.getPreferredSize();
		recLabel.setBounds(0,150,rec_size.width,rec_size.height);
		p.add(recLabel);
		String[][] rec_data;
		rec_data = state.getRecentData(); // stub for now TODO: implement getting recent purchases data
		String[] rec_names = {"Item", "Quantity", "Price"};
		JTable rec_tab = new JTable(rec_data, rec_names);
		JScrollPane rec_tab_scroller = new JScrollPane(rec_tab);
		rec_tab_scroller.setBounds(0,170,650,75);
		p.add(rec_tab_scroller);

		window.updateWindow(p);
	}

}
