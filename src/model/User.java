package model;

import java.time.LocalDate;


public abstract class User {

    public User(String email, String pwd) {
        email = email;
        password = pwd;
    } // TODO: vediamo se serve, non credo

    public User(String email, String pwd, String nome, String cognome, String cf, String dataNascita,
                String cittàNascita, String residenza, String tel)
    {
        email = email;
        password = pwd;
        nome = nome;
        cognome = cognome;
        cf = cf;
        dataNascita = dataNascita;
        cittàNascita = cittàNascita;
        residenza = residenza;
        telefono = tel;
    }

    private String nome;
    private String cognome;
    private String cf;
    private String cittàNascita;
    private LocalDate dataNascita; // TODO: capire se ha senso usare LocalDate
    private String email;
    private String telefono;
    private String password;

}
