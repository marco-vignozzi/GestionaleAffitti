package model;


public class Inquilino extends User{
    public Inquilino(String email, String pwd, String nome, String cognome, String cf, String dataNascita,
                     String cittàNascita, String residenza, String tel, String idContratto)
    {
        super(email, pwd, nome, cognome, cf, dataNascita, cittàNascita, residenza, tel);
        this.idContratto = idContratto;
    }

    private String idContratto;

}
