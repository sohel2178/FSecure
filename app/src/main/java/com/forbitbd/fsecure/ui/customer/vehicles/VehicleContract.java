package com.forbitbd.fsecure.ui.customer.vehicles;

import com.forbitbd.fsecure.model.User;
import com.forbitbd.fsecure.model.Vehicle;
import com.google.firebase.database.Query;

public interface VehicleContract {

    interface Presenter{

        void getQuery(User user);
        void showAssignDialog();
        void unassign(Vehicle vehicle);

    }
    interface View{
        void updateAdapter(Query query);
        void showAssignDialog();
        void complete();
    }
}
