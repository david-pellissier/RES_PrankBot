package ch.heigvd.res.prankbot;

import java.nio.charset.StandardCharsets;
import org.apache.commons.codec.binary.Base64;

/**
 * Classe servant à préparer des messages pour l'envoi par mail en fonction d'un template
 */
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

    /**
     * Formatte le message pour être prêt à être envoyé par SMTPClient. 
     * Les variables du template sont remplacées par les personnes passées en paramètre.
     * 
     * @param emetteur personne qui envoie le message
     * @param destinataire personne qui reçoit le message
     * @return le message avec en-tête 
     */
    public String getMessage(Personne emetteur, Personne destinataire){

        // En-tête
        String from = "From: " + emetteur + "\n";
        String to = "To: " + destinataire + "\n";
        String subject_e= encodeB64(replaceVariables(this.subject, emetteur, destinataire));
        String subject = "Subject: =?utf-8?B?" + subject_e + "?=\n";
        String encoding = "Content-Type: text/plain; charset=utf-8 \n\n";
        String content = replaceVariables(this.template, emetteur, destinataire);

        return from + to + subject + encoding + content;
    }

    /**
     * Encode en base64 le sujet du message pour préserver l'encodage UTF-8
     * @param s la chaîne de caractère à encoder
     * @return String du sujet du message pr
     */
    private String encodeB64(String s){

        return new String(Base64.encodeBase64(s.getBytes(StandardCharsets.UTF_8)));
    }

    /**
     * Remplace les variables dans la string passée en paramètre
     * @param s String à traiter
     * @param e émetteur (%e_mail% et %e_name%)
     * @param d destinataire  (%d_mail% et %d_name%)
     * @return la string avec les variables remplacées
     */
    private String replaceVariables(String s, Personne e, Personne d){

        String res = s;

        // Remplaçage des variables
        res = res.replace(E_MAIL, e.getMail());
        res = res.replace(E_NAME, e.getName());

        res = res.replace(D_MAIL, d.getMail());
        res = res.replace(D_NAME, d.getName());

        return res;
    }


}
