package ch.heigvd.res.prankbot.config;


import ch.heigvd.res.prankbot.Groupe;
import ch.heigvd.res.prankbot.Personne;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class ConfigManager {
    private final String smtpServerAdress;
    private final int smtpServerPort;
    private final int numberOfGroups;
    private final Groupe victimes;

    /**
     * @brief Classe gérant la configuration de l'application
     * @param configFile Fichier de configuration contenant les informations tel que le serveur SMTP, le port et la
     *                   taille des groupes
     * @param victimesFile Fichier contenant une liste de victimes
     */
    public ConfigManager (String configFile, String victimesFile) {
        Properties prop = readPropertiesFile(configFile);
        smtpServerAdress = prop.getProperty("smtpServerAdress");
        smtpServerPort = Integer.parseInt(prop.getProperty("smtpServerPort"));
        numberOfGroups = Integer.parseInt(prop.getProperty("numberOfGroups"));
        victimes = getVictimes(victimesFile, numberOfGroups);
    }

    /**
     * @brief Lit le fichier de configuration et récupère les informations
     * @param file Chemin du fichier
     * @return Les propriétés de la configuration
     */
    private Properties readPropertiesFile(String file) {
        Properties prop = null;
        try (Reader reader = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8)) {
            prop = new Properties();
            prop.load(reader);
        } catch (IOException fnfe) {
            fnfe.printStackTrace();
        }

        return prop;
    }

    /**
     * @brief Initialiste la liste de victimes à partir d'un fichier JSON. Tire un émetteur au hasard ainsi que les
     *        victimes parmi la liste. Lance une exception si le nombre de victimes est plus grand que la taille de la
     *        liste.
     * @param file Chemin du fichier de victimes
     * @param number Nombre de victimes
     * @return Un Groupe composé d'un émetteur et d'une liste de destinataire
     */
    private Groupe getVictimes (String file, int number) {
        Personne emetteur;
        Groupe victimes = null;

        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader(file)) {
            Object obj = jsonParser.parse(reader);

            JSONObject jsonObject = (JSONObject) obj;

            JSONArray listVictimes = (JSONArray) jsonObject.get("victimes");

            if (number > listVictimes.size())
                throw new IllegalArgumentException("number of victims must be lesser " +
                        "than the size of the victimes file");

            int numberEmetteur = getRandomNumberInRange(1, listVictimes.size());
            JSONObject jsonEmetteur = (JSONObject) listVictimes.get(numberEmetteur);
            if (jsonEmetteur.containsKey("name")) {
                emetteur = new Personne((String) jsonEmetteur.get("mail"), (String) jsonEmetteur.get("name"));
            } else {
                emetteur = new Personne((String) jsonEmetteur.get("mail"));
            }
            listVictimes.remove(numberEmetteur);

            victimes = new Groupe(emetteur);

            for (int i = 0; i < number - 1; ++i) {
                int randVictime = getRandomNumberInRange(1, listVictimes.size());
                JSONObject jsonVictime = (JSONObject) listVictimes.get(randVictime);
                if (jsonVictime.containsKey("name")) {
                    victimes.addDestinataire(new Personne((String) jsonVictime.get("mail"),
                            (String) jsonVictime.get("name")));
                } else {
                    victimes.addDestinataire(new Personne((String) jsonVictime.get("mail")));
                }
                listVictimes.remove(randVictime);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return victimes;
    }

    /**
     * @brief Génère un nombre aléatoire entre min et max
     * @param min Borne inférieur
     * @param max Borne supérieur
     * @return Un nombre aléatoire entre min et max
     */
    private int getRandomNumberInRange(int min, int max) {
        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt(max - min) + min;
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

    public Groupe getVictimes () {
        return victimes;
    }
}
