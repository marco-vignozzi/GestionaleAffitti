package controller;


import dao.*;
import model.Contratto;
import model.Immobile;
import model.Inquilino;
import model.Proprietario;


public class Controller {
    private ContrattoDAO contrattoDao;
    private ProprietarioDAO proprietarioDao;
    private InquilinoDAO inquilinoDao;
    private ImmobileDAO immobileDao;
    private Proprietario proprietario;

    public Controller() {
        proprietarioDao = new ProprietarioDAO();
        inquilinoDao = new InquilinoDAO();
        immobileDao = new ImmobileDAO();
        contrattoDao = new ContrattoDAO();
    }

    // qui passo tipo utente come parametro per decidere se è un proprietario o un inquilino
    public void aggiungiUtente(Proprietario p) {
        proprietarioDao.aggiungiUtente(p);
    }

    public void aggiungiContratto(Contratto c) {
        contrattoDao.aggiungiContratto(c);
    }

    public boolean emailDisponibile(String email) {
        return proprietarioDao.emailDisponibile(email);
    }

    public void aggiungiInquilino(Inquilino inquilino) {
        inquilinoDao.aggiungiInquilino(inquilino, proprietario.getCf());
    }

    public String getCfProprietario() {
        return proprietario.getCf();
    }

    // questo metodo permette di visualizzare tutti gli immobili
    public void visualizzaImmobili() {
        immobileDao.visualizzaImmobili(proprietario.getCf());
    }

    public void setProprietario(String email, String password) {
        proprietario = proprietarioDao.getUtente(email, password);
    }

    public boolean isUtente(String email, String password) {
        return proprietarioDao.verificaUtente(email, password);
    }

    public void visualizzaInquilini() {
        inquilinoDao.visualizzaInquilini(proprietario.getCf());
    }

    public void visualizzaContratti() {
        contrattoDao.visualizzaContratti(proprietario.getCf());
    }

    public void rimuoviInquilino(String idInquilino) {
        inquilinoDao.rimuoviInquilino(Integer.parseInt(idInquilino), proprietario.getCf());
    }

    public void aggiungiImmobile(Immobile immobile) {
        immobile.setIdProprietario(proprietario.getCf());
        immobileDao.aggiungiImmobile(immobile);
    }

    public boolean isInquilino(String idInquilino) {
        return inquilinoDao.verificaInquilino(Integer.parseInt(idInquilino), proprietario.getCf());
    }

    public boolean isImmobile(String idImmobile) {
        try {
            return immobileDao.verificaImmobile(Integer.parseInt(idImmobile), proprietario.getCf());
        }catch (NumberFormatException e) {
            System.out.println("L'ID inserito non è un valore valido.");            // TODO: aggiunta eccezione, vediamo se serve da altre parti
        }
        return false;
    }

    public void rimuoviImmobile(String idImmobile) {
        immobileDao.rimuoviImmobile(Integer.parseInt(idImmobile), proprietario.getCf());
    }

    // serve per resettare il controller quando si esce dal menu utente, per evitare di mantenere i dati dell'utente
    // acceduto in precedenza
    public void reset() {
        this.contrattoDao.tabella.dispose();
        this.immobileDao.tabella.dispose();
        this.inquilinoDao.tabella.dispose();

        this.immobileDao = new ImmobileDAO();
        this.inquilinoDao = new InquilinoDAO();
        this.contrattoDao = new ContrattoDAO();
        this.proprietarioDao = new ProprietarioDAO();
        this.proprietario = null;
    }

}
