package ch.heigvd.res.prankbot;

import ch.heigvd.res.prankbot.config.ConfigManager;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.io.IOException;

public class ConfigManagerTest {
    @Test
    public void InitConfigManager() {
        String expectedAddress = "127.0.0.1";
        int expectedPort = 2525;
        int expectedNumber = 10;
        int expectedNbPerGr = 3;

        ConfigManager cm = new ConfigManager(
                "src/test/java/ch/heigvd/res/prankbot/config/config.properties",
                "src/test/java/ch/heigvd/res/prankbot/config/victimes.json");

        assertEquals(expectedAddress, cm.getSmtpServerAdress());
        assertEquals(expectedPort, cm.getSmtpServerPort());
        assertEquals(expectedNumber, cm.getNumberOfGroups());
        assertEquals(expectedNumber, cm.getVictimes().size());
        for (int i = 0; i < expectedNumber; ++i) {
            assertEquals(expectedNbPerGr, cm.getVictimes().get(i).nbVictimes());
        }

    }
}
