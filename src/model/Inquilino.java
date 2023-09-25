package model;


public class Inquilino extends User{
    public Inquilino(String nome, String cognome, String cf, String dataNascita, String cittàNascita, String residenza,
                     String email,String tel,String idContratto)
    {
        super(nome, cognome, cf, dataNascita, cittàNascita, residenza, email, tel);
        this.idContratto = idContratto;
    }

    private String idContratto;

}
