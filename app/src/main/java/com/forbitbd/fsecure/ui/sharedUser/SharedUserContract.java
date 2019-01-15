package com.forbitbd.fsecure.ui.sharedUser;


import com.forbitbd.fsecure.model.SharedUser;

public interface SharedUserContract {

    interface Presenter{
        void requestForAllUsers(String vehicleId);
        void deleteSharedUser(SharedUser sharedUser, String vehicleId, int position);

    }

    interface View{
        void addSharedUser(SharedUser sharedUser);
        void userDeleted(int position);
    }
}
