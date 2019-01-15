package com.forbitbd.fsecure.ui.report;


import com.forbitbd.fsecure.api.model.RequestBody;

public interface ReportContract {

    interface Presenter{
        void requestForData(RequestBody requestBody);
        void updateTitle();
        void updateFuel();
        void destroy();
        void initialize();
    }

    interface View{
        void showDialog();
        void hideDialog();
        //void updateUI(List<RData> rDataList);
        void updateTitle();
        void updateFuel();
        void updateDistance(double distance);
        void updateFragment();
        void updateTravelTime(String travelTime);
        void visibleBottomNavigationView();
        void showToast(String message);
    }
}
