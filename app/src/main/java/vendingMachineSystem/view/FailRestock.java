package vendingMachineSystem.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FailRestock extends JDialog implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        dispose();
    }

    public FailRestock(){
        setBounds(150, 120, 500, 100);
        setLayout(new BorderLayout());

<<<<<<< HEAD
        String msg = String.format("Fail. Please enter valid information.");
=======
        String msg = String.format("Failed! Please enter valid information.");
>>>>>>> e60ba13938f9b28e976680dd1d4d098826a482dd
        JLabel title = new JLabel(msg, SwingConstants.CENTER);
        title.setVerticalAlignment(SwingConstants.CENTER);
        add(title, BorderLayout.NORTH);

        JButton close = new JButton("Close");
        close.addActionListener(this);
        add(close, BorderLayout.SOUTH);

        setVisible(true);
    }
}
