package controller;


import dao.*;
import mail_service.JavaMailUtil;
import model.*;

import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;


public class Controller {
    private ContrattoDAO contrattoDao;
    private ProprietarioDAO proprietarioDao;
    private InquilinoDAO inquilinoDao;
    private ImmobileDAO immobileDao;
    private Proprietario proprietario = null;
    private String testoSollecito = "Salve {0} {1},\nla presente lettera è per ricordarle gentilmente che il pagamento " +
            "del suo debito è ancora in sospeso. L''importo totale del debito, che la prego di estinguere al più presto, " +
            "è di {2, number, currency}.\nCordiali saluti,\n{3} {4}";

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
        try {
            LocalDate oggi = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String prossimoPagamento;
//            if (oggi.getDayOfMonth() > Integer.parseInt(contratto.getProssimoPagamento())) {
//                oggi = oggi.plusMonths(1);
//            }
            if (!oggi.isAfter(LocalDate.parse(contratto.getDataFine(), formatter))) {
                prossimoPagamento = oggi.getYear() + "-" + oggi.getMonthValue() + "-" + contratto.getProssimoPagamento();
                contratto.setProssimoPagamento(prossimoPagamento);
            } else {
                contratto.setProssimoPagamento("");
            }
        }catch (DateTimeParseException | NumberFormatException e) {
            System.out.println("ATTENZIONE! sono stati inseriti valori non validi per le date");
            System.out.println("Per poter utilizzare tutte le funzionalità del programma " +
                    "è necessario modificarle dal menu gestione contratti");
        }
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
        aggiorna();
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
            System.out.println("L'ID inserito non è un valore valido");            // TODO: aggiunta eccezione, vediamo se serve da altre parti
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
        } catch (NumberFormatException e) {
            System.out.println("L'ID inserito non è un valore valido");            // TODO: aggiunta eccezione, vediamo se serve da altre parti
        }
        return false;
    }

    public boolean isImmobile(String idImmobile) {
        try {
            return immobileDao.verificaImmobile(Integer.parseInt(idImmobile), proprietario.getCf());
        } catch (NumberFormatException e) {
            System.out.println("L'ID inserito non è un valore valido");            // TODO: aggiunta eccezione, vediamo se serve da altre parti
        }
        return false;
    }

    public void rimuoviImmobile(String idImmobile) {
        try {
            immobileDao.rimuoviImmobile(Integer.parseInt(idImmobile), proprietario.getCf());
            aggiorna();
        } catch (NumberFormatException e) {
            System.out.println("L'ID inserito non è un valore valido");            // TODO: aggiunta eccezione, vediamo se serve da altre parti
        }
    }

    public Inquilino getInquilino(String idInquilino) {
        try {
            return inquilinoDao.getInquilino(Integer.parseInt(idInquilino), proprietario.getCf());
        } catch (NumberFormatException e) {
            System.out.println("L'ID inserito non è un valore valido");            // TODO: aggiunta eccezione, vediamo se serve da altre parti
        }
        return null;
    }

    public void rimuoviContratto(String idContratto) {
        try {
            contrattoDao.rimuoviContratto(Integer.parseInt(idContratto), proprietario.getCf());
            aggiorna();
        } catch (NumberFormatException e) {
            System.out.println("L'ID inserito non è un valore valido");            // TODO: aggiunta eccezione, vediamo se serve da altre parti
        }
    }

    public boolean isContratto(String idContratto) {
        try {
            return contrattoDao.verificaContratto(Integer.parseInt(idContratto), proprietario.getCf());
        } catch (NumberFormatException e) {
            System.out.println("L'ID inserito non è un valore valido");            // TODO: aggiunta eccezione, vediamo se serve da altre parti
        }
        return false;
    }

    public void aggiungiPagamento(String idInquilino, String pagamento) {
        try {
            inquilinoDao.aggiungiPagamento(Integer.parseInt(idInquilino), Float.parseFloat(pagamento), proprietario.getCf());
            aggiorna();
            System.out.println("Pagamento salvato");
        } catch (NumberFormatException e) {
            System.out.println("Valore in denaro non valido");            // TODO: aggiunta eccezione, vediamo se serve da altre parti
        }
    }

    public void aggiungiSpesa(String idInquilino, String spesa) {
        try {
            inquilinoDao.aggiungiSpesa(Integer.parseInt(idInquilino), Float.parseFloat(spesa), proprietario.getCf());
        }catch (NumberFormatException e) {
            System.out.println("Valore in denaro non valido");
        }
    }

    public void visualizzaResoconto() {
        proprietarioDao.visualizzaResoconto(proprietario.getCf());
    }

    public void inviaSollecito(Inquilino[] inquilini) {
        JavaMailUtil mailService = new JavaMailUtil(proprietario.getEmail());
        for(int i=0; i < inquilini.length; i++) {
            Object[] argomenti = {inquilini[i].getNome(), inquilini[i].getCognome(),
                    inquilini[i].getTotaleDovuto()-inquilini[i].getTotalePagato(),
                    proprietario.getNome(), proprietario.getCognome()};

            System.out.println("Invio email di sollecito all'inquilino con ID " + inquilini[i].getID() + " in corso...");
            mailService.send(inquilini[i].getEmail(), "Sollecito di pagamento", MessageFormat.format(testoSollecito, argomenti));
        }
    }

    public Inquilino[] getInquiliniSollecito() {
        return inquilinoDao.getInquiliniSollecito(proprietario.getCf());
    }

    // serve ad aggiornare la logica dei pagamenti
    public void aggiorna() {
        try {
            contrattoDao.aggiornaDatePagamento(proprietario.getCf());
            immobileDao.aggiornaAffittato(proprietario.getCf());
            inquilinoDao.aggiornaFinanze(proprietario.getCf());
        }catch (DateTimeParseException | NumberFormatException e) {
            System.out.println(e);
            System.out.println("ATTENZIONE! nel database sono presenti date in un formato non riconosciuto");
            System.out.println("Se non si effettuano le dovute modifiche alla tabella contratti il programma " +
                    "potrebbe non funzionare correttamente");
            System.out.println("(è possibile modificare le date dei contratti dal menu gestione contratti)");
        }
    }

    // serve per resettare il controller quando si esce dal menu facade
    public void reset() {
        this.contrattoDao.tabella.dispose();
        this.immobileDao.tabella.dispose();
        this.inquilinoDao.tabella.dispose();
        this.proprietarioDao.tabella.dispose();

        this.immobileDao = new ImmobileDAO();
        this.inquilinoDao = new InquilinoDAO();
        this.contrattoDao = new ContrattoDAO();
        this.proprietarioDao = new ProprietarioDAO();
        this.proprietario = null;
    }

}
