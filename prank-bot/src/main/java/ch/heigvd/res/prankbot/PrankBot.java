package ch.heigvd.res.prankbot;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import java.util.concurrent.Callable;

import ch.heigvd.res.prankbot.config.ConfigManager;
import ch.heigvd.res.prankbot.smtp.SMTPClient;

@Command(
        name="prankbot",
        description = "Bot d'envoi de pranks",
        version="1.1",
        mixinStandardHelpOptions = true
)
public class PrankBot implements Callable<Integer>
{
    private final String defaultConfig = "config.properties";
    private final String defaultVictimes = "victimes.json";
    private final String defaultPranks = "pranks.json";
    private final String defaultDir = "config/";

    @Option(names = { "-c", "--config"}, description = "Fichier .properties contenant la configuration de PrankBot",
            defaultValue = defaultDir + defaultConfig)
    private String configfile;

    @Option(names= { "-v", "--victimes"}, description = "Fichier JSON contenant les victimes",
            defaultValue = defaultDir + defaultVictimes)
    private String victimesfile;

    @Option(names= { "-p", "--pranks"}, description = "Fichier JSON contenant les pranks",
            defaultValue = defaultDir + defaultPranks)
    private String prankfile;

    @Option(names= { "-d", "--directory"}, description = "Dossier contenant les fichiers de configuration",
            defaultValue = defaultDir)
    private String directory;

    // générée depuis : https://textfancy.com/multiline-text-art/
    static final String BANNER = "\u2591\u2591\u2591\u2591\u2591\u2591  \u2591\u2591\u2591\u2591\u2591\u2591   \u2591\u2591\u2591\u2591\u2591  \u2591\u2591\u2591    \u2591\u2591 \u2591\u2591   \u2591\u2591 \u2591\u2591\u2591\u2591\u2591\u2591   \u2591\u2591\u2591\u2591\u2591\u2591  \u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591 \n\u2592\u2592   \u2592\u2592 \u2592\u2592   \u2592\u2592 \u2592\u2592   \u2592\u2592 \u2592\u2592\u2592\u2592   \u2592\u2592 \u2592\u2592  \u2592\u2592  \u2592\u2592   \u2592\u2592 \u2592\u2592    \u2592\u2592    \u2592\u2592    \n\u2592\u2592\u2592\u2592\u2592\u2592  \u2592\u2592\u2592\u2592\u2592\u2592  \u2592\u2592\u2592\u2592\u2592\u2592\u2592 \u2592\u2592 \u2592\u2592  \u2592\u2592 \u2592\u2592\u2592\u2592\u2592   \u2592\u2592\u2592\u2592\u2592\u2592  \u2592\u2592    \u2592\u2592    \u2592\u2592    \n\u2593\u2593      \u2593\u2593   \u2593\u2593 \u2593\u2593   \u2593\u2593 \u2593\u2593  \u2593\u2593 \u2593\u2593 \u2593\u2593  \u2593\u2593  \u2593\u2593   \u2593\u2593 \u2593\u2593    \u2593\u2593    \u2593\u2593    \n\u2588\u2588      \u2588\u2588   \u2588\u2588 \u2588\u2588   \u2588\u2588 \u2588\u2588   \u2588\u2588\u2588\u2588 \u2588\u2588   \u2588\u2588 \u2588\u2588\u2588\u2588\u2588\u2588   \u2588\u2588\u2588\u2588\u2588\u2588     \u2588\u2588    \n";


    @Override
    public Integer call() throws Exception {
        ConfigManager config;
        PrankGenerator prankgen;

        try {
            System.out.println("\n" + BANNER);

            if (!directory.equals(defaultDir)) {

                // l'utilisateur peut ne pas avoir mis le "/" à la fin alors il faut l'ajouter
                if(! directory.endsWith("/"))
                    directory = directory + '/';

                config = initConfig(directory + defaultConfig, directory + defaultVictimes);
                prankgen = initPranks(directory + defaultPranks);
            } else {
                config = initConfig(configfile, victimesfile);
                prankgen = initPranks(prankfile);
            }

            System.out.print("OK\nConnexion au serveur SMTP (" + config.getSmtpServerAddress() + ":" + config.getSmtpServerPort() + ")…");
            SMTPClient client = new SMTPClient(config.getSmtpServerAddress(), config.getSmtpServerPort());
            
            System.out.print("Connexion réussie\n\nEnvoi des messages aux groupes…");
    
            // Envoi des pranks à chaque groupe de victimes
            for( Groupe g : config.getVictimes()){

                printVictimes(g);
            
                boolean res = client.sendPrank(g, prankgen.getRandomPrank());

                if(res)
                    System.out.println("Messages envoyés");
                
                System.out.println("\n");
            }
    
            client.close();

            System.out.println("…Fin du programme.\n");
        }
        catch(Exception e){
            System.out.println("\nErreur:\n" + e.getMessage());
            return -1;
        }
        
        return 0;
    }

    public static void main(String[] args )
    {
        int exitCode = new CommandLine(new PrankBot()).execute(args);
        System.exit(exitCode);
    }


    private void printVictimes(Groupe g){

        System.out.print("Groupe :\n\tEmetteur: \"" + g.getEmetteur().getName() + "\"\n\tDestinataires: ");
        for(Personne p : g.getDestinataires()){
            System.out.print("\""+ p.getName() + "\" ");
        }

        System.out.println("");
    }

    private ConfigManager initConfig(String c, String v) throws Exception {
        System.out.print("Récupération de la configuration…");
        return new ConfigManager(c, v);
    }

    private PrankGenerator initPranks(String p) throws Exception {
        System.out.print("OK\nLecture des pranks…");
        return new PrankGenerator(p);
    }
}

