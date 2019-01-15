package com.forbitbd.fsecure.ui.transactionAdd;

import com.forbitbd.fsecure.model.Account;
import com.forbitbd.fsecure.model.Transaction;
import com.forbitbd.fsecure.model.Vehicle;

import java.util.List;

public interface AddTransactionContract {

    interface Presenter{
        void setToolbar();
        void getAllAccounts();
        void getAllDevices();
        boolean validate(Transaction transaction);
        void saveTransaction(Transaction transaction);

    }

    interface View{
        void setToolbar();
        void clearPreError();
        void showError(String message,int field);
        void showToast(String message);


        void updateToFromAdapter(List<Account> accountList);
        void updateDeviceAdapter(List<Vehicle> vehicleList);

        void finishActivity(Transaction transaction);
    }
}
