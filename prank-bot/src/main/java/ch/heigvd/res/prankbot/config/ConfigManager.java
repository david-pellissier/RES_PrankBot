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
    private final ArrayList<Groupe> victimes;

    /**
     * @brief Classe gérant la configuration de l'application
     * @param configFile Fichier de configuration contenant les informations tel que le serveur SMTP, le port et le
     *                   nombre de groupes
     * @param victimesFile Fichier contenant une liste de victimes
     * @throws Exception
     */
    public ConfigManager (String configFile, String victimesFile) throws Exception {
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
     * @throws IOException
     */
    private Properties readPropertiesFile(String file) throws IOException {
        Properties prop = null;

        Reader reader = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8);
        prop = new Properties();
        prop.load(reader);

        return prop;
    }

    /**
     * @brief Initialise la liste de victimes à partir d'un fichier JSON. Tire un émetteur au hasard ainsi que les
     *        victimes parmi la liste. Lance une exception si le nombre de victimes est plus grand que la taille de la
     *        liste.
     * @param file Chemin du fichier de victimes
     * @param number Nombre de victimes
     * @return Un Groupe composé d'un émetteur et d'une liste de destinataire
     * @throws Exception
     */
    private ArrayList<Groupe> getVictimes (String file, int number) throws Exception {
        Personne emetteur;
        ArrayList<Groupe> groupesVictimes = new ArrayList<>();
        Groupe victimes;

        JSONParser jsonParser = new JSONParser();

        FileReader reader = new FileReader(file);
        Object obj = jsonParser.parse(reader);

        JSONObject jsonObject = (JSONObject) obj;

        JSONArray listVictimes = (JSONArray) jsonObject.get("victimes");

        if (number > listVictimes.size())
            throw new IllegalArgumentException("number of victims must be lesser " +
                    "than the size of the victimes file");

        if ((listVictimes.size() / number) < 3)
            throw new IllegalArgumentException("There must be at least 3 victims per groups");

        int listSize = listVictimes.size();

        for (int i = 0; i < number; ++i) {
            int numberEmetteur = getRandomNumberInRange(1, listVictimes.size());
            JSONObject jsonEmetteur = (JSONObject) listVictimes.get(numberEmetteur);
            if (jsonEmetteur.containsKey("name")) {
                emetteur = new Personne((String) jsonEmetteur.get("mail"), (String) jsonEmetteur.get("name"));
            } else {
                emetteur = new Personne((String) jsonEmetteur.get("mail"));
            }
            listVictimes.remove(numberEmetteur);

            victimes = new Groupe(emetteur);

            for (int j = 0; j < (listSize / number) - 1; ++j) {
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
            groupesVictimes.add(victimes);
        }

        return groupesVictimes;
    }

    /**
     * @brief Génère un nombre aléatoire entre min et max
     * @param min Borne inférieure
     * @param max Borne supérieure
     * @return Un nombre aléatoire entre min et max
     */
    private int getRandomNumberInRange(int min, int max) {
        /* Quand il n'y a plus qu'un seul élément dans la liste de victimes.json pendant la création de groupe, il n'est
         * pas nécessaire de générer un nombre aléatoire et on peut directement récupérer le seul élément de la liste
         */
        if (max == min)
            return 0;
        if (min > max) {
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

    public ArrayList<Groupe> getVictimes () {
        return victimes;
    }
}
