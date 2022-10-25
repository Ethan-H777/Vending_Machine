package vendingMachineSystem.controller;


import vendingMachineSystem.VendingMachine;
import vendingMachineSystem.view.LoggedInView;
import vendingMachineSystem.view.ReportsView;

import java.io.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class ReportsState extends VendingMachineState {

        private String role;

        public String getRole(){return role;}
        public ReportsState(VendingMachine vm, String role){
            super(vm);
            this.role = role;
        }

    @Override
    public void run(){
        ReportsView view = new ReportsView(this);
        view.display();
    }

    public void clickedCancel(){
        vm.setState( new LoggedInState(vm, role) );
    }

    void output_csv(String filename, String plaintext){
        File file = new File(filename);
        try
        {
            file.createNewFile();
            BufferedWriter file_w = new BufferedWriter(new FileWriter(filename, true));
            file_w.append(plaintext);
            file_w.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void changeReport(boolean is_csv) {
        // colnames
        String outString = "name,value,quantity\n";

        // append contents
        String[][] cashData = super.getCashData();
        for ( int i =0; i < cashData.length; i++){
            for ( int j = 0; j < cashData[i].length; j++){
                outString = outString + cashData[i][j];
                if ( j == cashData[i].length-1 ){ // last
                    outString = outString + "\n";
                } else{
                    outString = outString + ",";
                }
            }

        }
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String filename = new SimpleDateFormat("yyyyMMdd HH:mm:ss.S").format(timestamp) + "_change";
        if (is_csv){
            filename = filename +".csv";
        } else {
            filename = filename + ".txt";
        }
        output_csv(filename, outString);
    }


}
