package com.example.rifat.parcelbarcodescanner;

import com.google.gson.annotations.SerializedName;

public class NewCustomerClass {

    @SerializedName("pk") String pk;
    @SerializedName("phone") String phone;
    @SerializedName("name") String name;
    @SerializedName("address") String address;
    @SerializedName("password")String password;

    public String getPk() {
        return pk;
    }

    public void setPk(String pk) {
        this.pk = pk;
    }

    public NewCustomerClass(String pk, String phone, String name, String address, String password) {

        this.pk = pk;
        this.phone = phone;
        this.name = name;
        this.address = address;
        this.password = password;
    }

    public NewCustomerClass(String phone, String name, String address, String password) {
        this.phone = phone;
        this.name = name;
        this.address = address;
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
