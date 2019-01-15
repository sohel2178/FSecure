package com.forbitbd.fsecure.api.transModel;

import com.forbitbd.fsecure.model.Tran;
import com.forbitbd.fsecure.model.Transaction;

import java.util.List;

public class TranGetResponse {
    private int count;
    private List<Tran> transactions;

    public TranGetResponse() {
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<Tran> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Tran> transactions) {
        this.transactions = transactions;
    }
}
