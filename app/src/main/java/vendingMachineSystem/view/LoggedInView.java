package vendingMachineSystem.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;

import vendingMachineSystem.controller.*;

public class LoggedInView extends AbstractView {

    LoggedInState state;

    public LoggedInView(LoggedInState state) {
        this.state = state;
        // TODO Auto-generated constructor stub
    }

    @Override
    public void display() {
        // TODO Auto-generated method stub
        Window window = Window.getInstance();

        Panel p = new Panel();
        p.setLayout(new BorderLayout());
        p.add(new JLabel("Welcome to the Logged In Page"), BorderLayout.NORTH);

        JButton CancelButton = new JButton("Cancel");
        CancelButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                LoggedInView.this.state.cancelTransaction();
            }

        });

        Dimension size = CancelButton.getPreferredSize();
        CancelButton.setBounds(300, 200, size.width, size.height);
        p.add(CancelButton);

        window.updateWindow(p);
    }

}
