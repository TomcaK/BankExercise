package cz.comkop.bankexercise.bank;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class BankServer {
    private static BankServer bankServer;
    private List<BankOrder> awaitingBankOrders = Collections.synchronizedList(new LinkedList<>());

    public synchronized static BankServer getInstance(){
        if (bankServer == null){
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

//    public void addOrder(Account to,int amount){
//        awaitingBankOrders.add(new BankOrder(0,amount,null,to, LocalDateTime.now()));
//    }

    public boolean operationComplete(int index) {
        awaitingBankOrders.remove(index);
        return true;
    }


}
