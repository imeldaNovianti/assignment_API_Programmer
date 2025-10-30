package com.nutech.simsppob.model;

public class Banner {
    private String banner_name;
    private String banner_image;
    private String description;

    public Banner(String banner_name, String banner_image, String description) {
        this.banner_name = banner_name;
        this.banner_image = banner_image;
        this.description = description;
    }

    public String getBanner_name() {
        return banner_name;
    }

    public String getBanner_image() {
        return banner_image;
    }

    public String getDescription() {
        return description;
    }
}
