package com.forbitbd.fsecure.ui.customer.info;

import com.forbitbd.fsecure.model.User;

public interface CustomerInfoContract {

    interface Presenter{
        void updateUI(User user);
        boolean validate(User user);
        void updateUser(User user);
    }

    interface View{
        void updateUI(User user);
        void showDialog();
        void hideDialog();
        void complete();

        void showErrorMessage(int fieldId, String message);
        void clearPreErrors();
    }
}
