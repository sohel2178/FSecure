package com.forbitbd.fsecure.ui.atCommand;

import com.forbitbd.fsecure.model.Vehicle;

public interface ATCommandContract {

    interface Presenter{

        void visibleUI(Vehicle vehicle);
        boolean validate(String phoneNumber);
        void saveDevicePhoneNumber(Vehicle vehicle);
        void requestForCommand(int command,String phoneNumber);
        void sendMessage(String message,String phoneNumber,int command);
        void updateDatabase(int command,String vehicleID);

    }

    interface View{
        void visiblePseudoLayout();
        void visibleOriginalLayout();
        void showErrorMessage(String message,int field);
        void setStausText(String message);
        void sendCommand(int command,String phoneNumber);
        void sendMessage(String message,String phoneNumber,int command);
        void showDialog();
    }
}
