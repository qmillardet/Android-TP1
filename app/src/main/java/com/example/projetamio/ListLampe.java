package com.example.projetamio;

import java.util.ArrayList;
import java.util.HashMap;

public class ListLampe {

    private HashMap<String,DonneesLampe> listLampe;
    private static ListLampe instance;

    private ListLampe(){
        this.listLampe = new HashMap<String, DonneesLampe>();
        ListLampe.instance = this;
    }

    public DonneesLampe getLampe(String nomLampe){
        DonneesLampe lampe = this.listLampe.get(nomLampe);
        if (lampe != null){
            lampe = new DonneesLampe(nomLampe);
            this.listLampe.put(nomLampe, lampe);
        }
        return lampe;
    }

    public boolean removeLampe(String nomLampe){
        this.listLampe.put(nomLampe, null);
        return true;
    }

    public static ListLampe getInstance(){
        if (ListLampe.instance == null ){
            new ListLampe();
        }
        return ListLampe.instance;
    }
}
