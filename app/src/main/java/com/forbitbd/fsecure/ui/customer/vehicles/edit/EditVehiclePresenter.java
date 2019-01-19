package com.forbitbd.fsecure.ui.customer.vehicles.edit;

import com.forbitbd.fsecure.model.Vehicle;
import com.forbitbd.fsecure.singleton.MyDatabaseRef;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

public class EditVehiclePresenter implements EditVehicleContract.Presenter {

    private EditVehicleContract.View mView;

    public EditVehiclePresenter(EditVehicleContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void populateView(Vehicle vehicle) {
        mView.populateView(vehicle);
    }

    @Override
    public void updateVehicle(Vehicle vehicle) {
        MyDatabaseRef.getInstance()
                .getDeviceRef()
                .child(vehicle.getId())
                .setValue(vehicle, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                        mView.complete();
                    }
                });
    }

    @Override
    public boolean validate(Vehicle vehicle) {
        mView.clearPreError();

        if(vehicle.getDriver_name().equals("")){
            mView.setError("Empty Field is not Allowed",1);
            return false;
        }

        if(vehicle.getDriver_phone().equals("")){
            mView.setError("Empty Field is not Allowed",2);
            return false;
        }

        if(vehicle.getModel().equals("")){
            mView.setError("Empty Field is not Allowed",3);
            return false;
        }

        if(vehicle.getDevice_sim_number().equals("")){
            mView.setError("Empty Field is not Allowed",4);
            return false;
        }
        return true;
    }
}
