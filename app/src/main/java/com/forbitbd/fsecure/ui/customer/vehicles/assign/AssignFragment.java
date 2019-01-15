package com.forbitbd.fsecure.ui.customer.vehicles.assign;


import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.forbitbd.fsecure.R;
import com.forbitbd.fsecure.holder.AdminVehicleHolder;
import com.forbitbd.fsecure.model.User;
import com.forbitbd.fsecure.model.Vehicle;
import com.forbitbd.fsecure.singleton.MyDatabaseRef;
import com.forbitbd.fsecure.ui.customer.CustomerActivity;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;


public class AssignFragment extends DialogFragment {

    private RecyclerView mRecyclerView;

    private User user;

    FirebaseRecyclerAdapter<Vehicle,AdminVehicleHolder> adapter;


    public AssignFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getActivity() instanceof CustomerActivity){
            CustomerActivity ca = (CustomerActivity) getActivity();
            user = ca.getUser();
        }
    }

    /* @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_assign, container, false);
    }*/

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.fragment_assign, null);
        initView(view);


        AlertDialog alertDialog = new AlertDialog.Builder(getActivity(), R.style.MyDialog).create();

        //AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.ThemeOverlay_AppCompat_Dialog);
        alertDialog.setView(view);
        return alertDialog;
    }

    private void initView(View view) {
        mRecyclerView = view.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        Query query = MyDatabaseRef.getInstance().getDeviceRef().orderByChild("uid").equalTo(null);

        adapter = new FirebaseRecyclerAdapter<Vehicle, AdminVehicleHolder>(Vehicle.class, R.layout.item_vehicle_two, AdminVehicleHolder.class,
                query) {
            @Override
            protected void populateViewHolder(AdminVehicleHolder viewHolder,final Vehicle model, int position) {
                viewHolder.bind(model);

                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        MyDatabaseRef.getInstance().getDeviceRef()
                                .child(model.getId())
                                .child("uid")
                                .setValue(user.getUid(), new DatabaseReference.CompletionListener() {
                                    @Override
                                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                        AssignFragment.this.dismiss();
                                    }
                                });
                    }
                });
            }
        };
        mRecyclerView.setAdapter(adapter);
    }

}
