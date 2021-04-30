package ch.heigvd.res.prankbot;

import org.junit.Test;

import static org.junit.Assert.*;

import java.util.ArrayList;

public class GroupeTest 
{

    @Test
    public void InitGroup(){

        try {

        Personne p1 = new Personne("bla@bla.com"),
        p2 = new Personne("bla@bla.com"),
        p3 = new Personne("bla@bla.com"),
        p4 = new Personne("bla@bla.com"),
        p5 = new Personne("bla@bla.com"),
        p6 = new Personne("bla@bla.com"),
        p7 = new Personne("bla@bla.com"),
        p8 = new Personne("bla@bla.com"),
        p9 = new Personne("bla@bla.com"),
        p10 = new Personne("bla@bla.com");

        Groupe g = new Groupe(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10);
        ArrayList<Personne> destinataires = g.getDestinataires();

        // Check du nombre de victimes 
        assertEquals(10, g.nbVictimes());
        assertEquals(9, destinataires.size());

        // Emetteur correct
        assertEquals(p1, g.getEmetteur());

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    

    
    @Test
    public void AddLargeGroup(){

        final int MAX = 300;

        try {
            
            Personne e = null;
            
            e = new Personne("emitter@group.com");            

            Groupe g = new Groupe(e);

            for(int i = 0; i < MAX; ++i)
                g.addDestinataire(new Personne("destinataire" + i + "@group.com"));
            
            ArrayList<Personne> destinataires = g.getDestinataires();

            // Check du nombre de victimes 
            assertEquals(MAX+1, g.nbVictimes());
            assertEquals(MAX, destinataires.size());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


}
