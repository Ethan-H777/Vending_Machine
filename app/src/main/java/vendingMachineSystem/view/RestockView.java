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
    private boolean itemFound;
    private int itemIndex;

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

        //item table
        String[][] data = state.getItemNameList();
        String[] columns ={"Name", "Code"};

        JTable productTable = new JTable(data, columns);
        JScrollPane scrollPane = new JScrollPane(productTable);
        scrollPane.setBounds(450, 30, 200, 100);
        p.add(scrollPane);

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
                if (itemFound){
                    System.out.println("item find\n");
                    //update item
                    updateItem();
                }
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
                itemIndex = showProductToModify(p, "name");
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
                itemIndex = showProductToModify(p, "code");
            }

        });
        p.add(searchButton);

        showModifyButton(p);

        window.updateWindow(p);
    }

    public int showProductToModify(Panel p, String method) {
        int index = -1;

        if (method.equals("name")){
            for (int i = 0; i < state.getItemData().length; i++){
                if (state.getItemData()[i][1].equals(item.getText())){
                    index = i;
                }
            }

        } else if (method.equals("code")) {
            for (int i = 0; i < state.getItemData().length; i++){
                if (state.getItemData()[i][4].equals(code.getText())){
                    index = i;
                }
            }
        } else{
            //never reach this line
            System.out.println("either search by name or code!\n");
        }

        if (index == -1){
            System.out.println("Item not found.\n");
            itemFound = false;
            return index;
        }
        itemFound = true;

        String category = state.getItemData()[index][0];
        String name = state.getItemData()[index][1];
        String qty = state.getItemData()[index][2];
        String price = state.getItemData()[index][3];
        String id = state.getItemData()[index][4];

        String[][] data = {{id, category, name, qty, price}};
        String[] columns = {"Code", "Category", "Name", "Quantity", "Price"};
        JTable productTable = new JTable(data, columns);
        JScrollPane scrollPane = new JScrollPane(productTable);
        scrollPane.setBounds(70, 95 + 30, 300, 40);
        p.add(scrollPane);

        return index;
    }

    public void updateItem(){
        //check all text field empty or same as old one
        String name = checkTextField(newName.getText(), state.getItemData()[itemIndex][1]);
        String id = checkTextField(newCode.getText(), state.getItemData()[itemIndex][4]);
        String qty = checkTextField(newQuantity.getText(), state.getItemData()[itemIndex][2]);
        String category = checkTextField(newCategory.getText(), state.getItemData()[itemIndex][0]);
        String price = checkTextField(newPrice.getText(), state.getItemData()[itemIndex][3]);


        if (id.equals(state.getItemData()[itemIndex][4])){
            //item code is not changing so update item by id
            RestockView.this.state.updateItemByID(id, name, category, price, qty);
        } else{
            //the situation where seller wants to change all item info including id/code

            //update all info except code with original id
            RestockView.this.state.updateItemByID(state.getItemData()[itemIndex][4], name, category, price, qty);
            //after updating all other info, update the id by searching through new name
            RestockView.this.state.updateItemID(name, id);
        }

    }

    /** return the original string if input is same as original string
    return the original string if input is empty
    return input only when input is different from original **/
    public String checkTextField(String input, String original){
        //seller didn't enter new input
        if (input.equals("")) return original;

        if (input.equals(original)){
            return original;
        } else {
            return input;
        }

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
