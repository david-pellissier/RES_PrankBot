package ch.heigvd.res.prankbot;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Groupe composé un émetteur et 0 à n destinataires
 */
public class Groupe {

    private final Personne emetteur;
    private ArrayList<Personne> destinataires = new ArrayList<Personne>();

    public Groupe(Personne emetteur, Personne ... destinataires){

        this.emetteur = emetteur;

        if(destinataires != null)
            this.destinataires.addAll(Arrays.asList(destinataires));
    }

    /**
     * Ajoute un destinataire au groupe
     * 
     * @param p la personne à ajouter
     */
    public void addDestinataire(Personne p){
        destinataires.add(p);
    }

    /**
     * @return L'émetteur du groupe
     */
    public Personne getEmetteur(){
        return emetteur;
    }

    /**
     * @return La liste des destinataires (peut être vide)
     */
    public ArrayList<Personne> getDestinataires(){
        return destinataires;
    }

    /**
     * @return le nombre de victimes dans le groupe (y compris l'émetteur)
     */
    public int nbVictimes(){
        return 1 + this.destinataires.size();
    }

}
