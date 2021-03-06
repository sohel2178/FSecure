package com.forbitbd.fsecure.model;

import java.io.Serializable;

/**
 * Created by sohel on 5/20/2018.
 */

public class Vehicle implements Serializable {
    private String id;
    private String uid;
    private String driver_name;
    private String driver_phone;
    private String driver_photo;
    private String model;
    private String device_sim_number;
    private int vehicle_type;
    private double mileage;
    private double congestion_consumption;
    private FireData Data;
    private String fuelStatus;


    public Vehicle() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }


    public String getDriver_name() {
        return driver_name;
    }

    public void setDriver_name(String driver_name) {
        this.driver_name = driver_name;
    }

    public String getDriver_phone() {
        return driver_phone;
    }

    public void setDriver_phone(String driver_phone) {
        this.driver_phone = driver_phone;
    }

    public String getDriver_photo() {
        return driver_photo;
    }

    public void setDriver_photo(String driver_photo) {
        this.driver_photo = driver_photo;
    }

    public int getVehicle_type() {
        return vehicle_type;
    }

    public void setVehicle_type(int vehicle_type) {
        this.vehicle_type = vehicle_type;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public FireData getData() {
        return Data;
    }

    public void setData(FireData data) {
        Data = data;
    }

    public double getMileage() {
        return mileage;
    }

    public void setMileage(double mileage) {
        this.mileage = mileage;
    }

    public String getDevice_sim_number() {
        return device_sim_number;
    }

    public void setDevice_sim_number(String device_sim_number) {
        this.device_sim_number = device_sim_number;
    }

    public double getCongestion_consumption() {
        return congestion_consumption;
    }

    public void setCongestion_consumption(double congestion_consumption) {
        this.congestion_consumption = congestion_consumption;
    }

    public String getFuelStatus() {
        return fuelStatus;
    }

    public void setFuelStatus(String fuelStatus) {
        this.fuelStatus = fuelStatus;
    }

    @Override
    public String toString() {
        return this.model;
    }
}
