package controller;

import dao.DatabaseDAO;
import model.Inquilino;
import model.Proprietario;

public class Controller {
    private DatabaseDAO dao;
    private Inquilino inquilino;
    private Proprietario proprietario;

    public void aggiungiproprietario(Proprietario proprietario){
      //FUNZIONE: dao.aggiungiproprietario(proprietario);
    }

    public boolean emailDisponibile(String email) {
        return dao.emailDisponibile(email);
    }

    public void aggiungiinquilino(Inquilino inquilino){
    }

}
