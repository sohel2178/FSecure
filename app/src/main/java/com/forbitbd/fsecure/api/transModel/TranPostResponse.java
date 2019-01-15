package com.forbitbd.fsecure.api.transModel;

import com.forbitbd.fsecure.model.Tran;
import com.forbitbd.fsecure.model.Transaction;

public class TranPostResponse {
    private String message;
    private Tran transaction;

    public TranPostResponse() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Tran getTransaction() {
        return transaction;
    }

    public void setTransaction(Tran transaction) {
        this.transaction = transaction;
    }
}
