package com.example.ionut.licenta.Classes;

/**
 * Created by Ionut on 6/24/2015.
 */
public class Pictura {

    private int ID;
    private String nume;
    private String pictor;
    private String src;
    private String descriere;
    private int likes;
    private int galerie;
    private boolean favorite;

    public Pictura() {
        this.favorite = false;
    }

    public Pictura(int ID, String nume, String pictor, String src, String descriere, int likes, int galerie) {
        this.ID = ID;
        this.nume = nume;
        this.pictor = pictor;
        this.src = src;
        this.descriere = descriere;
        this.likes = likes;
        this.galerie = galerie;
        this.favorite = false;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getPictor() {
        return pictor;
    }

    public void setPictor(String pictor) {
        this.pictor = pictor;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getDescriere() {
        return descriere;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getGalerie() {
        return galerie;
    }

    public void setGalerie(int galerie) {
        this.galerie = galerie;
    }
}
