package com.forbitbd.fsecure.api;



import com.forbitbd.fsecure.api.model.AlartDeleteRequest;
import com.forbitbd.fsecure.api.model.AlartRequest;
import com.forbitbd.fsecure.api.model.AlartResponse;
import com.forbitbd.fsecure.api.model.Fence;
import com.forbitbd.fsecure.api.model.FenceR;
import com.forbitbd.fsecure.api.model.FenceReply;
import com.forbitbd.fsecure.api.model.ImeiReq;
import com.forbitbd.fsecure.api.model.MyData;
import com.forbitbd.fsecure.api.model.RUser;
import com.forbitbd.fsecure.api.model.RequestBody;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface DeviceClient {
    //http://localhost:5000/api/locations/getlocationsbyimeidate

    @POST("/api/locations/getlocationsbyimeidate")
    Call<MyData> getLocationData(@Body RequestBody data);

    @POST("/api/applyfence")
    Call<FenceR> applyFence(@Body Fence data);

    @POST("/api/getfencebyimei")
    Call<FenceR> getMyFence(@Body ImeiReq data);

    @POST("/api/deletefence")
    Call<FenceReply> deleteFence(@Body ImeiReq data);


    @POST("/api/customer/create")
    Call<FenceReply> resigterUser(@Body RUser rUser);

    @POST("/api/fencealertbyuid")
    Call<AlartResponse> getAllAlart(@Body AlartRequest alartRequest);

    @POST("/api/deletefencealert")
    Call<FenceReply> deleteFenceAlart(@Body AlartDeleteRequest deleteRequest);

}
