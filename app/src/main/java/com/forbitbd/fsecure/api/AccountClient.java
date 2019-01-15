package com.forbitbd.fsecure.api;



import android.arch.persistence.room.Delete;

import com.forbitbd.fsecure.api.headModel.HeadGetResponse;
import com.forbitbd.fsecure.api.headModel.HeadPostResponse;
import com.forbitbd.fsecure.api.transModel.PostTran;
import com.forbitbd.fsecure.api.transModel.TranGetResponse;
import com.forbitbd.fsecure.api.transModel.TranPostResponse;
import com.forbitbd.fsecure.api.transactionModel.PostTransaction;
import com.forbitbd.fsecure.model.Account;
import com.forbitbd.fsecure.api.accountModel.AccountGetResponse;
import com.forbitbd.fsecure.api.accountModel.AccountPostResponse;
import com.forbitbd.fsecure.api.model.AccountReq;
import com.forbitbd.fsecure.api.transactionModel.CustomerTransactionResponse;
import com.forbitbd.fsecure.model.Head;
import com.forbitbd.fsecure.model.Transaction;
import com.forbitbd.fsecure.api.transactionModel.TransactionPostResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface AccountClient {
    //http://localhost:5000/api/locations/getlocationsbyimeidate

    @POST("/accounts")
    Call<AccountPostResponse> createAccount(@Body Account account);

    @GET("/accounts")
    Call<AccountGetResponse> getAccounts();

    @POST("/accounts/get_customer_accounts")
    Call<AccountGetResponse> getCustomerAccounts(@Body AccountReq accountReq);

    @POST("/heads/get_customer_heads")
    Call<HeadGetResponse> getCustomerHeads(@Body AccountReq accountReq);

    @POST("/heads")
    Call<HeadPostResponse> createHead(@Body Head head);


    @POST("/transactions")
    Call<TransactionPostResponse> createTransaction(@Body PostTransaction postTransaction);

    @POST("/transactions/get_customer_transaction")
    Call<CustomerTransactionResponse> getCustomerTransactions(@Body AccountReq accountReq);

    @POST("/trans")
    Call<TranPostResponse> saveTransaction(@Body PostTran postTransaction);

    @PUT("/trans/{id}")
    Call<TranPostResponse> updateTransaction(@Path("id") String id, @Body PostTran postTransaction);

    @DELETE("/trans/{id}")
    Call<TranPostResponse> deleteTransaction(@Path("id") String id);

    @POST("/trans/get_customer_transaction")
    Call<TranGetResponse> getCustomerTrans(@Body AccountReq accountReq);

}
