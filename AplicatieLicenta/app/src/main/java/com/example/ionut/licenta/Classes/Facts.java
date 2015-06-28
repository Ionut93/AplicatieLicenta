package com.example.ionut.licenta.Classes;

/**
 * Created by Ionut on 6/25/2015.
 */
public class Facts {

    private int Id;
    private String fact;

    public Facts() {
    }

    public Facts(int id, String fact) {
        Id = id;
        this.fact = fact;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getFact() {
        return fact;
    }

    public void setFact(String fact) {
        this.fact = fact;
    }
}
