package controller;


import dao.*;
import model.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class Controller {
    private ContrattoDAO contrattoDao;
    private ProprietarioDAO proprietarioDao;
    private InquilinoDAO inquilinoDao;
    private ImmobileDAO immobileDao;
    private Proprietario proprietario = null;

    public Controller() {
        proprietarioDao = new ProprietarioDAO();
        inquilinoDao = new InquilinoDAO();
        immobileDao = new ImmobileDAO();
        contrattoDao = new ContrattoDAO();
    }

    public void aggiungiUtente(Proprietario proprietario) {
        proprietarioDao.aggiungiUtente(proprietario);
    }

    public void aggiungiContratto(Contratto contratto) {
        LocalDate oggi = LocalDate.now();
        String prossimoPagamento;
        if(oggi.getDayOfMonth() > Integer.parseInt(contratto.getProssimoPagamento())) {
            oggi = oggi.plusMonths(1);
        }
        prossimoPagamento = oggi.getYear() + "-" + oggi.getMonthValue() + "-" + contratto.getProssimoPagamento();
        contratto.setProssimoPagamento(prossimoPagamento);
        contrattoDao.aggiungiContratto(contratto);
        aggiorna();
    }

    public boolean emailDisponibile(String email) {
        return proprietarioDao.emailDisponibile(email);
    }

    public void aggiungiInquilino(Inquilino inquilino) {
        inquilinoDao.aggiungiInquilino(inquilino, proprietario.getCf());
        aggiorna();
    }

    public String getCfProprietario() {
        return proprietario.getCf();
    }

    public void visualizzaImmobili() {
        immobileDao.visualizzaImmobili(proprietario.getCf());
    }

    public void setProprietario(String email, String password) {
        proprietario = proprietarioDao.getUtente(email, password);
    }

    public Proprietario getProprietario() {
        return proprietario;
    }

    public boolean isUtente(String email, String password) {
        return proprietarioDao.verificaUtente(email, password);
    }

    public void visualizzaInquilini() {
        aggiorna();
        inquilinoDao.visualizzaInquilini(proprietario.getCf());
    }

    public void visualizzaContratti() {
        aggiorna();
        contrattoDao.visualizzaContratti(proprietario.getCf());
    }

    public void rimuoviInquilino(String idInquilino) {
        try {
            inquilinoDao.rimuoviInquilino(Integer.parseInt(idInquilino), proprietario.getCf());
            aggiorna();
        } catch (NumberFormatException e) {
            System.out.println("L'ID inserito non è un valore valido.");            // TODO: aggiunta eccezione, vediamo se serve da altre parti
        }
    }

    public void aggiungiImmobile(Immobile immobile) {
        immobile.setIdProprietario(proprietario.getCf());
        immobileDao.aggiungiImmobile(immobile);
        aggiorna();
    }

    public boolean isInquilino(String idInquilino) {
        try {
            return inquilinoDao.verificaInquilino(Integer.parseInt(idInquilino), proprietario.getCf());
        }catch (NumberFormatException e) {
            System.out.println("L'ID inserito non è un valore valido.");            // TODO: aggiunta eccezione, vediamo se serve da altre parti
        }
        return false;
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
        try {
            immobileDao.rimuoviImmobile(Integer.parseInt(idImmobile), proprietario.getCf());
            aggiorna();
        } catch (NumberFormatException e) {
            System.out.println("L'ID inserito non è un valore valido.");            // TODO: aggiunta eccezione, vediamo se serve da altre parti
        }
    }

    public Inquilino getInquilino(String idInquilino) {
        try {
            return inquilinoDao.getInquilino(Integer.parseInt(idInquilino), proprietario.getCf());
        }catch (NumberFormatException e) {
            System.out.println("L'ID inserito non è un valore valido.");            // TODO: aggiunta eccezione, vediamo se serve da altre parti
        }
        return null;
    }

    public void rimuoviContratto(String idContratto) {
        try {
            contrattoDao.rimuoviContratto(Integer.parseInt(idContratto), proprietario.getCf());
            aggiorna();
        }catch (NumberFormatException e) {
            System.out.println("L'ID inserito non è un valore valido.");            // TODO: aggiunta eccezione, vediamo se serve da altre parti
        }
    }

    public boolean isContratto(String idContratto) {
        try {
            return contrattoDao.verificaContratto(Integer.parseInt(idContratto), proprietario.getCf());
        } catch (NumberFormatException e) {
            System.out.println("L'ID inserito non è un valore valido.");            // TODO: aggiunta eccezione, vediamo se serve da altre parti
        }
        return false;
    }

    public void aggiungiPagamento(String idInquilino, String pagamento) {
        try {
            inquilinoDao.aggiungiPagamento(Integer.parseInt(idInquilino), Float.parseFloat(pagamento), proprietario.getCf());
            aggiorna();
        }catch (NumberFormatException e) {
            System.out.println("Valore non valido.");            // TODO: aggiunta eccezione, vediamo se serve da altre parti
        }
    }

    // serve ad aggiornare la logica dei pagamenti
    public void aggiorna() {
        contrattoDao.aggiornaDatePagamento(proprietario.getCf());
        inquilinoDao.aggiornaFinanze(proprietario.getCf());
    }

    // serve per resettare il controller quando si esce dal menu facade
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
