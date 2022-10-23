package vendingMachineSystem.view;

import vendingMachineSystem.controller.FillCashState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FillCashView extends AbstractView{
    private FillCashState state;
    private Dimension size;
    private JTextField cash;
    private JTextField newQty;

    public FillCashView(FillCashState state){
        this.state = state;
    }

    @Override
    public void display() {
        Window window = Window.getInstance();

        Panel p = new Panel();
        p.setLayout(null);
        JLabel pageLabel = new JLabel("Cash Re-Fill");
        size = pageLabel.getPreferredSize();
        pageLabel.setBounds(170, 30, size.width, size.height);
        p.add(pageLabel);

        //cash table
        String[][] data = state.getCashData();
        String[] columns ={"Name", "Value", "Quantity"};

        JTable productTable = new JTable(data, columns);
        JScrollPane scrollPane = new JScrollPane(productTable);
        scrollPane.setBounds(350, 10, 250, 150);
        p.add(scrollPane);



        // cancel
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setBounds(560,220,100,40);
        cancelButton.setFont(new Font("Arial", Font.PLAIN, 10));
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FillCashView.this.state.changeToLoggedInState();
            }
        });
        p.add(cancelButton);

//        JLabel noteLabel = new JLabel("Cash ($)        ");
        JLabel noteLabel = new JLabel("Cash ($ or c)");
        size = noteLabel.getPreferredSize();
        noteLabel.setBounds(70, 70, size.width, size.height);
        p.add(noteLabel);

        cash = new JTextField(18);
        cash.setBounds(70 + size.width, 65, 97, 26);
        p.add(cash);

        //search button
        JButton searchButton = new JButton("Select");
        size = searchButton.getPreferredSize();
        searchButton.setBounds(250,65, size.width, size.height);

        searchButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
//                System.out.println(cash.getText());

                for (int i = 0; i < state.getCashData().length; i++){

                    if (state.getCashData()[i][0].equals(cash.getText())){
                        String qty = state.getCashData()[i][2];
                        String name = state.getCashData()[i][0];
                        String[][] data = { {qty} };
                        String[] columns = {name};

                        JTable cashTable = new JTable(data, columns);
                        JScrollPane scrollPane = new JScrollPane(cashTable);
                        scrollPane.setBounds(70, 70 + 30, 40, 40);
                        p.add(scrollPane);
                    }
                }


            }

        });
        p.add(searchButton);

        //new quantity
        JLabel qtyLabel = new JLabel("New Quantity");
        size = qtyLabel.getPreferredSize();
        qtyLabel.setBounds(70, 100 + 40, size.width, size.height);
        p.add(qtyLabel);

        newQty = new JTextField(18);
        newQty.setBounds(70 + size.width, 140-5, 100, 26);
        p.add(newQty);

        //save
        JButton saveButton = new JButton("Save");
        size = saveButton.getPreferredSize();
        saveButton.setBounds(250 ,140-5, size.width, size.height);
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FillCashView.this.state.changeToLoggedInState();
                //modify database
//                state.updateCash(cash.getText(), newQty.getText());
            }
        });
        p.add(saveButton);



        window.updateWindow(p);
    }
}
