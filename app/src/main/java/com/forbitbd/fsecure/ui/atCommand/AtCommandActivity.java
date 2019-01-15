package com.forbitbd.fsecure.ui.atCommand;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.forbitbd.fsecure.R;
import com.forbitbd.fsecure.activities.PrebaseActivity;
import com.forbitbd.fsecure.model.Vehicle;
import com.forbitbd.fsecure.utility.Constant;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class AtCommandActivity extends PrebaseActivity implements ATCommandContract.View,View.OnClickListener{

    private static final int REQUIRED_PERMISSION=5000;

    private Vehicle mVehicle;
    private AtCommandPresenter mPresenter;

    private RelativeLayout mSeudo,mOriginal;

    private EditText etdeviceSimNumber;
    private Button btnSend;

    private TextView tvStatus;
    private Button btnAccOn,btnAccOff,btnViewINMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_at_command);
        this.mPresenter = new AtCommandPresenter(this);

        this.mVehicle = (Vehicle) getIntent().getSerializableExtra(Constant.VEHICLE);

        setupToolbar();
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle(R.string.command);

        initView();

       // Log.d("JJJJJ",mVehicle.getDriver_name());
    }

    private void initView() {
        mSeudo = findViewById(R.id.pseudo);
        mOriginal = findViewById(R.id.original);
        etdeviceSimNumber = findViewById(R.id.et_sim);
        btnSend = findViewById(R.id.set);

        tvStatus = findViewById(R.id.tvStatus);
        btnAccOn = findViewById(R.id.acc_on);
        btnAccOff = findViewById(R.id.acc_off);
        btnViewINMap = findViewById(R.id.view_in_map);

        btnSend.setOnClickListener(this);
        btnAccOn.setOnClickListener(this);
        btnAccOff.setOnClickListener(this);
        btnViewINMap.setOnClickListener(this);

        mPresenter.visibleUI(mVehicle);

    }

    @Override
    public void visiblePseudoLayout() {
        mSeudo.setVisibility(View.VISIBLE);
        mOriginal.setVisibility(View.GONE);
    }

    @Override
    public void visibleOriginalLayout() {
        mSeudo.setVisibility(View.GONE);
        mOriginal.setVisibility(View.VISIBLE);
    }

    @Override
    public void showErrorMessage(String message, int field) {
        etdeviceSimNumber.requestFocus();
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setStausText(String message) {
        tvStatus.setText(message);
    }

    @Override
    public void sendCommand(int command, String phoneNumber) {
        String message=null;
        switch (command){
            case 0:
                message = getString(R.string.command_acc_on);
                break;
            case 1:
                message = getString(R.string.command_acc_off);
                break;

            case 2:
                message = getString(R.string.command_url);
                break;

        }

        mPresenter.sendMessage(message,phoneNumber,command);
    }

    @Override
    public void sendMessage(String message, String phoneNumber, final int command) {

        // Intent Filter Tags for SMS SEND and DELIVER
        String SENT = "SMS_SENT";
        String DELIVERED = "SMS_DELIVERED";

        PendingIntent sentPI = PendingIntent.getBroadcast(this, 0, new Intent(
                SENT), 0);

        // DELIVER PendingIntent
        PendingIntent deliveredPI = PendingIntent.getBroadcast(this, 0,
                new Intent(DELIVERED), 0);

        BroadcastReceiver sendSMS = new BroadcastReceiver() {
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        Toast.makeText(getBaseContext(), "SMS sent",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        Toast.makeText(getBaseContext(), "Generic failure",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        Toast.makeText(getBaseContext(), "No service",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        Toast.makeText(getBaseContext(), "Null PDU",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        Toast.makeText(getBaseContext(), "Radio off",
                                Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };

        // DELIVERY BroadcastReceiver
        BroadcastReceiver deliverSMS = new BroadcastReceiver() {
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                switch (getResultCode()) {
                    case Activity.RESULT_OK:

                        hideProgressDialog();

                        mPresenter.updateDatabase(command,mVehicle.getId());

                        /*Toast.makeText(getBaseContext(), "SMS delivered",
                                Toast.LENGTH_SHORT).show();*/
                        break;
                    case Activity.RESULT_CANCELED:
                        Toast.makeText(getBaseContext(), "SMS not delivered",
                                Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };
// STEP-3___
        // ---Notify when the SMS has been sent---
        registerReceiver(sendSMS, new IntentFilter(SENT));

        // ---Notify when the SMS has been delivered---
        registerReceiver(deliverSMS, new IntentFilter(DELIVERED));

        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message, sentPI, deliveredPI);

    }

    @Override
    public void showDialog() {
        showProgressDialog();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.set:
                String phoneNumber = etdeviceSimNumber.getText().toString();
                boolean valid =mPresenter.validate(phoneNumber);

                if(!valid){
                    return;
                }

                mVehicle.setDevice_sim_number(phoneNumber);
                mPresenter.saveDevicePhoneNumber(mVehicle);



                break;

            case R.id.acc_on:
                getSMSPermission(0);
                break;

            case R.id.acc_off:
                getSMSPermission(1);
                break;

            case R.id.view_in_map:
                getSMSPermission(2);
                break;
        }
    }



    @AfterPermissionGranted(REQUIRED_PERMISSION)
    private void getSMSPermission(int command) {
        String[] perms = {Manifest.permission.SEND_SMS, Manifest.permission.READ_SMS,Manifest.permission.RECEIVE_SMS};
        if (EasyPermissions.hasPermissions(this, perms)) {
            // Already have permission, do the thing
            // ...

            // initialize After Permission
            mPresenter.requestForCommand(command,mVehicle.getDevice_sim_number());
        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(this, getString(R.string.permission_txt),
                    REQUIRED_PERMISSION, perms);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(
                requestCode, permissions, grantResults, this);
    }
}
