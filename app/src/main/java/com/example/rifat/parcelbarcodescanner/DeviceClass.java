package com.example.rifat.parcelbarcodescanner;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DeviceClass implements Serializable {
    @SerializedName("employee_id") String EmpId;
    @SerializedName("latitude") String Latitude;
    @SerializedName("longitude") String Longitude;
    @SerializedName("active") String activity;

    public DeviceClass(String empId, String latitude, String longitude, String activity) {
        EmpId = empId;
        Latitude = latitude;
        Longitude = longitude;
        this.activity = activity;
    }

    public String getEmpId() {
        return EmpId;
    }

    public void setEmpId(String empId) {
        EmpId = empId;
    }

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }
}
