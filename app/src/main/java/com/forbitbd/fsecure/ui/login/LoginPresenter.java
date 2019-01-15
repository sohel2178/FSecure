package com.forbitbd.fsecure.ui.login;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.Log;

import com.forbitbd.fsecure.model.User;
import com.forbitbd.fsecure.utility.MyUtil;
import com.forbitbd.fsecure.singleton.MyDatabaseRef;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class LoginPresenter implements LoginContract.Presenter {

    private LoginContract.View mView;
    private FirebaseAuth mAuth;

    public LoginPresenter(LoginContract.View mView) {
        this.mView = mView;
        this.mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public boolean validate(String email, String password) {
        if(email.equals("")){
            mView.showToastMessage("Empty Value Not Allowed",1);
            return false;
        }

        if(!MyUtil.isValidEmail(email)){
            mView.showToastMessage("Email is not Valid",1);
            return false;
        }

        if(password.equals("")){
            mView.showToastMessage("Empty Value Not Allowed",2);
            return false;
        }
        return true;
    }

    @Override
    public void signIn(String email, String password) {
        mView.showProDialog();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener((Activity) mView, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("HHHHH", "createUserWithEmail:success");
                            final FirebaseUser user = mAuth.getCurrentUser();

                            if(user.isEmailVerified()){
                                //hideProgressDialog();
                                Log.d("HHHHHH","Email Verified");

                                MyDatabaseRef.getInstance().getUserRef()
                                        .child(user.getUid())
                                        .addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                if(dataSnapshot.getValue()==null){
                                                    MyDatabaseRef.getInstance().getUserRef()
                                                            .child(user.getUid()).setValue(new User(user.getUid(),user.getEmail()), new DatabaseReference.CompletionListener() {
                                                        @Override
                                                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                                            mView.complete();
                                                        }
                                                    });

                                                }else{
                                                   mView.complete();
                                                }

                                            }

                                            @Override
                                            public void onCancelled(DatabaseError databaseError) {

                                            }
                                        });
                            }else {
                                mAuth.signOut();
                                mView.showVarificationDialog();
                            }


                        } else {
                            mView.hideProDialog();
                            mView.showToastMessage("Authentication Failed",5);
                        }

                        // ...
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
