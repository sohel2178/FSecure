package com.forbitbd.fsecure.ui.main.home.own;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.forbitbd.fsecure.R;
import com.forbitbd.fsecure.model.Vehicle;
import com.forbitbd.fsecure.ui.atCommand.AtCommandActivity;
import com.forbitbd.fsecure.ui.editVehicle.EditVehicleActivity;
import com.forbitbd.fsecure.ui.map.MapActivity;
import com.forbitbd.fsecure.ui.searchUser.SearchUserActivity;
import com.forbitbd.fsecure.ui.sharedUser.SharedUserActivity;
import com.forbitbd.fsecure.utility.Constant;
import com.forbitbd.fsecure.utility.UserLocalStore;
import com.forbitbd.fsecure.holder.VehicleHolder;
import com.forbitbd.fsecure.singleton.MyDatabaseRef;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

/**
 * A simple {@link Fragment} subclass.
 */
public class OwnVehicleFragment extends Fragment {

    private RecyclerView rvVehicle;
    private Query query;
    FirebaseRecyclerAdapter<Vehicle,VehicleHolder> adapter;




    public OwnVehicleFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //MyDatabaseRef.getInstance().getDataRef("0351608083136213").setValue(null);

        final String uid= FirebaseAuth.getInstance().getCurrentUser().getUid();

        query = MyDatabaseRef.getInstance().getDeviceRef().orderByChild("uid").equalTo(uid);
        adapter = new FirebaseRecyclerAdapter<Vehicle, VehicleHolder>(Vehicle.class, R.layout.single_driver, VehicleHolder.class,
                query) {
            @Override
            protected void populateViewHolder(VehicleHolder viewHolder, final Vehicle model, int position) {

                viewHolder.bindVehicle(model);

                viewHolder.getRootView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getContext(), MapActivity.class);

                        Bundle bundle = new Bundle();
                        bundle.putSerializable(Constant.DEVICE,model);
                        intent.putExtras(bundle);

                        startActivity(intent);

                    }
                });

                viewHolder.getEditView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getContext(), EditVehicleActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable(Constant.VEHICLE,model);
                        intent.putExtras(bundle);
                        startActivity(intent);

                    }
                });

                viewHolder.getShareView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent(getContext(),SearchUserActivity.class);
                        intent.putExtra(Constant.VEHICLE_ID,model.getId());
                        intent.putExtra(Constant.VEHICLE_NAME,model.getDriver_name());

                        startActivity(intent);


                    }
                });

                viewHolder.getShareUserView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getContext(),SharedUserActivity.class);
                        intent.putExtra(Constant.VEHICLE_ID,model.getId());
                        startActivity(intent);


                    }
                });

                viewHolder.getAtView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getContext(), AtCommandActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable(Constant.VEHICLE,model);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                });

                viewHolder.getCallView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", model.getDriver_phone(), null));
                        startActivity(intent);
                    }
                });

            }
        };

        if(!UserLocalStore.getInstance(getContext())
                .isMobiSubscribe()){

            MyDatabaseRef.getInstance().getDeviceRef().orderByChild("uid").equalTo(uid)
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if(dataSnapshot.getValue()==null){
                                Log.d("NNNN","No Device Assign");
                            }else{

                                if(!UserLocalStore.getInstance(getContext())
                                        .isMobiSubscribe()){
                                    FirebaseMessaging.getInstance().subscribeToTopic("Sultan");
                                    UserLocalStore.getInstance(getContext()).setMobiSubscribe(true);
                                }

                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

        }




    }



    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_own_vehicle, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {

        rvVehicle = view.findViewById(R.id.rv_vehicle);
        rvVehicle.setNestedScrollingEnabled(false);
        rvVehicle.setLayoutManager(new GridLayoutManager(getContext(),2));
        //rvVehicle.setLayoutManager(new LinearLayoutManager(getContext()));
        rvVehicle.setAdapter(adapter);

    }

}
