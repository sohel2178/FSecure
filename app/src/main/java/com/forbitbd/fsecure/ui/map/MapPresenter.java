package com.forbitbd.fsecure.ui.map;

import android.util.Log;

import com.forbitbd.fsecure.api.DeviceClient;
import com.forbitbd.fsecure.api.ServiceGenerator;
import com.forbitbd.fsecure.api.model.Fence;
import com.forbitbd.fsecure.api.model.FenceR;
import com.forbitbd.fsecure.api.model.FenceReply;
import com.forbitbd.fsecure.api.model.ImeiReq;
import com.forbitbd.fsecure.model.FireData;
import com.forbitbd.fsecure.singleton.MyDatabaseRef;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.iid.FirebaseInstanceId;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapPresenter implements MapContract.Presenter {
    private MapContract.View mView;
    private DatabaseReference deviceRef;


    public MapPresenter(MapContract.View mView) {
        this.mView = mView;

    }

    @Override
    public void initView() {
        mView.initView();
    }

    @Override
    public void start() {
        if(deviceRef!=null){
            deviceRef.addChildEventListener(listener);
        }
    }

    @Override
    public void stop() {
        if(deviceRef!=null){
            deviceRef.removeEventListener(listener);
        }
    }

    @Override
    public void startListenFromDevice(String deviceId) {
        this.deviceRef = MyDatabaseRef.getInstance().getDeviceRef().child(deviceId);
        deviceRef.addChildEventListener(listener);
    }

    @Override
    public void requestForFence(String deviceId) {
        ImeiReq imeiReq = new ImeiReq();
        imeiReq.setImei(deviceId);
        DeviceClient deviceClient = ServiceGenerator.createService(DeviceClient.class);

        Call<FenceR> call = deviceClient.getMyFence(imeiReq);

        call.enqueue(new Callback<FenceR>() {
            @Override
            public void onResponse(Call<FenceR> call, Response<FenceR> response) {
                FenceR fenceR = response.body();

                if(fenceR!=null){
                    if(fenceR.isSuccess()){
                        Fence fence = fenceR.getFence();
                        mView.drawFence(fence);
                    }else{
                        //mView.showToast("No Fence Data Found");
                    }
                }


            }

            @Override
            public void onFailure(Call<FenceR> call, Throwable t) {
                mView.showToast("Something Happen Wrong");
            }
        });
    }

    @Override
    public void applyFence(Fence fence) {
        String token = FirebaseInstanceId.getInstance().getToken();
        Log.d("HHHH",token);

        if(token!=null){
            fence.setUser_token(token);

            DeviceClient deviceClient = ServiceGenerator.createService(DeviceClient.class);

            Call<FenceR> call = deviceClient.applyFence(fence);

            call.enqueue(new Callback<FenceR>() {
                @Override
                public void onResponse(Call<FenceR> call, Response<FenceR> response) {
                    FenceR fenceR = response.body();

                    if(fenceR!=null){
                        if(fenceR.isSuccess()){
                            Fence fence = fenceR.getFence();
                            mView.drawFence(fence);
                        }else{
                            mView.showToast("No Fence Data Found");
                        }
                    }
                }

                @Override
                public void onFailure(Call<FenceR> call, Throwable t) {
                    mView.showToast("Something Happen Wrong");
                }
            });

        }
    }

    @Override
    public void removeFence(String deviceId) {
        ImeiReq imeiReq = new ImeiReq();
        imeiReq.setImei(deviceId);
        DeviceClient deviceClient = ServiceGenerator.createService(DeviceClient.class);

        Call<FenceReply> call = deviceClient.deleteFence(imeiReq);
        call.enqueue(new Callback<FenceReply>() {
            @Override
            public void onResponse(Call<FenceReply> call, Response<FenceReply> response) {
                FenceReply fenceReply = response.body();

                if(fenceReply!=null){
                    if(fenceReply.isSuccess()){
                        mView.removeFence();
                    }
                }
            }

            @Override
            public void onFailure(Call<FenceReply> call, Throwable t) {
                mView.showToast("Something Happen Wrong");
            }
        });
    }

    private ChildEventListener listener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            if(dataSnapshot.getValue()!=null){
                if(dataSnapshot.getKey().equals("Data")){
                    FireData fireData = dataSnapshot.getValue(FireData.class);
                    mView.setVehicleData(fireData);
                }
            }
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            if(dataSnapshot.getKey().equals("Data")){
                FireData fireData = dataSnapshot.getValue(FireData.class);
                mView.updateCurrentVehicle(fireData);

                Log.d("JJJJJJ","Called");

            }
        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {

        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };
}
