package ch.heigvd.res.prankbot;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

public class PrankGeneratorTest {

    static final String CONF_STRING = "config/pranks.json";

    @Test
    public void readContent() {

        try {
            PrankGenerator gen = new PrankGenerator(CONF_STRING);

            Personne alice = new Personne("alice@test.com");
            Personne bob = new Personne("bob@test.com");

            ArrayList<Prank> result = gen.getPranks();

            Prank[] expected = {new Prank("s1", "c1"), new Prank("s2", "c2"), new Prank("s3", "c3") };

            for(int i = 0; i < 3; ++i){
                assertEquals(expected[i].getMessage(alice, bob), result.get(i).getMessage(alice, bob));
            }

        } catch (Exception e) {
            
        }
    }
        
}
