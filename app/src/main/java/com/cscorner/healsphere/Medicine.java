package com.cscorner.healsphere;

public class Medicine {
    private String name;
    private String time;

    public Medicine(String name, String time) {
        this.name = name;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public String getTime() {
        return time;
    }
}
