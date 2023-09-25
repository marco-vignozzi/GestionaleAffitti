package model;

import java.util.Vector;

public class Proprietario extends User{
    public Proprietario(String email, String pwd, String nome, String cognome, String cf, String dataNascita,
                        String cittàNascita, String residenza, String tel)
    {
        super(email, pwd, nome, cognome, cf, dataNascita, cittàNascita, residenza, tel);
    }

    private String[] idContratti;
}
