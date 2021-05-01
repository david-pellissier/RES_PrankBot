package ch.heigvd.res.prankbot.smtp;

import ch.heigvd.res.prankbot.Groupe;
import ch.heigvd.res.prankbot.Prank;
import ch.heigvd.res.prankbot.Personne;

import org.junit.Test;

import static org.junit.Assert.*;

public class SMTPClientTest {

    SMTPClient smtp;
    static final String IP = "127.0.0.1"; // localhost

    public SMTPClientTest(){
        this.smtp = new SMTPClient(IP);
    }

    @Test
    public void connectionOpenedAndClosed(){

        assertTrue(smtp.isConnected());

        assertTrue(smtp.close());

        assertFalse(smtp.isConnected());
    }

    @Test
    public void sendPranks() {

        try {
        Groupe g1 = new Groupe(new Personne("test@gmail.com"), 
                                new Personne("test2@gmail.com"), 
                                new Personne("test4@gmail.com"),
                                new Personne("test5@gmail.com"));
        
        Groupe g2 = new Groupe(new Personne("test@bluewin.ch"), 
                                new Personne("test2@bluewin.ch"), 
                                new Personne("test4@bluewin.ch"),
                                new Personne("test5@bluewin.ch"));
                                
        Prank prank = new Prank("Mail test", "Ceci est un test, vous pouvez ignorer ce mail");

        assertTrue(smtp.sendPrank(g1, prank));
        assertTrue(smtp.sendPrank(g2, prank));

        smtp.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}