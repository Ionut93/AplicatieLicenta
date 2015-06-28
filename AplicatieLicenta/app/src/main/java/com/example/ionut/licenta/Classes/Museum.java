package com.example.ionut.licenta.Classes;

/**
 * Created by Ionut on 5/5/2015.
 */
public class Museum {
    private String nume;
    private String image_src;
    private String description;
    private String site;

    public Museum(String nume, String image_src, String description, String site) {
        this.nume = nume;
        this.image_src = image_src;
        this.description = description;
        this.site = site;
    }

    public Museum() {

    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }


    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getImage_src() {
        return image_src;
    }

    public void setImage_src(String image_src) {
        this.image_src = image_src;
    }
}
