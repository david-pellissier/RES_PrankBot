package ch.heigvd.res.prankbot;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PrankTest {

    Personne alice;
    Personne bob;
    private String alice_mail = "alice@gmail.com", alice_nom = "Alice";
    private String bob_mail = "bob@gmail.com", bob_nom = "Bob";

    public PrankTest(){
        try {
            alice = new Personne(alice_mail, alice_nom);
            bob = new Personne(bob_mail, bob_nom);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void PrankWithTemplate(){


        final String SUJET = "Demande importante à %d_name%";
        final String TEMPLATE = "Bonjour %d_name%,\n pouvez-vous me prêter 10'000chf ? C'est pour le travail \n\n %e_name%";

        final String EXPECTED = "From: " + alice_mail + 
                                "\nTo: " + bob_mail + 
                                "\nSubject: Demande importante à " + bob_nom + "\n\n" +
                                "Bonjour " + bob_nom + ",\n pouvez-vous me prêter 10'000chf ? C'est pour le travail \n\n " + alice_nom;

        Prank p = new Prank(SUJET, TEMPLATE);

        String msg = p.getMessage(alice, bob);

        assertEquals(EXPECTED, msg);

    }
    
}
