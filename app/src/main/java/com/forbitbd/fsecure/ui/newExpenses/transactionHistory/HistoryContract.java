package com.forbitbd.fsecure.ui.newExpenses.transactionHistory;

import com.forbitbd.fsecure.model.Head;
import com.forbitbd.fsecure.model.Tran;

import java.util.List;

public interface HistoryContract {

    interface Presenter{
        //void getAllHeads();
        void hideTransactionButton();
        void showTransactionButton();
        void hideDeleteContainer();
        void deleteTransaction(Tran tran);
        void initializeData();



    }

    interface View{
        void initData();
        void hideTransactionButton();
        void showTransactionButton();

        void deletedTransaction(Tran tran);
        void onItemClick(Tran tran);
        void onItemLongClick(Tran tran);

        void hideDeleteContainer();

        void showProgressBar();
        void hideProgressBar();
    }
}
