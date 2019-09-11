package com.forbitbd.fsecure.ui.transactionAdd;

import android.util.Log;

import com.forbitbd.fsecure.api.DeviceClient;
import com.forbitbd.fsecure.api.ServiceGenerator;
import com.forbitbd.fsecure.api.accountModel.AccountGetResponse;
import com.forbitbd.fsecure.api.model.AccountReq;
import com.forbitbd.fsecure.api.transactionModel.PostTransaction;
import com.forbitbd.fsecure.model.Transaction;
import com.forbitbd.fsecure.api.transactionModel.TransactionPostResponse;
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

public class AddTransactionPresenter implements AddTransactionContract.Presenter {

    private AddTransactionContract.View mView;
    private FirebaseUser mCurrentUser;
    private MyDatabaseRef myDatabaseRef;

    public AddTransactionPresenter(AddTransactionContract.View mView) {
        this.mView = mView;
        this.mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        this.myDatabaseRef = MyDatabaseRef.getInstance();
    }

    @Override
    public void setToolbar() {
        mView.setToolbar();
    }

    @Override
    public void getAllAccounts() {
        if(mCurrentUser!=null){
            DeviceClient client = ServiceGenerator.createService(DeviceClient.class);

            Call<AccountGetResponse> call =client.getCustomerAccounts(new AccountReq(mCurrentUser.getUid()));

            call.enqueue(new Callback<AccountGetResponse>() {
                @Override
                public void onResponse(Call<AccountGetResponse> call, Response<AccountGetResponse> response) {
                    if(response.code()==200){
                        mView.updateToFromAdapter(response.body().getAccounts());
                    }
                }

                @Override
                public void onFailure(Call<AccountGetResponse> call, Throwable t) {

                }
            });
        }
    }

    @Override
    public void getAllDevices() {
        myDatabaseRef.getDeviceRef().orderByChild("uid").equalTo(mCurrentUser.getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        List<Vehicle> vehicleList = new ArrayList<>();

                        for (DataSnapshot x: dataSnapshot.getChildren()){
                            vehicleList.add(x.getValue(Vehicle.class));
                        }

                        mView.updateDeviceAdapter(vehicleList);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }

    @Override
    public boolean validate(Transaction transaction) {
        mView.clearPreError();

        Log.d("HHHHHHH",transaction.getFrom().getName());

        if(transaction.getInvoice_no().equals("")){
            mView.showError("Empty Field is not Allowed",1);
            return false;
        }

        if(transaction.getPurpose().equals("")){
            mView.showError("Empty Field is not Allowed",2);
            return false;
        }

        if(transaction.getAmount()<=0){
            mView.showError("Transaction Must be Greater Than 0",3);
            return false;
        }

        if(transaction.getFrom()==null){
            mView.showToast("Create Account Before Add Transaction");
            return false;
        }

        if(transaction.getTo()==null){
            mView.showToast("Create Account Before Add Transaction");
            return false;
        }

        if(transaction.getTo().get_id().equals(transaction.getFrom().get_id())){
            mView.showToast("Transaction is not Possible in Same Account");
            return false;
        }

        if(transaction.getDevice_id()==null){
            mView.showToast("Select Device before Adding Transaction");
            return false;
        }

        return true;
    }

    @Override
    public void saveTransaction(Transaction transaction) {
        if(mCurrentUser!=null){
            transaction.setCustomer_id(mCurrentUser.getUid());

            DeviceClient client = ServiceGenerator.createService(DeviceClient.class);
            Call<TransactionPostResponse> call = client.createTransaction(new PostTransaction(transaction));

            call.enqueue(new Callback<TransactionPostResponse>() {
                @Override
                public void onResponse(Call<TransactionPostResponse> call, Response<TransactionPostResponse> response) {
                    if(response.code()==201){
                        Transaction tran = response.body().getTransaction();
                        mView.finishActivity(tran);
                    }

                    Log.d("JJJJJ",response.code()+"");
                }

                @Override
                public void onFailure(Call<TransactionPostResponse> call, Throwable t) {
                    Log.d("JJJJJ",call.toString()+" ");
                    Log.d("JJJJJ",t.getMessage()+" ");
                }
            });
        }

    }
}
