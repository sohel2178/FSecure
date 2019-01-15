package com.forbitbd.fsecure.ui.newExpenses;

import com.forbitbd.fsecure.model.Head;
import com.forbitbd.fsecure.model.Tran;
import com.forbitbd.fsecure.model.Vehicle;

import java.util.List;

public interface ExpensesContract {

    interface Presenter{
        void setToolBar();
        void getVehicleList();
        void getHeadList();
        void getAllTransactions();
    }

    interface View{
        void setToolBar();
        void setVehicleList(List<Vehicle> vehicleList);
        void setHeadList(List<Head> headList);
        void setTransactions(List<Tran> transactionList);
        void showDialog();
        void hideDialog();
        void showToast(String message);
    }
}
