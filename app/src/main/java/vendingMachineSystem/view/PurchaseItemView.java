package vendingMachineSystem.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import vendingMachineSystem.controller.PurchaseItemState;
import vendingMachineSystem.controller.VendingMachineState;

public class PurchaseItemView extends AbstractView {

	PurchaseItemState state;
	LinkedList<PurchaseItemLine> itemsToPurchase;
	String[][] itemDataArray;
	ItemData itemData;
	Panel purchaseItemPanel;
	
	public PurchaseItemView(PurchaseItemState state) {
		this.state = state;
		this.itemsToPurchase = new LinkedList<PurchaseItemLine>();
		itemDataArray = state.getItemData();
		itemData = new ItemData(itemDataArray);
	}
	
	private class ItemData {
		ArrayList<String> itemNames;
		Map<String, Integer> availableQty;
		
		public ItemData (String[][] itemDataArray) {
			itemNames = new ArrayList<String>();
			availableQty = new HashMap<String, Integer>();
			
			for (String[] line: itemDataArray) {
				String itemName = line[1];
				String quantity = line[2];
				itemNames.add(itemName);
				availableQty.put(itemName, Integer.parseInt(quantity));
			}
		}
		
		public void addQty(String itemName, int qty) {
			int prevQty = availableQty.get(itemName);
			availableQty.put(itemName, prevQty + qty);
		}
		
		public int getQty(String itemName) {
			return availableQty.get(itemName);
		}
		
		public String[] getItemNames() {
			return (String[]) itemNames.toArray(new String[0]);
		}
	}
	
	private class PurchaseItemLine extends JPanel {
		JLabel itemLabel;
		JLabel quantityLabel;
		JButton deleteButton;
		String itemName;
		int quantity;
		
		public PurchaseItemLine(String item, int quantity) {
			this.itemName = item;
			this.quantity = quantity;
			
			itemLabel = new JLabel(item);
			super.add(itemLabel);
			
			quantityLabel = new JLabel(Integer.toString(quantity));
            super.add(quantityLabel);
            
            deleteButton = new JButton("Delete");
            deleteButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					PurchaseItemView.this.state.checkTimedOut();
					PurchaseItemView.this.itemData.addQty(item, quantity);
					PurchaseItemView.this.itemsToPurchase.remove(PurchaseItemLine.this);
					PurchaseItemView.this.display();
				}
            	
            });
            
            super.add(deleteButton);
		}
		
		public String getItemName() {
			return this.itemName;
		}
		
		public int getQty() {
			return this.quantity;
		}
		
	}
	
	private class AddItemDialog extends JDialog {
		
		JComboBox items;
		JSpinner quantityField;
		SpinnerNumberModel numberModel;
		String selectedItem;
		int quantity;
		
		public AddItemDialog() {
			
			setSize(200, 200);
			setLayout(new BorderLayout());
			
			JLabel title = new JLabel("Add Item to Purchase", SwingConstants.CENTER);
			title.setVerticalAlignment(SwingConstants.CENTER);
			add(title, BorderLayout.NORTH);
			
			JPanel addItemPanel = new JPanel();
			
			String[] itemList = PurchaseItemView.this.itemData.getItemNames();
			items = new JComboBox(itemList);
			items.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					boolean timedout = PurchaseItemView.this.state.checkTransactionTimeout();
					if (timedout) {
						AddItemDialog.this.dispose();
						return;
					}
					String selectedItem = String.valueOf(items.getSelectedItem());
					if (AddItemDialog.this.selectedItem != selectedItem) {
						System.out.println(selectedItem);
						AddItemDialog.this.selectedItem = selectedItem;
						addItemPanel.remove(quantityField);
						AddItemDialog.this.addQtySpinner(addItemPanel, selectedItem);
			            AddItemDialog.this.revalidate();
					}
				}
				
			});
			addItemPanel.add(items);
			
			selectedItem = itemList[0];
			addQtySpinner(addItemPanel, String.valueOf(selectedItem));
            
            add(addItemPanel, BorderLayout.CENTER);
			
            JPanel buttonPanel = new JPanel();
            
			JButton addItemButton = new JButton("Add Item");
			addItemButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					boolean timedout = PurchaseItemView.this.state.checkTransactionTimeout();
					if (timedout) {
						AddItemDialog.this.dispose();
						return;
					}
					quantity = (Integer) numberModel.getValue();
					if (quantity > 0) {
						PurchaseItemView.this.itemsToPurchase.add(new PurchaseItemLine(selectedItem, quantity));
						PurchaseItemView.this.itemData.addQty(selectedItem, -quantity);						
					}
					AddItemDialog.this.dispose();
					PurchaseItemView.this.display();
				}
				
			});
			buttonPanel.add(addItemButton);
			
			JButton cancelButton = new JButton("Cancel");
			cancelButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					PurchaseItemView.this.state.checkTransactionTimeout();
					AddItemDialog.this.dispose();
				}
				
			});
			buttonPanel.add(cancelButton);
			add(buttonPanel, BorderLayout.SOUTH);
			
			setVisible(true);
			setAlwaysOnTop(true);
		}
		
		private void addQtySpinner(JPanel addItemPanel, String name) {
			numberModel = new SpinnerNumberModel(
				PurchaseItemView.this.itemData.getQty(name) > 0 ? new Integer(1): new Integer(0), //value
				new Integer(0), //min
				new Integer(PurchaseItemView.this.itemData.getQty(name)),
				new Integer(1)
			);
            quantityField = new JSpinner(numberModel);
            quantityField.addChangeListener(new ChangeListener() {

				@Override
				public void stateChanged(ChangeEvent e) {
					boolean timedout = PurchaseItemView.this.state.checkTransactionTimeout();
					if (timedout) {
						AddItemDialog.this.dispose();
						return;
					}
				}
            	
            });
            addItemPanel.add(quantityField);
		}
		
	}
	
	@Override
	public void display() {
		Window window = Window.getInstance();
		
		Panel p = new Panel();
		BoxLayout layout = new BoxLayout(p, BoxLayout.Y_AXIS);
		p.setLayout(layout);
		
		this.addMenu(p);
		this.addPurchaseItemPanel(p);
		this.addActionButtons(p);
		
		window.updateWindow(p);

	}
	
	private void addMenu(Panel p) {
		String[] names = {"Category", "Item", "Quantity", "Price"};
		JTable tab = new JTable(itemDataArray, names);
		JScrollPane tab_scroller = new JScrollPane(tab);
		tab_scroller.setPreferredSize(new Dimension(650, 100));
		p.add(tab_scroller);
	}
	
	private void addPurchaseItemPanel(Panel p) {
		Panel addItemPanel = new Panel();
		addItemPanel.setLayout(new FlowLayout());
		JLabel recLabel = new JLabel("Purchase Items");
		recLabel.setPreferredSize(new Dimension(650, 20));
		addItemPanel.add(recLabel, FlowLayout.LEFT);
		addItemPanel.setPreferredSize(new Dimension(650, 20));		
		p.add(addItemPanel);
		
		purchaseItemPanel = new Panel();
		purchaseItemPanel.setLayout(new FlowLayout());
		purchaseItemPanel.setPreferredSize(new Dimension(650, 100));
		JScrollPane purchaseItemScroller = new JScrollPane(purchaseItemPanel);
		purchaseItemScroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		purchaseItemScroller.setPreferredSize(new Dimension(650, 100));
		
		for (PurchaseItemLine item: itemsToPurchase) {
			purchaseItemPanel.add(item);
		}			
		
		p.add(purchaseItemScroller);
	}
	
	private void addPurchaseItemLine(Panel p) {
		new AddItemDialog();
		this.display();
	}

	private void addActionButtons(Panel p) {
		JPanel buttonPanel = new JPanel();
		FlowLayout flowLayout = new FlowLayout();
		buttonPanel.setLayout(flowLayout);
		
		JButton addPurchaseItemButton = new JButton("Add Item");
		addPurchaseItemButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean timedout = PurchaseItemView.this.state.checkTransactionTimeout();
				if (timedout) {
					return;
				}
				PurchaseItemView.this.addPurchaseItemLine(purchaseItemPanel);
			}
		});
		buttonPanel.add(addPurchaseItemButton);
		
		JButton cashPayButton = new JButton("Pay with Cash");
		cashPayButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				boolean timedout = PurchaseItemView.this.state.checkTransactionTimeout();
				if (timedout) {
					return;
				}
				Map<String, Integer> itemsToPurchase = PurchaseItemView.this.getItemsToPurchase();
				PurchaseItemView.this.state.changeToCashPaymentPage(itemsToPurchase);
			}
			
		});
		buttonPanel.add(cashPayButton);
		
		JButton cardPayButton = new JButton("Pay with Card");
		cardPayButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				boolean timedout = PurchaseItemView.this.state.checkTransactionTimeout();
				if (timedout) {
					return;
				}
				Map<String, Integer> itemsToPurchase = PurchaseItemView.this.getItemsToPurchase();
				PurchaseItemView.this.state.changeToCardPaymentPage(itemsToPurchase);
			}
			
		});

		cardPayButton.setBounds( 170, 250, 150, 25 );
		buttonPanel.add(cardPayButton);
		
		JButton cancelButton;

		if(PurchaseItemView.this.state.getLoggedInStatus()){
			cancelButton = new JButton("Cancel Transaction & Return");
		}else{
			cancelButton = new JButton("Cancel Transaction");
		}

		cancelButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				PurchaseItemView.this.state.cancelTransaction();
			}
			
		});
		buttonPanel.add(cancelButton);
		
		p.add(buttonPanel);
	}
	
	private Map<String, Integer> getItemsToPurchase() {
		Map<String, Integer> itemMap = new HashMap<String, Integer>();
		for (PurchaseItemLine itemLine: itemsToPurchase) {
			String itemName = itemLine.getItemName();
			int qty = itemLine.getQty();
			if (itemMap.containsKey(itemName)) {
				int prevQty = itemMap.get(itemName);
				itemMap.put(itemName, prevQty + qty);
			}
			else {
				itemMap.put(itemName, qty);
			}
		}
		return itemMap;
	}

}
