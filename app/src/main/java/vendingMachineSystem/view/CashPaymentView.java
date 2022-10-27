package vendingMachineSystem.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import vendingMachineSystem.controller.CashPaymentState;

import static java.lang.Math.round;

public class CashPaymentView extends AbstractView {
	
	CashPaymentState state;
	private float payment;
	
	
	public CashPaymentView(CashPaymentState state) {
		this.state = state;
	}

	@Override
	public void display() {
		Window window = Window.getInstance();
		
		Panel p = new Panel();
//		BoxLayout layout = new BoxLayout(p, BoxLayout.Y_AXIS);
//		p.setLayout(layout);
		p.setLayout(null);
		
		this.addTotalPanel(p);

		addPaymentButtons(p);

		confirmPayment(p);

		
		window.updateWindow(p);

	}
	
	private void addTotalPanel(Panel p) {
//		Panel totalPanel = new Panel();
//		totalPanel.setPreferredSize(new Dimension(650, 20));
		
		JLabel label = new JLabel("Cash Payment");
		Dimension size = label.getPreferredSize();
		label.setBounds(280, 10, size.width, size.height);
		p.add(label);
		
		JLabel total = new JLabel("Total Price:");
		Dimension sizeTotal = label.getPreferredSize();
		total.setBounds(30, 30, sizeTotal.width, sizeTotal.height);
		p.add(total);
		
		JLabel totalAmount = new JLabel(String.valueOf(state.calculateTotal()));
		size = label.getPreferredSize();
		totalAmount.setBounds(30 + sizeTotal.width,30, size.width, size.height);
		p.add(totalAmount);
		
//		p.add(totalPanel);
	}

	public void addPaymentButtons(Panel p){
		// cancel
		JButton cancelButton = new JButton("Cancel");
		cancelButton.setBounds(560,220,100,40);
		cancelButton.setFont(new Font("Arial", Font.PLAIN, 10));
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				CashPaymentView.this.state.changeToPurchaseState();
			}
		});
		p.add(cancelButton);

		JLabel amountLabel = new JLabel("Current Payment Amount:");
		Dimension sizeAmount = amountLabel.getPreferredSize();
		amountLabel.setBounds(30, 150, sizeAmount.width, sizeAmount.height);
		p.add(amountLabel);

		JLabel paymentLabel = new JLabel(String.valueOf(payment));
		Dimension paymentSize = new Dimension(100, sizeAmount.height);
		paymentLabel.setBounds(30 + sizeAmount.width + 5, 150, paymentSize.width, paymentSize.height);
		p.add(paymentLabel);

		JLabel payLabel = new JLabel("Select Payment");
		Dimension sizePay = payLabel.getPreferredSize();
		payLabel.setBounds(30, 50, sizePay.width, sizePay.height);
		p.add(payLabel);

		JButton button100 = new JButton("$100");
		Dimension size100 = button100.getPreferredSize();
		button100.setBounds(30,sizePay.height + 50 + 10, size100.width, size100.height);
		button100.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				payment += 100;
				paymentLabel.setText(String.valueOf(round(payment * 100.0) / 100.0));
			}
		});
		p.add(button100);

		JButton button50 = new JButton("$50");
		Dimension size50 = button50.getPreferredSize();
		button50.setBounds(30 + size100.width,sizePay.height + 60, size50.width, size50.height);
		button50.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				payment += 50;
				paymentLabel.setText(String.valueOf(round(payment * 100.0) / 100.0));
			}
		});
		p.add(button50);

		JButton button20 = new JButton("$20");
		Dimension size20 = button20.getPreferredSize();
		button20.setBounds(30 + size100.width + size50.width,sizePay.height + 60, size20.width, size20.height);
		button20.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				payment += 20;
				paymentLabel.setText(String.valueOf(round(payment * 100.0) / 100.0));
			}
		});
		p.add(button20);

		JButton button10 = new JButton("$10");
		Dimension size10 = button10.getPreferredSize();
		button10.setBounds(30 + size100.width + size50.width +size20.width,sizePay.height + 60, size10.width, size10.height);
		button10.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				payment += 10;
				paymentLabel.setText(String.valueOf(round(payment * 100.0) / 100.0));
			}
		});
		p.add(button10);

		JButton button5 = new JButton("$5");
		Dimension size5 = button5.getPreferredSize();
		button5.setBounds(30 + size100.width + size50.width +size20.width +size10.width,sizePay.height + 60, size5.width, size5.height);
		button5.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				payment += 5;
				paymentLabel.setText(String.valueOf(round(payment * 100.0) / 100.0));
			}
		});
		p.add(button5);

		JButton button50c = new JButton("50c");
		Dimension size50c = button50c.getPreferredSize();
		button50c.setBounds(30 ,sizePay.height + 60 + size100.height, size50c.width, size50c.height);
		button50c.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				payment += 0.5;
				paymentLabel.setText(String.valueOf(round(payment * 100.0) / 100.0));
			}
		});
		p.add(button50c);

		JButton button20c = new JButton("20c");
		Dimension size20c = button20c.getPreferredSize();
		button20c.setBounds(30 + size50c.width,sizePay.height + 60 + size100.height, size20c.width, size20c.height);
		button20c.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				payment += 0.2;
				paymentLabel.setText(String.valueOf(round(payment * 100.0) / 100.0));
			}
		});
		p.add(button20c);

		JButton button10c = new JButton("10c");
		Dimension size10c = button10c.getPreferredSize();
		button10c.setBounds(30 + size50c.width + size20c.width,sizePay.height + 60 + size100.height, size10c.width, size10c.height);
		button10c.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				payment += 0.1;
				paymentLabel.setText(String.valueOf(round(payment * 100.0) / 100.0));
			}
		});
		p.add(button10c);

		JButton button5c = new JButton("5c");
		Dimension size5c = button5c.getPreferredSize();
		button5c.setBounds(30 + size50c.width + size20c.width + size10c.width,sizePay.height + 60 + size100.height, size5c.width, size5c.height);
		button5c.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				payment += 0.05;
				paymentLabel.setText(String.valueOf(round(payment * 100.0) / 100.0));
			}
		});
		p.add(button5c);



	}

	public void confirmPayment(Panel p){
		JButton confirm = new JButton("Confirm Payment");
		Dimension size = confirm.getPreferredSize();
		confirm.setBounds(30,180, size.width, size.height);
		confirm.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				if (payment < state.calculateTotal()){
					float shortage = state.calculateTotal() - payment;
					new FailPayment(shortage);
				}
			}
		});
		p.add(confirm);


	}



}
