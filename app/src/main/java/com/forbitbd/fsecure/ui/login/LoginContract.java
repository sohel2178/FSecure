package com.forbitbd.fsecure.ui.login;

public interface LoginContract {

    interface Presenter{
        boolean validate(String email, String password);
        void signIn(String email, String password);
        void facebookClick();
        void twitterClick();
        void linkedinClick();
        void phoneClick();
    }

    interface View{
        void showProDialog();
        void hideProDialog();
        void showToastMessage(String message, int fieldId);
        void showVarificationDialog();
        void complete();

        void openFacebookPage();
        void openTwitterPage();
        void openLinkedInPage();
        void callCusmonerCare();

    }
}
