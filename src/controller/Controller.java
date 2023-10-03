package controller;

import dao.InquilinoDAO;
import dao.ProprietarioDAO;
import model.Inquilino;
import model.Proprietario;

import java.util.Scanner;

public class Controller {
    private Scanner scanner;
    private ProprietarioDAO proprietarioDao;
    private InquilinoDAO inquilinoDao;
    //private ContrattoDAO contrattoDao;
    //private ImmobileDao immobileDao;

    private Proprietario proprietario;


    public Controller() {
        proprietarioDao = new ProprietarioDAO();
        inquilinoDao = new InquilinoDAO();
    }

    // qui passo tipo utente come parametro per decidere se è un proprietario o un inquilino
    public void aggiungiUtente(Proprietario p) {
        proprietario = p;           // TODO: capire se serve
        proprietarioDao.aggiungiUtente(p);
    }

    public boolean emailDisponibile(String email) {
        return proprietarioDao.emailDisponibile(email);
    }

    public void aggiungiInquilino(Inquilino inquilino) {
        inquilinoDao.aggiungiInquilino(inquilino);
    }

    public boolean isInquilino(String email, String password) {
        return false;       // TODO: implementare
    }

    public boolean isProprietario(String email, String password) {
        return false;       // TODO: implementare
    }

    public String getCognomeProprietario() {
        return proprietario.getCognome();        // TODO: implementare
    }

    public String getNomeProprietario() {
        return proprietario.getNome();        // TODO: implementare
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

    public void setProprietario(String email, String password) {
        proprietario = proprietarioDao.getUtente(email, password);
    }

    public boolean utenteValido(String email, String password) {
        return proprietarioDao.verificaUtente(email, password);
    }

}
