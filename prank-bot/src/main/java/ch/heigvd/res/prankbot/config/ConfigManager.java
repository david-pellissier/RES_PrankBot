package ch.heigvd.res.prankbot.config;


import ch.heigvd.res.prankbot.Personne;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

public class ConfigManager {
    String smtpServerAdress;
    int smtpServerPort;
    int numberOfGroups;
    private final List<Personne> victimes;

    public ConfigManager (String configFile, String victimesFile) throws IOException {
        Properties prop = readPropertiesFile(configFile);
        smtpServerAdress = prop.getProperty("smtpServerAdress");
        smtpServerPort = Integer.parseInt(prop.getProperty("smtpServerPort"));
        numberOfGroups = Integer.parseInt(prop.getProperty("numberOfGroups"));
        victimes = getVictimes(victimesFile, numberOfGroups);
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

    private List<Personne> getVictimes (String file, int number) {
        List<Personne> victimes = new ArrayList<>();
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader(file)) {
            Object obj = jsonParser.parse(reader);

            JSONObject jsonObject = (JSONObject) obj;

            JSONArray victimesList = (JSONArray) jsonObject.get("victimes");

            int counter = 0;
            Iterator<JSONObject> iterator = victimesList.iterator();
            while (iterator.hasNext() && counter < number) {
                JSONObject truc = iterator.next();
                if (truc.containsKey("name")) {
                    victimes.add(new Personne((String) truc.get("mail"), (String) truc.get("name")));
                } else {
                    victimes.add(new Personne((String) truc.get("mail")));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return victimes;
    }

    public String getSmtpServerAdress () {
        return smtpServerAdress;
    }

    public int getSmtpServerPort () {
        return smtpServerPort;
    }

    public int getNumberOfGroups () {
        return numberOfGroups;
    }

    public List<Personne> getVictimes () {
        return victimes;
    }
}
