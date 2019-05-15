package com.example.talentmicro.smsgold.Dto;

import java.io.Serializable;

public class ProfessionDto implements Serializable {

    private int professionId;
    private String professionName;

    public ProfessionDto(int professionId, String professionName) {
        this.professionId = professionId;
        this.professionName = professionName;
    }

    public int getProfessionId() {
        return professionId;
    }

    public void setProfessionId(int professionId) {
        this.professionId = professionId;
    }

    public String getProfessionName() {
        return professionName;
    }

    public void setProfessionName(String professionName) {
        this.professionName = professionName;
    }

    @Override
    public String toString() {
        return professionName;
    }
}
