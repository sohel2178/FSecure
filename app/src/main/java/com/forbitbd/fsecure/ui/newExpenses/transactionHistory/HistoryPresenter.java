package com.forbitbd.fsecure.ui.newExpenses.transactionHistory;

import android.util.Log;

import com.forbitbd.fsecure.api.AccountClient;
import com.forbitbd.fsecure.api.AccountServiceGenerator;
import com.forbitbd.fsecure.api.headModel.HeadGetResponse;
import com.forbitbd.fsecure.api.model.AccountReq;
import com.forbitbd.fsecure.api.transModel.TranPostResponse;
import com.forbitbd.fsecure.model.Head;
import com.forbitbd.fsecure.model.Tran;
import com.forbitbd.fsecure.singleton.MyDatabaseRef;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryPresenter implements HistoryContract.Presenter {

    private HistoryContract.View mView;

    public HistoryPresenter(HistoryContract.View mView) {
        this.mView = mView;
    }


    @Override
    public void hideTransactionButton() {
        mView.hideTransactionButton();
    }

    @Override
    public void showTransactionButton() {
        mView.showTransactionButton();
    }

    @Override
    public void hideDeleteContainer() {
        mView.hideDeleteContainer();
    }

    @Override
    public void deleteTransaction(final Tran tran) {

        mView.showProgressBar();

        AccountClient client = AccountServiceGenerator.createService(AccountClient.class);
        Call<TranPostResponse> call = client.deleteTransaction(tran.get_id());

        call.enqueue(new Callback<TranPostResponse>() {
            @Override
            public void onResponse(Call<TranPostResponse> call, Response<TranPostResponse> response) {
                mView.hideProgressBar();

                if(response.code()==200){
                    mView.deletedTransaction(tran);
                    Log.d("HHHHHH",response.code()+" Code");
                }else {
                    Log.d("HHHHHH",response.code()+" Code");
                }


            }

            @Override
            public void onFailure(Call<TranPostResponse> call, Throwable t) {
                mView.hideProgressBar();
                Log.d("HHHHHH",t.getMessage()+" Message");

            }
        });


    }

    @Override
    public void initializeData() {
        mView.initData();
    }
}
