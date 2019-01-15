package com.forbitbd.fsecure.ui.admin.customers;

import com.forbitbd.fsecure.model.User;

import java.util.List;

public interface CustomerContract {

    interface Presenter{
        void requestForAllUser();
        void filterUser(List<User> userList,String filterText);

    }

    interface View{
        void updateUserList(List<User> userList);
        void addUser(User user);
        void startCustomerActivity(User user);
    }
}
