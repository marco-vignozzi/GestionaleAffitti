package controller;


import dao.*;
import mail_service.JavaMailUtil;
import model.*;

import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


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

    public void aggiungiInquilino(Inquilino inquilino) {
        inquilinoDao.aggiungiInquilino(inquilino, proprietario.getCf());
        aggiorna();
    }

    public void aggiungiImmobile(Immobile immobile) {
        immobileDao.aggiungiImmobile(immobile, proprietario.getCf());
        List<Immobile> immobili = immobileDao.getAllImmobili(proprietario.getCf());
        for(Immobile i: immobili) {
            if(i.getComune() == immobile.getComune() && i.getIndirizzo() == immobile.getIndirizzo() &&
                    i.getnCivico() == immobile.getnCivico() && i.getSubalterno() == immobile.getSubalterno()) {
                immobile.setId(i.getId());
            }
        }
        aggiorna();
    }

    public List<Immobile> getAllImmobili() {
        aggiorna();
        return immobileDao.getAllImmobili(proprietario.getCf());
    }

    public List<Inquilino> getAllInquilini() {
        aggiorna();
        return inquilinoDao.getAllInquilini(proprietario.getCf());
    }

    public List<Inquilino> getInquiliniSollecito() {
        List<Inquilino> inquilini = getAllInquilini();
        List<Inquilino> inquiliniSollecito = new ArrayList<>();

        for (Inquilino i :inquilini) {
            if (i.getTotaleDovuto() > i.getTotalePagato()) {
                inquiliniSollecito.add(i);
            }
        }
        return inquiliniSollecito;
    }

    public Inquilino getInquilino(String idInquilino) {
        try {
            return inquilinoDao.getInquilino(Integer.parseInt(idInquilino), proprietario.getCf());
        } catch (NumberFormatException e) {
            System.out.println("L'ID inserito non è un valore valido");
        }
        return null;
    }

    public List<Contratto> getAllContratti() {
        aggiorna();
        return contrattoDao.getAllContratti(proprietario.getCf());
    }

    public List<Resoconto> getResoconti() {
        aggiorna();
        return proprietarioDao.getResoconti(proprietario.getCf());
    }

    public boolean isUtente(String email, String password) {
        List<Proprietario> proprietari = proprietarioDao.getAllUtenti();
        for(Proprietario p: proprietari) {
            if(Objects.equals(p.getEmail(), email) && Objects.equals(p.getPassword(), password)) {
                return true;
            }
        }
        return false;
    }

    public boolean isInquilino(String idInquilino) {
        try {
            return inquilinoDao.getInquilino(Integer.parseInt(idInquilino), proprietario.getCf()) != null;
        } catch (NumberFormatException e) {
            System.out.println("L'ID inserito non è un valore valido");
        }
        return false;
    }

    public boolean isImmobile(String idImmobile) {
        try {
            return immobileDao.getImmobile(Integer.parseInt(idImmobile), proprietario.getCf()) != null;
        } catch (NumberFormatException e) {
            System.out.println("L'ID inserito non è un valore valido");
        }
        return false;
    }

    public boolean isAffittato(String idImmobile) {
        try {
            return immobileDao.getImmobile(Integer.parseInt(idImmobile), proprietario.getCf()).isAffittato();
        } catch (NumberFormatException e) {
            System.out.println("L'ID immobile non è valido");
        }
        return false;
    }

    public boolean isContratto(String idContratto) {
        try {
            return contrattoDao.getContratto(Integer.parseInt(idContratto), proprietario.getCf()) != null;
        } catch (NumberFormatException e) {
            System.out.println("L'ID inserito non è un valore valido");
        }
        return false;
    }

    public void rimuoviInquilino(String idInquilino) {
        try {
            inquilinoDao.rimuoviInquilino(Integer.parseInt(idInquilino), proprietario.getCf());
            aggiorna();
        } catch (NumberFormatException e) {
            System.out.println("L'ID inserito non è un valore valido");
        }
    }

    public void rimuoviImmobile(String idImmobile) {
        try {
            immobileDao.rimuoviImmobile(Integer.parseInt(idImmobile), proprietario.getCf());
            aggiorna();
        } catch (NumberFormatException e) {
            System.out.println("L'ID inserito non è un valore valido");
        }
    }

    public void rimuoviContratto(String idContratto) {
        try {
            contrattoDao.rimuoviContratto(Integer.parseInt(idContratto), proprietario.getCf());
            aggiorna();
        } catch (NumberFormatException e) {
            System.out.println("L'ID inserito non è un valore valido");
        }
    }

    public void modificaContratto(String idContratto, Contratto contratto) {
        contrattoDao.updateContratto(Integer.parseInt(idContratto), contratto, proprietario.getCf());
        aggiorna();
    }

    public void modificaImmobile(String idImmobile, Immobile immobile) {
        immobileDao.updateImmobile(Integer.parseInt(idImmobile), immobile);
        aggiorna();
    }

    public void modificaInquilino(String idInquilino, Inquilino inquilino) {
        inquilinoDao.updateInquilino(Integer.parseInt(idInquilino), inquilino);
        aggiorna();
    }

    public void aggiungiPagamento(String idInquilino, String pagamento) {
        try {
            Inquilino inquilino = inquilinoDao.getInquilino(Integer.parseInt(idInquilino), proprietario.getCf());
            inquilino.setTotalePagato(inquilino.getTotalePagato() + Float.parseFloat(pagamento));
            inquilinoDao.updateInquilino(Integer.parseInt(idInquilino), inquilino);

            System.out.println("Pagamento salvato");
        } catch (NumberFormatException e) {
            System.out.println("Valore in denaro non valido");
        }
    }

    public void aggiungiSpesa(String idInquilino, String spesa) {
        try {
            Inquilino inquilino = inquilinoDao.getInquilino(Integer.parseInt(idInquilino), proprietario.getCf());
            inquilino.setTotaleDovuto(inquilino.getTotaleDovuto() + Float.parseFloat(spesa));
            inquilinoDao.updateInquilino(Integer.parseInt(idInquilino), inquilino);

            System.out.println("Spesa salvata");
        }catch (NumberFormatException e) {
            System.out.println("Valore in denaro non valido");
        }
    }

    public void inviaSollecito(List<Inquilino> inquilini) {
        JavaMailUtil mailService = new JavaMailUtil(proprietario.getEmail());
        for (Inquilino i: inquilini) {
            Object[] argomenti = {i.getNome(), i.getCognome(),
                    i.getTotaleDovuto()-i.getTotalePagato(),
                    proprietario.getNome(), proprietario.getCognome()};

            System.out.println("Invio email di sollecito all'inquilino con ID " + i.getID() + " in corso...");
            mailService.send(i.getEmail(), "Sollecito di pagamento", MessageFormat.format(testoSollecito, argomenti));
        }
    }

    public boolean emailDisponibile(String email) {
        List<Proprietario> proprietari = proprietarioDao.getAllUtenti();
        for(Proprietario p: proprietari) {
            if(Objects.equals(p.getEmail(), email)) {
                return false;
            }
        }
        return true;
    }

    public void setProprietario(String email, String password) {
        proprietario = proprietarioDao.getUtente(email, password);
    }

    public Proprietario getProprietario() {
        return proprietario;
    }

    public String getCfProprietario() {
        return proprietario.getCf();
    }


    // serve ad aggiornare la logica dei pagamenti
    public void aggiorna() {
        try {
//            contrattoDao.aggiornaDatePagamento(proprietario.getCf());
//            immobileDao.aggiornaAffittato(proprietario.getCf());
            List<Immobile> immobili = immobileDao.getAllImmobili(proprietario.getCf());
            List<Contratto> contratti = contrattoDao.getAllContratti(proprietario.getCf());
            List<Inquilino> inquilini = inquilinoDao.getAllInquilini(proprietario.getCf());

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate adesso = LocalDate.now();

            // aggiorna le date di pagamento e il totale dovuto degli inquilini
            for(Contratto c: contratti) {
                if(!c.getProssimoPagamento().isEmpty()) {
                    LocalDate prossimoPagamento = LocalDate.parse(c.getProssimoPagamento(), formatter);
                    if (!adesso.isAfter(LocalDate.parse(c.getDataFine(), formatter)) && adesso.isAfter(prossimoPagamento)) {
                        prossimoPagamento = prossimoPagamento.plusMonths(1);
                        c.setProssimoPagamento(prossimoPagamento.toString());
                        // se aggiorno il prossimo pagamento aggiungo il canone al totale dovuto dall'inquilino
                        for (Inquilino i : inquilini) {
                            if (c.getCfInquilino() == i.getCf()) {
                                i.setTotaleDovuto(i.getTotaleDovuto() + c.getCanone());
                                inquilinoDao.updateInquilino(i.getID(), i);
                            }
                        }
                    } else if (adesso.isAfter(LocalDate.parse(c.getDataFine(), formatter))) {
                        c.setProssimoPagamento("");
                    }
                    // l'update lo fo solo se ho modificato qualcosa e quindi se prossimo pagamento non è null
                    contrattoDao.updateContratto(c.getID(), c, proprietario.getCf());
                }
            }

            // aggiorna il campo affittato degli immobili
            for(Immobile i : immobili) {
                boolean affittato = false;
                for(Contratto c : contratti) {
                    if(i.getId() == c.getIdImmobile() && !adesso.isAfter(LocalDate.parse(c.getDataFine(), formatter))) {
                        affittato = true;
                    }
                }
                i.setAffittato(affittato);
                immobileDao.updateImmobile(i.getId(), i);
            }


//            inquilinoDao.aggiornaFinanze(proprietario.getCf());
        }catch (DateTimeParseException | NumberFormatException e) {
            System.out.println(e);
            System.out.println("ATTENZIONE! nel database sono presenti date in un formato non riconosciuto (formato valido: YYYY-MM-DD)");
            System.out.println("Se non si effettuano le dovute modifiche alla tabella contratti il programma " +
                    "potrebbe non funzionare correttamente");
            System.out.println("(è possibile modificare le date dei contratti dal menu gestione contratti)");
        }
    }

    // serve per resettare il controller quando si esce dal menu facade
    public void reset() {

        this.immobileDao = new ImmobileDAO();
        this.inquilinoDao = new InquilinoDAO();
        this.contrattoDao = new ContrattoDAO();
        this.proprietarioDao = new ProprietarioDAO();
        this.proprietario = null;
    }

}
