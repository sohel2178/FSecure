package com.forbitbd.fsecure.ui.accountAdd;

import com.forbitbd.fsecure.model.Account;

public interface AddAccountContract {

    interface Presenter{
        void updateTitle();

        boolean validate(Account account);

        void createAccount(Account account);
    }

    interface View{
        void updateTitle();
        void setAccount(Account account);
        void showToast(String message);
    }
}
