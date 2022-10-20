package vendingMachineSystem.view;

import vendingMachineSystem.controller.LoggedInState;

import javax.swing.*;
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

        // cancel
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setBounds(560,220,100,40);
        cancelButton.setFont(new Font("Arial", Font.PLAIN, 10));
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LoggedInView.this.state.clickedCancel();
            }
        });
        p.add(cancelButton);

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

            // update user
        if ( state.getRole().equals("OWNER") ){
            JButton mgUsrButton = new JButton("Manage Users");
            mgUsrButton.setBounds(340,220,120,40); // 460
            mgUsrButton.setFont(new Font("Arial", Font.PLAIN, 10));
            mgUsrButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    LoggedInView.this.state.clickedManageUsers();
                }
            });
            p.add(mgUsrButton);
        }

            // reports
        if ( state.getRole().equals("OWNER") || state.getRole().equals("SELLER") || state.getRole().equals("CASHIER") ){
            JButton reportButton = new JButton("Reports");
            reportButton.setBounds(460,220,100,40); // 560
            reportButton.setFont(new Font("Arial", Font.PLAIN, 10));
            reportButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    LoggedInView.this.state.clickedReports();
                }
            });
            p.add(reportButton);
        }

        //the products scroll table

        if ( state.getRole().equals("SELLER") ) {
            //TODO: connect the table with database, this is a sample table
            String[][] data = { {"Mineral Water", "1001", "Drinks", "7", "2.5"},
                    {"Sprite", "1002", "Drinks", "7", "3"}};
            String[] columns = {"Item", "Code", "Category", "Quantity", "Price"};

            JTable productTable = new JTable(data, columns);
            JScrollPane scrollPane = new JScrollPane(productTable);
            scrollPane.setBounds(30, 10, 600, 200);
            p.add(scrollPane);
        }


        window.updateWindow(p);
    }


}
