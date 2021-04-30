package ch.heigvd.res.prankbot;

import ch.heigvd.res.prankbot.smtp.SMTPClient;
import picocli.CommandLine;
import java.util.concurrent.Callable;

@CommandLine.Command(
        name="prankbot",
        description = "Envoi de mails forgés à un groupe de victimes",
        version="fuck edition",
        mixinStandardHelpOptions = true
)
public class PrankBot implements Callable<Integer>
{
    @Override
    public Integer call() throws Exception {

        // TODO: utiliser les infos en argument pour run l'application

        return 0;
    }

    public static void main(String[] args )
    {
        int exitCode = new CommandLine(new PrankBot()).execute(args);
        System.exit(exitCode);
    }
}
