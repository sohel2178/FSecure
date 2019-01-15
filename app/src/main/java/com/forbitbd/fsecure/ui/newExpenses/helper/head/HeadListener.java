package com.forbitbd.fsecure.ui.newExpenses.helper.head;

import com.forbitbd.fsecure.model.Head;
import com.forbitbd.fsecure.model.Tran;

public interface HeadListener {

    void onHeadInserted(Head head);
    void transactionAdded(Tran transaction);
    void transactionUpdated(Tran transaction);
}
