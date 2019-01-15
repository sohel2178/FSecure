package com.forbitbd.fsecure.ui.main;

public interface MainContract {

    interface Presenter{
        void checkAndStart();
        void checkCurrentUser();
    }

    interface View{
        void checkAndStart();
        void startLoginActivity();
        void loadHomeFragment();
    }
}
