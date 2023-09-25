package model;

import java.time.LocalDate;


public abstract class User {
    public User(String nome, String cognome, String cf, String dataNascita, String cittàNascita, String residenza,
                String email, String telefono) {
        nome = nome;
        cognome = cognome;
        cf = cf;
        dataNascita = dataNascita;
        cittàNascita = cittàNascita;
        residenza = residenza;
        email = email;
        telefono = telefono;
    }

    private String nome;
    private String cognome;
    private String cf;
    private String cittàNascita;
    private LocalDate dataNascita;
    private String email;
    private String telefono;

}
