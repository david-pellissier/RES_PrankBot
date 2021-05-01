package ch.heigvd.res.prankbot.config;


import ch.heigvd.res.prankbot.Personne;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Properties;

public class ConfigManager {
    private List<Personne> victimes;

    public ConfigManager (String configFile, String victimesFile) throws IOException {
        Properties prop = readPropertiesFile(configFile);
        String smtpServerAdress = prop.getProperty("smtpServerAdress");
        int smtpServerPort = Integer.parseInt(prop.getProperty("smtpServerPort"));
        int numberOfGroups = Integer.parseInt(prop.getProperty("numberOfGroups"));
    }

    private Properties readPropertiesFile(String file) throws IOException {
        Properties prop = null;
        try (Reader reader = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8)) {
            prop = new Properties();
            prop.load(reader);
        } catch (IOException fnfe) {
            fnfe.printStackTrace();
        }

        return prop;
    }
}
