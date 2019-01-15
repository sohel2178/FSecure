package com.forbitbd.fsecure.ui.admin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.forbitbd.fsecure.R;
import com.forbitbd.fsecure.ui.admin.customers.CustomerFragment;
import com.forbitbd.fsecure.ui.main.home.HomeFragment;

public class AdminActivity extends AdminBaseActivity implements AdminContract.View {

    private AdminPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        mPresenter = new AdminPresenter(this);

        setUpNavigationDrawer();

        if(savedInstanceState==null){
            mPresenter.loadCustomerFragment();
        }
    }

    @Override
    public void loadCustomerFragment() {
        getSupportFragmentManager().beginTransaction().replace(R.id.main_container,new CustomerFragment())
                .commit();
    }

    public void setTitle(String title){
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle(title);
    }
}
