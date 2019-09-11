package com.forbitbd.fsecure.ui.report;

import android.util.Log;


import com.forbitbd.fsecure.api.DeviceClient;
import com.forbitbd.fsecure.api.ServiceGenerator;
import com.forbitbd.fsecure.api.model.MyData;
import com.forbitbd.fsecure.api.model.RData;
import com.forbitbd.fsecure.api.model.RequestBody;
import com.forbitbd.fsecure.model.VehicleStatus;
import com.forbitbd.fsecure.singleton.RawFData;
import com.forbitbd.fsecure.utility.Haversine;
import com.forbitbd.fsecure.utility.MyUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class ReportPresenter implements ReportContract.Presenter {
    private ReportContract.View mView;
    private Subscription distSubs,runningTimeSubs,filterSubs;
    private static final int THRESHOLD=40;

    public ReportPresenter(ReportContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void requestForData(RequestBody requestBody) {
        mView.showDialog();
        DeviceClient client = ServiceGenerator.createService(DeviceClient.class);
        Call<MyData> call = client.getLocationData(requestBody);

        call.enqueue(new Callback<MyData>() {
            @Override
            public void onResponse(Call<MyData> call,final Response<MyData> response) {
                mView.hideDialog();

                if(response.body().isSuccess()){
                    //mView.updateUI(response.body().getLocations());

                    if(response.body().getCount()<2){
                        mView.showToast("No Data Found");

                        return;
                    }

                    //Log.d("JJJJJ",response.body().);
                    Log.d("JJJJJ",response.body().getLocations().size()+"");

                    //Observable<List<RData>> filObs = Observable.from(response.body().getLocations());


                    Observable<List<RData>> filObs = Observable.fromCallable(new Callable<List<RData>>() {
                        @Override
                        public List<RData> call() throws Exception {
                            return getFilteredData(response.body().getLocations());
                        }
                    });

                    filterSubs = filObs.subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Action1<List<RData>>() {
                                @Override
                                public void call(List<RData> rDataList) {
                                    Log.d("JJJJJ",rDataList.size()+"");
                                    RawFData.getInstance().setData(rDataList);
                                    mView.visibleBottomNavigationView();

                                    Observable<Double> disObs = Observable.fromCallable(new Callable<Double>() {
                                        @Override
                                        public Double call() throws Exception {
                                            return getDistance(RawFData.getInstance().getData());
                                        }
                                    });

                                    distSubs = disObs.subscribeOn(Schedulers.io())
                                            .observeOn(AndroidSchedulers.mainThread())
                                            .subscribe(new Action1<Double>() {
                                                @Override
                                                public void call(Double aDouble) {
                                                    mView.updateDistance(aDouble);
                                                    mView.updateFuel();
                                                    mView.updateFragment();
                                                }
                                            });

                                            Observable<String> runningTimeObs = Observable.fromCallable(new Callable<String>() {
                                                @Override
                                                public String call() throws Exception {
                                                    return getRunningTime(RawFData.getInstance().getData());
                                                }
                                            });

                                            runningTimeSubs = runningTimeObs.subscribeOn(Schedulers.io())
                                                    .observeOn(AndroidSchedulers.mainThread())
                                                    .subscribe(new Action1<String>() {
                                                        @Override
                                                        public void call(String s) {
                                                            mView.updateTravelTime(s);
                                                        }
                                            });
                                }
                            });
                }else {
                    mView.showToast("No Data Found");
                }

            }

            @Override
            public void onFailure(Call<MyData> call, Throwable t) {
                mView.hideDialog();
                mView.showToast("Error Happen in the Fetching Data");
            }
        });
    }

    @Override
    public void updateTitle() {
        mView.updateTitle();
    }

    @Override
    public void updateFuel() {
        mView.updateFuel();
    }

    @Override
    public void destroy() {

        if (filterSubs != null) {
            filterSubs.unsubscribe();
        }
        if (distSubs != null) {
            distSubs.unsubscribe();
        }

        if (runningTimeSubs != null) {
            runningTimeSubs.unsubscribe();
        }
    }

    @Override
    public void initialize() {
        mView.updateTravelTime(null);
        mView.updateDistance(0);
    }

    private double getDistance(List<RData> rDataList){
        return MyUtil.getDistanceFrom(rDataList);
    }

    private String getRunningTime(List<RData> rDataList){

        List<VehicleStatus> vehicleStatuses = getVehicleStatusList(rDataList);

        long duration = 0;
        for (VehicleStatus x: vehicleStatuses){
            if (x.getStatus().equals("1")){
                duration = duration+(x.getEndTime()-x.getStartTime());

            }
        }

        duration = duration/1000;

        String val ="";

        if(duration>=3600){
            int hour = (int) (duration/3600);
            int min = (int) (duration%60);

            val = hour+" hr "+min+" min";
        }else if(duration<3600 && duration>=60){
            int min = (int) (duration/60);
            int sec = (int) (duration-min*60);
            val = min+" min "+sec+" sec";
        }else {
            val = duration+" sec";
        }

        return val;
    }

    private List<RData> getFilteredData(List<RData> rDataList ){
        /*List<RData> retList = new ArrayList<>();

        if(rDataList.size()>2){
            RData fd = rDataList.get(0);
            retList.add(fd);

            for (RData x: rDataList){
               if(rDataList.indexOf(x)!=0){
                   double d = Haversine.distance(fd.getLat(),fd.getLng(),x.getLat(),x.getLng());

                   if(d>=THRESHOLD){
                       retList.add(x);
                       fd = x;
                   }

               }
            }
        }

        retList.add(rDataList.get(rDataList.size()-1));*/

        return rDataList;
    }

    private List<VehicleStatus> getVehicleStatusList(List<RData> rDataList){

        RData initialFData = new RData();
        initialFData.setServerTime(MyUtil.getBeginingTime(rDataList.get(0).getServerTime()));
        initialFData.setStatus("0");

        List<VehicleStatus> vehicleStatusList = new ArrayList<>();

        for(int i=0;i<rDataList.size();i++){

            RData rData = rDataList.get(i);

            if(!rData.getStatus().equals(initialFData.getStatus())){
                VehicleStatus vehicleStatus = new VehicleStatus();
                vehicleStatus.setStartTime(initialFData.getServerTime());
                vehicleStatus.setEndTime(rData.getServerTime());
                vehicleStatus.setStatus(initialFData.getStatus());
                vehicleStatusList.add(vehicleStatus);

                initialFData = rData;
            }
        }

        return vehicleStatusList;
    }



}
