package com.example.rifat.parcelbarcodescanner;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CustomerDetails implements Serializable{

    @SerializedName("pk") String pk;
    @SerializedName("phone") String phone;
    @SerializedName("name") String name;
    @SerializedName("address") String address;

    public CustomerDetails(String pk, String phone, String name, String address) {
        this.pk = pk;
        this.phone = phone;
        this.name = name;
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;

    }

    public CustomerDetails(String phone, String name, String address) {
        this.phone = phone;
        this.name = name;
        this.address = address;
    }

    public String getPk() {
        return pk;
    }

    public void setPk(String pk) {
        this.pk = pk;
    }

    public String getAddress() {
        return address;
    }
}
