package ch.heigvd.res.prankbot.smtp;

import ch.heigvd.res.prankbot.Groupe;
import ch.heigvd.res.prankbot.Prank;

import java.io.*;
import java.net.Socket;

import static java.lang.System.exit;

public class SMTPClient {

    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    static final int DEFAULT_PORT = 25;
    static final String ED = "\r\n";
    static final String END_MSG = ED+"."+ED;

    public SMTPClient(String ip){
        this(ip, DEFAULT_PORT);
    }

    public SMTPClient(String ip, int port){
        try {
            this.socket = new Socket(ip, port);
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.out = new PrintWriter(socket.getOutputStream());

            if(! in.readLine().startsWith("220 ")){
                throw new Exception("");
            }
        }
        catch(final Exception e){
            System.out.println(e.getMessage());
            System.out.println("Erreur de connexion au serveur");
            exit(1);
        }
    }

    public boolean close(){
        try {
            out.print("quit" + ED);
            out.flush();

            // Confirmation du serveur
            if(! in.readLine().startsWith("221 ")){
                return false;
            }

            // fermeture des flux
            in.close();
            out.close();
            socket.close();
        }
        catch(final Exception e){
            return false;
        }

        return true;
    }

    private void waitAccept() throws IOException {

        String s = in.readLine();

        while(! s.startsWith("250 ")){
            // just wait
            s = in.readLine();
        }

    }

    private void send(String data){
        out.print(data + ED);
        out.flush();

    }
    private boolean sendMail(String msgContent, String from, String to){
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
            if(! in.readLine().startsWith("354 ")){
                return false;
            }

            // Envoi du contenu du message
            send(msgContent + END_MSG);
            waitAccept();
        }

        catch(Exception e){
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public boolean sendPrank(Groupe g, Prank p){

        // TODO utiliser les informations passées en paramètre
        String p1 = "dav.pellissier@gmail.com";
        String p2 = "richard@gmail.com";
        String msg =
                "From: " + p1 + "\n" +
                "To: " + p2 + "\n" +
                "Subject: Nop\n\n"+
                "YOU JUST GOT PRANNNKED BBRROO!!! :DDDDD";

        return sendMail(msg, p1, p2);
    }
}
