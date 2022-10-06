package cz.comkop.bankexercise.main;

import cz.comkop.bankexercise.bank.Bank;
import cz.comkop.bankexercise.bank.BankOrder;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.format.DateTimeFormatter;

public class UI extends Thread {
    private JFrame frame = new JFrame("Bank Exercise");
    private JLabel timeLabel = new JLabel();
    private JLabel timeText = new JLabel("Time");
    private JLabel bankText = new JLabel("Bank");
    private JLabel openClosedText = new JLabel();
    private JLabel awaitingOrdersText = new JLabel("Awaiting orders");
    private JLabel processedOrdersText = new JLabel("Processed orders");
    private Container container = frame.getContentPane();
    private JTable awaitingOrdersTable;
    private JTable processedOrdersTable;
    private DefaultTableModel awaitingOrdersModel;
    private DefaultTableModel processedOrdersModel;
    private final String[] columnNamesAwaiting = new String[]{"Time", "ID", "Type", "Amount", "From", "To", "Balance"};
    private final String[] columnNamesProcessed = new String[]{"Time", "ID", "Type", "Amount", "From", "To", "Balance before", "Balance after"};
    private JScrollPane jScrollPaneAwaiting;
    private JScrollPane jScrollPaneProcessed;
    private JTextField nameTextField;
    private JTextField amountTextField;
    private JButton sendMoneyButton;
    private JLabel sendMoneyText = new JLabel("Send money");
    private Bank bank;
    private String[] sendMoneyData;



    public UI() {
        container.setLayout(null);
        setClock();
        setBankInfo();
        setTables();
        setMoneySender();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        container.add(timeText);
        container.add(timeLabel);
        container.add(bankText);
        container.add(openClosedText);
        container.add(awaitingOrdersText);
        container.add(processedOrdersText);
        container.add(jScrollPaneAwaiting);
        container.add(jScrollPaneProcessed);
        container.add(sendMoneyButton);
        container.add(sendMoneyText);
        container.add(amountTextField);
        container.add(nameTextField);
        frame.setSize(1900, 1000);
        frame.setVisible(true);
    }

    private void setTables() {
        awaitingOrdersText.setBounds(0, 40, 200, 20);
        processedOrdersText.setBounds(800, 40, 200, 20);
        awaitingOrdersModel = new DefaultTableModel(columnNamesAwaiting, 0);
        processedOrdersModel = new DefaultTableModel(columnNamesProcessed, 0);
        awaitingOrdersTable = new JTable(awaitingOrdersModel);
        processedOrdersTable = new JTable(processedOrdersModel);
        jScrollPaneAwaiting = new JScrollPane(awaitingOrdersTable);
        jScrollPaneAwaiting.setBounds(0, 60, 750, 300);
        jScrollPaneProcessed = new JScrollPane(processedOrdersTable);
        jScrollPaneProcessed.setBounds(800, 60, 1050, 800);
    }

    private void setBankInfo() {
        bankText.setBounds(725, 0, 50, 20);
        openClosedText.setBounds(725, 20, 50, 20);
    }

    private void setClock() {
        timeText.setBounds(1300, 0, 50, 20);
        timeLabel.setBounds(1300, 20, 100, 20);
    }

    private void setTableData() {
        awaitingOrdersTable.setModel(awaitingOrdersModel);
        processedOrdersTable.setModel(processedOrdersModel);
    }

    public boolean doesRowExist(int id) {
        for (int i = 0; i < awaitingOrdersModel.getRowCount(); i++) {
            if (awaitingOrdersModel.getValueAt(i, 1).equals(id)) {
                return true;
            }
        }
        return false;
    }

    public void setMoneySender(){
        amountTextField = new JTextField("Amount");
        nameTextField = new JTextField("Name");
        sendMoneyButton = new JButton("Send");
        sendMoneyText.setBounds(400,500,100,20);
        nameTextField.setBounds(400,520,100,20);
        amountTextField.setBounds(400,540,100,20);
        sendMoneyButton.setBounds(400,560,100,20);
        sendMoneyButton.addActionListener((e)->{
            sendMoneyData = new String[]{nameTextField.getText(),amountTextField.getText()};
            bank.sendMoney(sendMoneyData);
            //sendMoneyData = null;
        });
    }

    public void setAll(Bank bank){
        this.bank = bank;
}







    public void addRow(boolean processed, BankOrder bankOrder, long before, long after) {
        String from = bankOrder.getFrom() == null ? "" : bankOrder.getFrom().getOwner().getName();
        String to = bankOrder.getTo() == null ? "" : bankOrder.getTo().getOwner().getName();

        if (processed) {
            processedOrdersModel.addRow(new Object[]{bankOrder.getTime().format(DateTimeFormatter.ofPattern("HH:mm d.M.y")), bankOrder.getId(), bankOrder.getType(), bankOrder.getAmount(), from, to, before, after});
        } else {
            awaitingOrdersModel.addRow(new Object[]{bankOrder.getTime().format(DateTimeFormatter.ofPattern("HH:mm d.M.y")), bankOrder.getId(), bankOrder.getType(), bankOrder.getAmount(), from, to, bankOrder.getFrom().getBalance()});
        }
    }

    public void removeRow(int id) {
        for (int i = 0; i < awaitingOrdersModel.getRowCount(); i++) {
            if (awaitingOrdersModel.getValueAt(i, 1).equals(id)) {
                awaitingOrdersModel.removeRow(i);
                break;
            }
        }
    }

    public void changeBalance(BankOrder order) {
        for (int i = 0; i < awaitingOrdersModel.getRowCount(); i++) {
            if (order.getFrom() != null && awaitingOrdersModel.getValueAt(i, 4).equals(order.getFrom().getOwner().getName())) {
                awaitingOrdersModel.setValueAt(order.getFrom().getBalance(), i, 6);
            }
            if (order.getTo() != null && awaitingOrdersModel.getValueAt(i, 4).equals(order.getTo().getOwner().getName())) {
                awaitingOrdersModel.setValueAt(order.getTo().getBalance(), i, 6);
            }
        }
    }

    public void setTimeLabel(String s) {
        timeLabel.setText(s);
    }

    public void setOpenCloseLabel(String s) {
        openClosedText.setText(s);
    }


    @Override
    public void run() {

        while (true) {
            setTableData();
            try {
                sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
