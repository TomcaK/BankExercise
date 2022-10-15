package cz.comkop.bankexercise.bank;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class BankServer {
    private static BankServer bankServer;
    private List<BankOrder> awaitingBankOrders = Collections.synchronizedList(new LinkedList<>());

    public synchronized static BankServer getInstance() {
        if (bankServer == null) {
            bankServer = new BankServer();
        }
        return bankServer;
    }

    public void receive(BankOrder bankOrder) {
        awaitingBankOrders.add(bankOrder);
    }

    public BankOrder get(int index) {
        return awaitingBankOrders.get(index);
    }

    public boolean isEmpty() {
        return awaitingBankOrders.isEmpty();
    }

    public Integer size() {
        return awaitingBankOrders.size();
    }

    public List<BankOrder> getAwaitingBankOrders() {
        return Collections.unmodifiableList(awaitingBankOrders);
    }

    public synchronized void removeProcessedOrder() {
        List<BankOrder> collect = awaitingBankOrders.stream().filter(BankOrder::isProcessed).toList();
        collect.forEach(awaitingBankOrders::remove);
    }
}
