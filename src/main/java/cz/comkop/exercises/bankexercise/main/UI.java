package cz.comkop.exercises.bankexercise.main;

import cz.comkop.exercises.bankexercise.bank.Bank;
import cz.comkop.exercises.bankexercise.bank.BankOrder;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class UI extends Thread {
    private final JFrame frame = new JFrame("Bank Exercise");
    private final JLabel clockText = new JLabel();
    private final JLabel timeText = new JLabel("Time");
    private final JLabel bankText = new JLabel("Bank");
    private final JLabel openClosedText = new JLabel();
    private final JLabel awaitingOrdersText = new JLabel("Awaiting orders");
    private final JLabel processedOrdersText = new JLabel("Processed orders");
    private final JLabel reportText = new JLabel();
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
    private final JLabel sendMoneyText = new JLabel("Send money");
    private Bank bank;
    private String[] createPaymentInputData;


    public UI() {
        Container container = frame.getContentPane();
        container.setLayout(null);
        setTextLabels();
        setTables();
        setMoneySender();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        container.add(reportText);
        container.add(timeText);
        container.add(clockText);
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

    public void setReportText(String s){
        reportText.setText(s);
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

    private void setTextLabels() {
        bankText.setBounds(725, 0, 50, 20);
        openClosedText.setBounds(725, 20, 50, 20);
        timeText.setBounds(1300, 0, 50, 20);
        clockText.setBounds(1300, 20, 100, 20);
        reportText.setBounds(5,400,1000,20);
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

    public void setMoneySender() {
        amountTextField = new JTextField("Amount");
        nameTextField = new JTextField("Name");
        sendMoneyButton = new JButton("Send");
        sendMoneyText.setBounds(400, 500, 100, 20);
        nameTextField.setBounds(400, 520, 100, 20);
        amountTextField.setBounds(400, 540, 100, 20);
        sendMoneyButton.setBounds(400, 560, 100, 20);
        sendMoneyButton.addActionListener((e) -> {
            createPaymentInputData = new String[]{nameTextField.getText(), amountTextField.getText()};
            bank.createPayment(createPaymentInputData);
        });
    }

    public void setInstances(Bank bank) {
        this.bank = bank;
    }



    public void addRow(boolean processed, BankOrder bankOrder, long before, long after) {
        String from = bankOrder.getFrom() == null ? "" : bankOrder.getFrom().getOwner().getName();
        String to = bankOrder.getTo() == null ? "" : bankOrder.getTo().getOwner().getName();
        if (processed) {
            processedOrdersModel.addRow(new Object[]{bankOrder.getTime().format(Time.TIME_FORMATTER), bankOrder.getId(), bankOrder.getType(), bankOrder.getAmount(), from, to, before, after});
        } else {
            awaitingOrdersModel.addRow(new Object[]{bankOrder.getTime().format(Time.DATE_TIME_FORMATTER), bankOrder.getId(), bankOrder.getType(), bankOrder.getAmount(), from, to, bankOrder.getFrom().getBalance()});
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

    public void checkBalance(String name, long balance) {
        for (int i = 0; i < awaitingOrdersModel.getRowCount(); i++) {
            if (awaitingOrdersModel.getValueAt(i, 4).equals(name)  && Long.parseLong(awaitingOrdersModel.getValueAt(i, 6).toString()) != balance){
                awaitingOrdersModel.setValueAt(balance, i, 6);
            }
        }

//        for (int i = 0; i < awaitingOrdersModel.getRowCount(); i++) {
//            if (bankOrder.getType().equals(BankOrder.OrderType.PAYMENT)) {
//                if (awaitingOrdersModel.getValueAt(i, 5).equals(bankOrder.getFrom().getOwner().getName()) || awaitingOrdersModel.getValueAt(i, 5).equals(bankOrder.getTo().getOwner().getName())){
//
//                }
//                awaitingOrdersModel.setValueAt(bankOrder.getFrom().getBalance(), i, 6);
//            }
//            if (bankOrder.getType().equals(BankOrder.OrderType.DEPOSIT) && awaitingOrdersModel.getValueAt(i, 4).equals(bankOrder.getTo().getOwner().getName())) {
//                awaitingOrdersModel.setValueAt(bankOrder.getTo().getBalance(), i, 6);
//            }
//        }
    }

    public void setClockText(String s) {
        clockText.setText(s);
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
