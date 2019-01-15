package com.forbitbd.fsecure.ui.expenses;

public interface ExpenseContract {

    interface Presenter{
        void updateTitle(String title);
        void loadHead();
        void loadTrans();
        void loadAccount();
        void loadTransaction();
        void loadReport();
    }

    interface View{
        void updateTitle(String title);

        void loadHeadFragment();
        void loadTransFragment();
        void loadAccountFragment();
        void loadTransactionFragment();
        void loadReportFragment();
    }
}
