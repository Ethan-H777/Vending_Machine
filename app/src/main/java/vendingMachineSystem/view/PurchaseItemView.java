package vendingMachineSystem.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import vendingMachineSystem.controller.PurchaseItemState;

public class PurchaseItemView extends AbstractView {

	PurchaseItemState state;
	
	public PurchaseItemView(PurchaseItemState state) {
		this.state = state;
	}
	
	@Override
	public void display() {
		Window window = Window.getInstance();
		
		Panel p = new Panel();
		p.setLayout(null);
		
		JButton cashPayButton = new JButton("Pay with Cash");
		cashPayButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
//				DefaultView.this.state.changeToLoginPage();
			}
			
		});

		cashPayButton.setBounds( 10, 250, 150, 25 );
		p.add(cashPayButton);
		
		JButton cardPayButton = new JButton("Pay with Card");
		cardPayButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
//				DefaultView.this.state.changeToLoginPage();
			}
			
		});

		cardPayButton.setBounds( 170, 250, 150, 25 );
		p.add(cardPayButton);
		
		JButton cancelButton = new JButton("Cancel");
		cardPayButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
//				DefaultView.this.state.changeToLoginPage();
			}
			
		});

		cancelButton.setBounds( 500, 250, 150, 25 );
		p.add(cancelButton);

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
		JLabel recLabel = new JLabel("Purchase Item");
		Dimension rec_size = recLabel.getPreferredSize();
		recLabel.setBounds(0,150,rec_size.width,rec_size.height);
		p.add(recLabel);
		
		JScrollPane purchaseItemScroller = new JScrollPane();
		purchaseItemScroller.setBounds(0,400,650,125);
		JButton addPurchaseItemButton = new JButton("Add Item");
		addPurchaseItemButton.setBounds(10, 200, 100, 25);
		purchaseItemScroller.add(addPurchaseItemButton);
		p.add(purchaseItemScroller);
		
		window.updateWindow(p);

	}

}
