package com.insulin.backend.dto;

public class GlucoseRequest {

    private int glucose;
    private int currentInsulin;

    public GlucoseRequest() {
    }

    public int getGlucose() {
        return glucose;
    }

    public void setGlucose(int glucose) {
        this.glucose = glucose;
    }

    public int getCurrentInsulin() {
        return currentInsulin;
    }

    public void setCurrentInsulin(int currentInsulin) {
        this.currentInsulin = currentInsulin;
    }
}
