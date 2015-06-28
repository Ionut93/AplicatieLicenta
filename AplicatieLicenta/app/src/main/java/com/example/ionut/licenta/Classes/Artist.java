package com.example.ionut.licenta.Classes;

/**
 * Created by Ionut on 6/18/2015.
 */
public class Artist {
    private String nume;
    private String descriere;
    private String url;

    public Artist(String url, String nume, String descriere) {
        this.url = url;
        this.nume = nume;
        this.descriere = descriere;
    }

    public Artist() {
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getDescriere() {
        return descriere;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


}
