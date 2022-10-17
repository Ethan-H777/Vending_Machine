package vendingMachineSystem.view;

import vendingMachineSystem.controller.RestockState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RestockView extends AbstractView{
    private RestockState state;
    private Dimension size;
    private JTextField item;
    private JTextField code;
    private JTextField newName;
    private JTextField newCode;
    private JTextField newCategory;
    private JTextField newQuantity;
    private JTextField newPrice;

    public RestockView(RestockState state){
        this.state = state;
    }

    @Override
    public void display() {
        Window window = Window.getInstance();

        Panel p = new Panel();
        p.setLayout(null);
        JLabel pageLabel = new JLabel("Modify/Restock");
        size = pageLabel.getPreferredSize();
        pageLabel.setBounds(170, 30, size.width, size.height);
        p.add(pageLabel);

        // cancel
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setBounds(560,220,100,40);
        cancelButton.setFont(new Font("Arial", Font.PLAIN, 10));
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RestockView.this.state.changeToLoggedInState();
            }
        });
        p.add(cancelButton);

        //save
        JButton saveButton = new JButton("Save");
        size = saveButton.getPreferredSize();
        saveButton.setBounds(560,170, 100, 40);
        saveButton.setFont(new Font("Arial", Font.PLAIN, 10));
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RestockView.this.state.changeToLoggedInState();
            }
        });
        p.add(saveButton);

        JLabel itemLabel = new JLabel("Item name:");
        size = itemLabel.getPreferredSize();
        itemLabel.setBounds(70, 70, size.width, size.height);
        p.add(itemLabel);

        item = new JTextField(18);
        item.setBounds(140, 65, 150, 26);
        p.add(item);

        JButton searchNameButton = new JButton("Search by name");
        size = searchNameButton.getPreferredSize();
        searchNameButton.setBounds(140 + 150,65, size.width, size.height);

        searchNameButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO: find the item by the code and display
                showProductToModify(p);
            }

        });
        p.add(searchNameButton);

        JLabel codeLabel = new JLabel("Item code:");
        size = codeLabel.getPreferredSize();
        codeLabel.setBounds(70, 100, size.width, size.height);
        p.add(codeLabel);

        code = new JTextField(18);
        code.setBounds(140, 95, 150, 26);
        p.add(code);

        JButton searchButton = new JButton("Search by code");
        size = searchButton.getPreferredSize();
        searchButton.setBounds(140 + 150,95, size.width, size.height);

        searchButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO: find the item by the code and display
                showProductToModify(p);
            }

        });
        p.add(searchButton);

        showModifyButton(p);

        window.updateWindow(p);
    }

    public void showProductToModify(Panel p) {
        String[][] data = { {"Mineral Water", "1001", "Drinks", "7", "2.5"} };
        String[] columns = {"Item", "Code", "Category", "Quantity", "Price"};

        JTable productTable = new JTable(data, columns);
        JScrollPane scrollPane = new JScrollPane(productTable);
        scrollPane.setBounds(70, 95 + 30, 300, 40);
        p.add(scrollPane);
    }

    public void showModifyButton(Panel p) {
        JLabel newNameLabel = new JLabel("New name:");
        size = newNameLabel.getPreferredSize();
        newNameLabel.setBounds(70, 100 + 40 + 30, size.width, size.height);
        p.add(newNameLabel);
        newName = new JTextField(18);
        newName.setBounds(140, 165, 150, 26);
        p.add(newName);

        JLabel newCodeLabel = new JLabel("New code:");
        size = newCodeLabel.getPreferredSize();
        newCodeLabel.setBounds(70, 200, size.width, size.height);
        p.add(newCodeLabel);
        newCode = new JTextField(18);
        newCode.setBounds(140, 200-5, 150, 26);
        p.add(newCode);

        JLabel newCatLabel = new JLabel("New Category:");
        size = newCatLabel.getPreferredSize();
        newCatLabel.setBounds(300, 200, size.width, size.height);
        p.add(newCatLabel);
        newCategory = new JTextField(18);
        newCategory.setBounds(390, 200-5, 150, 26);
        p.add(newCategory);

        JLabel newQuaLabel = new JLabel("New Quantity:");
        size = newQuaLabel.getPreferredSize();
        newQuaLabel.setBounds(300, 170, size.width, size.height);
        p.add(newQuaLabel);
        newQuantity = new JTextField(18);
        newQuantity.setBounds(390, 170-5, 150, 26);
        p.add(newQuantity);

        JLabel newPriLabel = new JLabel("New Price:");
        size = newPriLabel.getPreferredSize();
        newPriLabel.setBounds(70, 230, size.width, size.height);
        p.add(newPriLabel);
        newPrice = new JTextField(18);
        newPrice.setBounds(140, 230-5, 150, 26);
        p.add(newPrice);
    }

}
