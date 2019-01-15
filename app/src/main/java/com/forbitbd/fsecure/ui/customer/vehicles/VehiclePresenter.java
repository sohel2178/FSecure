package com.forbitbd.fsecure.ui.customer.vehicles;

import com.forbitbd.fsecure.model.User;
import com.forbitbd.fsecure.model.Vehicle;
import com.forbitbd.fsecure.singleton.MyDatabaseRef;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

public class VehiclePresenter implements VehicleContract.Presenter {

    private VehicleContract.View mView;

    public VehiclePresenter(VehicleContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void getQuery(User user) {
        Query query = MyDatabaseRef.getInstance().getDeviceRef().orderByChild("uid").equalTo(user.getUid());
        mView.updateAdapter(query);
    }

    @Override
    public void showAssignDialog() {
        mView.showAssignDialog();
    }

    @Override
    public void unassign(Vehicle vehicle) {
        MyDatabaseRef.getInstance().getDeviceRef()
                .child(vehicle.getId())
                .child("uid").setValue(null, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                mView.complete();
            }
        });
    }
}
