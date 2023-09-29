package model;


// TODO: modifica e aggiungi builder

public class Inquilino {
    private String nome;
    private String cognome;
    private String cf;
    private String dataNascita;
    private String cittàNascita;
    private String residenza;
    private String telefono;
    private String email;
    private boolean pagato = false;

    public Inquilino(String cf, String nome, String cognome, String dataNascita,
                     String cittàNascita, String residenza, String tel, String email)
    {
        this.nome = nome;
        this.cognome = cognome;
        this.cf = cf;
        this.dataNascita = dataNascita;
        this.cittàNascita = cittàNascita;
        this.residenza = residenza;
        this.telefono = tel;
        this.email = email;
    }

}
