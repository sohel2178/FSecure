package com.forbitbd.fsecure.ui.expenses.transaction;

import com.forbitbd.fsecure.api.DeviceClient;
import com.forbitbd.fsecure.api.ServiceGenerator;
import com.forbitbd.fsecure.api.model.AccountReq;
import com.forbitbd.fsecure.api.transactionModel.CustomerTransactionResponse;
import com.forbitbd.fsecure.model.Transaction;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TransactionPresenter implements TransactionContract.Presenter {

    private TransactionContract.View mView;
    private FirebaseUser mCurrentUser;

    public TransactionPresenter(TransactionContract.View mView) {
        this.mView = mView;
        this.mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
    }

    @Override
    public void getAllTransaction() {
        if(mCurrentUser!=null){
            DeviceClient client = ServiceGenerator.createService(DeviceClient.class);

            Call<CustomerTransactionResponse> call = client.getCustomerTransactions(new AccountReq(mCurrentUser.getUid()));

            call.enqueue(new Callback<CustomerTransactionResponse>() {
                @Override
                public void onResponse(Call<CustomerTransactionResponse> call, Response<CustomerTransactionResponse> response) {
                    if(response.code()==201){
                        for (Transaction x:response.body().getTransactions()){
                            mView.updateAdapter(x);
                        }
                    }
                }

                @Override
                public void onFailure(Call<CustomerTransactionResponse> call, Throwable t) {

                }
            });
        }
    }
}
