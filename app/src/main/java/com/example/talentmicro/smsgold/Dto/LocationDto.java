package com.example.talentmicro.smsgold.Dto;

import android.location.Location;

import java.io.Serializable;

public class LocationDto implements Serializable {

    private int locationId;
    private String locationName;

    public LocationDto(int locationId, String locationName) {
        this.locationId = locationId;
        this.locationName = locationName;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    @Override
    public String toString() {
        return locationName;
    }
}
