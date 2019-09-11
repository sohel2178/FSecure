package com.forbitbd.fsecure.ui.signup;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.forbitbd.fsecure.R;
import com.forbitbd.fsecure.activities.PrebaseActivity;


public class SignUpActivity extends PrebaseActivity implements View.OnClickListener,SignUpContract.View {


    private EditText etEmail,etPassword;

    private Button btnSignUp;

    private SignUpPresenter mPresenter;

    private View borderEmail,borderPass;

    private ImageView ivFacebook,ivTwitter,ivLinkedIn,ivPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Hide Status Bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_sign_up);

        mPresenter = new SignUpPresenter(this);

        initView();
    }

    private void initView() {
        etEmail = findViewById(R.id.email);
        etPassword = findViewById(R.id.password);
        btnSignUp = findViewById(R.id.sign_up);
        btnSignUp.setOnClickListener(this);

        ivFacebook = findViewById(R.id.facebook);
        ivTwitter = findViewById(R.id.twitter);
        ivLinkedIn = findViewById(R.id.linked_in);
        ivPhone = findViewById(R.id.phone);

        ivFacebook.setOnClickListener(this);
        ivTwitter.setOnClickListener(this);
        ivLinkedIn.setOnClickListener(this);
        ivPhone.setOnClickListener(this);

        borderEmail = findViewById(R.id.email_bottom);
        borderPass = findViewById(R.id.pass_bottom);

        etEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b){
                    borderEmail.setVisibility(View.GONE);
                }else {
                    borderEmail.setVisibility(View.VISIBLE);
                }
            }
        });

        etPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b){
                    borderPass.setVisibility(View.GONE);
                }else {
                    borderPass.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.sign_up:
                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                boolean valid = mPresenter.isValid(email,password);

                if(!valid){
                    return;
                }

                mPresenter.signUpWithEmailAndPassword(email,password);
                break;

            case R.id.facebook:
                mPresenter.facebookClick();
                break;

            case R.id.twitter:
                mPresenter.twitterClick();
                break;

            case R.id.linked_in:
                mPresenter.linkedinClick();
                break;

            case R.id.phone:
                mPresenter.phoneClick();
                break;
        }


    }

    /*private void signUpWithEmailAndPassword(String email, String password) {

        showProgressDialog();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //hideProgressDialog();
                            // Sign in success, update UI with the signed-in user's information
                            //Log.d("HHHHH", "createUserWithEmail:success");
                            final FirebaseUser user = mAuth.getCurrentUser();

                            if(user!=null){

                                Log.d("HHHH","USer Found");

                                Log.d("HHHHH",user.isEmailVerified()+"");

                                user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            hideProgressDialog();
                                            FirebaseAuth.getInstance().signOut();
                                            SignUpActivity.this.finish();
                                        }else {
                                            hideProgressDialog();
                                            Toast.makeText(SignUpActivity.this, "Email Not Send", Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                });


                            }

                        } else {
                            hideProgressDialog();
                            // If sign in fails, display a message to the user.
                            Log.d("HHHHH", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(getApplicationContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // ...
                    }
                });
    }*/

    @Override
    public void showErrorToast(String message, int fieldId) {

        switch (fieldId){
            case 1:
                etEmail.requestFocus();
                break;

            case 2:
                etPassword.requestFocus();
                break;
        }

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showDialog() {
        showProgressDialog();
    }


    @Override
    public void hideDialog() {
        hideProgressDialog();
    }

    @Override
    public void complete() {
        hideProgressDialog();
        finish();
    }


    @Override
    public void openFacebookPage() {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.face_book_link)));
        startActivity(browserIntent);
    }

    @Override
    public void openTwitterPage() {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.twitter_link)));
        startActivity(browserIntent);
    }

    @Override
    public void openLinkedInPage() {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.linked_in_link)));
        startActivity(browserIntent);
    }

    @Override
    public void callCusmonerCare() {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", getString(R.string.mobi_phone), null));
        startActivity(intent);
    }
}
