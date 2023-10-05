package controller;


import dao.ContrattoDAO;
import dao.ImmobileDAO;
import dao.InquilinoDAO;
import dao.ProprietarioDAO;
import model.Contratto;
import model.Immobile;
import model.Inquilino;
import model.Proprietario;

import java.util.Scanner;

public class Controller {
    private Scanner scanner;            // TODO: capire se serve
    private ContrattoDAO contrattoDao;
    private ProprietarioDAO proprietarioDao;
    private InquilinoDAO inquilinoDao;
    private Proprietario proprietario;
    private ImmobileDAO immobileDAO;


    public Controller() {
        proprietarioDao = new ProprietarioDAO();
        inquilinoDao = new InquilinoDAO();
        immobileDAO = new ImmobileDAO();
        contrattoDao = new ContrattoDAO();
    }

    // qui passo tipo utente come parametro per decidere se è un proprietario o un inquilino
    public void aggiungiUtente(Proprietario p) {
        proprietario = p;           // TODO: capire se serve
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

    public String getCognomeProprietario() {
        return proprietario.getCognome();
    }

    public String getNomeProprietario() {
        return proprietario.getNome();
    }

    public String getCfProprietario() {
        return proprietario.getCf();
    }

    // questo metodo permette di visualizzare tutti gli immobili
    public void visualizzaImmobili() {
        immobileDAO.visualizzaImmobili(proprietario.getCf());
    }

    public void setProprietario(String email, String password) {
        proprietario = proprietarioDao.getUtente(email, password);
    }

    public boolean utenteValido(String email, String password) {
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
        immobileDAO.aggiungiImmobile(immobile);
    }

    public boolean isImmobile(int idImmobile) {
        return immobileDAO.verificaImmobile(idImmobile, proprietario.getCf());
    }
}
