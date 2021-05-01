package ch.heigvd.res.prankbot;

//import ch.heigvd.res.prankbot.smtp.SMTPClient;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import java.util.concurrent.Callable;

import ch.heigvd.res.prankbot.config.ConfigManager;
import ch.heigvd.res.prankbot.smtp.SMTPClient;

@Command(
        name="prankbot",
        description = "Bot d'envoi de pranks",
        version="1.0",
        mixinStandardHelpOptions = true
)
public class PrankBot implements Callable<Integer>
{

    @Option(names = { "-c", "--config"}, description = "Fichier .properties contenant la configuration de PrankBot", defaultValue = "config/config.properties") 
    private String configfile;

    @Option(names= { "-v", "--victimes"}, description = "Fichier JSON contenant les victimes", defaultValue = "config/victimes.json")
    private String victimesfile;

    @Option(names= { "-p", "--pranks"}, description = "Fichier JSON contenant les pranks", defaultValue = "config/pranks.json")
    private String prankfile;

    // générée depuis : https://textfancy.com/multiline-text-art/
    static final String BANNER = "\u2591\u2591\u2591\u2591\u2591\u2591  \u2591\u2591\u2591\u2591\u2591\u2591   \u2591\u2591\u2591\u2591\u2591  \u2591\u2591\u2591    \u2591\u2591 \u2591\u2591   \u2591\u2591 \u2591\u2591\u2591\u2591\u2591\u2591   \u2591\u2591\u2591\u2591\u2591\u2591  \u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591 \n\u2592\u2592   \u2592\u2592 \u2592\u2592   \u2592\u2592 \u2592\u2592   \u2592\u2592 \u2592\u2592\u2592\u2592   \u2592\u2592 \u2592\u2592  \u2592\u2592  \u2592\u2592   \u2592\u2592 \u2592\u2592    \u2592\u2592    \u2592\u2592    \n\u2592\u2592\u2592\u2592\u2592\u2592  \u2592\u2592\u2592\u2592\u2592\u2592  \u2592\u2592\u2592\u2592\u2592\u2592\u2592 \u2592\u2592 \u2592\u2592  \u2592\u2592 \u2592\u2592\u2592\u2592\u2592   \u2592\u2592\u2592\u2592\u2592\u2592  \u2592\u2592    \u2592\u2592    \u2592\u2592    \n\u2593\u2593      \u2593\u2593   \u2593\u2593 \u2593\u2593   \u2593\u2593 \u2593\u2593  \u2593\u2593 \u2593\u2593 \u2593\u2593  \u2593\u2593  \u2593\u2593   \u2593\u2593 \u2593\u2593    \u2593\u2593    \u2593\u2593    \n\u2588\u2588      \u2588\u2588   \u2588\u2588 \u2588\u2588   \u2588\u2588 \u2588\u2588   \u2588\u2588\u2588\u2588 \u2588\u2588   \u2588\u2588 \u2588\u2588\u2588\u2588\u2588\u2588   \u2588\u2588\u2588\u2588\u2588\u2588     \u2588\u2588    \n";


    @Override
    public Integer call() throws Exception {

        try {

            System.out.println("\n" + BANNER);
            
            System.out.print("Récupération de la configuration ...");
            ConfigManager config = new ConfigManager(configfile, victimesfile);

            System.out.print("OK\nLecture des pranks...");
            PrankGenerator prankgen = new PrankGenerator(prankfile);

            System.out.print("OK\nConnexion au serveur SMTP (" + config.getSmtpServerAddress() + ":" + config.getSmtpServerPort() + ")...");
            SMTPClient client = new SMTPClient(config.getSmtpServerAddress(), config.getSmtpServerPort());
            
            System.out.print("Connexion réussie\n\nEnvoi des messages aux groupes...");
    
            // Envoi des pranks à chaque groupe de victimes
            for( Groupe g : config.getVictimes()){

                printVictimes(g);
            
                boolean res = client.sendPrank(g, prankgen.getRandomPrank());

                if(res)
                    System.out.println("Messages envoyés");
                
                System.out.println("\n");
            }
    
            client.close();

            System.out.println("...Fin du programme.\n");
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
}
