package com.forbitbd.fsecure.ui.newExpenses.helper.head;

import android.util.Log;

import com.forbitbd.fsecure.api.AccountClient;
import com.forbitbd.fsecure.api.AccountServiceGenerator;
import com.forbitbd.fsecure.api.headModel.HeadPostResponse;
import com.forbitbd.fsecure.model.Head;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HeadPresenter implements HeadContract.Presenter {

    private HeadContract.View mView;
    private FirebaseUser mFirebaseUser;

    public HeadPresenter(HeadContract.View mView) {
        this.mView = mView;
        this.mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    }

    @Override
    public void onCancelClick() {
        mView.dismissDialog();
    }

    @Override
    public boolean validate(Head head) {
        mView.clearPreError();
        if(head.getName().equals("")){
            mView.showError("Empty Field is not Allowed",1);
            return false;
        }
        return true;
    }

    @Override
    public void saveHead(final Head head) {
        if(mFirebaseUser!=null){
            head.setCustomer_id(mFirebaseUser.getUid());

            mView.showProgressBar();

            AccountClient client = AccountServiceGenerator.createService(AccountClient.class);

            Call<HeadPostResponse> call = client.createHead(head);
            call.enqueue(new Callback<HeadPostResponse>() {
                @Override
                public void onResponse(Call<HeadPostResponse> call, Response<HeadPostResponse> response) {
                    if(response.code()==201){
                        mView.hideProgressBar();
                        Head resHead = response.body().getHead();
                        mView.setHead(resHead);
                    }else if(response.code()==401){
                        mView.hideProgressBar();
                        mView.showError(head.getName()+" Head Already Exist",1);
                    }
                }

                @Override
                public void onFailure(Call<HeadPostResponse> call, Throwable t) {
                    Log.d("HHHHH","CALL ERROR");
                    Log.d("HHHHH","CALL ERROR "+t.getMessage());
                }
            });


        }
    }
}
