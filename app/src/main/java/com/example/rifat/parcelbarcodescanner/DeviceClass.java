package com.example.rifat.parcelbarcodescanner;


import com.google.gson.annotations.SerializedName;

public class DeviceClass {
    @SerializedName("EmpId") int EmpId;
    @SerializedName("id") int pk;
    @SerializedName("Latitude") float Latitude;
    @SerializedName("Longitude") float Longitude;
    @SerializedName("Token") String Token;
    @SerializedName("password") String password;
    @SerializedName("activity") String activity;

    public DeviceClass(int empId, int pk, float latitude, float longitude,
                       String token, String password, String activity) {
        EmpId = empId;
        this.pk = pk;
        Latitude = latitude;
        Longitude = longitude;
        Token = token;
        this.password = password;
        this.activity = activity;
    }

    public int getEmpId() {
        return EmpId;
    }

    public void setEmpId(int empId) {
        EmpId = empId;
    }

    public int getPk() {
        return pk;
    }

    public void setPk(int pk) {
        this.pk = pk;
    }

    public float getLatitude() {
        return Latitude;
    }

    public void setLatitude(float latitude) {
        Latitude = latitude;
    }

    public float getLongitude() {
        return Longitude;
    }

    public void setLongitude(float longitude) {
        Longitude = longitude;
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }
}
