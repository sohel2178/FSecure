package com.forbitbd.fsecure.ui.newExpenses.transactionSummery.deviceWise;

import com.forbitbd.fsecure.model.Head;
import com.forbitbd.fsecure.model.Tran;
import com.forbitbd.fsecure.model.Vehicle;
import com.forbitbd.fsecure.ui.newExpenses.transactionSummery.headwise.MyHead;

import java.util.List;

public interface DeviceContract {

    interface Presenter{
        void processData(List<Tran> tranList);
        void setVehicleList(List<Vehicle> vehicleList);
        void filterData(List<Tran> tranList, Head head);
    }

    interface View{
        void clearAdapter();
        void addItem(MyHead myHead);
    }
}
