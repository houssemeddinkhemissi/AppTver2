package com.khemissi.houssemeddin.apptver2;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Lenovo on 20/11/2017.
 */

public class EmissionDB {

    @SerializedName("idEmission")
    private int idEmission;

    @SerializedName("nom")
    private String nom;

    @SerializedName("image")
    private String image;

    @SerializedName("description")
    private String description;

    @SerializedName("categorie")
    private String categorie;

    public int getIdEmission() {
        return idEmission;
    }

    public void setIdEmission(int idEmission) {
        this.idEmission = idEmission;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public class Ch {

        @SerializedName("idChaine")
        private int idChaine;


        @SerializedName("nom")
        private String nom;

        @SerializedName("image")
        private String image;


        public int getIdChaine() {
            return idChaine;
        }

        public void setIdChaine(int idChaine) {
            this.idChaine = idChaine;
        }

        public String getNom() {
            return nom;
        }

        public void setNom(String nom) {
            this.nom = nom;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }
    }


}
