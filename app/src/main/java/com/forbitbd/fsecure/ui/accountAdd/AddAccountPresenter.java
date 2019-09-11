package com.forbitbd.fsecure.ui.accountAdd;

import android.util.Log;

import com.forbitbd.fsecure.api.DeviceClient;
import com.forbitbd.fsecure.api.ServiceGenerator;
import com.forbitbd.fsecure.model.Account;
import com.forbitbd.fsecure.api.accountModel.AccountPostResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddAccountPresenter implements AddAccountContract.Presenter {

    private AddAccountContract.View mView;
    private FirebaseUser mCurrentUser;

    public AddAccountPresenter(AddAccountContract.View mView) {
        this.mView = mView;
        this.mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
    }

    @Override
    public void updateTitle() {
        mView.updateTitle();
    }

    @Override
    public boolean validate(Account account) {

        if(account.getName().equals("")){
            return false;
        }
        return true;
    }

    @Override
    public void createAccount(Account account) {
        account.setCustomer_id(mCurrentUser.getUid());
        DeviceClient client = ServiceGenerator.createService(DeviceClient.class);
        Call<AccountPostResponse> call=client.createAccount(account);

        call.enqueue(new Callback<AccountPostResponse>() {
            @Override
            public void onResponse(Call<AccountPostResponse> call, Response<AccountPostResponse> response) {
                if(response.code()==201){
                   Log.d("JJJJJJ",response.body().getMessage());
                   mView.setAccount(response.body().getAccount());
                }else{
                    mView.showToast("Account Already Exist");
                    Log.d("JJJJJJ",response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<AccountPostResponse> call, Throwable t) {
                Log.d("JJJJJJ","Failure Called");
                Log.d("JJJJJJ",t.getMessage()+"");

            }
        });
    }
}
