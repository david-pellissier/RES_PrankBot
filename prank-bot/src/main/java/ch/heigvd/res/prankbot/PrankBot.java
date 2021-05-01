package ch.heigvd.res.prankbot;

//import ch.heigvd.res.prankbot.smtp.SMTPClient;
import picocli.CommandLine;
import java.util.concurrent.Callable;

import ch.heigvd.res.prankbot.smtp.SMTPClient;

@CommandLine.Command(
        name="prankbot",
        description = "Envoi de mails forgés à un groupe de victimes",
        version="1.0",
        mixinStandardHelpOptions = true
)
public class PrankBot implements Callable<Integer>
{
    @Override
    public Integer call() throws Exception {

        // TODO: utiliser les infos en argument pour run l'application
        
        SMTPClient client = new SMTPClient("localhost");

        Personne alice = new Personne("alice@gmail.com", "Alice");
        Personne bob = new Personne("bob@gmail.com", "Bob");
        Personne charlie = new Personne("charlie@gmail.com", "Charlie");

        final String SUJET = "Demande importante à %d_name%";
        final String TEMPLATE = "Bonjour %d_name%,\n pouvez-vous me prêter 10'000chf ? C'est pour le travail \n\n %e_name%";
        Prank p = new Prank(SUJET, TEMPLATE);
        Groupe g = new Groupe(alice, bob, charlie);

        if(client.isConnected()){
            client.sendPrank(g, p);
        }

        client.close();

        return 0;
    }

    public static void main(String[] args )
    {
        int exitCode = new CommandLine(new PrankBot()).execute(args);
        System.exit(exitCode);
    }
}
