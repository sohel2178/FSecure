package com.forbitbd.fsecure.ui.signup;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.forbitbd.fsecure.api.DeviceClient;
import com.forbitbd.fsecure.api.ServiceGenerator;
import com.forbitbd.fsecure.api.model.FenceReply;
import com.forbitbd.fsecure.api.model.RUser;
import com.forbitbd.fsecure.model.User;
import com.forbitbd.fsecure.utility.MyUtil;
import com.forbitbd.fsecure.singleton.MyDatabaseRef;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpPresenter implements SignUpContract.Presenter {

    private SignUpContract.View mView;
    private FirebaseAuth mAuth;
    private MyDatabaseRef myDatabaseRef;

    public SignUpPresenter(SignUpContract.View mView) {
        this.mView = mView;
        this.mAuth = FirebaseAuth.getInstance();
        this.myDatabaseRef = MyDatabaseRef.getInstance();
    }

    @Override
    public boolean isValid(String email, String password) {

        if(email.equals("")){
            mView.showErrorToast("Empty Email Field",1);
            return false;
        }

        if(!MyUtil.isValidEmail(email)){
            mView.showErrorToast("Email is not Valid",1);
            return false;
        }

        if(password.equals("")){
            mView.showErrorToast("Empty Password Field",2);
            return false;
        }

        if(password.length()<6){
            mView.showErrorToast("Password at least 6 character long",2);
            return false;
        }
        return true;
    }

    @Override
    public void signUpWithEmailAndPassword(final String email, final String password) {
        mView.showDialog();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener((Activity) mView, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //hideProgressDialog();
                            // Sign in success, update UI with the signed-in user's information
                            //Log.d("HHHHH", "createUserWithEmail:success");
                            final FirebaseUser user = mAuth.getCurrentUser();

                            if(user!=null){

                                user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            RUser rUser = new RUser();
                                            rUser.setEmail(email);
                                            rUser.setPassword(password);
                                            rUser.setUid(user.getUid());

                                            User fUser = new User(user.getUid(),user.getEmail());
                                            myDatabaseRef.getUserRef().child(user.getUid())
                                                    .setValue(fUser, new DatabaseReference.CompletionListener() {
                                                        @Override
                                                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                                            mView.complete();
                                                        }
                                                    });


                                        }else {
                                            mView.hideDialog();
                                            mView.showErrorToast("Failed to Send Varification Email",3);
                                        }

                                    }
                                });


                            }

                        } else {
                            mView.hideDialog();
                            mView.showErrorToast("Signup Failed",3);
                        }
                    }
                });
    }


    @Override
    public void facebookClick() {
        mView.openFacebookPage();
    }

    @Override
    public void twitterClick() {
        mView.openTwitterPage();
    }

    @Override
    public void linkedinClick() {
        mView.openLinkedInPage();
    }

    @Override
    public void phoneClick() {
        mView.callCusmonerCare();
    }
}
