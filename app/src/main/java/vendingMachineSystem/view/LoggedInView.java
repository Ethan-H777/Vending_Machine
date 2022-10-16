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
        p.setLayout(new BorderLayout());

        JButton purchaseButton = new JButton("Purchase Items");
        purchaseButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                LoggedInView.this.state.clickedPurchase();
            }
        });

        p.add(purchaseButton, BorderLayout.SOUTH);
        window.updateWindow(p);
    }
}
