package com.forbitbd.fsecure.ui.newExpenses.transactionSummery.headwise;

import com.forbitbd.fsecure.model.Tran;
import com.forbitbd.fsecure.model.Vehicle;

import java.util.List;

public interface HeadContract {

    interface Presenter{
        void processData(List<Tran> tranList);
        void filterData(List<Tran> tranList,Vehicle vehicle);
    }

    interface View{
        void clearAdapter();
        void addItem(MyHead myHead);
    }
}
