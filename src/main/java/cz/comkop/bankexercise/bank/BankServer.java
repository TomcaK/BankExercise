package cz.comkop.bankexercise.bank;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class BankServer {
    private List<BankOrder> awaitingBankOrders = Collections.synchronizedList(new LinkedList<>());

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

    public boolean operationComplete(int index) {
        awaitingBankOrders.remove(index);
        return true;
    }


}
