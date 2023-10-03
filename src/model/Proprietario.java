package model;

import java.time.LocalDate;
import java.util.Vector;

public class Proprietario {
    public Proprietario(String email, String pwd, String nome, String cognome, String cf)
    {
        this.email = email;
        password = pwd;
        this.nome = nome;
        this.cognome = cognome;
        this.cf = cf;
    }

    public String getNome() {
        return nome;
    }
    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getCf() {
        return cf;
    }

    public void setCf(String cf) {
        this.cf = cf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }



    private String nome;
    private String cognome;
    private String cf;
    private String email;
    private String password;

}
