package com.forbitbd.fsecure.api.transactionModel;

import com.forbitbd.fsecure.model.Account;
import com.forbitbd.fsecure.model.Transaction;

import java.util.Date;

public class PostTransaction {

    private String _id;
    private Date date;
    private String purpose;
    private String device_id;
    private String customer_id;
    private String invoice_no;
    private double amount;
    private String from;
    private String to;

    public PostTransaction(Transaction transaction) {
        this._id = transaction.get_id();
        this.date = transaction.getDate();
        this.purpose = transaction.getPurpose();
        this.device_id = transaction.getDevice_id();
        this.customer_id = transaction.getCustomer_id();
        this.invoice_no = transaction.getInvoice_no();
        this.amount = transaction.getAmount();
        this.from = transaction.getFrom().get_id();
        this.to = transaction.getTo().get_id();
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public String getInvoice_no() {
        return invoice_no;
    }

    public void setInvoice_no(String invoice_no) {
        this.invoice_no = invoice_no;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }
}
