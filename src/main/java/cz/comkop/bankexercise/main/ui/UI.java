package cz.comkop.bankexercise.main.ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;

public class UI extends Thread{
    private JFrame frame = new JFrame("Bank Exercise");
    private JLabel timeLabel = new JLabel();
    private JLabel timeText = new JLabel("Time");
    private JLabel bankText = new JLabel("Bank");
    private JLabel openClosedText = new JLabel();
    private JLabel awaitingOrdersText = new JLabel("Awaiting orders");
    private JLabel processedOrdersText = new JLabel("Processed orders");
    private Container container = frame.getContentPane();
    private JTable awaitingOrdersTable ;
    private JTable processedOrdersTable ;
    private DefaultTableModel awaitingOrdersModel ;
    private DefaultTableModel processedOrdersModel;
    private String[][] awaitingOrdersData;
    private String[][]processedOrdersData;
    private String[] columnNames = new String[]{"ID","Type","Amount","From","TO"};


    public UI() {
        container.setLayout(null);
        timeText.setBounds(450,0,50,20);
        timeLabel.setBounds(450,20,50,20);
        bankText.setBounds(250,0,50,20);
        openClosedText.setBounds(250,20,50,20);
        awaitingOrdersText.setBounds(0,40,200,20);
        processedOrdersText.setBounds(250,40,200,20);
        awaitingOrdersModel = new DefaultTableModel(columnNames,0);
        processedOrdersModel = new DefaultTableModel(columnNames,0);
        awaitingOrdersTable = new JTable(awaitingOrdersModel);
        processedOrdersTable = new JTable(processedOrdersModel);
        awaitingOrdersTable.setBounds(0,60,250,250);
        processedOrdersTable.setBounds(250,60,250,250);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        container.add(timeText);
        container.add(timeLabel);
        container.add(bankText);
        container.add(openClosedText);
        container.add(awaitingOrdersText);
        container.add(processedOrdersText);
        container.add(awaitingOrdersTable);
        container.add(processedOrdersTable);

//        String data[][]={ {"101","Amit","670000"},
//                {"102","Jai","780000"},
//                {"101","Sachin","700000"}};
//        String column[]={"ID","NAME","SALARY"};
//
//        jt.setBounds(0,0,200,300);
//        JScrollPane sp=new JScrollPane(jt);
//        container.add(jt);
//        container.add(sp);

        frame.setSize(500, 500);
        frame.setVisible(true);
    }

    private void setTableData(){
        awaitingOrdersModel.addRow(new Object[]{5,"Deposit",5000,"Tomas","Lukas"});
    }
    public void setTimeLabel(String s) {
        timeLabel.setText(s);
    }

    public void setOpenCloseLabel(String s) {
        openClosedText.setText(s);
    }


    @Override
    public void run() {
        while (true){
            setTableData();
            try {
                sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
