package com.forbitbd.fsecure.ui.main.nav;

import com.forbitbd.fsecure.model.User;

public interface NavContract {
    interface Presenter{
        void updateNav();
        void itemClick(int action);
    }

    interface View{
        void updateNav(User user);
        void loadHomeFragment();
        void startAdminActivity();
        void loadProfile();
        void loadNotification();
        void loadAlert();
        void loadPayment();
        void startExpenseActivity();
        void contactUs();
        void logout();
    }
}
