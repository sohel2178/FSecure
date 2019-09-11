package com.forbitbd.fsecure.ui.main;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;


import com.forbitbd.fsecure.activities.BaseActivity;
import com.forbitbd.fsecure.R;
import com.forbitbd.fsecure.ui.login.LoginActivity;
import com.forbitbd.fsecure.ui.main.alert.AlertFragment;
import com.forbitbd.fsecure.ui.main.home.HomeFragment;
import com.forbitbd.fsecure.ui.main.profile.ProfileFragment;
import com.forbitbd.fsecure.utility.Constant;
import com.forbitbd.fsecure.utility.MyUtil;
import com.google.android.gms.auth.api.Auth;
import com.google.firebase.auth.FirebaseAuth;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.IOException;



public class MainActivity extends BaseActivity implements MainContract.View{

    private MainPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPresenter = new MainPresenter(this);

        Bundle bundle = getIntent().getExtras();

        if(bundle!=null){
            if(bundle.getString(Constant.ALERT_TYPE)!=null){
                String alartType = bundle.getString(Constant.ALERT_TYPE);
                if(alartType.equals("Fencing")){
                    //Log.d("HHHHHHH",getIntent().getStringExtra(Constant.ALERT_TYPE));
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_container,new AlertFragment()).commit();
                }
            }

        }

        mPresenter.checkAndStart();

        setupWindowAnimations();
    }

    @Override
    public void checkAndStart() {
        mPresenter.checkCurrentUser();
    }


    @Override
    public void startLoginActivity() {
        finish();
        Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
        startActivity(intent);
    }



    @Override
    public void initializeComponents() {
        setUpNavigationDrawer();
        getSupportFragmentManager().beginTransaction().replace(R.id.main_container,new HomeFragment())
                .commit();

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), result.getUri());
                    Bitmap scaledBitMap = MyUtil.getScaledBitmap(bitmap,100,100);

                    if(getSupportFragmentManager().findFragmentById(R.id.main_container) instanceof ProfileFragment){
                        ProfileFragment profileFragment = (ProfileFragment) getSupportFragmentManager().findFragmentById(R.id.main_container);
                        profileFragment.setBitmap(scaledBitMap);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }





    @Override
    public void logout() {
        if(FirebaseAuth.getInstance().getCurrentUser().getProviderData().get(1).getProviderId().equals("google.com")){
            Auth.GoogleSignInApi.signOut(getGoogleApiClient());
        }
        FirebaseAuth.getInstance().signOut();
        finish();
        startActivity(new Intent(getApplicationContext(),LoginActivity.class));
    }
}
