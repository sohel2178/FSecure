package com.forbitbd.fsecure.ui.transactionAdd;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.DatePicker;
import com.applandeo.materialcalendarview.builders.DatePickerBuilder;
import com.applandeo.materialcalendarview.listeners.OnSelectDateListener;
import com.forbitbd.fsecure.R;
import com.forbitbd.fsecure.activities.PrebaseActivity;
import com.forbitbd.fsecure.model.Account;
import com.forbitbd.fsecure.model.Transaction;
import com.forbitbd.fsecure.model.Vehicle;
import com.forbitbd.fsecure.utility.Constant;
import com.forbitbd.fsecure.utility.MyUtil;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AddTransactionActivity extends PrebaseActivity implements AddTransactionContract.View,View.OnClickListener {

    private EditText etDate,etAmount,etPurpose,etVoucherNo;
    private TextInputLayout tDate,tAmount,tPurpose,tVoucherNo;
    private AppCompatSpinner spFrom,spTo,spVehicle;
    private Button btnAdd;

    private AddTransactionPresenter mPresenter;

    private long dateTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);
        mPresenter = new AddTransactionPresenter(this);
        mPresenter.setToolbar();

        initView();
    }

    private void initView() {

        etDate = findViewById(R.id.et_date);
        etDate.setText(MyUtil.getStringDate(new Date()));
        etPurpose = findViewById(R.id.purpose);
        etVoucherNo = findViewById(R.id.voucher_no);
        etAmount = findViewById(R.id.amount);

        tDate = findViewById(R.id.ti_date);
        tVoucherNo = findViewById(R.id.ti_voucher_no);
        tPurpose = findViewById(R.id.ti_purpose);
        tAmount = findViewById(R.id.ti_amount);

        spFrom = findViewById(R.id.from);
        spTo = findViewById(R.id.to);
        spVehicle = findViewById(R.id.sp_vehicle);
        btnAdd = findViewById(R.id.btn_add);



        etDate.setOnClickListener(this);
        btnAdd.setOnClickListener(this);

        mPresenter.getAllAccounts();
        mPresenter.getAllDevices();

    }

    @Override
    public void setToolbar() {
        setupToolbar();
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle(R.string.new_transaction);
    }

    @Override
    public void clearPreError() {
        tAmount.setErrorEnabled(false);
        tPurpose.setErrorEnabled(false);
        tVoucherNo.setErrorEnabled(false);
    }

    @Override
    public void showError(String message, int field) {
        switch (field){
            case 1:
                etVoucherNo.requestFocus();
                tVoucherNo.setError(message);
                break;

            case 2:
                etPurpose.requestFocus();
                tPurpose.setError(message);
                break;

            case 3:
                etAmount.requestFocus();
                tAmount.setError(message);
                break;
        }
    }

    @Override
    public void showToast(String message) {

        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void updateToFromAdapter(List<Account> accountList) {

        ArrayAdapter<Account> accountAdapter = new ArrayAdapter<Account>(this,android.R.layout.simple_list_item_1,accountList);
        spFrom.setAdapter(accountAdapter);
        spTo.setAdapter(accountAdapter);

    }

    @Override
    public void updateDeviceAdapter(List<Vehicle> vehicleList) {
        ArrayAdapter<Vehicle> vAdapter = new ArrayAdapter<Vehicle>(this,android.R.layout.simple_list_item_1,vehicleList);
        spVehicle.setAdapter(vAdapter);
    }

    @Override
    public void finishActivity(Transaction transaction) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.TRANSACTION,transaction);

        Intent intent = new Intent();
        intent.putExtras(bundle);
        setResult(RESULT_OK,intent);
        finish();
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.et_date:
                DatePickerBuilder builder = new DatePickerBuilder(this, new OnSelectDateListener() {
                    @Override
                    public void onSelect(List<Calendar> calendar) {
                        dateTime = calendar.get(0).getTimeInMillis();
                        etDate.setText(MyUtil.getStringDate(new Date(dateTime)));

                    }
                }).pickerType(CalendarView.ONE_DAY_PICKER).date(Calendar.getInstance());

                DatePicker datePicker = builder.build();
                datePicker.show();
                break;

            case R.id.btn_add:
                String purpose = etPurpose.getText().toString().trim();
                String invoice = etVoucherNo.getText().toString().trim();
                String amountStr = etAmount.getText().toString().trim();

                double amount =0;
                try{
                    amount = Double.parseDouble(amountStr);
                }catch (Exception e){

                }

                Transaction transaction = new Transaction();
                transaction.setDate(new Date(dateTime));
                transaction.setPurpose(purpose);
                transaction.setInvoice_no(invoice);
                transaction.setAmount(amount);

                transaction.setTo((Account) spTo.getSelectedItem());
                transaction.setFrom((Account) spFrom.getSelectedItem());
                transaction.setDevice_id(((Vehicle) spVehicle.getSelectedItem()).getId());

                boolean valid = mPresenter.validate(transaction);

                if(!valid){
                    return;
                }

                mPresenter.saveTransaction(transaction);

                break;
        }

    }
}
