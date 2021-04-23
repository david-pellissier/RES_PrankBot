package ch.heigvd.res.prankbot.smtp;

import ch.heigvd.res.prankbot.Groupe;
import ch.heigvd.res.prankbot.Prank;

import java.io.*;
import java.net.Socket;

public class SMTPClient {

    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private boolean connected = false;

    static final int DEFAULT_PORT = 25;
    static final String CRLF = "\r\n";
    static final String END_MSG = CRLF + "." + CRLF;

    public SMTPClient(String ip) {
        this(ip, DEFAULT_PORT);
    }

    public SMTPClient(String ip, int port) {
        try {

            this.socket = new Socket(ip, port);
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.out = new PrintWriter(socket.getOutputStream());

            // Message de bienvenue
            if (!in.readLine().startsWith("220 ")) {
                throw new Exception();
            }

            connected = true;
        } catch (final Exception e) {
            System.out.println("Erreur: la connexion au serveur SMTP a échoué");
        }

    }

    /**
     * Ferme la connexion au serveur SMTP
     * @return true si la connexion a pu être fermée
     */
    public boolean close() {
        try {
            send("quit");

            // Confirmation du serveur
            if (!in.readLine().startsWith("221 ")) {
                return false;
            }

            // fermeture des flux
            in.close();
            out.close();
            socket.close();
        } catch (final Exception e) {
            System.out.println("Erreur: la connexion n'a pas pu être fermée");
            return false;
        }

        connected = false;
        return true;
    }

    /**
     * Attend de recevoir une ligne commençant par "250 "
     * @throws IOException dans le cas où il y a un problème de connexion (fermeture du côté serveur, timeout, etc...)
     */
    private void waitAccept() throws IOException {

        String s = in.readLine();

        while (!s.startsWith("250 ")) {
            // just wait
            s = in.readLine();
        }

    }

    /**
     * Envoie des données au serveur. Ajoute un CRLF à la fin du message.
     * @param data données à envoyer
     */
    private void send(String data) {
        out.print(data + CRLF);
        out.flush();
    }

    /**
     * Envoie un email avec les informations passées en argument.
     * @param msgContent Contenu du message à envoyer.
     *                   Le header doit y figurer mais les caractères de fin sont ajoutés à l'envoi.
     * @param from adresse de l'expéditeur
     * @param to adresse du destinataire
     * @return true si le mail a pu être envoyé
     */
    private boolean sendMail(String msgContent, String from, String to) {

        if(!isConnected())
            return false;

        try {

            // EHLO <>
            send("EHLO " + from);
            waitAccept();

            // MAIL FROM
            send("MAIL FROM: " + from);
            waitAccept();

            // RCPT TO <>
            send("RCPT TO: " + to);
            waitAccept();

            // DATA
            send("DATA");

            // Attente du message "ready"
            if (!in.readLine().startsWith("354 ")) {
                return false;
            }

            // Envoi du contenu du message
            send(msgContent + END_MSG);
            waitAccept();

        } catch (Exception e) {
            return false;
        }

        return true;
    }

    public boolean sendPrank(Groupe g, Prank p) {

        if(!isConnected())
            return false;

        // TODO utiliser les informations passées en paramètre
        String p1 = "dav.pellissier@gmail.com";
        String p2 = "richard@gmail.com";
        String msg =
                "From: " + p1 + "\n" +
                        "To: " + p2 + "\n" +
                        "Subject: Nop\n\n" +
                        "YOU JUST GOT PRANNNKED BBRROO!!! :DDDDD";

        return sendMail(msg, p1, p2);
    }

    public boolean isConnected() {
        return connected;
    }
}
