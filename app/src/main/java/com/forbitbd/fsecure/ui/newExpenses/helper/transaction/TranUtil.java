package com.forbitbd.fsecure.ui.newExpenses.helper.transaction;

import com.forbitbd.fsecure.markerAnimation.LatLngInterpolator;
import com.forbitbd.fsecure.model.Head;
import com.forbitbd.fsecure.model.Tran;
import com.forbitbd.fsecure.model.User;
import com.forbitbd.fsecure.model.Vehicle;

import java.util.List;

public class TranUtil {

    public static int getHeadPosition(List<Head> headList,Head head){
        int retInt=-1;
        for (Head x:headList){
            if(x.get_id().equals(head.get_id())){
                retInt = headList.indexOf(x);
                break;
            }
        }

        return retInt;
    }

    public static int getDevicePosition(List<Vehicle> vehicleList, String vehicleID){
        int retInt=-1;
        for (Vehicle x:vehicleList){
            if(x.getId().equals(vehicleID)){
                retInt = vehicleList.indexOf(x);
                break;
            }
        }

        return retInt;
    }

    public static int getTransactionPosition(List<Tran> tranList, Tran tran){
        int retInt=-1;
        for (Tran x:tranList){
            if(x.get_id().equals(tran.get_id())){
                retInt = tranList.indexOf(x);
                break;
            }
        }

        return retInt;
    }

    public static int getUserPosition(List<User> userList,User user){
        int retInt=-1;
        for (User x:userList){
            if(x.getUid().equals(user.getUid())){
                retInt = userList.indexOf(x);
                break;
            }
        }

        return retInt;
    }
}
