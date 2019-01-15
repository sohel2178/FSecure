package com.forbitbd.fsecure.holder;

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
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by sohel on 5/22/2018.
 */

public class VehicleHolder extends RecyclerView.ViewHolder {
    private View mView;

    private ImageView ivEdit,ivShare,ivShareUser,ivCall,ivAt;

    public VehicleHolder(View itemView) {
        super(itemView);
        mView = itemView;
    }

    public void bindVehicle(Vehicle vehicle){

        CircleImageView driverImage = mView.findViewById(R.id.driver_image);
        TextView tvDriverName = mView.findViewById(R.id.driver_name);
        TextView tvModel = mView.findViewById(R.id.model);
        TextView tvLocation = mView.findViewById(R.id.current_location);

        ImageView ivIndicator = mView.findViewById(R.id.indicator);

        ivEdit = mView.findViewById(R.id.edit);
        ivShare = mView.findViewById(R.id.share);
        ivShareUser = mView.findViewById(R.id.share_user);
        ivCall = mView.findViewById(R.id.driver_phone);
        ivAt = mView.findViewById(R.id.at);

        if(vehicle.getDriver_photo()!=null && !vehicle.getDriver_photo().equals("")){
            Picasso.with(mView.getContext())
                    .load(vehicle.getDriver_photo())
                    .into(driverImage);
        }

        tvDriverName.setText(vehicle.getDriver_name());
        //tvDriverPhone.setText(vehicle.getDriver_phone());
        tvModel.setText(vehicle.getModel());

        FireData fireData = vehicle.getData();

        if(fireData!=null){

            LatLng latLng = getLatLong(vehicle.getData().getLat(),vehicle.getData().getLng());

            tvLocation.setText(MyUtil.getAddress(mView.getContext(),latLng));

            if(fireData.getStatus().equals("1")){
                ivIndicator.setImageDrawable(ContextCompat.getDrawable(mView.getContext(),R.drawable.green_car));
            }else{
                ivIndicator.setImageDrawable(ContextCompat.getDrawable(mView.getContext(),R.drawable.red_car));
            }
        }



    }

    public View getRootView(){
        return mView;
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


    private LatLng getLatLong(String lat, String lng){
        double lati = (double)Long.parseLong(lat,16)/1800000;
        double longi = (double)Long.parseLong(lng,16)/1800000;

        return new LatLng(lati,longi);
    }
}
