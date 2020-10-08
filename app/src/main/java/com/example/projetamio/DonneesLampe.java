package com.example.projetamio;

import android.content.Intent;
import android.net.Uri;

import java.util.ArrayList;

public class DonneesLampe {

    private final String nom;
    private final ArrayList<Long> donnees;
    private boolean etat = false;

    public DonneesLampe(String nom){
        this.nom = nom;
        this.donnees = new ArrayList<Long>();
    }


    public boolean isEtat() {
        return etat;
    }

    public boolean addEtat(Long valeur){
        this.donnees.add(valeur);
        int nbDonne = this.donnees.size();
        if(Math.abs((this.donnees.get(nbDonne) - this.donnees.get(nbDonne-1))) >= 15){
            // On vérifie le mode dans lequel mettre la lampe
            if (valeur > 250 ){
                this.etat = true;
            }
            else{
                this.etat = false;
            }
            return true;
        }
        return false;
    }

    public String getNom() {
        return nom;
    }
}
