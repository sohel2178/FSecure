package com.forbitbd.fsecure.ui.searchUser;


import com.forbitbd.fsecure.model.ShareVehicle;
import com.forbitbd.fsecure.model.User;

import java.util.List;

public interface SearchUserContract {

    interface Presenter{
        void loadData(String text);
        void shareClick(ShareVehicle shareVehicle,String uid,String vehicleId);
    }

    interface View{
        void renderAdapter(List<User> userList);
        void userSelect(User user);
        void showDialog();
        void shareDone(String uid);
    }
}
