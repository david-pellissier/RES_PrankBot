package ch.heigvd.res.prankbot;

import java.util.ArrayList;
import java.util.Arrays;

public class Groupe {

    private final Personne emetteur;
    private ArrayList<Personne> destinataires = new ArrayList<Personne>();

    public Groupe(Personne emetteur, Personne ... destinataires){

        this.emetteur = emetteur;

        if(destinataires != null)
            this.destinataires.addAll(Arrays.asList(destinataires));
    }

    public void addDestinataire(Personne p){
        destinataires.add(p);
    }

    public Personne getEmetteur(){
        return emetteur;
    }

    public ArrayList<Personne> getDestinataires(){
        return destinataires;
    }

    public int nbVictimes(){
        return 1 + this.destinataires.size();
    }

}
