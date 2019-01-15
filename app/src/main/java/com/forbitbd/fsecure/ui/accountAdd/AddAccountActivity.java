package com.forbitbd.fsecure.ui.accountAdd;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.forbitbd.fsecure.R;
import com.forbitbd.fsecure.activities.PrebaseActivity;
import com.forbitbd.fsecure.model.Account;
import com.forbitbd.fsecure.utility.Constant;

public class AddAccountActivity extends PrebaseActivity implements AddAccountContract.View,View.OnClickListener {

    private AddAccountPresenter mPresenter;

    private AppCompatSpinner mSpinner;

    private EditText etName;
    private Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_account);
        mPresenter = new AddAccountPresenter(this);
        mPresenter.updateTitle();

        initView();
    }

    private void initView() {
        etName = findViewById(R.id.et_account_name);
        btnSubmit = findViewById(R.id.submit);
        btnSubmit.setOnClickListener(this);
        mSpinner = findViewById(R.id.sp_types);
        mSpinner.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.account_type)));
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void updateTitle() {
        setupToolbar();
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle(R.string.create_new_account);
    }

    @Override
    public void setAccount(Account account) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.ACCOUNT,account);
        intent.putExtras(bundle);

        setResult(RESULT_OK,intent);

        finish();
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {
        String name = etName.getText().toString().trim();
        int type = mSpinner.getSelectedItemPosition();

        Account account = new Account(name,type);

        boolean valid =mPresenter.validate(account);

        if(!valid){
            return;
        }

        mPresenter.createAccount(account);
    }
}
