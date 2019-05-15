package com.example.talentmicro.smsgold.Dto;

import java.io.Serializable;

public class NationalityDto implements Serializable {

    private int nationalityId;
    private String nationalityName;

    public NationalityDto(int nationalityId, String nationalityName) {
        this.nationalityId = nationalityId;
        this.nationalityName = nationalityName;
    }

    public int getNationalityId() {
        return nationalityId;
    }

    public void setNationalityId(int nationalityId) {
        this.nationalityId = nationalityId;
    }

    public String getNationalityName() {
        return nationalityName;
    }

    public void setNationalityName(String nationalityName) {
        this.nationalityName = nationalityName;
    }

    @Override
    public String toString() {
        return nationalityName;
    }
}
