package ch.heigvd.res.prankbot;

import org.junit.Test;
import static org.junit.Assert.*;

public class PersonneTest 
{

    @Test
    public void Personne()
    {
        final String NAME = "Batman";
        final String MAIL = "batmanlevrai@gmail.com";
        Personne batman = null;
        try {
            batman = new Personne(MAIL, NAME);
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertEquals(NAME, batman.getName());
        assertEquals(MAIL, batman.getMail());
        assertEquals(MAIL, batman.toString());
    }

    @Test
    public void PersonneMailOnly(){
        final String MAIL = "robin145@yahoo.fr";
        Personne robin = null;
        
        try {
            robin = new Personne(MAIL);
        } catch (Exception e) {
            
            e.printStackTrace();
        }

        assertEquals(MAIL, robin.getName());
        assertEquals(MAIL, robin.getMail());
        assertEquals(MAIL, robin.toString());
    }

    @Test
    public void PersonneBadMail(){

        Personne p;

        // exemples tirés de : https://docs.blackberry.com/en/id-comm-collab/blackberry-athoc/blackberry-athoc/7_8/create-publish-alerts/email-format-validation/invalid-email-address-examples
        String[] badMails = new String[]{"Abc.example.com", "A@b@c@example.com", "a\"b(c)d,e:f;g<h>i[j\\k]l@example.com", 
        "just\"not\"right@example.com", "this is\"not\\allowed@example.com", "this\\ still\\\"notallowed@example.com" };

        for(String mail : badMails){

            p = null;

            try {
                p = new Personne(mail);
            }
            catch(final Exception e){
                assertEquals(e.getMessage(), "Adresse mail non conforme");
            }

            assertEquals(p, null);
        }
    }


}
