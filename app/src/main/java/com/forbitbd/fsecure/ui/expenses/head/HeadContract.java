package com.forbitbd.fsecure.ui.expenses.head;

import com.forbitbd.fsecure.model.Account;
import com.forbitbd.fsecure.model.Head;

public interface HeadContract {

    interface Presenter{
        void addButtonClick();
        void requestForGetAllHeads();
    }

    interface View{
        void startAddHeadActivity();
        void updateAdapter(Head head);
    }
}
