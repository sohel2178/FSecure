package com.forbitbd.fsecure.ui.atCommand;

import com.forbitbd.fsecure.model.Vehicle;
import com.forbitbd.fsecure.singleton.MyDatabaseRef;
import com.forbitbd.fsecure.utility.MyUtil;

public class AtCommandPresenter implements ATCommandContract.Presenter {

    private ATCommandContract.View mView;
    private MyDatabaseRef myDatabaseRef;

    public AtCommandPresenter(ATCommandContract.View mView) {
        this.mView = mView;
        this.myDatabaseRef = MyDatabaseRef.getInstance();
    }

    @Override
    public void visibleUI(Vehicle vehicle) {
        if(vehicle.getDevice_sim_number()==null){
            mView.visiblePseudoLayout();
        }else {
            mView.visibleOriginalLayout();
        }

        if(vehicle.getFuelStatus()==null){
            mView.setStausText("FUEL Not Defined");
        }else {
            mView.setStausText("FUEL ".concat(vehicle.getFuelStatus()));
        }
    }

    @Override
    public boolean validate(String phoneNumber) {
        if(!MyUtil.isValidPhone(phoneNumber)){
            mView.showErrorMessage("This is not a Valid Phone Number",1);
        }
        return true;
    }

    @Override
    public void saveDevicePhoneNumber(Vehicle vehicle) {
        myDatabaseRef.getDeviceRef().child(vehicle.getId())
                .child("device_sim_number").setValue(vehicle.getDevice_sim_number());

        mView.visibleOriginalLayout();
    }

    @Override
    public void requestForCommand(int command, String phoneNumber) {
        mView.sendCommand(command,phoneNumber);
    }

    @Override
    public void sendMessage(String message, String phoneNumber, int command) {
        mView.showDialog();
        mView.sendMessage(message,phoneNumber,command);
    }

    @Override
    public void updateDatabase(int command,String vehicleID) {
        switch (command){
            case 0:
                myDatabaseRef.getDeviceRef().child(vehicleID)
                        .child("fuelStatus").setValue("ON");
                mView.setStausText("FUEL ON");
                break;
            case 1:
                myDatabaseRef.getDeviceRef().child(vehicleID)
                        .child("fuelStatus").setValue("OFF");

                mView.setStausText("FUEL OFF");
                break;

            case 2:

                break;
        }
    }
}
