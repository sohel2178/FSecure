package com.forbitbd.fsecure.ui.monthlyReport;



import com.forbitbd.fsecure.api.model.MonthlyData;
import com.forbitbd.fsecure.model.MonthlyRBody;

import java.util.List;

import okhttp3.ResponseBody;

public interface MonthlyReportContract {

    interface Presenter{
        void requestMonthlyData(String imei, int year, int month);
        void updateMonthText();
        void updateFuelAndDistance();
        void requestForFile(MonthlyRBody monthlyRBody);
    }

    interface View{
        void showDialog();
        void hideDialog();
        void updateMonthText();
        void updateUI(List<MonthlyData> monthlyDataList);
        void updateFuelAndDistance();
        void showInfoFragment();

        String saveFile(ResponseBody body);
        void showToast(String message);
        void openFile(String filePath);
    }
}
