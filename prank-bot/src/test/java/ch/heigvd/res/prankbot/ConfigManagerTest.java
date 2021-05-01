package ch.heigvd.res.prankbot;

import ch.heigvd.res.prankbot.config.ConfigManager;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.io.IOException;

public class ConfigManagerTest {
    @Test
    public void InitConfigManager() throws IOException {
        String expectedAddress = "127.0.0.1";
        int expectedPort = 2525;
        int expectedNumber = 8;
        int expectedNbVictimes = 8;

        ConfigManager cm = new ConfigManager("config/config.properties", "config/victimes.json");

        assertEquals(expectedAddress, cm.getSmtpServerAdress());
        assertEquals(expectedPort, cm.getSmtpServerPort());
        assertEquals(expectedNumber, cm.getNumberOfGroups());
        assertEquals(expectedNbVictimes, cm.getVictimes().nbVictimes());
    }
}
