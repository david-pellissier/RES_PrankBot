package ch.heigvd.res.prankbot;

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
        // Affichage de l'aide si on ne spécifie pas d'argument
        return new CommandLine(new PrankBot()).execute("--help");
    }

    public static void main(String[] args )
    {
        int exitCode = new CommandLine(new PrankBot()).execute(args);
        System.exit(exitCode);
    }
}
