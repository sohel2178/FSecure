package com.forbitbd.fsecure.ui.searchUser;


import com.forbitbd.fsecure.model.ShareVehicle;
import com.forbitbd.fsecure.model.User;

public interface SearchUserContract {

    interface Presenter{
        void requestForData(String value, int before, int after);
        void shareClick(ShareVehicle shareVehicle, String uid, String projectId);
    }

    interface View{
        void updateData(User user, String val);
        void updateAdapter(String val);
        void shearClick(User user);
        void shareDone(String uid);
        void showDialog();
    }
}
