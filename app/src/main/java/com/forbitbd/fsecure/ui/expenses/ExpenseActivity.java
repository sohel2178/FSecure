package com.forbitbd.fsecure.ui.expenses;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.forbitbd.fsecure.R;
import com.forbitbd.fsecure.activities.PrebaseActivity;
import com.forbitbd.fsecure.ui.expenses.account.AccountFragment;
import com.forbitbd.fsecure.ui.expenses.report.ReportFragment;
import com.forbitbd.fsecure.ui.expenses.transaction.TransactionFragment;
import com.forbitbd.fsecure.utility.Constant;

public class ExpenseActivity extends PrebaseActivity implements ExpenseContract.View {

    private ExpensePresenter mPresenter;

    private BottomNavigationView mNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense);
        mPresenter = new ExpensePresenter(this);

        initView(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        String name = getIntent().getStringExtra(Constant.NAME);
        mPresenter.updateTitle(name);
    }

    private void initView(Bundle savedInstanceState) {
        mNavigationView = findViewById(R.id.navigation);
        mNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.action_head:
                        mPresenter.loadHead();
                        break;
                    case R.id.action_trans:
                        mPresenter.loadTrans();
                        break;
                    case R.id.action_account:
                        mPresenter.loadAccount();
                        break;

                    case R.id.action_transaction:
                        mPresenter.loadTransaction();
                        break;

                    case R.id.action_report:
                        mPresenter.loadReport();
                        break;
                }
                return true;
            }
        });

        if(savedInstanceState==null){
            mPresenter.loadAccount();
        }


    }

    @Override
    public void updateTitle(String title) {
        Log.d("TTTTT",title);
        setupToolbar();
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle(title);
    }

    @Override
    public void loadHeadFragment() {

    }

    @Override
    public void loadTransFragment() {

    }

    @Override
    public void loadAccountFragment() {

        if(!(getSupportFragmentManager().findFragmentById(R.id.main_container) instanceof AccountFragment)){
            getSupportFragmentManager().beginTransaction().replace(R.id.main_container,new AccountFragment()).commit();
        }

    }

    @Override
    public void loadTransactionFragment() {
        if(!(getSupportFragmentManager().findFragmentById(R.id.main_container) instanceof TransactionFragment)){
            getSupportFragmentManager().beginTransaction().replace(R.id.main_container,new TransactionFragment()).commit();
        }
    }

    @Override
    public void loadReportFragment() {
        if(!(getSupportFragmentManager().findFragmentById(R.id.main_container) instanceof ReportFragment)){
            getSupportFragmentManager().beginTransaction().replace(R.id.main_container,new ReportFragment()).commit();
        }
    }
}
