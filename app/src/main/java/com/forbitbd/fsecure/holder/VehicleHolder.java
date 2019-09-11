package com.forbitbd.fsecure.holder;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.forbitbd.fsecure.R;
import com.forbitbd.fsecure.model.FireData;
import com.forbitbd.fsecure.model.Vehicle;
import com.forbitbd.fsecure.utility.MyUtil;
import com.google.android.gms.maps.model.LatLng;
import com.ramotion.foldingcell.FoldingCell;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by sohel on 5/22/2018.
 */

public class VehicleHolder extends RecyclerView.ViewHolder {

    private FoldingCell mFoldingCell;

    private ImageView ivEdit,ivShare,ivShareUser,ivCall,ivAt,ivIndicator,ivView;

    private TextView tvDriverName,tvDriverPhone,tvModel
            ,tvVehicleType,getTvVehicleTypeTwo,tvLocation,tvRegistrationNumber,tvMilage,tvEngineStatus;

    private CircleImageView driverImage;

    private Context mContext;

    public VehicleHolder(View itemView) {
        super(itemView);
        mContext = itemView.getContext();

        mFoldingCell = itemView.findViewById(R.id.folding_cell);

        driverImage = itemView.findViewById(R.id.driver_image);

        tvDriverName = itemView.findViewById(R.id.driver_name);
        tvDriverPhone = itemView.findViewById(R.id.driver_phone_txt);
        tvModel = itemView.findViewById(R.id.model);
        tvRegistrationNumber = itemView.findViewById(R.id.registration_number);
        tvVehicleType = itemView.findViewById(R.id.vehicle_type);
        getTvVehicleTypeTwo = itemView.findViewById(R.id.vehicle_type_two);
        tvLocation = itemView.findViewById(R.id.current_location);
        tvMilage = itemView.findViewById(R.id.mileage);
        tvEngineStatus = itemView.findViewById(R.id.engine_status);

        ivIndicator =  itemView.findViewById(R.id.indicator);
        ivEdit = itemView.findViewById(R.id.edit);
        ivShare = itemView.findViewById(R.id.share);
        ivShareUser = itemView.findViewById(R.id.share_user);
        ivCall = itemView.findViewById(R.id.driver_phone);
        ivAt = itemView.findViewById(R.id.at);
        ivView = itemView.findViewById(R.id.view);


    }

    public void bindVehicle(Vehicle vehicle){



        if(vehicle.getVehicle_type()<=1){
            tvVehicleType.setText(mContext.getResources().getStringArray(R.array.vehicle_type_array)[0]);
            getTvVehicleTypeTwo.setText(mContext.getResources().getStringArray(R.array.vehicle_type_array)[0]);
        }else {
            tvVehicleType.setText(mContext.getResources().getStringArray(R.array.vehicle_type_array)[vehicle.getVehicle_type()-1]);
            getTvVehicleTypeTwo.setText(mContext.getResources().getStringArray(R.array.vehicle_type_array)[vehicle.getVehicle_type()-1]);
        }


        if(vehicle.getDriver_photo()!=null && !vehicle.getDriver_photo().equals("")){
            Picasso.with(mContext)
                    .load(vehicle.getDriver_photo())
                    .into(driverImage);
        }

        if(vehicle.getDriver_phone()!=null){
            tvDriverPhone.setText(vehicle.getDriver_phone());
        }

        if(vehicle.getModel()!=null){
            tvRegistrationNumber.setText(vehicle.getModel());
            tvModel.setText(vehicle.getModel());
        }

        if(vehicle.getDriver_name()!=null){
            tvDriverName.setText(vehicle.getDriver_name());
        }

        if(vehicle.getMileage()==0){
            tvMilage.setText("Not Defined");
        }else{
            tvMilage.setText(vehicle.getMileage()+" Km per Lit");
        }

        FireData fireData = vehicle.getData();

        if(fireData!=null){

            LatLng latLng = getLatLong(vehicle.getData().getLat(),vehicle.getData().getLng());

            tvLocation.setText(MyUtil.getAddress(mContext,latLng));

            if(fireData.getStatus().equals("1")){
                ivIndicator.setColorFilter(ContextCompat.getColor(mContext, R.color.green), android.graphics.PorterDuff.Mode.SRC_IN);
                //ivIndicator.setImageDrawable(ContextCompat.getDrawable(mView.getContext(),R.drawable.green_car));
                tvEngineStatus.setText("ON");

            }else{
                ivIndicator.setColorFilter(ContextCompat.getColor(mContext, R.color.red), android.graphics.PorterDuff.Mode.SRC_IN);
                //ivIndicator.setImageDrawable(ContextCompat.getDrawable(mView.getContext(),R.drawable.red_car));
                tvEngineStatus.setText("OFF");
            }
        }



    }


    public FoldingCell getmFoldingCell() {
        return mFoldingCell;
    }

    public View getEditView(){
        return ivEdit;
    }

    public View getShareView(){
        return ivShare;
    }

    public View getShareUserView(){
        return ivShareUser;
    }

    public View getCallView(){
        return ivCall;
    }

    public View getAtView(){
        return ivAt;
    }
    public View getLiveView(){
        return ivView;
    }


    private LatLng getLatLong(String lat, String lng){
        double lati = (double)Long.parseLong(lat,16)/1800000;
        double longi = (double)Long.parseLong(lng,16)/1800000;
        return new LatLng(lati,longi);
    }
}
