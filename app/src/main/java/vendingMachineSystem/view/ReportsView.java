package vendingMachineSystem.view;

import vendingMachineSystem.controller.LoggedInState;
import vendingMachineSystem.controller.ReportsState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ReportsView extends AbstractView{

    ReportsState state;
    String label_string;

    public ReportsView(ReportsState state) {
        this.state = state;
        label_string = state.getRole() + " Reports";
    }

    @Override
    public void display() {
        Window window = Window.getInstance();

        Panel p = new Panel();
        p.setLayout(null);

        // label
        JLabel label = new JLabel(label_string);
        Dimension size = label.getPreferredSize();
        label.setBounds(0,0,size.width,size.height);
        p.add(label);

        // report buttons

        // cancel
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ReportsView.this.state.clickedCancel();
            }
        });
        cancelButton.setBounds(560,230,100,40);
        cancelButton.setFont(new Font("Arial", Font.PLAIN, 10));
        p.add(cancelButton);

        // finish
        window.updateWindow(p);
    }
}
