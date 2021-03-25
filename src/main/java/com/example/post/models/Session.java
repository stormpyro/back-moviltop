package com.example.post.models;

import java.util.UUID;

public class Session {
    private UUID ID;
    private UUID SSID;
    private String email;
    private Boolean enabled;
    private String startDate;
    private String endDate;
    private String createdDate;
    private String lastUpdateDate;

    public void setID(UUID ID) {
        this.ID = ID;
    }

    public void setSSID(UUID SSID) {
        this.SSID = SSID;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public void setLastUpdateDate(String lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public UUID getID() {
        return ID;
    }

    public UUID getSSID() {
        return SSID;
    }

    public String getEmail() {
        return email;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public String getLastUpdateDate() {
        return lastUpdateDate;
    }
}
