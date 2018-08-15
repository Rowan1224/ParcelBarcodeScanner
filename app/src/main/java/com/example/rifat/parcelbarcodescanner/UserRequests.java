package com.example.rifat.parcelbarcodescanner;

import com.google.gson.annotations.SerializedName;

public class UserRequests {

    @SerializedName("pk") String pk;
    @SerializedName("sender_phone") String sender_phone;
    @SerializedName("QR_code") String QR_code;
    @SerializedName("issue_time") String issue_time;
    @SerializedName("destination_code") String destination_code;
    @SerializedName("description") String description;

    public String getSender_phone() {
        return sender_phone;
    }

    public void setSender_phone(String sender_phone) {
        this.sender_phone = sender_phone;
    }

    public UserRequests(String pk, String sender_phone, String QR_code, String issue_time,
                        String destination_code, String description) {
        this.pk = pk;
        this.sender_phone = sender_phone;
        this.QR_code = QR_code;
        this.issue_time = issue_time;
        this.destination_code = destination_code;
        this.description = description;
    }

    public String getPk() {
        return pk;
    }

    public void setPk(String pk) {
        this.pk = pk;
    }

    public String getQR_code() {
        return QR_code;
    }

    public void setQR_code(String QR_code) {
        this.QR_code = QR_code;
    }

    public String getIssue_time() {
        return issue_time;
    }

    public void setIssue_time(String issue_time) {
        this.issue_time = issue_time;
    }

    public String getDestination_code() {
        return destination_code;
    }

    public void setDestination_code(String destination_code) {
        this.destination_code = destination_code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
