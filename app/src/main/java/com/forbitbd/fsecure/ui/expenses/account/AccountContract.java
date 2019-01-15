package com.forbitbd.fsecure.ui.expenses.account;

import com.forbitbd.fsecure.model.Account;

public interface AccountContract {

    interface Presenter{
        void addButtonClick();
        void requestForGetAllAccount();
    }

    interface View{
        void startAddAccountActivity();
        void updateAdapter(Account account);
    }
}
