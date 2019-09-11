package com.forbitbd.fsecure.ui.expenses.head;

import com.forbitbd.fsecure.api.DeviceClient;
import com.forbitbd.fsecure.api.ServiceGenerator;
import com.forbitbd.fsecure.api.headModel.HeadGetResponse;
import com.forbitbd.fsecure.api.model.AccountReq;
import com.forbitbd.fsecure.model.Head;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HeadPresenter implements HeadContract.Presenter {

    private HeadContract.View mView;
    private FirebaseUser mCurrentUser;

    public HeadPresenter(HeadContract.View mView) {
        this.mView = mView;
        this.mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
    }

    @Override
    public void addButtonClick() {
        mView.startAddHeadActivity();
    }

    @Override
    public void requestForGetAllHeads() {

        if(mCurrentUser!=null){
            String uid = mCurrentUser.getUid();

            DeviceClient client = ServiceGenerator.createService(DeviceClient.class);

            Call<HeadGetResponse> call = client.getCustomerHeads(new AccountReq(uid));

            call.enqueue(new Callback<HeadGetResponse>() {
                @Override
                public void onResponse(Call<HeadGetResponse> call, Response<HeadGetResponse> response) {

                    if(response.code()==200){
                        for (Head x: response.body().getHeads()){
                            mView.updateAdapter(x);
                        }
                    }
                }

                @Override
                public void onFailure(Call<HeadGetResponse> call, Throwable t) {

                }
            });
        }

    }
}
