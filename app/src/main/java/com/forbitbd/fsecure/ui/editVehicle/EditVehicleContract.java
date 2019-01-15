package com.forbitbd.fsecure.ui.editVehicle;

import com.forbitbd.fsecure.model.Vehicle;

public interface EditVehicleContract {

    interface Presenter{
       boolean validate(Vehicle vehicle);
       void saveVehicle(Vehicle vehicle);
       void saveVehicleWithImage(Vehicle vehicle,byte[] bytes);
       void cropImageRequest();
    }

    interface View{
       void clearError();
       void showError(int fieldId,String message);
       void openCropImageActivity();
       void showDialog();
       void hideDialog();
       void complete();
    }
}
