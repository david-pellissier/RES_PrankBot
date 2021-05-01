package ch.heigvd.res.prankbot;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Classe servant à lire et servir des pranks depuis un fichier JSON
 */
public class PrankGenerator {

    ArrayList<Prank> pranks = new ArrayList<Prank>();

    /**
     * Lit le fichier contenant la liste des pranks (ayant au moins un prank)
     * @param file fichier à lire
     * @throws ParseException
     * @throws IOException
     */
    public PrankGenerator(String file) throws IOException, ParseException, IllegalArgumentException{

        FileReader reader = new FileReader(file);

        JSONParser jsonParser = new JSONParser();
        
        JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);

        JSONArray listPranks = (JSONArray) jsonObject.get("pranks");

        if(listPranks.size() <= 0)
            throw new IllegalArgumentException("Pas de prank trouvé dans le fichier" + file);

        for(int i = 0; i < listPranks.size(); ++i){

            JSONObject jsonPrank = (JSONObject) listPranks.get(i);
            Prank p = new Prank((String)jsonPrank.get("subject"), (String)jsonPrank.get("content"));

            pranks.add(p);
        }
    }

    /**
     * @return un prank aléatoire dans la liste
     */
    public Prank getPrank(){

        Random r = new Random();
        int index = r.nextInt(pranks.size());

        return pranks.get(index);

    }

    public ArrayList<Prank> getPranks(){
        return pranks;
    }

}
