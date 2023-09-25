package model;

import java.time.LocalDate;


public abstract class User {
    public User(String email, String pwd, String nome, String cognome, String cf, String dataNascita,
                String cittàNascita, String residenza, String tel)
    {
        nome = nome;
        cognome = cognome;
        cf = cf;
        dataNascita = dataNascita;
        cittàNascita = cittàNascita;
        residenza = residenza;
        email = email;
        telefono = telefono;
        password = password;
    }

    private String nome;
    private String cognome;
    private String cf;
    private String cittàNascita;
    private LocalDate dataNascita;
    private String email;
    private String telefono;
    //TODO: capire se aggiungere o no la password, per ora non è inizializzata
    private String password;

}
