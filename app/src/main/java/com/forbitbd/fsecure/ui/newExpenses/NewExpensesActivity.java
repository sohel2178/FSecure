package com.forbitbd.fsecure.ui.newExpenses;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.forbitbd.fsecure.R;
import com.forbitbd.fsecure.activities.PrebaseActivity;
import com.forbitbd.fsecure.model.Head;
import com.forbitbd.fsecure.model.Tran;
import com.forbitbd.fsecure.model.Vehicle;
import com.forbitbd.fsecure.ui.newExpenses.helper.transaction.TranUtil;
import com.forbitbd.fsecure.ui.newExpenses.transactionHistory.TransactionHistoryFragment;
import com.forbitbd.fsecure.ui.newExpenses.transactionSummery.TransactionSummeryFragment;

import java.util.ArrayList;
import java.util.List;

public class NewExpensesActivity extends PrebaseActivity implements ExpensesContract.View{
    
    private ExpensesPresenter mPresenter;

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;

    private List<Vehicle> vehicleList;
    private List<Head> headList;
    private List<Tran> tranList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_expenses);
        mPresenter = new ExpensesPresenter(this);
        mPresenter.setToolBar();

        this.vehicleList = new ArrayList<>();
        this.headList = new ArrayList<>();



        mPresenter.getVehicleList();
       // mPresenter.getHeadList();
        mPresenter.getAllTransactions();
        



    }

    private void initView() {
        viewPager = findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                // Todo Dome Cod Goes Here
              //  pushData(position);

                switch (position){
                    case 1:
                        //Todo Do Same thing For Summery Fragment
                        TransactionSummeryFragment tsf = (TransactionSummeryFragment) adapter.getItem(position);
                        tsf.initUpdateData();
                        //tsf.setVehicleList(this.vehicleList);
                        break;
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void setupViewPager(ViewPager viewPager) {
        if(adapter==null){
            adapter = new ViewPagerAdapter(getSupportFragmentManager());
        }
        adapter.addFragment(new TransactionHistoryFragment(), "Transaction History");
        adapter.addFragment(new TransactionSummeryFragment(), "Transaction Summery");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void setToolBar() {
        setupToolbar();
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Expenses");
        
    }

    @Override
    public void setVehicleList(List<Vehicle> vehicleList) {
        this.vehicleList = vehicleList;

    }

    @Override
    public void setHeadList(List<Head> headList) {
        this.headList=headList;

        initView();

    }

    @Override
    public void setTransactions(List<Tran> transactionList) {
        this.tranList = transactionList;
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
    public void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    public List<Vehicle> getVehicleList() {
        return vehicleList;
    }

    public List<Head> getHeadList() {
        return headList;
    }

    public List<Tran> getTranList() {
        return tranList;
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
