package com.forbitbd.fsecure.ui.main;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;

import com.forbitbd.fsecure.activities.BaseActivity;
import com.forbitbd.fsecure.R;
import com.forbitbd.fsecure.ui.login.LoginActivity;
import com.forbitbd.fsecure.ui.main.alert.AlertFragment;
import com.forbitbd.fsecure.ui.main.home.HomeFragment;
import com.forbitbd.fsecure.ui.main.noInternet.NoInternetFragment;
import com.forbitbd.fsecure.ui.main.profile.ProfileFragment;
import com.forbitbd.fsecure.utility.Constant;
import com.forbitbd.fsecure.utility.MyUtil;
import com.mxn.soul.flowingdrawer_core.ElasticDrawer;
import com.mxn.soul.flowingdrawer_core.FlowingDrawer;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.IOException;

public class MainActivity extends BaseActivity implements MainContract.View{


    private MainPresenter mPresenter;

    private FlowingDrawer mDrawer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPresenter = new MainPresenter(this);
        mPresenter.checkAndStart();

        setupWindowAnimations();
    }

    @Override
    public void checkAndStart() {
        mPresenter.checkCurrentUser();
        /*if(!isOnline()){
            getSupportFragmentManager().beginTransaction().replace(R.id.main_container,new NoInternetFragment()).commit();
        }else{
            mPresenter.checkCurrentUser();
        }*/
    }

    private void setupDrawer(){

        mDrawer = (FlowingDrawer) findViewById(R.id.drawerlayout);
        mDrawer.setTouchMode(ElasticDrawer.TOUCH_MODE_BEZEL);
        mDrawer.setOnDrawerStateChangeListener(new ElasticDrawer.OnDrawerStateChangeListener() {
            @Override
            public void onDrawerStateChange(int oldState, int newState) {
                if (newState == ElasticDrawer.STATE_CLOSED) {
                    Log.i("MainActivity", "Drawer STATE_CLOSED");
                }
            }

            @Override
            public void onDrawerSlide(float openRatio, int offsetPixels) {
                Log.i("MainActivity", "openRatio=" + openRatio + " ,offsetPixels=" + offsetPixels);
            }
        });

    }

    @Override
    public void startLoginActivity() {
        finish();
        Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void loadHomeFragment() {
        //setUpNavigationDrawer();
        setupToolbar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportFragmentManager().beginTransaction().replace(R.id.main_container,new HomeFragment())
                .commit();

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
}
