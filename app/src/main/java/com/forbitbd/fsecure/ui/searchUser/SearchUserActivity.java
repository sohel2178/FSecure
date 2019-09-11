package com.forbitbd.fsecure.ui.searchUser;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;


import com.forbitbd.fsecure.R;
import com.forbitbd.fsecure.activities.PrebaseActivity;
import com.forbitbd.fsecure.model.ShareVehicle;
import com.forbitbd.fsecure.model.User;
import com.forbitbd.fsecure.utility.Constant;

import java.util.List;

public class SearchUserActivity extends PrebaseActivity implements SearchUserContract.View,SearchView.OnQueryTextListener{

    private SearchUserPresenter mPresenter;

    private UserAdapter adapter;

    private String vehicleId;

    private RecyclerView rvUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_user);

        this.vehicleId = getIntent().getStringExtra(Constant.VEHICLE_ID);

        mPresenter = new SearchUserPresenter(this);
        adapter = new UserAdapter(this);

        setupToolbar();
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle(R.string.search_user);

        initView();
    }

    private void initView() {
        rvUsers = findViewById(R.id.rv_user);
        rvUsers.setLayoutManager(new LinearLayoutManager(this));
        rvUsers.setAdapter(adapter);

        SearchView mSearchView  = findViewById(R.id.search_view);
        mSearchView.setOnQueryTextListener(this);
    }


    @Override
    public void renderAdapter(List<User> userList) {
        adapter.setData(userList);
    }

    @Override
    public void userSelect(User user) {
        ShareVehicle shareVehicle = new ShareVehicle(1,vehicleId);
        mPresenter.shareClick(shareVehicle,user.getUid(),vehicleId);

        Log.d("HHHHH",user.getEmail());
        /*Intent intent = new Intent(getApplicationContext(), ProjectShareActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.USER,user);
        bundle.putString(Constant.PROJECT_ID,projectId);
        bundle.putString(Constant.PROJECT_NAME,projectName);
        intent.putExtras(bundle);
        startActivity(intent);*/
    }

    @Override
    public void showDialog() {
        showProgressDialog();
    }

    @Override
    public void shareDone(String uid) {
        hideProgressDialog();
        int position = adapter.getPosition(uid);
        UserAdapter.UserHolder holder = (UserAdapter.UserHolder) rvUsers.findViewHolderForAdapterPosition(position);
        holder.btnShare.setVisibility(View.GONE);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        adapter.clear();

        if(newText.length()==1){
           mPresenter.loadData(newText);
        }else{
            mPresenter.filter(newText);
        }
        return false;
    }
}
