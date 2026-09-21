package com.example.engan.model;

import java.io.Serializable;

public class Palabra implements Serializable {
    private int id;
    private String texto;
    private String pista;

    public Palabra(int id, String texto, String pista) {
        this.id = id;
        this.texto = texto;
        this.pista = pista;
    }

    public int getId() {
        return id;
    }

    public String getTexto() {
        return texto;
    }

    public String getPista() {
        return pista;
    }
}
