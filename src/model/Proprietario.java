package model;

import java.util.Vector;

public class Proprietario extends User{
    public Proprietario(String nome, String cognome, String cf, String dataNascita, String cittàNascita, String residenza,
                        String email,String tel,String idContratti) {
        super(nome, cognome, cf, dataNascita, cittàNascita, residenza, email, tel);
    }
    private String[] idContratti;
}
