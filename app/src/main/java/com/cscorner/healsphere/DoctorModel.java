package com.cscorner.healsphere;

public class DoctorModel {

    private String name;
    private String specialty;
    private String fee;
    private float rating;
    private int imageRes;

    public DoctorModel(String name, String specialty, String fee, float rating, int imageRes) {
        this.name = name;
        this.specialty = specialty;
        this.fee = fee;
        this.rating = rating;
        this.imageRes = imageRes;
    }

    public String getName() {
        return name;
    }

    public String getSpecialty() {
        return specialty;
    }

    public String getFee() {
        return fee;
    }

    public float getRating() {
        return rating;
    }

    public int getImageRes() {
        return imageRes;
    }
}
