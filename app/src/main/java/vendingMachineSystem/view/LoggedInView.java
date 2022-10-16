package vendingMachineSystem.view;

import vendingMachineSystem.controller.LoggedInState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoggedInView extends AbstractView {

    LoggedInState state;

    public LoggedInView(LoggedInState state) {
        this.state = state;
    }


    @Override
    public void display() {
        Window window = Window.getInstance();

        Panel p = new Panel();
        p.setLayout(null);

        JButton purchaseButton = new JButton("Purchase Items");
        purchaseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LoggedInView.this.state.clickedPurchase();
            }
        });
        purchaseButton.setBounds(0,220,100,40); //300x300 window, 100
        purchaseButton.setFont(new Font("Arial", Font.PLAIN, 10));
        p.add(purchaseButton);

        // buttons based on role
            // modify/restock
        if ( state.getRole().equals("OWNER") || state.getRole().equals("SELLER") ){
            JButton modButton = new JButton("Modify/Restock Items");
            modButton.setBounds(100,220,120,40); // 220
            modButton.setFont(new Font("Arial", Font.PLAIN, 10));
            modButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    LoggedInView.this.state.clickedModifyRestock();
                }
            });
            p.add(modButton);
        }

        // update change
        if ( state.getRole().equals("OWNER") || state.getRole().equals("CASHIER") ){
            JButton updateButton = new JButton("Update Change");
            updateButton.setBounds(220,220,120,40); // 340
            updateButton.setFont(new Font("Arial", Font.PLAIN, 10));
            updateButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    LoggedInView.this.state.clickedUpdateChange();
                }
            });
            p.add(updateButton);
        }

        window.updateWindow(p);
    }
}
