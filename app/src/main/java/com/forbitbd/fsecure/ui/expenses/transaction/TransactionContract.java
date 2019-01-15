package com.forbitbd.fsecure.ui.expenses.transaction;

import com.forbitbd.fsecure.model.Transaction;

public interface TransactionContract {

    interface Presenter{
        void getAllTransaction();
    }

    interface View{
       void updateAdapter(Transaction transaction);
    }
}
