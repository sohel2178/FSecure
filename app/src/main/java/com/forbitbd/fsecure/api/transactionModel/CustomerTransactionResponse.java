package com.forbitbd.fsecure.api.transactionModel;

import com.forbitbd.fsecure.model.Transaction;

import java.util.List;

public class CustomerTransactionResponse {
    private int count;
    private List<Transaction> transactions;

    public CustomerTransactionResponse() {
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }
}
