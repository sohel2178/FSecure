package com.forbitbd.fsecure.ui.signup;


import com.forbitbd.fsecure.api.model.RUser;

public interface SignUpContract {

    interface Presenter{
        boolean isValid(String email, String password);
        void signUpWithEmailAndPassword(String email, String password);
        //void createUser(RUser rUser);

        void facebookClick();
        void twitterClick();
        void linkedinClick();
        void phoneClick();
    }

    interface View{
        void showErrorToast(String message, int fieldId);
        void showDialog();
        void hideDialog();
        void complete();

        void openFacebookPage();
        void openTwitterPage();
        void openLinkedInPage();
        void callCusmonerCare();
    }
}
