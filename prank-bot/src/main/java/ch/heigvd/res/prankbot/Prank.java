package ch.heigvd.res.prankbot;

public class Prank {

    private final String template;
    private final String subject;

    private static final String 
        E_NAME = "%e_name%",
        E_MAIL = "%e_mail%",
        D_NAME = "%d_name%",
        D_MAIL = "%d_mail%";

    public Prank(String subject, String template){
        this.subject = subject;
        this.template = template;
    }

    public String getMessage(Personne emetteur, Personne destinataire){

        // En-tête
        String from = "From: " + emetteur + "\n";
        
        String to = "To: " + destinataire + "\n";
        String subject = "Subject: " + replaceVariables(this.subject, emetteur, destinataire) + "\n\n";
        String content = replaceVariables(this.template, emetteur, destinataire);

        return from + to + subject + content;
    }

    private String replaceVariables(String s, Personne e, Personne d){

        String res = s;

        // Remplaçage des variables
        res = res.replace(E_MAIL, e.getMail());
        res = res.replace(E_NAME, e.getName());

        res = res.replace(D_MAIL, d.getMail());
        res = res.replace(D_NAME, d.getMail());

        return res;
    }


}
