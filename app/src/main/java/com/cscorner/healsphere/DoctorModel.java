package com.cscorner.healsphere;

public class DoctorModel {

    private String name;
    private String specialty;
    private String fee;
    private float rating;
    private int imageResId;

    public DoctorModel(String name, String specialty, String fee, float rating, int imageResId) {
        this.name = name;
        this.specialty = specialty;
        this.fee = fee;
        this.rating = rating;
        this.imageResId = imageResId;
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

    public int getImageResId() {
        return imageResId;
    }
}
