package com.forbitbd.fsecure.ui.newExpenses;


import com.forbitbd.fsecure.api.AccountClient;
import com.forbitbd.fsecure.api.AccountServiceGenerator;
import com.forbitbd.fsecure.api.headModel.HeadGetResponse;
import com.forbitbd.fsecure.api.model.AccountReq;
import com.forbitbd.fsecure.api.transModel.TranGetResponse;
import com.forbitbd.fsecure.model.Head;
import com.forbitbd.fsecure.model.Tran;
import com.forbitbd.fsecure.model.Vehicle;
import com.forbitbd.fsecure.singleton.MyDatabaseRef;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExpensesPresenter implements ExpensesContract.Presenter {

    private ExpensesContract.View mView;
    private FirebaseUser mFirebaseUser;
    private MyDatabaseRef myDatabaseRef;

    public ExpensesPresenter(ExpensesContract.View mView) {
        this.mView = mView;
        this.mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        this.myDatabaseRef = MyDatabaseRef.getInstance();
    }

    @Override
    public void setToolBar() {
        mView.setToolBar();
    }

    @Override
    public void getVehicleList() {
        myDatabaseRef.getDeviceRef().orderByChild("uid").equalTo(mFirebaseUser.getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        List<Vehicle> vehicleList = new ArrayList<>();

                        for (DataSnapshot x: dataSnapshot.getChildren()){
                            vehicleList.add(x.getValue(Vehicle.class));
                        }

                        mView.setVehicleList(vehicleList);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }

    @Override
    public void getHeadList() {
        if(mFirebaseUser!=null){
            AccountClient client = AccountServiceGenerator.createService(AccountClient.class);

            Call<HeadGetResponse> call = client.getCustomerHeads(new AccountReq(mFirebaseUser.getUid()));

            call.enqueue(new Callback<HeadGetResponse>() {
                @Override
                public void onResponse(Call<HeadGetResponse> call, Response<HeadGetResponse> response) {
                    if(response.code()==200){
                        mView.hideDialog();
                        List<Head> headList = response.body().getHeads();
                        mView.setHeadList(headList);
                        //Log.d("JJJJJJJJ","fdf "+response.code());
                    }else {
                        mView.hideDialog();
                       // Log.d("JJJJJJJJ","fdf "+response.code());
                    }
                }

                @Override
                public void onFailure(Call<HeadGetResponse> call, Throwable t) {
                    mView.hideDialog();
                    //Log.d("JJJJJJJJ","Error "+t.getMessage());
                }
            });

        }
    }

    @Override
    public void getAllTransactions() {
        mView.showDialog();

        if(mFirebaseUser!=null){
            AccountClient client = AccountServiceGenerator.createService(AccountClient.class);

            Call<TranGetResponse> call = client.getCustomerTrans(new AccountReq(mFirebaseUser.getUid()));

            call.enqueue(new Callback<TranGetResponse>() {
                @Override
                public void onResponse(Call<TranGetResponse> call, Response<TranGetResponse> response) {
                    if(response.code()==201){

                        List<Tran> tranList = response.body().getTransactions();
                        mView.setTransactions(tranList);
                        getHeadList();
                    }else{
                        mView.hideDialog();
                        mView.showToast("Something Happen Wrong");
                    }
                }

                @Override
                public void onFailure(Call<TranGetResponse> call, Throwable t) {
                    mView.hideDialog();
                    mView.showToast("Error: "+t.getMessage());
                    //Log.d("JJJJJJ","Error: "+t.getMessage());
                }
            });

        }

    }
}
