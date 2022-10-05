package cz.comkop.bankexercise.main;

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
    private String[] columnNamesAwaiting = new String[]{"Time", "ID", "Type", "Amount", "From", "To", "Balance"};
    private String[] columnNamesProcessed = new String[]{"Time", "ID", "Type", "Amount", "From", "To", "Balance before", "Balance after"};
    private JScrollPane jScrollPaneAwaiting;
    private JScrollPane jScrollPaneProcessed;


    public UI() {
        container.setLayout(null);
        setClock();
        setBankInfo();
        setTables();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        container.add(timeText);
        container.add(timeLabel);
        container.add(bankText);
        container.add(openClosedText);
        container.add(awaitingOrdersText);
        container.add(processedOrdersText);
        container.add(jScrollPaneAwaiting);
        container.add(jScrollPaneProcessed);
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
