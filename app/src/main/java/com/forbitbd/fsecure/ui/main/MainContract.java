package com.forbitbd.fsecure.ui.main;

import com.forbitbd.fsecure.model.User;
import com.google.firebase.auth.FirebaseUser;

public interface MainContract {

    interface Presenter{
        void checkAndStart();
        void checkCurrentUser();
    }

    interface View{
        void checkAndStart();
        void startLoginActivity();
        void initializeComponents();
        void logout();
    }
}
