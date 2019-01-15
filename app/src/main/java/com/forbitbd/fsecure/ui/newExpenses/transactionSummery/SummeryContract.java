package com.forbitbd.fsecure.ui.newExpenses.transactionSummery;

import com.forbitbd.fsecure.model.Tran;

import java.util.List;

public interface SummeryContract {

    interface Presenter{
        void processData(List<Tran> tranList);
    }

    interface View{
        void setCount(int number);
        void setTotal(double total);
    }
}
