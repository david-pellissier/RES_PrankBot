package ch.heigvd.res.prankbot;

//import ch.heigvd.res.prankbot.smtp.SMTPClient;
import picocli.CommandLine;
import java.util.concurrent.Callable;

import ch.heigvd.res.prankbot.config.ConfigManager;
import ch.heigvd.res.prankbot.smtp.SMTPClient;

@CommandLine.Command(
        name="prankbot",
        description = "Envoi de mails forgés à un groupe de victimes",
        version="1.0",
        mixinStandardHelpOptions = true
)
public class PrankBot implements Callable<Integer>
{

    //@CommandLine.Option 

    @Override
    public Integer call() throws Exception {

        // TODO: utiliser les infos en argument pour run l'application
        try {
            ConfigManager config = new ConfigManager("prank-bot/config/config.properties", "prank-bot/config/victimes.json");
            PrankGenerator prankgen = new PrankGenerator("prank-bot/config/pranks.json");
            SMTPClient client = new SMTPClient(config.getSmtpServerAdress(), config.getSmtpServerPort());
    
            for( Groupe g : config.getVictimes()){
                client.sendPrank(g, prankgen.getPrank());
            }
    
            client.close();
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return -1;
        }
        
        return 0;
    }

    public static void main(String[] args )
    {
        int exitCode = new CommandLine(new PrankBot()).execute(args);
        System.exit(exitCode);
    }
}
