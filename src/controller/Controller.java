package controller;

import dao.DatabaseDAO;
import model.Inquilino;
import model.Proprietario;

public class Controller {

   public Controller() {
       dao = new DatabaseDAO();
       dao.connect();
   }

    public void aggiungiproprietario(Proprietario proprietario){
      // TODO: dao.aggiungiproprietario(proprietario);
    }

    public boolean emailDisponibile(String email) {
        return dao.emailDisponibile(email);
    }

    public void aggiungiinquilino(Inquilino inquilino){
        // TODO: implementare
    }

    public boolean isInquilino(String email, String password) {
        return false;       // TODO: implementare
    }

    public boolean isProprietario(String email, String password) {
        return false;       // TODO: implementare
    }

    public String getCognome(String email, String password) {
        return null;        // TODO: implementare
    }

    public String getNome(String email, String password) {
        return null;        // TODO: implementare
    }

    // questo metodo permette di pagare l'affitto dall'app
    public void pagaAffitto() {
        // TODO: implementare
    }

    // questo metodo permette di visualizzare il contratto di affitto
    public void visualizzaContratto() {
        // TODO: implementare
    }

    // questo metodo permette di visualizzare le notifiche
    public void visualizzaNotifiche() {
        // TODO: implementare
    }

    // questo metodo permette di visualizzare tutti gli immobili disponibili per l'affitto
    public void visualizzaImmobili() {
        // TODO: implementare
    }

    private DatabaseDAO dao;
    private Inquilino inquilino;
    private Proprietario proprietario;
}
