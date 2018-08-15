package com.example.rifat.parcelbarcodescanner;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ParcelDetails implements Serializable{

    @SerializedName("sender_phone") String phone;
    @SerializedName("receiver_phone")String receiver_phone;
    @SerializedName("QR_code") String QR_code;
    @SerializedName("delivery_type")String delivery_type;
    @SerializedName("description") String description;
    @SerializedName("destination_code")String destination_code;
    @SerializedName("issue_time")String issue_time;
    @SerializedName("delivery_time")String delivery_time;
    @SerializedName("tracking_code")String tracking_code;
    @SerializedName("informed") Boolean informed;
    @SerializedName("store_code")String store_code;
    @SerializedName("temporary_parcel") Boolean temporary_parcel;

    public ParcelDetails(String phone, String receiver_phone, String QR_code, String delivery_type,
                         String description, String destination_code, String issue_time,
                         String delivery_time, String tracking_code, Boolean informed,
                         String store_code, Boolean temporary_parcel) {
        this.phone = phone;
        this.receiver_phone = receiver_phone;
        this.QR_code = QR_code;
        this.delivery_type = delivery_type;
        this.description = description;
        this.destination_code = destination_code;
        this.issue_time = issue_time;
        this.delivery_time = delivery_time;
        this.tracking_code = tracking_code;
        this.informed = informed;
        this.store_code = store_code;
        this.temporary_parcel = temporary_parcel;
    }



    public ParcelDetails(String phone, String receiver_phone, String QR_code, String delivery_type,
                         String description, String destination_code, String issue_time, Boolean temporary_parcel) {
        this.phone = phone;
        this.receiver_phone = receiver_phone;
        this.QR_code = QR_code;
        this.delivery_type = delivery_type;
        this.description = description;
        this.destination_code = destination_code;
        this.issue_time = issue_time;
        this.temporary_parcel = temporary_parcel;
    }


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getReceiver_phone() {
        return receiver_phone;
    }

    public void setReceiver_phone(String receiver_phone) {
        this.receiver_phone = receiver_phone;
    }

    public String getQR_code() {
        return QR_code;
    }

    public void setQR_code(String QR_code) {
        this.QR_code = QR_code;
    }

    public String getDelivery_type() {
        return delivery_type;
    }

    public void setDelivery_type(String delivery_type) {
        this.delivery_type = delivery_type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDestination_code() {
        return destination_code;
    }

    public void setDestination_code(String destination_code) {
        this.destination_code = destination_code;
    }

    public String getIssue_time() {
        return issue_time;
    }

    public void setIssue_time(String issue_time) {
        this.issue_time = issue_time;
    }

    public String getDelivery_time() {
        return delivery_time;
    }

    public void setDelivery_time(String delivery_time) {
        this.delivery_time = delivery_time;
    }

    public String getTracking_code() {
        return tracking_code;
    }

    public void setTracking_code(String tracking_code) {
        this.tracking_code = tracking_code;
    }

    public Boolean getInformed() {
        return informed;
    }

    public void setInformed(Boolean informed) {
        this.informed = informed;
    }

    public String getStore_code() {
        return store_code;
    }

    public void setStore_code(String store_code) {
        this.store_code = store_code;
    }

    public Boolean getTemporary_parcel() {
        return temporary_parcel;
    }

    public void setTemporary_parcel(Boolean temporary_parcel) {
        this.temporary_parcel = temporary_parcel;
    }
}
