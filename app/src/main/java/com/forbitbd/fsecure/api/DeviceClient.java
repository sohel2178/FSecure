package com.forbitbd.fsecure.api;



import com.forbitbd.fsecure.api.accountModel.AccountGetResponse;
import com.forbitbd.fsecure.api.accountModel.AccountPostResponse;
import com.forbitbd.fsecure.api.headModel.HeadGetResponse;
import com.forbitbd.fsecure.api.headModel.HeadPostResponse;
import com.forbitbd.fsecure.api.model.AccountReq;
import com.forbitbd.fsecure.api.model.AlartDeleteRequest;
import com.forbitbd.fsecure.api.model.AlartRequest;
import com.forbitbd.fsecure.api.model.AlartResponse;
import com.forbitbd.fsecure.api.model.Fence;
import com.forbitbd.fsecure.api.model.FenceR;
import com.forbitbd.fsecure.api.model.FenceReply;
import com.forbitbd.fsecure.api.model.ImeiReq;
import com.forbitbd.fsecure.api.model.MonthlyReaponse;
import com.forbitbd.fsecure.api.model.MonthlyRequestBody;
import com.forbitbd.fsecure.api.model.MyData;
import com.forbitbd.fsecure.api.model.RUser;
import com.forbitbd.fsecure.api.model.RequestBody;
import com.forbitbd.fsecure.api.transModel.PostTran;
import com.forbitbd.fsecure.api.transModel.TranGetResponse;
import com.forbitbd.fsecure.api.transModel.TranPostResponse;
import com.forbitbd.fsecure.api.transactionModel.CustomerTransactionResponse;
import com.forbitbd.fsecure.api.transactionModel.PostTransaction;
import com.forbitbd.fsecure.api.transactionModel.TransactionPostResponse;
import com.forbitbd.fsecure.model.Account;
import com.forbitbd.fsecure.model.Head;
import com.forbitbd.fsecure.model.MonthlyRBody;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface DeviceClient {
    //http://localhost:5000/api/locations/getlocationsbyimeidate

    @POST("/api/locations/getlocationsbyimeidate")
    Call<MyData> getLocationData(@Body RequestBody data);

    @POST("/api/locations/monthly")
    Call<MonthlyReaponse> getMonthlyData(@Body MonthlyRequestBody body);

    @POST("/api/pdf")
    Call<ResponseBody> getFile(@Body MonthlyRBody monthlyData);

    @POST("/api/fences/applyfence")
    Call<FenceR> applyFence(@Body Fence data);

    @POST("/api/fences/getfencebyimei")
    Call<FenceR> getMyFence(@Body ImeiReq data);

    @POST("/api/fences/deletefence")
    Call<FenceReply> deleteFence(@Body ImeiReq data);


    @POST("/api/fencealerts/fencealertbyuid")
    Call<AlartResponse> getAllAlart(@Body AlartRequest alartRequest);

    @POST("/api/fencealerts/deletefencealert")
    Call<FenceReply> deleteFenceAlart(@Body AlartDeleteRequest deleteRequest);



    // Account and Transaction Api
    @POST("/api/accounts")
    Call<AccountPostResponse> createAccount(@Body Account account);

    @GET("/api/accounts")
    Call<AccountGetResponse> getAccounts();

    @POST("/api/accounts/get_customer_accounts")
    Call<AccountGetResponse> getCustomerAccounts(@Body AccountReq accountReq);

    @POST("/api/heads/get_customer_heads")
    Call<HeadGetResponse> getCustomerHeads(@Body AccountReq accountReq);

    @POST("/api/heads")
    Call<HeadPostResponse> createHead(@Body Head head);


    @POST("/api/transactions")
    Call<TransactionPostResponse> createTransaction(@Body PostTransaction postTransaction);

    @POST("/api/transactions/get_customer_transaction")
    Call<CustomerTransactionResponse> getCustomerTransactions(@Body AccountReq accountReq);

    @POST("/api/trans")
    Call<TranPostResponse> saveTransaction(@Body PostTran postTransaction);

    @PUT("/api/trans/{id}")
    Call<TranPostResponse> updateTransaction(@Path("id") String id, @Body PostTran postTransaction);

    @DELETE("/api/trans/{id}")
    Call<TranPostResponse> deleteTransaction(@Path("id") String id);

    @POST("/api/trans/get_customer_transaction")
    Call<TranGetResponse> getCustomerTrans(@Body AccountReq accountReq);

}
