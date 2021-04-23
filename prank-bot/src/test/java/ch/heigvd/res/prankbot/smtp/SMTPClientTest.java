package ch.heigvd.res.prankbot.smtp;

import ch.heigvd.res.prankbot.Groupe;
import ch.heigvd.res.prankbot.Prank;
import org.junit.Test;

import static org.junit.Assert.*;

public class SMTPClientTest {

    SMTPClient smtp;
    static final String IP = "127.0.0.1"; // localhost

    public SMTPClientTest(){
        SMTPClient smtp = new SMTPClient(IP);
    }

    @Test
    public void connectionOpenedAndClosed(){

        assertTrue(smtp.isConnected());

        assertTrue(smtp.close());

        assertFalse(smtp.isConnected());
    }

    @Test
    public void sendPranks() {

        // TODO: initialiser les objets
        Groupe g1 = null;
        Groupe g2 = null;
        Prank prank = null;


        assertTrue(smtp.sendPrank(g1, prank));
        assertTrue(smtp.sendPrank(g2, prank));

        smtp.close();

    }
}