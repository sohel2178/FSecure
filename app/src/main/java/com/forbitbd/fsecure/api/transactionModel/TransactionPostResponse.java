package com.forbitbd.fsecure.api.transactionModel;

import com.forbitbd.fsecure.model.Transaction;

public class TransactionPostResponse {
    private String message;
    private Transaction transaction;

    public TransactionPostResponse() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }
}
