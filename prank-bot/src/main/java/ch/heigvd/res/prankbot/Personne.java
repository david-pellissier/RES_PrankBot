package ch.heigvd.res.prankbot;

import java.lang.Exception;

import org.apache.commons.validator.routines.EmailValidator;

public class Personne {

    private final String mail;
    private final String name;
    private static EmailValidator validator = EmailValidator.getInstance();
    
    public Personne(String mail) throws Exception {
        this(mail, mail);
    }

    public Personne(String mail, String name) throws Exception {

        if(!validator.isValid(mail))
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

}
