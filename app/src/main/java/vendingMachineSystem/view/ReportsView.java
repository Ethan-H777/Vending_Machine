
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
        int report_stagger = 0;
        int buttonWidth = 90;
        Dimension reportDim;
        Window window = Window.getInstance();

        Panel p = new Panel();
        p.setLayout(null);

        // label
        JLabel label = new JLabel(label_string);
        Dimension top_size = label.getPreferredSize();
        label.setBounds(0,0,top_size.width,top_size.height);
        report_stagger += top_size.height;
        p.add(label);

        // report buttons

        // change
        if (state.getRole().equals("OWNER") || state.getRole().equals("SELLER")) {
            JButton reportButtonCSV = new JButton("csv"); // button
            reportButtonCSV.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    ReportsView.this.state.changeReport(true);
                }
            });
            JLabel reportLabel = new JLabel("Change"); // label
            reportDim = reportLabel.getPreferredSize(); // txt
            JButton reportButtonTXT = new JButton("text");
            reportButtonTXT.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    ReportsView.this.state.changeReport(false);
                }
            });

            reportLabel.setBounds(0, report_stagger, reportDim.width, reportDim.height);
            reportButtonCSV.setBounds(reportDim.width + 10, report_stagger, buttonWidth, reportDim.height);
            reportButtonTXT.setBounds(reportDim.width + 20 + buttonWidth, report_stagger, buttonWidth, reportDim.height);
            p.add(reportLabel);
            p.add(reportButtonCSV);
            p.add(reportButtonTXT);
            report_stagger += reportDim.height;
        }

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
