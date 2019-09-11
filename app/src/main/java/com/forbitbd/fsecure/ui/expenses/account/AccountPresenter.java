package com.forbitbd.fsecure.ui.expenses.account;

import com.forbitbd.fsecure.api.DeviceClient;
import com.forbitbd.fsecure.api.ServiceGenerator;
import com.forbitbd.fsecure.model.Account;
import com.forbitbd.fsecure.api.accountModel.AccountGetResponse;
import com.forbitbd.fsecure.api.model.AccountReq;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountPresenter implements AccountContract.Presenter {

    private AccountContract.View mView;
    private FirebaseUser mCurrentUser;

    public AccountPresenter(AccountContract.View mView) {
        this.mView = mView;
        this.mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
    }

    @Override
    public void addButtonClick() {
        mView.startAddAccountActivity();
    }

    @Override
    public void requestForGetAllAccount() {
        if(mCurrentUser!=null){
            String uid = mCurrentUser.getUid();

            DeviceClient client = ServiceGenerator.createService(DeviceClient.class);

            Call<AccountGetResponse> call = client.getCustomerAccounts(new AccountReq(uid));

            call.enqueue(new Callback<AccountGetResponse>() {
                @Override
                public void onResponse(Call<AccountGetResponse> call, Response<AccountGetResponse> response) {

                    if(response.code()==200){
                        for (Account x: response.body().getAccounts()){
                            mView.updateAdapter(x);
                        }
                    }
                }

                @Override
                public void onFailure(Call<AccountGetResponse> call, Throwable t) {

                }
            });
        }
    }
}
