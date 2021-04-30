package ch.heigvd.res.prankbot;

import java.util.regex.*;
import java.lang.Exception;

public class Personne {

    private final String mail;
    private final String name;
    
    public Personne(String mail) throws Exception {
        this(mail, mail);
    }

    public Personne(String mail, String name) throws Exception {

        if(!validateMail(mail))
            throw new Exception("Adresse mail non conforme");

        this.mail = mail;
        this.name = name;

    }

    public String getMail(){
        return this.mail;
    }

    public String getName(){
        return this.name;
    }

    @Override
    public String toString() {
        return this.mail;
    }

    private boolean validateMail(String m){
        // source : https://www.emailregex.com/
        final String PATTERN_STRING ="(?:[a-z0-9!#$%&'*+\\/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+\\/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])/gm";

        Pattern pattern = Pattern.compile(PATTERN_STRING);
        Matcher matcher = pattern.matcher(m);

        return matcher.matches();
    }
}
