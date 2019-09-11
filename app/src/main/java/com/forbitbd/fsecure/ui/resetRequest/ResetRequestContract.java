package com.forbitbd.fsecure.ui.resetRequest;

public interface ResetRequestContract {

    interface Presenter{
        boolean validate(String email);
        void sendResetRequest(String email);

        void facebookClick();
        void twitterClick();
        void linkedinClick();
        void phoneClick();
    }

    interface View {
        void clearPreErrors();
        void hideDialog();
        void showDialog();
        void showMessageDialog();

        void showErrorMessage(int fieldId, String message);

        void openFacebookPage();
        void openTwitterPage();
        void openLinkedInPage();
        void callCusmonerCare();
    }
}
