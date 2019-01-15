package com.forbitbd.fsecure.ui.newExpenses.transactionSummery;

import com.forbitbd.fsecure.model.Tran;

import java.util.List;

public class SummeryPresenter implements SummeryContract.Presenter {

    private SummeryContract.View mView;

    public SummeryPresenter(SummeryContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void processData(List<Tran> tranList) {
        mView.setCount(tranList.size());
        mView.setTotal(getTotal(tranList));
    }

    private List<Tran> getTran(){
        return null;
    }

    private double getTotal(List<Tran> tranList){
        double total =0;
        for (Tran x: tranList){
            total = total+x.getAmount();
        }
        return total;

    }
}
