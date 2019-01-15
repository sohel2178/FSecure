package com.forbitbd.fsecure.ui.expenses;

import android.util.Log;

import com.forbitbd.fsecure.singleton.MyDatabaseRef;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ExpensePresenter implements ExpenseContract.Presenter {
    private ExpenseContract.View mView;

    public ExpensePresenter(ExpenseContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void updateTitle(String title) {
        mView.updateTitle(title.concat(" Accounts"));
    }

    @Override
    public void loadHead() {
        mView.loadHeadFragment();
    }

    @Override
    public void loadTrans() {
        mView.loadTransFragment();
    }

    @Override
    public void loadAccount() {
        mView.loadAccountFragment();
    }

    @Override
    public void loadTransaction() {
        mView.loadTransactionFragment();
    }

    @Override
    public void loadReport() {
        mView.loadReportFragment();
    }
}
