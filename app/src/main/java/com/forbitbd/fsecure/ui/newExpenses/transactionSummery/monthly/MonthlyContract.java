package com.forbitbd.fsecure.ui.newExpenses.transactionSummery.monthly;

import com.forbitbd.fsecure.model.Tran;

import java.util.Date;
import java.util.List;

public interface MonthlyContract {

    interface Presenter{
        void updateMonthText();
        void processData(List<Tran> tranList, Date start,Date finish);
    }

    interface View{
        void updateMonth();
        void clearAdapter();
        void addItem(Tran tran);
    }
}
