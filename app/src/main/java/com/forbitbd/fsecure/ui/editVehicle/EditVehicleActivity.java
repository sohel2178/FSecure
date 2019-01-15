package com.forbitbd.fsecure.ui.editVehicle;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.forbitbd.fsecure.R;
import com.forbitbd.fsecure.activities.PrebaseActivity;
import com.forbitbd.fsecure.model.Vehicle;
import com.forbitbd.fsecure.singleton.MyDatabaseRef;
import com.forbitbd.fsecure.singleton.MyStorageRef;
import com.forbitbd.fsecure.utility.Constant;
import com.forbitbd.fsecure.utility.MyUtil;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class EditVehicleActivity extends PrebaseActivity implements View.OnClickListener,EditVehicleContract.View {
    private static final int CAMERA_PERM =2000;

    private Vehicle vehicle;

    private EditText etDriverName,etDriverPhone,etModel,etMilage;
    private TextInputLayout tiDriverName,tiDriverPhone,tiModel,tiMilage;
    private ImageView ivDriverImage;
    private Button btnSelect,btnUpdate;

    private byte[] byteArray;

    private EditVehiclePresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_vehicle);

        mPresenter = new EditVehiclePresenter(this);

        setupToolbar();
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Update Vehicle");

        vehicle = (Vehicle) getIntent().getSerializableExtra(Constant.VEHICLE);

        initView();
    }

    private void initView() {
        etDriverName = findViewById(R.id.driver_name);
        etDriverPhone = findViewById(R.id.driver_phone);
        etModel = findViewById(R.id.model);
        etMilage = findViewById(R.id.mileage);

        tiDriverName = findViewById(R.id.ti_driver_name);
        tiDriverPhone = findViewById(R.id.ti_driver_phone);
        tiModel = findViewById(R.id.ti_model);
        tiMilage = findViewById(R.id.ti_milage);

        ivDriverImage = findViewById(R.id.driver_image);

        btnSelect = findViewById(R.id.select);
        btnUpdate = findViewById(R.id.update);

        btnSelect.setOnClickListener(this);
        btnUpdate.setOnClickListener(this);

        if(vehicle.getDriver_name()!=null){
            etDriverName.setText(vehicle.getDriver_name());
        }

        if(vehicle.getDriver_phone()!=null){
            etDriverPhone.setText(vehicle.getDriver_phone());
        }

        if(vehicle.getDriver_photo()!=null && !vehicle.getDriver_photo().equals("")){
            Picasso.with(getApplicationContext())
                    .load(vehicle.getDriver_photo())
                    .into(ivDriverImage);
        }

        if(vehicle.getModel()!=null){
            etModel.setText(vehicle.getModel());
        }

        etMilage.setText(MyUtil.getTwoDecimalFormat(vehicle.getMileage()));


    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.select:
                mPresenter.cropImageRequest();
                //getCameraPermission();
                break;

            case R.id.update:

                String driverName = etDriverName.getText().toString().trim();
                String driverPhone = etDriverPhone.getText().toString().trim();
                String model = etModel.getText().toString().trim();
                String mileageStr = etMilage.getText().toString().trim();

                vehicle.setDriver_name(driverName);
                vehicle.setDriver_phone(driverPhone);
                vehicle.setModel(model);

                if(!mileageStr.equals("")){
                    vehicle.setMileage(Double.parseDouble(mileageStr));
                }


                boolean valid = mPresenter.validate(vehicle);

                if(byteArray==null){
                    mPresenter.saveVehicle(vehicle);
                }else{
                    mPresenter.saveVehicleWithImage(vehicle,byteArray);
                }

                /*if(TextUtils.isEmpty(driverName)){
                    etDriverName.requestFocus();
                    Toast.makeText(this, "Please Fill up Driver Name", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(driverPhone)){
                    etDriverPhone.requestFocus();
                    Toast.makeText(this, "Please Fill up Driver Phone", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(model)){
                    etModel.requestFocus();
                    Toast.makeText(this, "Please Fill up Driver Photo", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(mileageStr)){
                    etModel.requestFocus();
                    Toast.makeText(this, "Please Fill up Driver Photo", Toast.LENGTH_SHORT).show();
                    return;
                }

                vehicle.setDriver_name(driverName);
                vehicle.setDriver_phone(driverPhone);
                vehicle.setModel(model);
                vehicle.setMileage(Double.parseDouble(mileageStr));

                showProgressDialog();*/

                /*if (byteArray==null){
                    updateDatabase(vehicle);
                }else{
                    MyStorageRef.getInstance().getVehicleStoreRef().child(vehicle.getId())
                            .putBytes(byteArray)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    String url = taskSnapshot.getDownloadUrl().toString();

                                    vehicle.setDriver_photo(url);
                                    updateDatabase(vehicle);
                                }
                            });
                }*/



                break;
        }

    }

    private void updateDatabase(Vehicle vehicle){

        Map<String,Object> vehicleMap = new HashMap<>();
        vehicleMap.put("driver_name",vehicle.getDriver_name());
        vehicleMap.put("driver_phone",vehicle.getDriver_phone());
        vehicleMap.put("model",vehicle.getModel());
        vehicleMap.put("mileage",vehicle.getMileage());

        if(vehicle.getDriver_photo()!=null){
            vehicleMap.put("driver_photo",vehicle.getDriver_photo());
        }

        MyDatabaseRef.getInstance().getDeviceRef().child(vehicle.getId()).updateChildren(vehicleMap, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {

                hideProgressDialog();
                onBackPressed();

            }
        });
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
//                imageUri = result.getUri();
//
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), result.getUri());

                    Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap,200,200,false);

                    ivDriverImage.setImageBitmap(scaledBitmap);

                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    scaledBitmap.compress(Bitmap.CompressFormat.PNG, 60, stream);
                    byteArray = stream.toByteArray();

                } catch (IOException e) {
                    e.printStackTrace();
                }

                /*if(imageUri!=null){
                    ivDriverImage.setImageURI(imageUri);
                }*/
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    @Override
    public void clearError() {
        tiDriverName.setErrorEnabled(false);
        tiDriverPhone.setErrorEnabled(false);
        tiModel.setErrorEnabled(false);
        tiMilage.setErrorEnabled(false);
    }

    @Override
    public void showError(int fieldId, String message) {
        switch (fieldId){
            case 1:
                tiDriverName.setError(message);
                break;

            case 2:
                tiDriverPhone.setError(message);
                break;

            case 3:
                tiModel.setError(message);
                break;

            case 4:
                tiMilage.setError(message);
                break;
        }
    }

    @Override
    public void openCropImageActivity() {
        openCropImageActivity(true);
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
}
