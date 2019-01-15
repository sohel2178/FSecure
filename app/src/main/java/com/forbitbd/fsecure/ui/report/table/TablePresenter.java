package com.forbitbd.fsecure.ui.report.table;

public class TablePresenter implements TableContract.Presenter {
    private TableContract.View mView;

    public TablePresenter(TableContract.View mView) {
        this.mView = mView;
    }
}
